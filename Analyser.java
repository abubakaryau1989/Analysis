/*
		Analyser.java 
		
    This is a utility for data analysis
    Copyright (C) 2013  Magdalen Berns

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.

 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Analyser {

    public static void main(String[] args) throws IOException {
    			
      	System.out.printf("Please type in the name of the file you wish to open: ");
    		String fileName = IOUtil.typedInput();
      System.out.printf("Found file %s \n", fileName);

      BufferedReader rawData=new BufferedReader(new FileReader(fileName));
      PrintWriter fitFout= new PrintWriter("data_"+fileName);
      PrintWriter residualsFout= new PrintWriter("residuals_"+fileName);

  
      Scanner scanData = new Scanner(rawData);     
    		//System.out.printf("\nPlease type in the next file of the file you wish to open : ");
    		//String aveFileName = IOUtil.typedInput();
    		//System.out.printf("Found file %s", aveFileName);
          
      Scanner scanB= new Scanner(new BufferedReader(new FileReader("errorB.txt"))); 
      Scanner scanF= new Scanner(new BufferedReader(new FileReader("errorF.txt")));    
      Scanner scanMeasured= new Scanner(new BufferedReader(new FileReader("measurements.txt")));
      
      int lengthMeasured = IOUtil.skipToInt(scanMeasured);
      double[] measured = PlotUtil.data1D(scanMeasured,lengthMeasured);
      double meanMeasured =StatsUtil.mean(measured);
      
      //This gives the errors on the measurements taken and should not be combined with anything else here
      double stdDevMeasured = Math.sqrt(StatsUtil.variance(meanMeasured,measured)/lengthMeasured);
      System.out.printf("No of measurements %d", lengthMeasured);
    		System.out.printf("\nMean of measurements %2.3fcm", meanMeasured);
	    	System.out.printf("\nStandard Deviation of measurements %2.3fcm\n", stdDevMeasured);
      
      Scanner scanNonLinear = new Scanner (new BufferedReader(new FileReader("glycerin-02.txt")));
      int lengthNonLinearData = IOUtil.skipToInt(scanNonLinear);
      
      double paramNonLinear=1.0/1.0;
      double[][] nonLinearData = PlotUtil.data2D(scanNonLinear,lengthNonLinearData);

      handleData(fitFout,residualsFout,scanData,scanB,scanF);

    }
    public static void handleData(PrintWriter fitFout,PrintWriter residualsFout, Scanner scanData, Scanner scanBAve, Scanner scanFAve){
       
			//will skip over commas and letters but won't read the numbers unless there is a space.
      int max=IOUtil.skipToInt(scanData);
      double[][] data =  PlotUtil.data2D(scanData, max);
        
      //set this to 1 if you don't wish to change data parameter    
      double paramB=1.0/1.0;
      double paramF=1.0/1.0;  
      
  			int lengthB =  IOUtil.skipToInt(scanBAve);
			double[] b = PlotUtil.data1D(scanBAve,lengthB);
			double meanB=StatsUtil.mean(b);
			double stdDevB=Math.sqrt(StatsUtil.variance(meanB,b)/lengthB);  
 
      //Read elements from file
      //Initialise arrays for experimental data by reading X and Y data from the files
     	data = PlotUtil.param2D(data,paramB,0);
     	data = PlotUtil.param2D(data,paramF,1);
      
     	double xMean= StatsUtil.mean(PlotUtil.x(data));
     	double yMean= StatsUtil.mean(PlotUtil.y(data));
          	
     	double xVar= StatsUtil.variance(xMean,PlotUtil.x(data));
     	double yVar= StatsUtil.variance(yMean,PlotUtil.y(data));
     	double covariance= StatsUtil.covariance(xMean,yMean,data);

			double gradient = StatsUtil.gradient(covariance,xVar);//linear correlation coefficient
			double offset = StatsUtil.yIntercept(xMean,yMean, gradient);


			double[] fit = PlotUtil.param1D(StatsUtil.fit(data, gradient, offset),paramF);

      double[] yResiduals = StatsUtil.residuals(PlotUtil.y(data), fit);
      
      double ssr = StatsUtil.ssr(fit, yMean);	
		  double rss = StatsUtil.rss(data,fit);//Standard error of mean 	  
		 	double linearCorrelationCoefficient = StatsUtil.linearCC(ssr,yVar);
			double errorGradient = StatsUtil.errorGradient(xVar,rss, data.length);
			double errorOffset = StatsUtil.errorOffset(data.length, xVar, xMean, rss);


			System.out.printf("\nLength of data = %2.0f  ",(float) data.length);
			System.out.printf("\nSum of squares of residuals = %2.5f  ", rss);
			System.out.printf("\nGradient= %2.4f with error +/-  %2.4f ", gradient, errorGradient);
			
			double gyromagnetic = (Math.pow(gradient,-1)*((Math.PI*2)));//TODO remove later 
			double relativeErrorGyro= errorGradient/gradient * 2 * Math.PI;
			
			double litVal = 0.2675;
			double absolute = Errors.absolute(litVal,gyromagnetic);
			double relative = Errors.relative(absolute,litVal);
			
			System.out.printf("\nGyromagnetic ratio= %2.4f M/(sT) literature value = %2.4f M/(sT) and difference %2.4f M/(sT) \nrelative error %2.4f ", gyromagnetic, litVal, absolute,relative);
			System.out.printf("\nResidual sum squares  = %2.2f ", Math.sqrt(rss/data.length));
			System.out.printf("\nOffset = %g with error  +/-  %g ", offset, errorOffset);		
			System.out.printf("\nLinear Correlation Coefficient %g", linearCorrelationCoefficient);
			
			double relativeErrorField=stdDevB/meanB;
			System.out.printf("\nStdDeviation error in field %g mT",stdDevB);

			System.out.printf("\nRelative error in field %g ",relativeErrorField);
			double relativeErrorFrequency = relativeErrorField;
			double[] yError = PlotUtil.param1D(PlotUtil.y(data), relativeErrorFrequency);

			System.out.printf("\nRelative error propergated to frequency %g \n", relativeErrorFrequency);
			PlotUtil.writeXYwithErrorsAndFit(PlotUtil.x(data), PlotUtil.y(data),fit, relativeErrorFrequency,stdDevB, fitFout);
   		fitFout.close();
	
			//writes out residuals plot and switches axis to make B field y 
   		PlotUtil.writeXYwithError(PlotUtil.x(data),yResiduals,stdDevB,residualsFout);
   	  residualsFout.close();
    }
    

}
