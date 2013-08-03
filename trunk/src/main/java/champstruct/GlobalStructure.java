package champstruct;

import java.util.ArrayList;
import java.util.Map;

/**
 * User: boris
 * Date: 29.07.13
 * Time: 0:12
 */
public class GlobalStructure {
    Map<Integer, LeagueStructure> yearToLeague;

    public GlobalStructure(Map<Integer, LeagueStructure> yearToLeague) {
        //rpl stub
        ArrayList<Integer> divisionTeamsCount = new ArrayList<Integer>();
        divisionTeamsCount.add(new Integer(16));
        LeagueStructure leagueStructure = new LeagueStructure(divisionTeamsCount);
        for (int i = 1992; i <= 2013; i++)
            yearToLeague.put(i, leagueStructure);
        this.yearToLeague = yearToLeague;
//        this.yearToLeague = yearToLeague;
    }

    /**
     * get global team position in whole league structure
     *
     * @param year             year
     * @param divisionPosition division level (1 - top)
     * @param position         position in division (1 - winner)
     * @return global position (1 - global winner)
     */
    public int getPosition(int year, int divisionPosition, int position) {
        return yearToLeague.get(year).getPosition(divisionPosition, position);
    }
}
