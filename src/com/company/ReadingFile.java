package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class ReadingFile
{
    public static int m=768, n=8; //это хочу переделать на List<Double[]>, но пока лень)
    public static double[][] x = new double[n][m];
    public static double[] y_real = new double[m];
    public static void read() throws FileNotFoundException
    {
        File file = new File("C:\\Users\\allis\\IdeaProjects\\lab1\\src\\test.TXT");
        Scanner scanner = new Scanner(file);
        int j=-1;
        while (scanner.hasNextLine())
        {
            if (j==-1)
            {
                scanner.nextLine();
            }
            else {
                String line = scanner.nextLine();
                String[] data = line.split("\t");
                int i = 0;

                for (String dat : data)
                {
                    if (i < n)
                    {
                        x[i][j] = Double.parseDouble(dat);
                    } else if (i == n)
                    {
                        y_real[j] = Double.parseDouble(dat);
                    }
                    i++;
                }
            }
            j++;
        }
        scanner.close();
    }
}
