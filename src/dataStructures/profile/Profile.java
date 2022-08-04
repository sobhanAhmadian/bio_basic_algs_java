package dataStructures.profile;

import util.DnaSequenceUtility;
import util.Util;

import javax.swing.*;
import java.util.Arrays;
import java.util.List;

public class Profile {
    private List<String> sequences;
    private final List<Character> elements;
    private final double[][] profile;
    private final int t;
    private final int k;


    /**
     * @param elements line nucleotides or amino acids
     * @param sequences sequences with that you want to make profile
     */
    public Profile(Character[] elements, String[] sequences, boolean pseudoCount) {
        this(elements, Arrays.asList(sequences), pseudoCount);
    }

    /**
     * @param elements line nucleotides or amino acids
     * @param sequences sequences with that you want to make profile
     */
    public Profile(Character[] elements, List<String> sequences, boolean pseudoCount) {
        if (sequences.size() <= 0) throw new IllegalArgumentException("sequence list is empty");

        this.t = elements.length;
        this.k = sequences.get(0).length();
        this.elements = Arrays.asList(elements);
        this.sequences = sequences;
        profile = new double[t][k];

        if (pseudoCount) makeProfileWithPseudoCount();
        else makeProfile();
    }

    /**
     * @param elements line nucleotides or amino acids
     * @param sequences sequences with that you want to make profile
     */
    public Profile(Character[] elements, String[] sequences) {
        this(elements, Arrays.asList(sequences));
    }

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
     * @param elements line nucleotides or amino acids
     * @param profile matrix of probabilities
     */
    public Profile(Character[] elements, double[][] profile) {
        this.elements = Arrays.asList(elements);
        this.profile = profile;
        this.k = profile[0].length;
        this.t = profile.length;
    }

    /**
     * this method fills the matrix
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


    /**
     * this method fills the matrix with pseudoCount
     */
    private void makeProfileWithPseudoCount() {
        for (int i = 0; i < k; i++) {
            for (String sequence :
                    sequences) {
                profile[elements.indexOf(sequence.charAt(i))][i]++;
            }
        }
        for (int i = 0; i < t; i++) {
            for (int j = 0; j < k; j++) {
                profile[i][j]++;
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
            distance += DnaSequenceUtility.hammingDistance(consensus, sequence);
        }
        return distance;
    }

    public String getConsensus() {
        StringBuilder consensus = new StringBuilder();
        for (int i = 0; i < k; i++) {
            double[] array = new double[t];
            for (int j = 0; j < t; j++) {
                array[j] = profile[j][i];
            }
            double max = Util.getMax(array);
            for (int j = 0; j < t; j++) {
                if (profile[j][i] == max) {
                    consensus.append(elements.get(j));
                    break;
                }
            }
        }
        return consensus.toString();
    }

    public double probabilityOf(String sequence) {
        if (sequence.length() != k) throw new IllegalArgumentException("sequence length should be equal to profile length");
        double p = 1;
        for (int i = 0; i < k; i++) {
            char c = sequence.charAt(i);
            p *= profile[elements.indexOf(c)][i];
        }
        return p;
    }

    public String getMostProbable(String sequence) {
        if (sequence.length() < getK()) throw new IllegalArgumentException();
        String mostProbable = "";
        double maxProb = -1;
        int n = sequence.length() - getK() + 1;
        for (int i = 0; i < n; i++) {
            String pattern = sequence.substring(i, i + getK());
            if (probabilityOf(pattern) > maxProb) {
                mostProbable = pattern;
                maxProb = probabilityOf(pattern);
            }
        }
        return mostProbable;
    }

    public int getK() {
        return k;
    }
}
