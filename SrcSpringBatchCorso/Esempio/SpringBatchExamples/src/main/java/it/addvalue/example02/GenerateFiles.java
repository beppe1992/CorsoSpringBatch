package it.addvalue.example02;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;
import java.util.regex.Pattern;

public class GenerateFiles {

	/**
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException
    {
        int numFiles = askForInteger("Quanti files?", "[0-9]*");
        int numRows[] = askForIntegers("NumeroRighe?", "[0-9]*-[0-9]*");

        for ( int i = 0; i < numFiles; i++ )
        {
            int n = random(numRows);
            writeFile(i, n);
        }

    }

    private static void writeFile(int i,
                                  int n) throws IOException
    {
        FileWriter fw = new FileWriter("C:/txtGen/" + threeChars(i) + ".txt");

        for ( int j = 0; j < n; j++ )
        {
            fw.write(generateLine());
        }

        fw.flush();
        fw.close();
    }

    private static SecureRandom random = new SecureRandom();

    private static String generateLine()
    {
        return generateString(20) + generateLong(10) + generateString(13)+ generateLong(10) + generateString(13)+ generateLong(10) + generateString(13)+ generateLong(10) + generateString(13) + "\n";
    }

    private static String ZEROS = "00000000000000000000000000000000000000000000000000000000000000000";

    private static String generateLong(int i)
    {
        long nextLong = random.nextLong();

        if ( nextLong < 0 )
        {
            nextLong *= -1;
        }
        String str = ZEROS + nextLong;

        return str.substring(str.length() - i);
    }

    private static String generateString(int i)
    {
        return new BigInteger(130, random).toString(32).substring(0, i);
    }

    private static String threeChars(int i)
    {
        if ( i > 99 )
        {
            return "" + i;
        }

        if ( i > 9 )
        {
            return "0" + i;
        }

        return "00" + i;
    }

    public static int random(int[] chains)
    {
        Random r = new Random();
        int ret = chains[0];
        if ( chains[1] > ret )
        {
            ret += r.nextInt(chains[1] - ret);
        }
        return ret;
    }

    public static int[] askForIntegers(String message,
                                       String regEx)
    {
        int[] ret = new int[]
        { 0,
        0 };
        String[] str = askForString(message, regEx).split("-");

        ret[0] = Integer.parseInt(str[0]);
        ret[1] = Integer.parseInt(str[1]);

        return ret;
    }

    public static int askForInteger(String message,
                                    String regEx)
    {
        return Integer.parseInt(askForString(message, regEx));
    }

    public static String askForString(String message,
                                      String regEx)
    {
        while (true)
        {
            System.out.print(message + " ");

            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

            try
            {
                String readLine = in.readLine();

                if ( !Pattern.matches(regEx, readLine) )
                {
                    continue;
                }

                return readLine;
            }
            catch ( IOException e )
            {

            }
        }
    }

}
