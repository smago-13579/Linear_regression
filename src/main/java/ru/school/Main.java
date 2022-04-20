package ru.school;

import org.jfree.ui.RefineryUtilities;

import java.util.*;

public class Main {

    public static Scanner scanner = new Scanner(System.in);
    public static Double xKM;

    public static void main(String[] args) {

        for (; ;) {
            int i = title();

            if (i == 1) {
                CalculateTheta.calculateTheta();
            } else if (i == 2) {
                CalculateTheta.printTheta();
            } else if (i == 3) {
                xKM = (double) getKM();
                CalculateTheta.predictPrice(xKM);
            } else if (i == 4) {
                LineChart chart = new LineChart(xKM, 1);
                chart.pack();
                RefineryUtilities.centerFrameOnScreen(chart);
                chart.setVisible(true);
            } else if (i == 5) {
                CalculateTheta.precision();
            } else if (i == 6) {
                CalculateThetaNormVal.calculateTheta();
            } else if (i == 7) {
                CalculateThetaNormVal.printTheta();
            } else if (i == 8) {
                xKM = (double) getKM();
                CalculateThetaNormVal.predictPrice(xKM);
            } else if (i == 9) {
                LineChart chart = new LineChart(xKM, 2);
                chart.pack();
                RefineryUtilities.centerFrameOnScreen(chart);
                chart.setVisible(true);
            } else if (i == 10) {
                CalculateThetaNormVal.precision();
            } else if (i == 0) {
                System.exit(0);
            } else {
                System.out.println("Incorrect number!!!");
            }

        }
    }

    private static int title() {
        int i;

        System.out.println("1 - Teach program from file");
        System.out.println("2 - Show theta0 and theta1");
        System.out.println("3 - Predict car price");
        System.out.println("4 - Create graph(BONUS)");
        System.out.println("5 - Display precision (error rate)(BONUS)");
        System.out.println("6 - Teach program with norm values(BONUS)");
        System.out.println("7 - Show theta0 and theta1 from norm values(BONUS)");
        System.out.println("8 - Predict car price from norm values(BONUS)");
        System.out.println("9 - Create graph from norm values(BONUS)");
        System.out.println("10 - Display precision from norm values(BONUS)");
        System.out.println("0 - Exit program");

        try {
            String str = scanner.next();
            i = Integer.parseInt(str);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println("Incorrect number!!!");
            i = title();
        }

        return i;
    }

    private static int getKM() {
        int i;

        System.out.print("Enter KM value: ");

        try {
            String str = scanner.next();
            i = Integer.parseInt(str);

            if (i < 0) {
                throw new Exception("Incorrect KM value!!!");
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.out.println("Incorrect number!!!");
            i = getKM();
        }

        return i;
    }
}
