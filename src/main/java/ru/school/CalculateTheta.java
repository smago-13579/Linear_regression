package ru.school;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class CalculateTheta {
    public static TreeMap<Double, Double> data = new TreeMap<>();
    public static double theta0, theta1, count, mse;
    public static List<Double> arr, estPrice, listKM, listPrice;
    public static double learningRate = 0.1;

    public static void calculateTheta() {
        if (theta0 != 0 || theta1 != 0) {
            System.out.println("The program is already trained ");
            return ;
        }

        try {
            List<String> list = Files.readAllLines(Paths.get("data.csv"));

            list.stream().skip(1).forEach(el -> {
                String[] arr = el.split(",\\s*");

                if (arr.length == 2) {
                    data.put(Double.parseDouble(arr[0]), Double.parseDouble(arr[1]));
                }
            });
            createValues();

            for (int epoch = 0; epoch < 5000; epoch++) {

                for (int i = 0; i < arr.size(); i++) {
                    arr.set(i, theta0 + theta1 * listKM.get(i) - listPrice.get(i));
                }
                theta0 -= learningRate * arr.stream().reduce(0d, Double::sum) / count;

                List<Double> arr2 = Arrays.asList(new Double[arr.size()]);
                for (int i = 0; i < arr2.size(); i++) {
                    arr2.set(i, arr.get(i) * listKM.get(i));
                }
                theta1 -= learningRate * arr2.stream().reduce(0d, Double::sum) / count;

                for (int i = 0; i < estPrice.size(); i++) {
                    estPrice.set(i, theta0 + theta1 * listKM.get(i));
                }
                if (meanSquaredError() < 0.0000001 || epoch == 4999) {
                    System.out.println("Epoch - " + (epoch + 1));
                    System.out.println("norm theta1: " + theta1);
                    theta1 /= data.lastKey();
                    printTheta();
                    break;
                }
            }
        } catch (Exception ex) {
            System.out.println("ERROR in CalculateTheta!!!");
            System.out.println(ex.getMessage());
        }
    }

    public static void printTheta() {
        System.out.println("theta0: " + theta0);
        System.out.println("theta1: " + theta1);
    }

    public static void predictPrice(double xKM) {
        System.out.println("Price: " + (theta1 * xKM + theta0));
    }

    private static double meanSquaredError() {
        double prevMse = mse;
        ArrayList<Double> sqDiff = new ArrayList<>();

        for (int i = 0; i < estPrice.size(); i++) {
            sqDiff.add(Math.abs(estPrice.get(i) - listPrice.get(i)));
        }
        mse = sqDiff.stream().map(a -> Math.pow(a, 2)).reduce(0d, Double::sum) / count;

        return Math.abs(mse - prevMse);
    }

    private static void createValues() {
        count = data.size();

        listKM = new ArrayList<>();
        data.keySet().forEach(a -> {
            listKM.add(a / data.lastKey());
        });
        listPrice = new ArrayList(data.values());
        arr = Arrays.asList(new Double[data.size()]);
        estPrice = Arrays.asList(new Double[data.size()]);
    }

    public static void precision() {
        List<Double> errorList = new ArrayList<>();
        List<Double> tmpKM = new ArrayList<>(data.keySet());

        for (int i = 0; i < data.size(); i++) {
            Double price = listPrice.get(i);
            Double predictPrice = theta1 * (double)tmpKM.get(i) + theta0;
            Double error = Math.abs(predictPrice - price) / predictPrice * 100;
            errorList.add(error);

            System.out.printf("%d \tkm: %6.0f \tprice: %4.2f \tpredict price: %4.2f \terror, %%: %4.3f\n",
                    i, tmpKM.get(i), price, predictPrice, error);
        }

        if (errorList.isEmpty()) {
            System.out.println("Average error: 0 %");
        } else {
            Double avError = errorList.stream().reduce(0d, Double::sum) / count;
            System.out.printf("Average error: %4.3f %%\n\n", avError);
        }
    }
}
