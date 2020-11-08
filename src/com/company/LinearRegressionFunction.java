package com.company;

import jdk.internal.util.xml.impl.Input;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

import static com.company.ReadingFile.*;

public class LinearRegressionFunction
{
    private final double[] thetaVector;
    public double alpha=0.5;

    LinearRegressionFunction(double[] thetaVector)
    {
       this.thetaVector = Arrays.copyOf(thetaVector, thetaVector.length);
    }

    public static double z (LinearRegressionFunction lin, double[] data)
    {
        double z=lin.thetaVector[0];
        for (int i=0; i< data.length; i++)
        {
            z +=lin.thetaVector[i+1]*data[i];
        }
        return z;
    }

    //функция линейной регрессии
    public static double function (LinearRegressionFunction lin, double[] data)
    {
        double result;
        result = (1/(1+Math.exp(-z(lin,data))));
        return result;
    }

    //обучение линейной регресии
    public static LinearRegressionFunction train(LinearRegressionFunction lin)
    {
        double[] th= lin.thetaVector;
        double[] new_th=new double[th.length];

        for (int i=0; i< th.length; i++)
        {
            double sumErrors=0;
            if (i==0)
            {
                for (int j=0; j<m; j++)
                {
                    double[] featureVector=new double[n];
                    for (int l=0; l<n; l++)
                    {
                        featureVector[l]=x[l][j];
                    }
                    double error =y_real[j]-function(lin,featureVector);
                    sumErrors+=error;
                }
            }
            else
                {
                for (int j=0; j<m; j++)
                {
                    double[] featureVector=new double[n];
                    for (int l=0; l<n; l++)
                    {
                        featureVector[l]=x[l][j];
                    }
                    double error =y_real[j]-function(lin,featureVector);
                    sumErrors+=error*featureVector[i-1];
                }
            }
            double gradient = (1.0 / m) * sumErrors;
            new_th[i] = th[i] + (lin.alpha * gradient);
        }

        return new LinearRegressionFunction(new_th);
    }

    public static void probability (LinearRegressionFunction lin)
    {
        float K=0;
        for (int j=0; j<m; j++)
        {
            double[] featureVector=new double[n];
            for (int l=0; l<n; l++)
            {
                featureVector[l]=x[l][j];
            }
            if(y_real[j]==function(lin,featureVector)) K++;
        }
        System.out.println("Коректность = "+K/m);
    }

    public static void main(String[] args) throws FileNotFoundException
    {
        int t=500;
        read(); //считывет данные из файла

        //создание начальной линейной регрессии
        double[] theta={0,0,0,0,0,0,0,0,0};
        LinearRegressionFunction linear = new LinearRegressionFunction(theta);

        //menu
        Scanner in= new Scanner(System.in);
        boolean flag=true;
        while (flag==true) {
            System.out.println("1 - Обнуление регресии, 2 - Обучение регресии, 3 - Ввести данные для проверки, 4 - Изменить количество циклов обучения, 5 - Изменить alpha, 6 - Коректность существующей регрессии, 0 - Выход");
            switch (in.nextInt()) {
                case 0:
                    flag = false;
                    break;
                case 1:
                    linear = new LinearRegressionFunction(theta);
                    break;
                case 2:
                    for (int i = 0; i < t; i++)
                        linear = train(linear);
                    System.out.println("Обучение завершенно");
                    break;
                case 3:
                    double[] data= new double[n];
                    System.out.println("введите");

                    for (int i=0; i<n;i++){
                        data[i]= in.nextDouble();
                    }
                    System.out.println(function(linear,data));
                    break;
                case 4:
                    System.out.println("Введите количество циклов");
                    t = in.nextInt();
                    break;
                case 5:
                    System.out.println("Введите alpha");
                    linear.alpha = in.nextDouble();
                    break;
                case 6:
                    probability(linear);
                    break;
                default:
                    System.out.println("Такого варианта нет");
                    break;
            }
        }
    }
}
