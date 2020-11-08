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

    public static void veroyatnost (LinearRegressionFunction lin)
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
        System.out.println(K/m);
    }

    public static void main(String[] args) throws FileNotFoundException
    {
        read(); //считывет данные из файла

        //создание начальной линейной регрессии
        double[] theta={0,0,0,0,0,0,0,0,0};
        LinearRegressionFunction linear = new LinearRegressionFunction(theta);
//menu
        //обучение регрессии
        for (int i=0; i<500; i++)
        linear = train(linear);

        veroyatnost(linear);
        //Ввод данных, которые мы хотим проверить
        /*double[] data= new double[n];
        System.out.println("введите");
        Scanner in= new Scanner(System.in);
        for (int i=0; i<n;i++){
            data[i]= in.nextDouble();
        }
        System.out.println(function(linear,data));*/
    }
}
