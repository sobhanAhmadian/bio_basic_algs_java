package content.motifFinding.profileMostProbable;

import bioObjects.profile.Profile;
import util.SequenceUtility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MostProbableString {

    private MostProbableString() {
    }

    /**
     * @param sequences list of sequences you want to find motif
     * @param k         the length of motif
     * @return motifs that happened at list once in each of sequences
     */
    public static List<String> findMotif(List<String> sequences, Character[] elements, int k) {
        int t = sequences.size();
        String[] bestMotif = new String[t];
        for (int i = 0; i < t; i++) bestMotif[i] = sequences.get(i).substring(0, k);

        String[] motif = new String[t];
        List<String> patternsInFirstDna = SequenceUtility.getAllPatterns(sequences.get(0), k, 0, false);
        for (String pattern :
                patternsInFirstDna) {
            motif[0] = pattern;
            for (int i = 1; i < t; i++)
                motif[i] = getMostProbable(
                        new Profile(elements, Arrays.copyOfRange(motif, 0, i)),
                        sequences.get(i)
                );
            Profile bestProfile = new Profile(elements, bestMotif);
            Profile newProfile = new Profile(elements, motif);
            if (newProfile.getHammingDistance(newProfile.getConsensus()) <
                    bestProfile.getHammingDistance(bestProfile.getConsensus())
            )
                bestMotif = Arrays.copyOf(motif, motif.length);
        }
        return List.of(bestMotif);
    }

    public static String getMostProbable(Profile profile, String sequence) {
        if (sequence.length() < profile.getK()) throw new IllegalArgumentException();
        String mostProbable = "";
        double maxProb = -1;
        int n = sequence.length() - profile.getK() + 1;
        for (int i = 0; i < n; i++) {
            String pattern = sequence.substring(i, i + profile.getK());
            if (profile.probabilityOf(pattern) > maxProb) {
                mostProbable = pattern;
                maxProb = profile.probabilityOf(pattern);
            }
        }
        return mostProbable;
    }
}
