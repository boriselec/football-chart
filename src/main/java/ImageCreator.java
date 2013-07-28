import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.io.File;
import java.io.IOException;
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
            ChartUtilities.saveChartAsJPEG(new File(team.getName() + ".jpg"), chart, 500, 300);
        } catch (IOException e) {
            System.err.println("Problem occurred creating chart.");
        }
    }
}

