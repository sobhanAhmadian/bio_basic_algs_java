package bioObjects.profile;

import util.SequenceUtility;

import java.util.Arrays;
import java.util.List;

public class Profile {
    private final List<String> sequences;
    private final List<Character> elements;
    private final double[][] profile;
    private final int t;
    private final int k;

    /**
     * @param elements line nucleotides or amino acids
     * @param sequences sequences with that you want to make profile
     */
    public Profile(Character[] elements, List<String> sequences) {
        if (sequences.size() <= 0) throw new IllegalArgumentException("sequence list is empty");

        this.t = elements.length;
        this.k = sequences.get(0).length();
        this.elements = Arrays.asList(elements);
        this.sequences = sequences;
        profile = new double[t][k];

        makeProfile();
    }

    /**
     * this method fills the matrix
     * @param sequences sequences that you want to make profile with
     */
    private void makeProfile() {
        for (int i = 0; i < k; i++) {
            for (String sequence :
                    sequences) {
                profile[elements.indexOf(sequence.charAt(i))][i]++;
            }
        }
        for (int i = 0; i < t; i++) {
            for (int j = 0; j < k; j++) {
                profile[i][j] /= sequences.size();
            }
        }
    }

    public void printProfile() {
        for (int i = 0; i < t; i++) {
            System.out.print(elements.get(i) + " : ");
            for (int j = 0; j < k; j++) {
                System.out.printf("%.2f ",profile[i][j]);
            }
            System.out.println();
        }
    }

    public double getEntropy(int base) {
        double entropy = 0;
        for (int i = 0; i < k; i++) {
            for (int j = 0; j < t; j++) {
                entropy += -1 * profile[j][i] *
                        Math.log10(profile[j][i] == 0 ? 1 : profile[j][i]) / Math.log10(base);
            }
        }
        return entropy;
    }

    public double getHammingDistance(String consensus) {
        double distance = 0;
        for (String sequence :
                sequences) {
            distance += SequenceUtility.hammingDistance(consensus, sequence);
        }
        return distance;
    }
}
