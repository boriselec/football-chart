import champstruct.GlobalStructure;
import champstruct.LeagueStructure;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * User: boris
 * Date: 03.08.13
 * Time: 17:57
 */
public class StatsLoader
{
    private static final String LINE_REGEX = "(\\d{4}) (\\d+) (\\d+)";
    private static final Pattern LINE_PATTERN = Pattern.compile(LINE_REGEX);
    private static final String LEAGUE_LINE_REGEX = "(\\d{4})( \\d+)+";
    private static final Pattern LEAGUE_LINE_PATTERN = Pattern.compile(LEAGUE_LINE_REGEX);

    private static final String STATS_PATH = "./stats/";
    private static final String TEAMS_PATH = "./teams/";
    private static final String STRUCTURE_PATH = "/structure.txt";

    private GlobalStructure globalStructure;// = new champstruct.GlobalStructure(new HashMap<Integer, LeagueStructure>());

    public List<Team> loadTeamsStats(String league, GlobalStructure globalStructure) throws IOException
    {
        File folderName = new File(STATS_PATH + league + TEAMS_PATH);
        this.globalStructure = globalStructure;
        List<Team> teams = new ArrayList<Team>();
        if (!folderName.isDirectory())
            throw new IllegalArgumentException(folderName.getName() + " is not directory");
        for (final File file : folderName.listFiles())
        {
            if (file.isFile())
                teams.add(parseTeamStats(file));
        }

        return teams;
    }

    private Team parseTeamStats(File file) throws IOException
    {
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);

        String rusName = br.readLine();
        String enName = br.readLine();

        if (rusName == null || enName == null)
            throw new IOException("wrong file format");
        Team team = new Team(rusName, enName);

        Map<Integer, Integer> rowData = new TreeMap<Integer, Integer>();

        String line;
        while ((line = br.readLine()) != null)
        {
            Matcher matcher = LINE_PATTERN.matcher(line);
            if (!matcher.matches())
                throw new IOException("wrong file format");

            Integer year = Integer.parseInt(matcher.group(1));
            Integer divisionPosition = Integer.parseInt(matcher.group(2));
            Integer localPosition = Integer.parseInt(matcher.group(3));
            Integer globalPosition = globalStructure.getPosition(year, divisionPosition, localPosition);

            rowData.put(year, globalPosition);
        }

        team.addData(rowData);
        //TODO: extract different lines

        return team;
    }

    public GlobalStructure loadLeagueStructure(String league) throws IOException
    {
        File file = new File(STATS_PATH + league + STRUCTURE_PATH);
        if (!file.isFile())
            throw new IllegalArgumentException("missing league structure file");

        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);

        Map<Integer, LeagueStructure> globalStructure = new HashMap<Integer, LeagueStructure>();
        String line;
        int minYear = 3000;
        int maxYear = 0;
        int positionCount = 0;
        int maxDivision = 0;
        while ((line = br.readLine()) != null)
        {
            Matcher matcher = LEAGUE_LINE_PATTERN.matcher(line);
            if (!matcher.matches())
                throw new IOException("wrong file format");

            Integer year = Integer.parseInt(matcher.group(1));
            if (year > maxYear)
                maxYear = year;
            if (year < minYear)
                minYear = year;

            List<Integer> leagueTeamsCount = new ArrayList<Integer>();
            String[] groups = line.split(" ");
            for (int i = 1; i < groups.length; i++)
            {
                leagueTeamsCount.add(Integer.parseInt(groups[i]));
                if (groups.length - 1 > maxDivision)
                    maxDivision = groups.length - 1;
            }

            int sum = 0;
            for (int i : leagueTeamsCount)
                sum += i;
            if (sum > positionCount)
                positionCount = sum;

            globalStructure.put(year, new LeagueStructure(leagueTeamsCount));
            //TODO: merge LeagueStructure with same structure
        }
        return new GlobalStructure(league, globalStructure, minYear, maxYear, positionCount, maxDivision);
    }
}
