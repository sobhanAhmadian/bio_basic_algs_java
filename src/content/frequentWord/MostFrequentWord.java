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

        System.out.println("<<<<<<<<<< DATASET INFORMATION >>>>>>>>>>");
        System.out.println("Genome Length: " + genome.length());
        System.out.println("Ori Length :" + ori.length());
        int[] numbers = new int[30];
        for (int i = 0; i < ori.length(); i++) {
            numbers[((int) ori.charAt(i)) - 97] += 1;
        }
        System.out.println("a : " + numbers[0] + "\nc : " + numbers[2] + "\ng : " + numbers[6] + "\nt : " + numbers[19]);
        System.out.println("a + t : " + (numbers[0] + numbers[19]));
        System.out.println("c + g : " + (numbers[2] + numbers[6]));
        System.out.println("<<<<<<<<<< END >>>>>>>>>>");

        System.out.println("\nk : count : ");
        for (int i = 3; i < 10; i++) {
            System.out.printf("%d : %5d : ", i, findMFW(ori, i).getE());
            for (String word :
                    findMFW(ori, i).getT()) {
                System.out.printf("%9s%s", word, " | ");
            }
            System.out.println();
        }
        System.out.println();
        System.out.println("cttgatcat and " + SequenceUtility.reverseComplement("cttgatcat") + " are complementary");
        System.out.println();
        System.out.println("cttgatcat positions in genome : " + SequenceUtility.getPatternPositions(genome, "cttgatcat", 0, false));
        System.out.println("atgatcaag positions in genome : " + SequenceUtility.getPatternPositions(genome, "atgatcaag", 0, false));


        System.out.println("\n<<<<<<<<<< Clumps >>>>>>>>>>");
        String sequenceForClumpFinding = genome.substring(3923620, 3924120);
        for (String word :
                ClumpFinding.findClump(sequenceForClumpFinding, 9, 500, 2)) {
            System.out.printf("%s ", word);
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
