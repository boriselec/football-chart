import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Author: Boris Puzikov
 * Date: 7/28/13
 * Time: 9:48 PM
 */
public class GraphCreator {
    public static void main(String... args) throws IOException {
        StatsLoader loader = new StatsLoader();
        String currentPath = System.getProperty("user.dir");
        List<Team> teams = loader.load(new File(currentPath + "/stats/"));

        ImageCreator imageCreator = new ImageCreator();
        for (Team team : teams)
            imageCreator.create(team, team.getEnName());
    }
}
