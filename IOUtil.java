import java.util.Scanner;
import java.io.*;

public class IOUtil {

    public static double skipToDouble(Scanner scanner) {


        while (scanner.hasNext() && !scanner.hasNextDouble()) {
            scanner.next();
        }
        return scanner.hasNextDouble() ? scanner.nextDouble() : Double.NaN;
    }
    public static FileReader file(String fileName)throws FileNotFoundException {
        return new FileReader(fileName);
    }
    public static String typedInput() throws IOException {
        BufferedReader keyIn = new BufferedReader(new InputStreamReader(System.in));
        return keyIn.readLine();

    }
    /*
    *Call this every time wrong thing is typed.*/
    public static void abuse() {
        System.out.println("Invalid entry.");
        System.out.println("Abusive statement.");
        System.exit(0);
    }
}