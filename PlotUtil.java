/*****
 *
 *
 * This is a utility for data analysis
 *
 * For reading and writing files so they can be plotted using pgfplots or similar
 *
 * Composed entirely of static methods.
 *
 * By Magdalen Berns
 *
 *
 *
 */
import java.io.PrintWriter;
import java.util.Scanner;

public class PlotUtil{

    //Initialise the array with the values from a file
    public static double[][] data(double[][] data, Scanner scan){
        for (int i=0;i<data.length;i++){
            for (int j=0;j<data[0].length;j++){
                data[i][j] = (float) IOUtil.skipToDouble(scan);
            }
        }
        return data;
    }

    public static double[] data(double[] data, Scanner scan){
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
            fileOut.printf("%2.2f %2.2f", x[i],y[i]);
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
    //Work out the minimum value from the SNR (Signal to Noise ratio)
    public static void peaks(double[][] data, PrintWriter peakFile, double minValue){
        double peaks=0.0;

        //loop through data
        for(int i=0; i<data.length; i++){

            //Check that there has been an initial increase and that it is bigger than 2.8%
            if(data[i][1]>data[0][1] && data[i][1]>minValue){

                //if so check that it's significant  enough to bother
                if(data[i][1]>peaks){
                    peaks = data[i][1];
                    peakFile.printf("%2.2f %2.2f ",data[i][0], peaks);
                    peakFile.println();
                }
            }
            //When the peak has reached its highest point
            else if(data[i][1]<peaks){
                //reset
                peaks=0;
            }
        }
    }
    //Unsorted. TODO
    public static void writePeaksDown(double[][] data, PrintWriter downFile){
        double largeDown=0.0;

        for(int i=data.length-1; i>=0; i--){

            if(data[i][1]>largeDown){

                largeDown=data[i][1];
                double xPos = data[i][0];
                downFile.printf("%2.2f %2.2f ",xPos,largeDown);
                downFile.println();
            }
        }
    }
}