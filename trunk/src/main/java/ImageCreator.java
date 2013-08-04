import champstruct.GlobalStructure;
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
import org.jfree.ui.Align;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.Map;

/**
 * Author: Boris Puzikov
 * Date: 7/28/13
 * Time: 9:16 PM
 */
public class ImageCreator
{
    private static final String xAxis = "";
    private static final String yAxis = "";
    private static final Color mainColor = Color.BLUE;
    private static final Shape dotShape = new Rectangle2D.Double(-3, -3, 6, 6);
    private String name;
    private GlobalStructure globalStructure;
    private DateFormat dateFormat;

    public void create(GlobalStructure globalStructure, Team team, String name)
    {
        this.globalStructure = globalStructure;
        this.name = name;
        if (globalStructure.getCountry().equals("russia"))
            dateFormat = new RussianDateFormat();
        else
            dateFormat = new SimpleDateFormat("yyyy");
        createTimeChart(team);
    }

    private void createTimeChart(Team team)
    {
        TimeSeriesCollection dataset = new TimeSeriesCollection();

        for (Map line : team.getData())
        {
            TimeSeries series = new TimeSeries(line.toString());
            Iterator i = line.entrySet().iterator();
            while (i.hasNext())
            {
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
        valueAxis.setRange(0.5, globalStructure.getTotalPositions() + 0.5);
        DateAxis dateAxis = (DateAxis) plot.getDomainAxis();
        dateAxis.setTickUnit(new DateTickUnit(DateTickUnit.YEAR, 1, dateFormat));
        plot.getDomainAxis().setVerticalTickLabels(true);
        plot.getRangeAxis().setInverted(true);
        plot.setDomainGridlinesVisible(false);
        plot.setRangeGridlinesVisible(false);

        for (int i = 0; i < dataset.getSeriesCount(); i++)
        {
            renderer.setSeriesPaint(i, mainColor);
            renderer.setSeriesShapesVisible(i, true);
            renderer.setSeriesShape(i, dotShape);
        }

        ImageIcon image = new ImageIcon(System.getProperty("user.dir") + "/background/" + globalStructure.getCountry() + ".png");
        plot.setBackgroundImage(image.getImage());
        plot.setBackgroundImageAlignment(Align.FIT);
        plot.setBackgroundAlpha(0.1f);


        try
        {
            ChartUtilities.saveChartAsJPEG(new File(name + ".jpg"), chart, 25 * globalStructure.getTotalYears(),
                    15 * globalStructure.getTotalPositions());
        } catch (IOException e)
        {
            System.err.println("Problem occurred creating chart.");
        }
    }
}

