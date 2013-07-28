import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.Year;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Author: Boris Puzikov
 * Date: 7/28/13
 * Time: 9:16 PM
 */
public class ImageCreator {
    private static final String xAxis = "";
    private static final String yAxis = "";

    public void create(Team team) {
        createXYChart(team);
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
                team.getName(),
                xAxis,
                yAxis,
                dataset,
                false,                      // Show Legend
                false,                      // Use tooltips
                false                       // Configure chart to generate URLs?
        );
        try {
            ChartUtilities.saveChartAsJPEG(new File("time" + team.getName() + ".jpg"), chart, 500, 300);
        } catch (IOException e) {
            System.err.println("Problem occurred creating chart.");
        }
    }

    private void createXYChart(Team team) {
        XYSeriesCollection dataset = new XYSeriesCollection();

        for (Map line : team.getData()) {
            XYSeries series = new XYSeries(line.toString());
            Iterator i = line.entrySet().iterator();
            while (i.hasNext()) {
                Map.Entry<Integer, Integer> pair = (Map.Entry) i.next();
                series.add(pair.getKey(), pair.getValue());
            }
            dataset.addSeries(series);
        }

        JFreeChart chart = ChartFactory.createXYLineChart(
                team.getName(),
                xAxis,
                yAxis,
                dataset,
                PlotOrientation.VERTICAL,   // Plot Orientation
                false,                      // Show Legend
                false,                      // Use tooltips
                false                       // Configure chart to generate URLs?
        );
        try {
            ChartUtilities.saveChartAsJPEG(new File("xy" + team.getName() + ".jpg"), chart, 500, 300);
        } catch (IOException e) {
            System.err.println("Problem occurred creating chart.");
        }
    }

}

