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
        String sequenceForClumpFinding = genome.substring(3923620, 3924120);
        for (String word :
                findClump(sequenceForClumpFinding, 9, 500, 2)) {
            System.out.printf("%s ", word);
        }

        System.out.println("\n<<<<<<<<<< Skew >>>>>>>>>>");
        String skewSequence = genome;
        int[] skew = giveAllSkew(skewSequence);
        for (Integer i :
                Util.getAllMinIndexes(skew)) {
            System.out.printf("%d ", i);
        }

        System.out.println("\n<<<<<<<<<< Approximate Pattern Count >>>>>>>>>>");
        String aSequence = "ACTCCTGCGTTGTAGCCGTCGTCAGTGTCGCGTGTTTTAAGTAATCCACGCATGCCGAGCATACGTGGATAGTATACACTGCGTCTATCCCAGCGCACGGTGTGACACATTAAAACTTCACGGATTCTCTGATCTTGGGGTATATGTTCGTATACAAGTTGAAGATAATCGTTGGTCTAAGCTCAGATGTCCGGAAAGATTATTGAGCTGTGGAGTTTGCCACGTCTCATGGGCTCAGCTCGCCTGGTCTAAAACACTAACAACTAAGCGCAGTTTCTCGTAGGGGCTGCTCTCACGTGGATTGGTAGCTTATGGAAGGTCCGCACTCATCTTCATTGAGACATAAATAGTCCGATTC";
        String aPattern = "TTTTAAG";
        int d = 2;
        System.out.println(aPattern + " : " + approximatePatternCount(aSequence, aPattern, d));
        for (Integer i :
                findApproximatePatternPositionsInGenome(aSequence, aPattern, d)) {
            System.out.printf("%d ", i);
        }
    }

    private static StringBuilder getPatternPositions(String sequence, String pattern) {
        StringBuilder list = new StringBuilder();
        for (int i :
                findPatternPositionsInGenome(sequence, pattern)) {
            list.append(i).append(" ");
        }
        return list;
    }

    public static int patternCount(String sequence, String pattern) {
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

    public static int approximatePatternCount(String sequence, String pattern, int d) {
        sequence = sequence.toLowerCase();
        pattern = pattern.toLowerCase();
        int counter = 0;
        char startChar = pattern.charAt(0);
        for (int i = 0; i <= sequence.length() - pattern.length(); i++) {
            if (hammingDistance(pattern, sequence.substring(i, i + pattern.length())) <= d)
                counter++;
        }
        return counter;
    }

    public static List<Integer> findPatternPositionsInGenome(String sequence, String pattern) {
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

    public static List<Integer> findApproximatePatternPositionsInGenome(String sequence, String pattern, int d) {
        List<Integer> positions = new ArrayList<>();
        char startChar = pattern.charAt(0);
        for (int i = 0; i <= sequence.length() - pattern.length(); i++) {
            if (hammingDistance(pattern, sequence.substring(i, i + pattern.length())) <= d)
                positions.add(i);
        }
        return positions;
    }

    public static Pair<List<String>, Integer> findMostFrequentWordsAlg1(String sequence, int k) {
        List<String> patterns = new ArrayList<>();
        int n = sequence.length() - k + 1;
        int[] counts = new int[n];
        for (int i = 0; i < n; i++) {
            counts[i] = patternCount(sequence, sequence.substring(i, i + k));
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

    /**
     * find most frequent word with mismatch and reverse complement
     **/
    public static Pair<List<String>, Integer> findMFW(String sequence, int k, int d) {
        List<String> patterns = new ArrayList<>();
        Map<String, Integer> freq = approximateFrequencyTable(sequence, k, d);
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

    private static Map<String, Integer> approximateFrequencyTable(String sequence, int k, int d) {
        Map<String, Integer> freq = new HashMap<>();
        int n = sequence.length() - k + 1;
        for (int i = 0; i < n; i++) {
            String pattern = sequence.substring(i, i + k);
            for (String neighbor :
                    neighborhood(pattern, d)) {
                if (freq.containsKey(neighbor))
                    freq.replace(neighbor, freq.get(neighbor) + 1);
                else
                    freq.put(neighbor, 1);

                String rv = reverseComplement(neighbor);
                if (freq.containsKey(rv))
                    freq.replace(rv, freq.get(rv) + 1);
                else
                    freq.put(rv, 1);
            }
        }
        return freq;
    }

    public static List<String> neighborhood(String sequence, int d) {
        List<String> neighbours = new ArrayList<>(neighbors(sequence, d));
        neighbours.add(sequence);
        return neighbours;
    }

    public static List<String> neighbors(String sequence, int d) {
        List<String> neighbours = new ArrayList<>();
        if (d == 0) return neighbours;
        neighbours.addAll(neighbors(sequence, d - 1));
        neighbours.addAll(neighbors(sequence, 0, d));
        return neighbours;
    }

    public static List<String> neighbors(String sequence, int start, int numberOfMutations) {
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

    public static String replace(String sequence, int position, char c) {
        if (position == sequence.length() - 1)
            return sequence.substring(0, position) + c;
        return sequence.substring(0, position) + c + sequence.substring(position + 1);
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

    public static int hammingDistance(String p, String q) {
        if (p.length() != q.length()) throw new IllegalArgumentException("p and q should have same length");

        int distance = 0;
        for (int i = 0; i < p.length(); i++) {
            if (p.charAt(i) != q.charAt(i)) distance++;
        }
        return distance;
    }
}
