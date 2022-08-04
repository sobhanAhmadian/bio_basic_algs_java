package content.motifFinding.bruteForce;

import util.DnaSequenceUtility;

import java.util.ArrayList;
import java.util.List;

public class BruteForceMotifFinding {
    private BruteForceMotifFinding() {
    }

    /**
     * @param sequences list of sequences you want to find motif
     * @param k the length of motif
     * @param d maximum number of mutations which is acceptable for motif
     * @return motifs that happened at list once in each of sequences
     */
    public static List<String> motifEnumeration(List<String> sequences, int k, int d) {
        List<String> motifs = new ArrayList<>();
        List<String> patternsInFirstDna = DnaSequenceUtility.getAllPatterns(sequences.get(0), k, d, false);
        for (String pattern :
                patternsInFirstDna) {
            boolean all = true;
            for (String s :
                    sequences) {
                if (DnaSequenceUtility.patternCount(s, pattern, d, false) <= 0) all = false;
            }
            if (all) motifs.add(pattern);
        }
        return motifs;
    }
}
