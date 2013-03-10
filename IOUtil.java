/*
    IOUtils.java
    Assistant methods for handling IO related happenings
    Composed entirely of static methods.

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
    //Picks out the first integer value it sees and returns it.
    public static int skipToInt(Scanner scanner) {

        while (scanner.hasNext() && !scanner.hasNextInt()) {
            scanner.next();
        }
        return scanner.hasNextInt() ? scanner.nextInt() : (int) Double.NaN;//workaround
    }
    // Call this every time wrong thing is typed.
    public static void abuse() {
        System.out.println("Invalid entry.");
        System.out.println("Abusive statement.");
        System.exit(0);
    }
}
