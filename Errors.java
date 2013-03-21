/*
 
		Errors.java 	
		
    This is a part of a program which serves as a utility for data analysis
    of experimental data. Copyright (C) 2013  Magdalen Berns

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

public class Errors{
   public static double absolute(double accepted, double experimental){
   	return Math.abs(experimental - accepted);
  	}
  	public static double relative(double absolute, double accepted){
  		return absolute/accepted;
  	}
  	public static double sumSquareErrors(double[] errors){
  		double sumSquares=0.0;
  	
  		for(int i =0;i< errors.length;i++){
  			sumSquares+=errors[i]*errors[i];
  		
  		}
  		return Math.sqrt(sumSquares);
  	}
  public static double experimental(double a, double deltaA, double b, double deltaB){
  	  
    		System.out.println("\nMath.pow(deltaA/a,2) "+Math.pow(deltaA/a,2)); 
			System.out.println("Math.pow(deltaB/b,2) "+Math.pow(deltaB/b,2));
    		System.out.println("error squared "+Math.pow((deltaA/a),2)+Math.pow((deltaB/b),2));
    		System.out.println("error "+Math.sqrt(Math.pow(deltaA/a,2)+Math.pow(deltaB/b,2)));
    
    		return Math.sqrt(Math.pow(deltaA/a,2)+Math.pow(deltaB/b,2));


	}
}
