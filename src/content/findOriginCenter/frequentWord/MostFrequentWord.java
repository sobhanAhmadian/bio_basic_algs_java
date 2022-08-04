package content.findOriginCenter.frequentWord;

import util.DnaSequenceUtility;
import util.Util;
import dataStructures.Pair;

import java.util.*;

public final class MostFrequentWord {

    private MostFrequentWord() {

    }

    /**
     * find most frequent words without mismatch and reverse complement
     *
     * @param sequence the sequence in which we want to find the most frequent word
     * @param k        the length of k-mer
     * @return a pair whose first element is list of all most frequent k-mers and
     * the second element is number of times this k-mers occur
     */
    public static Pair<List<String>, Integer> findMFW(String sequence, int k) {
        return findMFW(sequence, k, 0, false);
    }

    /**
     * find most frequent word with mismatch and reverse complement
     *
     * @param sequence    the sequence in which we want to find the most frequent word
     * @param k           the length of k-mer
     * @param d           most possible mutations witch is accepted in k-mer
     * @param withReverse indicates that should count reverse complement of k-mers or not
     * @return a pair whose first element is list of all most frequent k-mers
     * with mismatches and reverse complement and the second element is number
     * of times this k-mers occur
     */
    public static Pair<List<String>, Integer> findMFW(String sequence, int k, int d, boolean withReverse) {
        List<String> patterns = new ArrayList<>();
        Map<String, Integer> freq = DnaSequenceUtility.frequencyTable(sequence, k, d, withReverse);
        int max = Util.getMax(freq.values());
        freq.forEach((s, integer) -> {
            if (integer == max) patterns.add(s);
        });
        return new Pair<>(patterns, max);
    }
}
