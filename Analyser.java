
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Analyser {

    public static void main(String[] args) throws IOException {
    //    System.out.printf(" Please type in the file you want to open. ");
     //   String fileName = IOUtil.typedInput();
        int max=1729;
     //   System.out.printf(" Found file %s", fileName);

        BufferedReader cd=new BufferedReader(new FileReader("Cd04.asc"));
        BufferedReader zn=new BufferedReader(new FileReader("Z03.asc"));
        BufferedReader k=new BufferedReader(new FileReader("K.asc"));
        BufferedReader na=new BufferedReader(new FileReader("Na04.asc"));
        BufferedReader mg=new BufferedReader(new FileReader("mercury01feb.asc"));

        PrintWriter upPeaksFile = new PrintWriter("peaksUp");
        PrintWriter residualsFile = new PrintWriter("residuals");
        Scanner scanCd = new Scanner(cd);
        Scanner scanZn = new Scanner(zn);
        Scanner scanK = new Scanner(k);
        Scanner scanNa = new Scanner(na);
        Scanner scanHg = new Scanner(mg);

        double dataCd[][] = new double[max][2];
        double dataZn[][] = new double[max][2];
        double dataK[][] = new double[max][2];
        double dataNa[][] = new double[max][2];
        double dataHg[][] = new double[max][2];

        //Read elements from files
        dataCd = PlotUtil.data(dataCd,scanCd);

        dataZn = PlotUtil.data(dataZn,scanZn);
        dataK = PlotUtil.data(dataK,scanK);
        dataNa = PlotUtil.data(dataNa,scanNa);
        dataHg= PlotUtil.data(dataHg,scanHg);

        writePeaksUp("cd ", dataCd, upPeaksFile);
        writePeaksUp("zn ", dataZn, upPeaksFile);
        writePeaksUp("k ", dataK, upPeaksFile);
        writePeaksUp("na ", dataNa, upPeaksFile);
        writePeaksUp("Hg ", dataHg, upPeaksFile);
        upPeaksFile.close();

    }
    //put the real value
    public static void calculateStuff(double[][] data, PrintWriter out){

        double xVar =  PlotUtil.xVariance(data);
        double yVar =  PlotUtil.yVariance(data);

        double covariance = PlotUtil.covariance(xVar, yVar, data);
        double xxVar =PlotUtil.xxVariance(xVar, data);
        double yyVar =PlotUtil.yyVariance(yVar, data);

        double gradient=PlotUtil.gradient(covariance, xxVar);

        double offset = PlotUtil.yIntercept(xVar, yVar, gradient);

        //In this calculation the fit is actually the values which are writen in the NIST database.
       // double[] fit = PlotUtil.fit(data, gradient,offset);
         double[] fit;  //Get expectation values from a file or type in the keyboard manually TODO

        //Write to file once you have verified the right stuff is happeneing.

      //  double[] residuals = PlotUtil.residuals(data, fit);

   //     for(int i = 0; i < data.length; i++){
      //     System.out.printf(" %g %g", data[i][0], residuals[i]);
      //     System.out.println();
     //   }
    }

    public static void writePeaksUp(String label, double[][] data, PrintWriter upFile){
        double largeUp=0.0;

        System.out.println(label);
        for(int i=0; i<data.length-1; i++){

            //Check that there has been an initial increase
            if(data[i][1]>data[0][1]){

                //if so check that  it's significant
                if(data[i][1]>largeUp){
                    largeUp = data[i][1];

                    System.out.printf("%2.2f  ",data[i][0]);

                    upFile.printf("%2.2f %2.2f ",data[i][0], largeUp);
                    upFile.println();

                }
            }

        }
        System.out.println();

    }

    public static void writePeaks(double[][] data, PrintWriter upFile){
        double largeUp=0.0;


        for(int i=0; i<data.length-1; i++){

            //Check that there has been an initial increase
            if(data[i][1]>data[0][1]){

                //if so check that  it's significant
                if(data[i][1]>largeUp){
                    largeUp = data[i][1];

                    upFile.printf("%2.2f %2.2f ",data[i][0], largeUp);
                    upFile.println();

                    //set some sort of minimum
                    if(largeUp==100){

                        //reset
                        largeUp=0;
                    }
                }
            }
        }
    }
    //This is to make it possible to analyse the curves linearly.
    public static void writePeaksDown(double[][] data, PrintWriter downFile) {
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
