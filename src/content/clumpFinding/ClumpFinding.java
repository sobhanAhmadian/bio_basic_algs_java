package content.clumpFinding;

import util.SequenceUtility;
import util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ClumpFinding {

    public static List<String> findClump(String sequence, int k, int l, int t) {
        List<String> patterns = new ArrayList<>();
        int n = sequence.length() - l + 1;
        for (int i = 0; i < n; i++) {
            String frame = sequence.substring(i, i + l);
            Map<String, Integer> freq = SequenceUtility.frequencyTable(frame, k, 0, false);
            for (int j = 0; j < freq.size(); j++) {
                freq.forEach((s, integer) -> {
                    if (integer >= t)
                        patterns.add(s);
                });
            }
        }
        Util.removeDuplicates(patterns);
        return patterns;
    }
}
