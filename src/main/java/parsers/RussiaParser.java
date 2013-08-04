package parsers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.Iterator;

/**
 * User: boris
 * Date: 04.08.13
 * Time: 21:15
 */
public class RussiaParser extends WebParser
{
    public static final String COUNTRY = "russia";
    private static final String URL = "http://www.stat-football.com/a/rus.php";

    @Override
    public void parse(int begin, int end) throws IOException
    {
        parseStructure(begin, end);
        parseTeams(begin, end);
    }

    private void parseTeams(int begin, int end) throws IOException
    {
        int divisions = 2;
        int totalPages = divisions * (end - begin + 1);
        int count = 0;
        //parse two divisions
        for (int division = 1; division <= divisions; division++)
            for (int year = begin; year <= end; year++)
            {
                try
                {
                    Document document = Jsoup.connect(URL + divisionUrl(division) + yearUrl(year)).get();
                    System.out.println("parsed " + ++count + " pages of " + totalPages);
                    Element table = document.select("table[id=tb01]").first().select("tbody[class=f11]").first();
                    Iterator<Element> iterName = table.select("td[class=nw s10 tal").iterator();

                    int positionCount = 1;
                    while (iterName.hasNext())
                    {
                        String name = iterName.next().text();
                        int position = positionCount++;
                        System.out.println(name + " " + position);
                    }
                } catch (NullPointerException e)
                {
                    throw new NullPointerException("year" + year + "div" + division);
                }
            }

    }

    private void parseStructure(int begin, int end)
    {
        //TODO: implement
    }

    private String divisionUrl(int division)
    {
        return "?b=" + division + "0";
    }

    private String yearUrl(int year)
    {
        if (year <= 2010)
            return "&d=" + year + String.valueOf(year).substring(2, 4);
        else
            return "&d=" + year + String.valueOf(year + 1).substring(2, 4);
    }
}
