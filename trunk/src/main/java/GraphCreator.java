import champstruct.GlobalStructure;
import parsers.RussiaParser;
import parsers.WebParser;

import javax.script.ScriptException;
import java.io.IOException;
import java.util.List;

/**
 * Author: Boris Puzikov
 * Date: 7/28/13
 * Time: 9:48 PM
 */
public class GraphCreator
{

    public static void main(String... args) throws IOException, ScriptException
    {
        final String league = "russia";

        WebParser parser = new RussiaParser();
        parser.parse(1994, 2012);

        StatsLoader loader = new StatsLoader();
        GlobalStructure globalStructure = loader.loadLeagueStructure(league);
        List<Team> teams = loader.loadTeamsStats(league, globalStructure);

        BackgroudCreator backgroudCreator = new BackgroudCreator();
        backgroudCreator.create(league, globalStructure);

        ImageCreator imageCreator = new ImageCreator();
        for (Team team : teams)
        {
            imageCreator.create(globalStructure, team, team.getLocalName());
            imageCreator.create(globalStructure, team, team.getEnName());
        }
    }
}
