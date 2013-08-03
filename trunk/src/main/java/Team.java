import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Author: Boris Puzikov
 * Date: 7/28/13
 * Time: 9:36 PM
 */
public class Team {
    private String rusName;
    private String enName;
    private List<Map<Integer, Integer>> data = new ArrayList();

    public Team(String rusName, String enName) {
        this.rusName = rusName;
        this.enName = enName;
    }

    public String getRusName() {
        return rusName;
    }

    public String getEnName() {
        return enName;
    }

    public List<Map<Integer, Integer>> getData() {
        return data;
    }

    public void addData(Map data) {
        this.data.add(data);
    }
}
