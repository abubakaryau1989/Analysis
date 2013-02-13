/*****
 *
 *
 * This is the utility for data analysis
 *
 * Composed soley of static methods, it
 *
 *
 *
 */


import java.util.Scanner;

public class PlotUtil{

    //Initialise the array with the values from a file
    public static double[][] data(double[][] data, Scanner scan){

        for (int i=0;i<data.length-1;i++){
            for (int j=0;j<data[0].length;j++){
                data[i][j] = (float)   IOUtil.skipToDouble(scan);
                //           System.out.printf("%5.2f ",data[i][j]);
            }
            //    System.out.println();

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
    public static double[]  y(double[][] data){
        double[] y = new double[data.length];

        for (int i=0;i<data.length;i++){
            y[i]=data[i][1];
        }
        return y;
    }
    public static double xVariance(double[][] data){


        double sumX = 0.0, sumY = 0.0;

        for (int i=0;i<data.length;i++){

            sumX  += data[i][0];
        }
        return sumX / data.length;
    }
    public static double yVariance(double[][] data){


        double sumY = 0.0;

        for (int i=0;i<data.length;i++){

            sumY  += data[0][i];
        }
        return sumY / data.length;
    }
    public static double covariance(double xVariance, double yVariance, double[][] data){

        double covariance=0.0;

        //works out the difference of least squares fit
        for (int i = 0; i < data.length; i++) {
            covariance += (data[i][0] - xVariance) * (data[0][i] - yVariance);
        }
        return covariance;
    }
    public static double xxVariance(double xVariance, double[][] data){

        double xxVariance=0.0;

        //works out the difference of least squares fit
        for (int i = 0; i < data.length; i++) {
            xxVariance += (data[i][0] - xVariance) * (data[i][0] - xVariance);

        }
        return xxVariance;
    }
    public static double yyVariance(double yVariance, double[][] data){

        double yyVariance=0.0;

        //works out the difference of least squares fit
        for (int i = 0; i < data.length; i++) {
            yyVariance += (data[0][i] - yVariance) * (data[0][i] - yVariance);
        }
        return yyVariance;
    }
    public static double gradient(double covariance, double xxVariance){

        return covariance / xxVariance;//linear correlation coefficient
    }
    public static double yIntercept(double yVariance, double xVariance, double gradient){
        return yVariance - gradient * xVariance;
    }
    public static double[] fit(double[][] data, double gradient, double offset){
        double[] fit=new double[data.length];
        for(int i=0; i<data.length; i++)
            fit[i] = gradient*data[i][0] + offset;
        return fit;
    }

    // Residual Sum of Squares.
    public static double rss(double[][] data, double[] fit){

        double rss = 0.0;  //standard error in mean i.e. residual sum of squares
        for (int i = 0; i < data.length; i++)
            rss += (fit[i] - data[0][i]) * (fit[i] - data[0][i]);
        return rss;
    }
    //Regression sum of squares.
    public static double ssr(double[][] data, double[] fit, double yVariance) {
        double ssr = 0.0;  // regression sum of squares
        for (int i = 0; i < data.length; i++){
            ssr += (fit[i] - yVariance) * (fit[i] - yVariance);
        }
        return ssr;
    }
    public static double[] residuals(double residual, double[][] data, double[] fit){

        double[] residuals=new double[data.length];
        for (int i = 0; i < data.length; i++)
            residuals[i] =data[0][i]- fit[i];
        return residuals;
    }
    //Returns the linear corrilation coefficient
    public static double linearCC(double ssr, double yyVariance){
        return ssr/yyVariance;
    }
    //Assumes that data has only 2 degrees of freedom TODO develop later.
    public static double stdFit(double rss,double[][] data, double xVariance, double errorGradient) {

        double degreesFreedom=2;
        double stdVar  = rss / degreesFreedom;
        return stdVar/data.length + xVariance*xVariance*Math.sqrt(errorGradient);
    }

    public static double errorGradient(double stdVariation, double xxVariance){

        return Math.sqrt(stdVariation/ xxVariance);
    }

    public static double[] gaussian(int sampleNumber, double sigma, double mean){

        //instantiate and initialise an array to hold Gaussian
        double[] gaussian = new double[sampleNumber];
        double temp= 0.0;
        for (int i=0; i<sampleNumber; i++){
            gaussian[i] = (1/(2*Math.PI*sigma*sigma))*(Math.exp(-(i-mean)*(i-mean)/(2*sigma*sigma)));
            temp += gaussian[i];
        }
        //Normalize the gaussian
        for (int i=0; i<sampleNumber; i++){
            gaussian[i] /= temp;
        }
        return gaussian;
    }

    //Static method to take in a normalised gaussian and convolve it with the data
    // returns the convolution as a 2D array.
    public static double[][] convolve(int[][] data, double[] gaussian, int sampleNumber){


        double convolved[][] = new double[data.length - (sampleNumber + 1)][2];
        for (int i=0; i<convolved.length; i++){
            convolved[i][1] = 0.0;  // Set all doubles to 0.
            for (int j=i, k=0; j<i+sampleNumber; j++, k++){
                convolved[i][1] +=  data[j][i] * gaussian[k];
            }
        }
        return convolved;
    }
}
