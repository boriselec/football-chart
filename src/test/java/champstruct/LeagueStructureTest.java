package champstruct;

import junit.framework.TestCase;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * User: boris
 * Date: 29.07.13
 * Time: 0:27
 */
public class LeagueStructureTest extends TestCase {
    @Test
    public void testGetPosition() throws Exception {
        List<Integer> data = new ArrayList<Integer>();
        data.add(10);
        data.add(20);
        data.add(30);
        LeagueStructure leagueStructure = new LeagueStructure(data);

        int expected = 35;
        int result = leagueStructure.getPosition(3, 5);
        assertEquals(expected, result);
    }
}
