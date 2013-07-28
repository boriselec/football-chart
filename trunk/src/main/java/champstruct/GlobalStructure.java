package champstruct;

import java.util.Map;

/**
 * User: boris
 * Date: 29.07.13
 * Time: 0:12
 */
public class GlobalStructure {
    Map<Integer, LeagueStructure> yearToLeague;

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
