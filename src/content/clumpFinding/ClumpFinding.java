package content.clumpFinding;

import util.SequenceUtility;
import util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ClumpFinding {

    /**
     * @param sequence the sequence in which you want to find clump
     * @param k        length of k-mer
     * @param d        most possible mutations
     * @param w        with reverse complement
     * @param l        length of window
     * @param t        times of occurrences in window
     * @return k-mers that happened at least t times in a window of length l
     */
    public static List<String> findClump(String sequence, int k, int d, boolean w, int l, int t) {
        List<String> patterns = new ArrayList<>();
        int n = sequence.length() - l + 1;
        for (int i = 0; i < n; i++) {
            String frame = sequence.substring(i, i + l);
            Map<String, Integer> freq = SequenceUtility.frequencyTable(frame, k, d, w);
            freq.forEach((s, integer) -> {
                if (integer >= t)
                    patterns.add(s);
            });
        }
        Util.removeDuplicates(patterns);
        return patterns;
    }
}
