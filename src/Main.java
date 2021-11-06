import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner;
        String genome = "";
        String ori = "";
        try {
            scanner = new Scanner(new File("src/EcoliGenome.txt"));
            genome = scanner.next().toLowerCase();
            scanner = new Scanner(new File("src/VibrioOri.txt"));
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
            System.out.printf("%d : %5d : ", i, findMostFrequentWordsAlg2(ori, i).getE());
            for (String word :
                    findMostFrequentWordsAlg2(ori, i).getT()) {
                System.out.printf("%9s%s", word, " | ");
            }
            System.out.println();
        }
        System.out.println();
        System.out.println("cttgatcat and " + reverseComplement("cttgatcat") + " are complementary");
        System.out.println();
        System.out.println("cttgatcat positions in genome : " + getPatternPositions(genome, "cttgatcat"));
        System.out.println("atgatcaag positions in genome : " + getPatternPositions(genome, "atgatcaag"));


        System.out.println("\n<<<<<<<<<< Clumps >>>>>>>>>>");
        String sequenceForClumpFinding = genome.substring(0, 200);
        for (String word :
                findClump(sequenceForClumpFinding, 9, 500, 3)) {
            System.out.printf("%s ", word);
        }

        System.out.println("\n<<<<<<<<<< Skew >>>>>>>>>>");
        String skewSequence = genome;
        int[] skew = giveAllSkew(skewSequence);
        for (Integer i :
                Util.getAllMinIndexes(skew)) {
            System.out.printf("%d ", i);
        }
    }

    private static StringBuilder getPatternPositions(String sequence, String pattern) {
        StringBuilder list = new StringBuilder();
        for (int i :
                findPatterPositionsInGenome(sequence, pattern)) {
            list.append(i).append(" ");
        }
        return list;
    }

    public static int patterCount(String sequence, String pattern) {
        sequence = sequence.toLowerCase();
        pattern = pattern.toLowerCase();
        int counter = 0;
        char startChar = pattern.charAt(0);
        for (int i = 0; i <= sequence.length() - pattern.length(); i++) {
            if (sequence.charAt(i) == startChar) {
                if (sequence.startsWith(pattern, i))
                    counter++;
            }
        }
        return counter;
    }

    public static List<Integer> findPatterPositionsInGenome(String sequence, String pattern) {
        List<Integer> positions = new ArrayList<>();
        char startChar = pattern.charAt(0);
        for (int i = 0; i <= sequence.length() - pattern.length(); i++) {
            if (sequence.charAt(i) == startChar) {
                if (sequence.startsWith(pattern, i))
                    positions.add(i);
            }
        }
        return positions;
    }

    public static Pair<List<String>, Integer> findMostFrequentWordsAlg1(String sequence, int k) {
        List<String> patterns = new ArrayList<>();
        int n = sequence.length() - k + 1;
        int[] counts = new int[n];
        for (int i = 0; i < n; i++) {
            counts[i] = patterCount(sequence, sequence.substring(i, i + k));
        }
        int max = Util.getMax(counts);
        for (int i = 0; i < n; i++) {
            if (counts[i] == max)
                patterns.add(sequence.substring(i, i + k));
        }
        Util.removeDuplicates(patterns);
        return new Pair<>(patterns, max);
    }

    public static Pair<List<String>, Integer> findMostFrequentWordsAlg2(String sequence, int k) {
        List<String> patterns = new ArrayList<>();
        Map<String, Integer> freq = frequencyTable(sequence, k);
        int max = Util.getMax(freq.values());
        freq.forEach((s, integer) -> {
            if (integer == max) patterns.add(s);
        });
        return new Pair<>(patterns, max);
    }

    private static Map<String, Integer> frequencyTable(String sequence, int k) {
        Map<String, Integer> freq = new HashMap<>();
        int n = sequence.length() - k + 1;
        for (int i = 0; i < n; i++) {
            String pattern = sequence.substring(i, i + k);
            if (freq.containsKey(pattern))
                freq.replace(pattern, freq.get(pattern) + 1);
            else
                freq.put(pattern, 1);
        }
        return freq;
    }

    public static String reverseComplement(String dnaSeq) {
        StringBuilder rc = new StringBuilder();
        for (int i = dnaSeq.length() - 1; i >= 0; i--) {
            rc.append(complement(dnaSeq.charAt(i)));
        }
        return rc.toString();
    }

    public static char complement(char n) {
        switch (n) {
            case 'A':
                return 'T';
            case 'T':
                return 'A';
            case 'C':
                return 'G';
            case 'G':
                return 'C';
            case 'a':
                return 't';
            case 't':
                return 'a';
            case 'c':
                return 'g';
            case 'g':
                return 'c';
            default:
                return 'n';
        }
    }

    public static List<String> findClump(String sequence, int k, int l, int t) {
        List<String> patterns = new ArrayList<>();
        int n = sequence.length() - l + 1;
        for (int i = 0; i < n; i++) {
            String window = sequence.substring(i, i + l);
            Map<String, Integer> freq = frequencyTable(window, k);
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

    public static int[] giveAllSkew(String sequence) {
        sequence = sequence.toLowerCase();
        int[] skew = new int[sequence.length() + 1];
        skew[0] = 0;
        for (int i = 1; i < sequence.length() + 1; i++) {
            if (sequence.charAt(i - 1) == 'g') skew[i] = skew[i - 1] + 1;
            else if (sequence.charAt(i - 1) == 'c') skew[i] = skew[i - 1] - 1;
            else skew[i] = skew[i - 1];
        }
        return skew;
    }
}
