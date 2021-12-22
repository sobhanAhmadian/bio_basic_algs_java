package content.frequentWord;

import content.clumpFinding.ClumpFinding;
import content.minimumSkew.MinimumSkew;
import util.SequenceUtility;
import util.Util;
import dataStructures.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public final class MostFrequentWord {

    private MostFrequentWord() {

    }

    public static void hi() {
        Scanner scanner;
        String genome = "";
        String ori = "";
        try {
            scanner = new Scanner(new File("src/res/EcoliGenome.txt"));
            genome = scanner.next().toLowerCase();
            scanner = new Scanner(new File("src/res/VibrioOri.txt"));
            ori = scanner.next();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }



        System.out.println("\n<<<<<<<<<< Skew >>>>>>>>>>");
        String skewSequence = genome;
        int[] skew = MinimumSkew.getAllSkew(skewSequence);
        for (Integer i :
                Util.getAllMinIndexes(skew)) {
            System.out.printf("%d ", i);
        }

        System.out.println("\n<<<<<<<<<< Approximate Pattern Count >>>>>>>>>>");
        String aSequence = "ACTCCTGCGTTGTAGCCGTCGTCAGTGTCGCGTGTTTTAAGTAATCCACGCATGCCGAGCATACGTGGATAGTATACACTGCGTCTATCCCAGCGCACGGTGTGACACATTAAAACTTCACGGATTCTCTGATCTTGGGGTATATGTTCGTATACAAGTTGAAGATAATCGTTGGTCTAAGCTCAGATGTCCGGAAAGATTATTGAGCTGTGGAGTTTGCCACGTCTCATGGGCTCAGCTCGCCTGGTCTAAAACACTAACAACTAAGCGCAGTTTCTCGTAGGGGCTGCTCTCACGTGGATTGGTAGCTTATGGAAGGTCCGCACTCATCTTCATTGAGACATAAATAGTCCGATTC";
        String aPattern = "TTTTAAG";
        int d = 2;
        System.out.println(aPattern + " : " + SequenceUtility.patternCount(aSequence, aPattern, d, false));
        for (Integer i :
                SequenceUtility.getPatternPositions(aSequence, aPattern, d, false)) {
            System.out.printf("%d ", i);
        }
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
        Map<String, Integer> freq = SequenceUtility.frequencyTable(sequence, k, d, withReverse);
        int max = Util.getMax(freq.values());
        freq.forEach((s, integer) -> {
            if (integer == max) patterns.add(s);
        });
        return new Pair<>(patterns, max);
    }
}
