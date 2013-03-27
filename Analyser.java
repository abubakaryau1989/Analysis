/*
		Analyser.java 
		=============
		
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
    			
	String fileName = IOUtil.getFileName();
	Scanner scanData = new Scanner(new BufferedReader(new FileReader(fileName)));
    PrintWriter fitFout = new PrintWriter("data_"+fileName);

      
	int lengthOfScanned1 = IOUtil.skipToInt(scanData);
	double xError=IOUtil.skipToDouble(scanData);
	double yError = IOUtil.skipToDouble(scanData);

  double[][] data = PlotReader.data2Column(scanData,lengthOfScanned1);
 	PlotWriter.errors(PlotUtil.x(data), PlotUtil.y(data), PlotUtil.parameter(PlotUtil.x(data),xError), PlotUtil.parameter(PlotUtil.y(data),yError), fitFout);
	fitFout.close();
     	
     	
    System.out.printf("Would you like to pull a column out? \n"); 
   	String choice1 = IOUtil.getFileName();
    	if(choice1.equals("!")){
    	 	System.out.println("No file selected to write a column to");
    }
    	else{
		PrintWriter nFout = new PrintWriter("S2.txt");	
		PlotWriter.aColumn(PlotUtil.y(data),nFout);
		nFout.close();
    }    
    System.out.printf("Would you like to take the average value of any data file? \n"); 
	String choice2= IOUtil.getFileName();
    	if(choice2.equals("!")){
    	 	System.out.println("No file selected to average.");
 	}
	else{
	
		Scanner scanToGetAverage = new Scanner(new BufferedReader(new FileReader(IOUtil.getFileName())));
		int lengthOfScanned2 = IOUtil.skipToInt(scanToGetAverage);
		double[] averageThisData = PlotReader.data1Column(scanToGetAverage,lengthOfScanned2);
		double meanValueFromFile = StatsUtil.mean(averageThisData);
     	System.out.printf("Mean value %g \n", meanValueFromFile); 
	}
    	
   	System.out.printf("Would you like to linearise any data file? \n"); 
	String choice3= IOUtil.getFileName();
    	if(choice3.equals("!")){
    	 	System.out.println("No file selected to Linearise.");
   	}
    	else{
	  	Scanner scanToLinear = new Scanner(new BufferedReader(new FileReader(choice3)));
	  	int lengthOfScanned3 = IOUtil.skipToInt(scanToLinear);
		double[][] linearised= PlotUtil.linearise(PlotReader.data3Column(scanToLinear,
                                                                    lengthOfScanned3));
    
		PrintWriter linearFout = new PrintWriter("linear.txt");
    handleData(linearFout,linearised);
    linearFout.close();
		
  	 }
    }   
    public static void handleData(PrintWriter fitFout, double[][] data){

     	double xMean= StatsUtil.mean(PlotUtil.x(data));
     	double yMean= StatsUtil.mean(PlotUtil.y(data));
          	
     	double xVar= StatsUtil.variance(xMean,PlotUtil.x(data));
     	double yVar= StatsUtil.variance(yMean,PlotUtil.y(data));
     	double covariance= StatsUtil.covariance(xMean,yMean,data);

			double gradient = StatsUtil.gradient(covariance,xVar);//linear correlation coefficient
			double offset = StatsUtil.yIntercept(xMean,yMean, gradient);
      double[] fit = StatsUtil.fit(data, gradient, offset);


      double ssr = StatsUtil.ssr(fit, yMean);
		  double rss = StatsUtil.rss(data,fit);//Standard error 	  
		 	double linearCorrelationCoefficient = StatsUtil.linearCC(ssr,yVar);
			double errorGradient = StatsUtil.errorGradient(xVar,rss, data.length);
			double errorOffset = StatsUtil.errorOffset(data.length, xVar, xMean, rss);

			System.out.printf("\nLength of data = %2.0f  ",(float) data.length);
			System.out.printf("\nSum of squares of residuals = %2.4f  ", rss);
			System.out.printf("\nGradient= %2.4f with error +/-  %2.4f ", gradient, errorGradient);
			
			System.out.printf("\nResidual sum squares  = %2.2f ", Math.sqrt(rss/data.length));
			System.out.printf("\nOffset = %g with error  +/-  %g ", offset, errorOffset);		
			System.out.printf("\nLinear Correlation Coefficient %g", linearCorrelationCoefficient);

      PlotWriter.errorsFit(PlotUtil.x(data),PlotUtil.y(data),fit,0.0,0.0,fitFout);
		
		}
}
