import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: Boris Puzikov
 * Date: 7/28/13
 * Time: 9:48 PM
 */
public class GraphCreator {
    public static void main(String... args) {
        //test
        Map line1 = new HashMap<Integer, Integer>();
        line1.put(1946, 1);
        line1.put(1947, 4);
        line1.put(1948, 9);
        line1.put(1949, 1);
        Map line2 = new HashMap<Integer, Integer>();
        line2.put(1976, 1);
        line2.put(1977, 5);
        line2.put(1978, 9);
        line2.put(1979, 1);
        Team team = new Team("Dynamo");
        team.addData(line1);
        team.addData(line2);

        ImageCreator imageCreator = new ImageCreator();
        imageCreator.create(team);
    }
}
