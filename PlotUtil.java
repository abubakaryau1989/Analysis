/*
    PlotUtil.java
    =============
    
    This is a utility for plotting
	For handling arrays of data plots  
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
public class PlotUtil{

 
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
    public static double[] parameter(double[] data, double parameter){
    			double[] errorTemp= new double[data.length];
        for(int i=0; i<data.length;i++){
        			errorTemp[i]= data[i] * parameter;
    			}    					
      		return errorTemp;
    }
    public static double[][] removeOffset(double[][] data, double offset){
  
		for(int i=data.length-1; i>=0; i--) data[i][0] +=-offset;
		return data;
    }
    	//Returns y'=mx'+c for x'=x*x y=y
    public static double[][] linearise(double[][] data){
    		double[][] dummy = new double[data.length][2];

        for (int i=0;i<data.length;i++){
        
        		//TODO move 
        		//double sumSq = Math.sqrt(Math.pow(Calculate.divide(xError,data[i][0]),2));//does th error for x and then squares
       	  	dummy[i][1] = data[i][1];
            dummy[i][0] = data[i][0]* data[i][0];
        }
        return dummy;
	}
	
}
