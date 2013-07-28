package champstruct;

import java.util.List;

/**
 * User: boris
 * Date: 29.07.13
 * Time: 0:06
 */
public class LeagueStructure {
    private int divisionCount;
    List<Integer> divisionTeamsCount;

    public LeagueStructure(List<Integer> divisionTeamsCount) {
        this.divisionTeamsCount = divisionTeamsCount;
        this.divisionCount = divisionTeamsCount.size();
    }

    /**
     * get global team position in whole league structure
     *
     * @param divisionPosition division level (1 - top)
     * @param position         position in division (1 - winner)
     * @return global position (1 - global winner)
     */
    public int getPosition(int divisionPosition, int position) {
        divisionPosition--;

        if (divisionCount < divisionPosition)
            throw new IllegalArgumentException();
        if (divisionTeamsCount.get(divisionPosition) < position)
            throw new IllegalArgumentException();

        int result = 0;
        for (int i = 0; i < divisionPosition; i++)
            result += divisionTeamsCount.get(i);

        result += position;

        return result;
    }
}
