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
public class StatsLoader {
    private static final String LINE_REGEX = "(\\d{4}) (\\d+) (\\d+)";
    private static final Pattern LINE_PATTERN = Pattern.compile(LINE_REGEX);

    private GlobalStructure globalStructure = new GlobalStructure(new HashMap<Integer, LeagueStructure>());

    public List<Team> load(File folderName) throws IOException {
        List<Team> teams = new ArrayList<Team>();
        if (!folderName.isDirectory())
            throw new IllegalArgumentException(folderName.getName() + " is not directory");
        for (final File file : folderName.listFiles()) {
            if (file.isFile())
                teams.add(parseStats(file));
        }

        return teams;
    }

    private Team parseStats(File file) throws IOException {
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);

        String rusName = br.readLine();
        String enName = br.readLine();

        if (rusName == null || enName == null)
            throw new IOException("wrong file format");
        Team team = new Team(rusName, enName);

        Map<Integer, Integer> rowData = new TreeMap<Integer, Integer>();

        String line;
        while ((line = br.readLine()) != null) {
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
}
