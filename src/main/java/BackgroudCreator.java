import champstruct.GlobalStructure;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * User: boris
 * Date: 03.08.13
 * Time: 23:16
 */
public class BackgroudCreator
{
    public static final String BACKGROUND = "/background/";
    private static final String CURRENT_PATH = System.getProperty("user.dir");
    private static final double xSize = 500;
    private static final double ySize = 500;
    private static final double MAGIC_INDENT = 0.4;

    private List<Color> colorList = new ArrayList<Color>();

    public void create(String league, GlobalStructure structure) throws IOException
    {
        initColors(structure.getMaxDivision());
        BufferedImage img = new BufferedImage((int) xSize, (int) ySize, BufferedImage.TYPE_3BYTE_BGR);
        img.createGraphics();
        Graphics2D g = (Graphics2D) img.getGraphics();

        double xStep = xSize / (structure.getMaxYear() - structure.getMinYear() + MAGIC_INDENT * 2);
        double yStep = ySize / (structure.getTotalPositions());

        for (int i = structure.getMinYear(); i <= structure.getMaxYear(); i++)
        {
            double x1 = -xStep * MAGIC_INDENT / 4 + (i - structure.getMinYear()) * xStep;
            double x2 = x1 + xStep;
            if (i == structure.getMinYear())
                x1 = 0;

            List<Integer> divisions = structure.getYearToLeague().get(i).getDivisionTeamsCount();

            double y1;
            double y2 = 0;//yStep / 2;
            for (int j = 0; j < divisions.size(); j++)
            {
                y1 = y2;
                y2 += divisions.get(j) * yStep;
                if (j == 0)
                    y1 = 0;
                g.setColor(colorList.get(j));
                g.fillRect((int) x1, (int) y1, (int) x2, (int) y2);
            }
            g.setColor(Color.WHITE);
            g.fillRect((int) x1, (int) y2, (int) x2, (int) ySize);
            x1 += xStep;
            x2 += xStep;
        }
        String format = "png";
        ImageIO.write(img, format, new File(CURRENT_PATH + BACKGROUND + league + "." + format));

    }

    private void initColors(int maxDivision)
    {
        for (int i = 0; i < maxDivision; i++)
        {
            int depth = 100 + i * 155 / maxDivision;
            colorList.add(new Color(depth, depth, 255));
        }
    }
}
