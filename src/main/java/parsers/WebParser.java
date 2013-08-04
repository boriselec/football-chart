package parsers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * User: boris
 * Date: 04.08.13
 * Time: 21:15
 */
public abstract class WebParser
{
    private class Line
    {
        public Integer year;
        public Integer divisionPosition;
        public Integer position;
    }

    private Map<String, Line> teamToStats = new HashMap<String, Line>();

    public abstract void parse(int begin, int end) throws IOException;
}
