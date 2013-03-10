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
      System.out.printf("Found file %s", fileName);

      BufferedReader rawData=new BufferedReader(new FileReader(fileName));
      PrintWriter fitFout= new PrintWriter("data_"+fileName);
      PrintWriter residualsFout= new PrintWriter("residuals_"+fileName);

  
      Scanner scanData = new Scanner(rawData);     
    //System.out.printf("\nPlease type in the next file of the file you wish to open : ");
    //String aveFileName = IOUtil.typedInput();
    
      String samplesFileB = "errorB.txt";
      String samplesFileF = "errorF.txt";
    //System.out.printf("Found file %s", aveFileName);

      BufferedReader rawB=new BufferedReader(new FileReader(samplesFileB));
      BufferedReader rawF=new BufferedReader(new FileReader(samplesFileF));
      
      Scanner scanB= new Scanner(rawB); 
      Scanner scanF= new Scanner(rawF); 
      handleData(fitFout,residualsFout,scanData,scanB,scanF);

    }
    public static void handleData(PrintWriter fitFout,PrintWriter residualsFout, Scanner scanData, Scanner scanBAve, Scanner scanFAve){
       
			//will skip over commas and letters but won't read the numbers unless there is a space.
      int max=IOUtil.skipToInt(scanData);
      double data[][] = new double[max][2];
        
      //set this to 1 if you don't wish to change data parameter    
      double paramB=1.0/1;
      double paramF=1.0/1;
      
      data = PlotUtil.data(data,scanData);
               			
      //Read elements from file
      //Initialise arrays for experimental data by reading X and Y data from the files
     	data = PlotUtil.param(data,paramB,0);
     	data = PlotUtil.param(data,paramF,1);
      
     	double xMean= StatsUtil.mean(PlotUtil.x(data));
     	double yMean= StatsUtil.mean(PlotUtil.y(data));
     	
     	
     	double xVar= StatsUtil.variance(xMean,PlotUtil.x(data));
     	double yVar= StatsUtil.variance(yMean,PlotUtil.y(data));
     	double covariance= StatsUtil.covariance(xMean,yMean,data);

			double gradient = StatsUtil.gradient(covariance,xVar);//linear correlation coefficient
			double offset = StatsUtil.yIntercept(xMean,yMean, gradient);

			double[] fit = PlotUtil.param(StatsUtil.fit(data, gradient, offset),paramF);


      double[] xResiduals = StatsUtil.residuals(PlotUtil.x(data), fit);	
      double[] yResiduals = StatsUtil.residuals(PlotUtil.y(data), fit);
      
      double ssr = StatsUtil.ssr(fit, yMean);	
		  double rss = StatsUtil.rss(data,fit);//Standard error of mean 	  
		 	double linearCorrelationCoefficient = StatsUtil.linearCC(ssr,yVar);
			double errorGradient = StatsUtil.errorGradient(xVar,rss, data.length);
			double errorOffset = StatsUtil.errorOffset(data.length, xVar, xMean, rss);
			
			System.out.println();
			System.out.printf("\nLength of data = %2.5f  ",(float) data.length);
			System.out.printf("\nSum of squares of residuals = %2.5f  ", rss);
			System.out.printf("\nError in Y = %2.5f  ", Math.sqrt(rss/data.length));
			System.out.printf("\nGradient= %2.5f with error +/-  %2.5f ", gradient, errorGradient);
			System.out.printf("\nOffset = %g with error  +/-  %g ", offset, errorOffset);		
			System.out.printf("\nLinear Correlation Coefficient %g \n", linearCorrelationCoefficient);			

			PlotUtil.writeXYwithErrorsAndFit(PlotUtil.x(data), PlotUtil.y(data),fit, 0.000001, Math.sqrt(rss/data.length), fitFout);
   		fitFout.close();
	
   		PlotUtil.writeXYwithError(PlotUtil.x(data), yResiduals, Math.sqrt(rss/data.length),residualsFout);
   	  residualsFout.close();
    }
}
