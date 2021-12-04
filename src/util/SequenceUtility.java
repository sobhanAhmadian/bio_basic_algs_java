package util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class SequenceUtility {

    private SequenceUtility() {
    }

    public static int hammingDistance(String p, String q) {
        if (p.length() != q.length()) throw new IllegalArgumentException("p and q should have same length");

        int distance = 0;
        for (int i = 0; i < p.length(); i++) {
            if (p.charAt(i) != q.charAt(i)) distance++;
        }
        return distance;
    }

    public static String reverseComplement(String dnaSeq) {
        StringBuilder rc = new StringBuilder();
        for (int i = dnaSeq.length() - 1; i >= 0; i--) {
            rc.append(NucleotideUtility.complement(dnaSeq.charAt(i)));
        }
        return rc.toString();
    }

    public static String replace(String sequence, int position, char c) {
        if (position == sequence.length() - 1)
            return sequence.substring(0, position) + c;
        return sequence.substring(0, position) + c + sequence.substring(position + 1);
    }

    /**
     * @param mostMutations most hamming distance by sequence
     * @return all sequences that have at most 'mostMutations hamming distance' with sequence
     */
    public static List<String> neighborhood(String sequence, int mostMutations) {
        List<String> neighbours = new ArrayList<>(neighbors(sequence, mostMutations));
        neighbours.add(sequence);
        return neighbours;
    }

    private static List<String> neighbors(String sequence, int mostMutations) {
        List<String> neighbours = new ArrayList<>();
        if (mostMutations == 0) return neighbours;
        neighbours.addAll(neighbors(sequence, mostMutations - 1));
        neighbours.addAll(neighbors(sequence, 0, mostMutations));
        return neighbours;
    }

    private static List<String> neighbors(String sequence, int start, int numberOfMutations) {
        List<String> neighbors = new ArrayList<>();
        if (numberOfMutations <= 0) {
            neighbors.add(sequence);
            return neighbors;
        } else if (start == sequence.length() - numberOfMutations + 1) {
            return neighbors;
        }

        neighbors.addAll(neighbors(sequence, start + 1, numberOfMutations));

        List<Character> list = new ArrayList<>();
        list.add('A');
        list.add('T');
        list.add('C');
        list.add('G');
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) == sequence.charAt(start))
                list.remove(i);
        }

        List<String> temp = new ArrayList<>();
        for (char c :
                list) {
            temp.add(replace(sequence, start, c));
        }
        for (String n :
                temp) {
            neighbors.addAll(neighbors(n, start + 1, numberOfMutations - 1));
        }
        return neighbors;
    }

    /**
     * @param sequence          the sequence in witch we want to count occurrences of pattern
     * @param pattern           the pattern for which we want to find number of occurrences
     * @param d                 most possible mutations witch is accepted in pattern
     * @param reverseComplement indicates that should count reverse complement of pattern or not
     * @return a map witch keys are the k-mers and values are number of occurrence
     */
    public static int patternCount(String sequence, String pattern, int d, boolean reverseComplement) {
        sequence = sequence.toLowerCase();
        pattern = pattern.toLowerCase();
        int counter = 0;
        for (int i = 0; i <= sequence.length() - pattern.length(); i++) {
            String frame = sequence.substring(i, i + pattern.length());
            if (SequenceUtility.hammingDistance(pattern, frame) <= d)
                counter++;
            if (reverseComplement)
                if (SequenceUtility.hammingDistance(reverseComplement(pattern), frame) <= d)
                    counter++;
        }
        return counter;
    }

    /**
     * to find positions of a patten in sequence, with mismatch and reverse complement
     *
     * @param sequence          the sequence in witch we want to find positions of pattern
     * @param pattern           the pattern witch we want to find its positions
     * @param d                 most possible mutations witch is accepted in pattern
     * @param reverseComplement indicates that should consider reverse complement of pattern or not
     * @return positions of pattern
     */
    public static List<Integer> getPatternPositions(String sequence, String pattern, int d, boolean reverseComplement) {
        List<Integer> positions = new ArrayList<>();
        for (int i = 0; i <= sequence.length() - pattern.length(); i++) {
            String frame = sequence.substring(i, i + pattern.length());
            if (SequenceUtility.hammingDistance(pattern, frame) <= d)
                positions.add(i);
            if (reverseComplement)
                if (SequenceUtility.hammingDistance(SequenceUtility.reverseComplement(pattern), frame) <= d)
                    positions.add(i);
        }
        return positions;
    }

    /**
     * frequency table of each k-mer in a sequence with consideration of mismatches and reverse complement
     *
     * @param sequence    the sequence in witch we want to find frequency of k-mers
     * @param k           the length of k-mers
     * @param d           most possible mutations witch is accepted in k-mer
     * @param withReverse indicates that should count reverse complement of k-mers or not
     * @return a map witch keys are the k-mers and values are number of occurrence
     */
    public static Map<String, Integer> frequencyTable(String sequence, int k, int d, boolean withReverse) {
        Map<String, Integer> freq = new HashMap<>();
        int n = sequence.length() - k + 1;
        for (int i = 0; i < n; i++) {
            String pattern = sequence.substring(i, i + k);
            for (String neighbor :
                    SequenceUtility.neighborhood(pattern, d)) {
                if (freq.containsKey(neighbor))
                    freq.replace(neighbor, freq.get(neighbor) + 1);
                else
                    freq.put(neighbor, 1);

                if (withReverse) {
                    String rv = SequenceUtility.reverseComplement(neighbor);
                    if (freq.containsKey(rv))
                        freq.replace(rv, freq.get(rv) + 1);
                    else
                        freq.put(rv, 1);
                }
            }
        }
        return freq;
    }
}
