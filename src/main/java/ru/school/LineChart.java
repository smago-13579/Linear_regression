package ru.school;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RectangleInsets;

import java.awt.*;

public class LineChart extends ApplicationFrame {

    public LineChart(Double xKM, int i) {
        super("Linear Regression");

        XYDataset dataset    = Dataset.createDataset(xKM, i);
        JFreeChart chart      = createChart(dataset);
        ChartPanel chartPanel = new ChartPanel(chart);

        chartPanel.setPreferredSize(new java.awt.Dimension(1120, 960));
        setContentPane(chartPanel);
    }

    private JFreeChart createChart(XYDataset dataset)
    {
        final JFreeChart chart = ChartFactory.createXYLineChart(
                "Linear Regression", "KM", "Price", dataset,
                PlotOrientation.VERTICAL, true, false, false);

        chart.setBackgroundPaint(Color.white);

        final XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(new Color(232, 232, 232));

        plot.setDomainGridlinePaint(Color.gray);
        plot.setRangeGridlinePaint (Color.gray);

        plot.setAxisOffset(new RectangleInsets(1.0, 1.0, 1.0, 1.0));

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesLinesVisible (1, false);
        renderer.setSeriesShapesVisible(0, false);

        renderer.setSeriesPaint  (2, Color.green);
        renderer.setSeriesStroke (2, new BasicStroke(2.5f));
        plot.setRenderer(renderer);

        return chart;
    }

}
