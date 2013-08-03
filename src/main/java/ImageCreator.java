import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.Year;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.Map;

/**
 * Author: Boris Puzikov
 * Date: 7/28/13
 * Time: 9:16 PM
 */
public class ImageCreator {
    private static final String xAxis = "";
    private static final String yAxis = "";
    private static final Color mainColor = Color.RED;
    private static final Shape dotShape = new Rectangle2D.Double(-3, -3, 6, 6);
    private String name;

    public void create(Team team, String name) {
        this.name = name;
        createTimeChart(team);
    }

    private void createTimeChart(Team team) {
        TimeSeriesCollection dataset = new TimeSeriesCollection();

        for (Map line : team.getData()) {
            TimeSeries series = new TimeSeries(line.toString());
            Iterator i = line.entrySet().iterator();
            while (i.hasNext()) {
                Map.Entry<Integer, Integer> pair = (Map.Entry) i.next();
                series.add(new Year(pair.getKey()), pair.getValue());
            }
            dataset.addSeries(series);
        }
        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                name,
                xAxis,
                yAxis,
                dataset,
                false,                      // Show Legend
                false,                      // Use tooltips
                false                       // Configure chart to generate URLs?
        );
        XYPlot plot = (XYPlot) chart.getPlot();
        XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) plot.getRenderer();
        ValueAxis valueAxis = plot.getRangeAxis();
        valueAxis.setAutoTickUnitSelection(false);
        valueAxis.setRange(0.5, 16);
        DateAxis dateAxis = (DateAxis) plot.getDomainAxis();
        dateAxis.setTickUnit(new DateTickUnit(DateTickUnit.YEAR, 1, new SimpleDateFormat("yyyy")));
        plot.getDomainAxis().setVerticalTickLabels(true);
        plot.getRangeAxis().setInverted(true);

        for (int i = 0; i < dataset.getSeriesCount(); i++) {
            renderer.setSeriesPaint(i, mainColor);
            renderer.setSeriesShapesVisible(i, true);
            renderer.setSeriesShape(i, dotShape);
        }

        try {
            ChartUtilities.saveChartAsJPEG(new File(name + ".jpg"), chart, 500, 300);
        } catch (IOException e) {
            System.err.println("Problem occurred creating chart.");
        }
    }
}
