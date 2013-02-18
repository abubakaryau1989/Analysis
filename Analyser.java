
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Analyser {

    public static void main(String[] args) throws IOException {
        // System.out.printf(" Please type in the file you want to open. ");
        // String fileName = IOUtil.typedInput();
        int max=1729;
        // System.out.printf(" Found file %s", fileName);
//
        BufferedReader cd=new BufferedReader(new FileReader("Cd04.asc"));
        BufferedReader zn=new BufferedReader(new FileReader("Z03.asc"));
        BufferedReader k=new BufferedReader(new FileReader("K.asc"));

        //Sodium
        BufferedReader na=new BufferedReader(new FileReader("Na04.asc"));
        BufferedReader modelNaReader=new BufferedReader(new FileReader("ModelNa.asc"));

        //Mercury
        BufferedReader hg=new BufferedReader(new FileReader("mercury01feb.asc"));
        BufferedReader modelHgReader=new BufferedReader(new FileReader("ModelHg.asc"));

        PrintWriter residualsFout= new PrintWriter("residuals");

        PrintWriter peaksHgFile = new PrintWriter("peaksHg");
        PrintWriter peaksNaFile = new PrintWriter("peaksNa");
        Scanner scanCd = new Scanner(cd);
        Scanner scanZn = new Scanner(zn);
        Scanner scanK = new Scanner(k);
        Scanner scanNa = new Scanner(na);
        Scanner scanHg = new Scanner(hg);

        Scanner modelHgScan = new Scanner(modelHgReader);
        Scanner modelNaScan = new Scanner(modelNaReader);

        double dataCd[][] = new double[max][2];
        double dataZn[][] = new double[max][2];
        double dataK[][] = new double[max][2];
        double dataNa[][] = new double[max][2];
        double dataHg[][] = new double[max][2];

        //Read elements from file
        //Initialise arrays for experimental data by reading X and Y data from the files
        dataCd = PlotUtil.data(dataHg,scanHg);
        dataZn = PlotUtil.data(dataZn,scanZn);
        dataK = PlotUtil.data(dataK,scanK);
        dataNa = PlotUtil.data(dataNa,scanNa);
        dataHg= PlotUtil.data(dataHg,scanHg);


        int modelSampleNoHg=IOUtil.skipToInt(modelHgScan);
        int modelSampleNoNa=IOUtil.skipToInt(modelNaScan);

        double[] modelHg = new double[modelSampleNoHg];
        double[] modelNa = new double[modelSampleNoNa];
        modelHg = PlotUtil.data(modelHg,modelHgScan);
        modelNa = PlotUtil.data(modelNa,modelNaScan);
        PlotUtil.peaks(dataHg,peaksHgFile, 2.8);
        PlotUtil.peaksUp(dataNa,peaksNaFile, 20);


        peaksHgFile.close();
        peaksNaFile.close();

        BufferedReader hgPeaksFileReader=new BufferedReader(new FileReader("peaksHg"));
        Scanner hgPeaksScan = new Scanner(hgPeaksFileReader);
        BufferedReader peaksFileReaderNa=new BufferedReader(new FileReader("peaksNa"));
        Scanner peaksScanNa = new Scanner(peaksFileReaderNa);


        double[][] peaksHg =  new double[modelSampleNoHg][2];
        peaksHg =  PlotUtil.data(peaksHg, hgPeaksScan);
        double[][] peaksNa =  new double[modelSampleNoNa][2];
        peaksNa =  PlotUtil.data(peaksNa, peaksScanNa);


        double[] xPeaksHg = PlotUtil.x(peaksHg);
        double[] yPeaksHg = PlotUtil.y(peaksHg);

        double[] xPeaksNa = PlotUtil.x(peaksNa);


        double[] residualsHg = StatsUtil.residuals(xPeaksHg, modelHg);
        double[] residualsNa = StatsUtil.residuals(xPeaksNa, modelNa);

        PlotUtil.writeXY(xPeaksHg, residualsHg, residualsFout);
        //writeXY(xPeaksNa, residualsNa, residualsFout);
        residualsFout.close();

        PrintWriter modelHgFile = new PrintWriter("modelHgPlot");
        PlotUtil.writeXY(modelHg,yPeaksHg,modelHgFile);
        modelHgFile.close();

        PrintWriter calibratedHgFile = new PrintWriter("calibratedHgPlot");
        PlotUtil.calibratedXY(xPeaksHg,yPeaksHg,calibratedHgFile,residualsHg);
        calibratedHgFile.close();

    }

}