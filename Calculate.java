/*
 
	Calculate.java 	
		
    I am sure this has been done more than a million times already but this is my take on it.
    A general purpose class for performing simple mathematical operations.
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

public class Calculate{

	//Instanciate variables to be used by instance methods 
	private double num1, num;
	
	//Initialise variables to be used by instance methods.
	public Calculate(){
		num1=0.0;
		num=0.0;
	} 
	/*
	Static Methods 
	*/
   	public static double subtract(double a, double b){
   		return a - b;	
	}
	public static double add(double a,double b){
  		return a+b;
	}
  	public static double divide(double a, double b){
  		return a/b;
  	}
  	public static double multiply(double a, double b){
  		return a*b;
  	}
  		/*
	Instance Methods 
	*/
	public double subtract(double num2){
   	return num1-num2;	
	}
	public double add(double num2){
  	 	return num1+num2;	
	}
  	public double divide(double num2){
  		return num1/num2;
  	}
  	public double multiply(double num2){
  		return num1*num2;
  	}
}
