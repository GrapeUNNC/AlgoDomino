package unnc.cs.grape.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Fingal on 2017-04-13.
 */
public class Algorithm {
    Map<String, Integer> AlgoMap = new HashMap<String, Integer>();

    public Algorithm() {
        AlgoMap.put("bubble", 0);
        AlgoMap.put("insert", 1);
        AlgoMap.put("select", 2);
        AlgoMap.put("quick", 3);
        AlgoMap.put("merge", 4);
        AlgoMap.put("heap", 5);
    }
}
