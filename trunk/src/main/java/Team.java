import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Author: Boris Puzikov
 * Date: 7/28/13
 * Time: 9:36 PM
 */
public class Team
{
    private String localName;
    private String enName;
    private List<Map<Integer, Integer>> data = new ArrayList();

    public Team(String localName, String enName)
    {
        this.localName = localName;
        this.enName = enName;
    }

    public String getLocalName()
    {
        if (localName != null)
            return localName;
        else
            return enName;
    }

    public String getEnName()
    {
        if (enName != null)
            return enName;
        else
            return localName;
    }

    public List<Map<Integer, Integer>> getData()
    {
        return data;
    }

    public void addData(Map data)
    {
        this.data.add(data);
    }
}
