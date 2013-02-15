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
    //read the user input
    public static String typedInput() throws IOException {
        BufferedReader keyIn = new BufferedReader(new InputStreamReader(System.in));
        return keyIn.readLine();

    }
    
    /*
* *
* * Picks out the first integer value it sees and returns it.
* */
    public static int skipToInt(Scanner scanner) {

        while (scanner.hasNext() && !scanner.hasNextInt()) {
            scanner.next();
        }
        //This is probably a bit naughty because 'int' is primitive -but it does what I want
        return scanner.hasNextInt() ? scanner.nextInt() : (int) Double.NaN;
    }
    /*
    *Call this every time wrong thing is typed.*/
    public static void abuse() {
        System.out.println("Invalid entry.");
        System.out.println("Abusive statement.");
        System.exit(0);
    }
}
