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

	private double[] x, y, z;
	private double [][] data;
	
	public PlotUtil(double[][] data){
		this.data=data;
	
		x = new double[data.length];
		y = new double[data.length];
		z = new double[data.length];
	} 
  //Returns the x component of a 2D array
  public double[] x(){


      for (int i=0;i<data.length;i++){
          x[i]= data[i][0];
      }
      return x;
  }
  //Returns the y component of a 2D array
  public double[] y(){
      for (int i=0;i<data.length;i++){
          y[i]=data[i][1];
      }
      return y;
  }
  //Returns the y component of a 2D array
  public double[] z(){
  
    for (int i=0;i<data.length;i++){
      z[i]=data[i][2];
    }
    return z;
  }
	public static double[][] removeOffset(double[][] data, double offset){

		for(int i=data.length-1; i>=0; i--) data[i][0] +=-offset;
			return data;
	}
}
