package champstruct;

import java.util.Map;

/**
 * User: boris
 * Date: 29.07.13
 * Time: 0:12
 */
public class GlobalStructure
{
    private int minYear;
    private int maxYear;
    private int totalPositions;
    private int maxDivision;
    private String country;

    public Map<Integer, LeagueStructure> getYearToLeague()
    {
        return yearToLeague;
    }

    Map<Integer, LeagueStructure> yearToLeague;

    public GlobalStructure(String country, Map<Integer, LeagueStructure> yearToLeague, int minYear, int maxYear, int positionCount, int maxDivision)
    {
        this.minYear = minYear;
        this.maxYear = maxYear;
        this.totalPositions = positionCount;
        this.yearToLeague = yearToLeague;
        this.maxDivision = maxDivision;
        this.country = country;
    }

    /**
     * get global team position in whole league structure
     *
     * @param year             year
     * @param divisionPosition division level (1 - top)
     * @param position         position in division (1 - winner)
     * @return global position (1 - global winner)
     */
    public int getPosition(int year, int divisionPosition, int position)
    {
        return yearToLeague.get(year).getPosition(divisionPosition, position);
    }

    public int getTotalPositions()
    {
        return totalPositions;
    }

    public int getMaxDivision()
    {
        return maxDivision;
    }

    public String getCountry()
    {
        return country;
    }

    public int getMinYear()
    {
        return minYear;
    }

    public int getMaxYear()
    {
        return maxYear;
    }

}
