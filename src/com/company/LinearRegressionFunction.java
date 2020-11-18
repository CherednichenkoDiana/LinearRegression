package com.company;

import jdk.internal.util.xml.impl.Input;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

import static com.company.ReadingFile.*;

public class LinearRegressionFunction
{
    private final double[] thetaVector;
    public double alpha=0.01;
    public int  o=614;

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
        return (1/(1+Math.exp(-z(lin,data))));
    }

    //обучение линейной регресии
    public static LinearRegressionFunction train(LinearRegressionFunction lin)
    {
        double[] th= lin.thetaVector;
        double[] new_th=new double[th.length];

        double sumErrors=0;
        for (int j=0; j<lin.o; j++)
        {
            double[] featureVector=new double[n];
            for (int l=0; l<n; l++)
            {
                featureVector[l]=x[l][j];
            }
            double error =y_real[j]-function(lin,featureVector);
            sumErrors+=error;
        }
        double gradient = (1.0 / lin.o) * sumErrors;
        new_th[0] = th[0] + (lin.alpha * gradient);

        for (int i=1; i< th.length; i++)
        {
            sumErrors=0;
            for (int j=0; j<lin.o; j++)
            {
                double[] featureVector=new double[n];
                for (int l=0; l<n; l++)
                {
                    featureVector[l]=x[l][j];
                }
                double error =y_real[j]-function(lin,featureVector);
                sumErrors+=error*featureVector[i-1];
        }
            gradient = (1.0 / lin.o) * sumErrors;
            new_th[i] = th[i] + (lin.alpha * gradient);
        }

        return new LinearRegressionFunction(new_th);
    }

    public static void probability (LinearRegressionFunction lin)
    {
        float K=0;
        for (int j=lin.o; j<m; j++)
        {
            double[] featureVector=new double[n];
            for (int l=0; l<n; l++)
            {
                featureVector[l]=x[l][j];
            }
            if(Math.abs(y_real[j]-function(lin,featureVector))<0.1) K++;
        }
        System.out.println("Точность = "+(K/(m-lin.o))*100);
    }

    public static double dist (LinearRegressionFunction lin, LinearRegressionFunction old_lin){
        double r=0;
        for (int i=0; i<lin.thetaVector.length; i++)
        {
            double d= lin.thetaVector[i]-old_lin.thetaVector[i];
            r+=d*d;
        }
        return Math.sqrt(r);
    }

    public static void main(String[] args) throws FileNotFoundException
    {
        read(); //считывет данные из файла

        //создание начальной линейной регрессии
        double[] theta={0,0,0,0,0,0,0,0,0};
        LinearRegressionFunction linear = new LinearRegressionFunction(theta);

        //menu
        Scanner in= new Scanner(System.in);
        boolean flag=true;
        while (flag==true)
        {
            System.out.println("1 - Обнуление регресии, 2 - Обучение регресии, 3 - Ввести данные для проверки, 4 - Изменить количество циклов обучения, 5 - Изменить alpha, 6 - Точность, 0 - Выход");
            switch (in.nextInt())
            {
                case 0:
                    flag = false;
                    break;
                case 1:
                    linear = new LinearRegressionFunction(theta);
                    break;
                case 2:
                    LinearRegressionFunction old_linear=new LinearRegressionFunction(theta);
                    int t=0;
                    do {
                        for (int i=0; i<old_linear.thetaVector.length; i++)
                        {
                            old_linear.thetaVector[i]=linear.thetaVector[i];
                        }
                        linear = train(linear);
                        t++;
                    }
                    while (dist(linear,old_linear)>0.1);
                    System.out.println("Обучение завершенно " + t);
                    break;
                case 3:
                    double[] data= new double[n];
                    for (int i=0; i<n;i++){
                        switch (i)
                        {
                            case 0:
                                System.out.println("Беременность");
                                break;
                            case 1:
                                System.out.println("Глюкоза");
                                break;
                            case 2:
                                System.out.println("АД");
                                break;
                            case 3:
                                System.out.println("Толщина КС");
                                break;
                            case 4:
                                System.out.println("Инсулин");
                                break;
                            case 5:
                                System.out.println("ИМТ");
                                break;
                            case 6:
                                System.out.println("Наследственность");
                                break;
                            case 7:
                                System.out.println("Возраст");
                                break;
                        }
                        data[i]= in.nextDouble();
                    }
                    System.out.println("Диагноз "+function(linear,data));
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
