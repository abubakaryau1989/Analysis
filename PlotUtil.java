/*
    This is a utility for plotting
    For reading and writing files so they can be plotted using pgfplots or similar
    Composed entirely of static methods
    
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

import java.io.PrintWriter;
import java.util.Scanner;

public class PlotUtil{

    //Initialise the array with the values from a file
    public static double[][] data2D(Scanner scan, int length){
    			double[][] data = new double[length][2];
        for (int i=0;i<data.length;i++){
            for (int j=0;j<data[0].length;j++){
                data[i][j] = (float) IOUtil.skipToDouble(scan);
            }
        }
        return data;
    }
    public static double[][] param2D(double[][] data,double param, int index){
        for (int i=0;i<data.length;i++){
          for (int j=0;j<data[0].length;j++){
          		if(j!=1){
               data[i][j] = data[i][j] * param;              

          		}
          }
        }
        return data;
    }
    public static double[] data1D(Scanner scan, int length){
    			double[] data=new double[length];
        for (int i=0;i<data.length;i++){
            data[i] = IOUtil.skipToDouble(scan);
        }
        return data;
    }
    //Returns the x component of a 2D array
    public static double[] x(double[][] data){
        double[] x = new double[data.length];

        for (int i=0;i<data.length;i++){
            x[i]= data[i][0];
        }
        return x;
    }
    //Returns the y component of a 2D array
    public static double[] y(double[][] data){
        double[] y = new double[data.length];

        for (int i=0;i<data.length;i++){
            y[i]=data[i][1];
        }
        return y;
    }
    public static void writeXY(double[] x, double[] y, PrintWriter fileOut){
        for(int i=0; i<y.length;i++){
            fileOut.printf("%2.5f %2.5f", x[i],y[i]);
            fileOut.println();
        }
    }
        //Will write to file all needed columns 
    public static void writeXYwithError(double[] x, double[] y, double[] error, PrintWriter fileOut){
        for(int i=0; i<x.length;i++){
            fileOut.printf("%2.5f %2.5f %2.5f", x[i], y[i], error[i]);
            fileOut.println();
        }
    }
    public static double[] parameter(double[] data, double relativeError){
    			double[] errorTemp= new double[data.length];
        for(int i=0; i<data.length;i++){
        			errorTemp[i]= data[i] * relativeError;
    			}    					
      		return errorTemp;
    }
    //Will write to file all needed columns 
   public static void writeXYwithErrorsAndFit(double[] x, double[] y, double[] fit, double xError, double yError, PrintWriter fileOut){
        for(int i=0; i<y.length;i++){
            fileOut.printf("%2.5f %2.5f %2.5f %2.5f %2.5f", x[i], y[i], fit[i], xError,yError);
            fileOut.println();
        }
    }
    public static void calibratedXY(double[] x, double[] y, PrintWriter fileOut, double[] amount){
        double[] calibrate = new double[x.length];
        for(int i=0; i<y.length;i++){
            System.out.println(amount[i]);
            calibrate[i]+= x[i]-amount[i];

            fileOut.printf("%2.2f %2.2f",calibrate[i] ,y[i]);
            fileOut.println();
        }
    }
    public static void peaksUp(double[][] data, PrintWriter upFile, double minValue){
    
        double largeUp=0.0;

        for(int i=0; i<data.length-1; i++){

            //Check that there has been an initial increase
            if(data[i][1]>data[0][1]){

                //if so check that it's significant
                if(data[i][1]>largeUp && data[i][1] > minValue){
                
                    largeUp = data[i][1];
                    System.out.printf("%2.2f ",data[i][0]);
                    upFile.printf("%2.2f %2.2f ",data[i][0], largeUp);
                    upFile.println();
                }
            }
        }
        System.out.println();
    }

    public static void peaks(double[][] data, PrintWriter peakFile, double threshold){
        double peaks=0.0;

        //loop through data
        for(int i=0; i<data.length; i++){

            //Check that there has been an initial increase and that it is bigger than minvalue
           if(data[i][1]>data[0][1] && data[i][1]<threshold){

                //if so check that it's significant  enough to bother
                if(data[i][1]>peaks){
                    peaks = data[i][1];
                    peakFile.printf("%2.2f %2.2f ",data[i][0], peaks);
                    peakFile.println();
                }
        //    }
            //When the peak has reached its highest point
            else if(data[i][1]<peaks){
                //reset
                peaks=0;
            }
        }
    }
  }
  public static double[][] removeOffset(double[][] data, double offset){
  
      		for(int i=data.length-1; i>=0; i--) data[i][0] +=-offset;
      
      		return data;
  }
 }
