import champstruct.GlobalStructure;

import java.io.IOException;
import java.util.List;

/**
 * Author: Boris Puzikov
 * Date: 7/28/13
 * Time: 9:48 PM
 */
public class GraphCreator
{

    public static void main(String... args) throws IOException
    {
        final String league = "russia";

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
