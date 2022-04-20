package ru.school;

import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.util.Objects;
import java.util.TreeMap;

public class Dataset {
    private static double theta0, theta1;
    private static TreeMap<Double, Double> data;

    public static XYDataset createDataset(Double xKM, int i) {
        if (i == 1) {
            data = CalculateTheta.data;
            theta0 = CalculateTheta.theta0;
            theta1 = CalculateTheta.theta1;
        } else {
            data = CalculateThetaNormVal.data;
            theta0 = CalculateThetaNormVal.theta0;
            theta1 = CalculateThetaNormVal.theta1;
        }

        final XYSeries linearRegression = new XYSeries("Linear Regression");

        if (!data.isEmpty()) {
            linearRegression.add(0, calculate(0));
            if (Objects.nonNull(xKM) && xKM > data.lastKey()) {
                linearRegression.add((double)xKM, calculate(xKM));
            } else {
                linearRegression.add((double) data.lastKey(), calculate(data.lastKey()));
            }
        }

        final XYSeries dataXY = new XYSeries("Data");
        data.forEach((a, b) -> {
            dataXY.add(a, b);
        });

        final XYSeries estPrice = new XYSeries("Estimated Price");

        if (Objects.nonNull(xKM)) {
            estPrice.add((double)xKM, calculate(xKM));
        }

        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(linearRegression);
        dataset.addSeries(dataXY);
        dataset.addSeries(estPrice);

        return dataset;
    }

    private static double calculate(double x) {
        return theta1 * x + theta0;
    }
}
