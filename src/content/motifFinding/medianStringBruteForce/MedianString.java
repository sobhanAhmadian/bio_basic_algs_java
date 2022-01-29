package content.motifFinding.medianStringBruteForce;

import bioObjects.profile.Profile;
import res.Sequences;
import util.SequenceUtility;

import java.util.ArrayList;
import java.util.List;

public class MedianString {

    private MedianString() {}

    public static String findMedianString(List<String> sequences, int k) {
        StringBuilder base = new StringBuilder();
        base.append("A".repeat(Math.max(0, k)));
        List<String> patterns = SequenceUtility.neighborhood(base.toString(), k);

        String bestPattern = base.toString();
        Profile bestProfile = findMedianString(sequences, k, bestPattern);
        for (String pattern :
                patterns) {
            Profile profile = findMedianString(sequences, k, pattern);
            if (profile.getHammingDistance(pattern) < bestProfile.getHammingDistance(bestPattern)) {
                bestPattern = pattern;
                bestProfile = profile;
            }
        }
        return bestPattern;
    }

    private static Profile findMedianString(List<String> sequences, int k, String consensus) {
        List<String> patterns = new ArrayList<>();
        for (String sequence :
                sequences) {
            String bestPattern = sequence.substring(0, k);
            int bestScore = SequenceUtility.hammingDistance(consensus, bestPattern);
            int n = sequence.length() - k + 1;
            for (int i = 0; i < n; i++) {
                String pattern = sequence.substring(i, i + k);
                int score = SequenceUtility.hammingDistance(consensus, pattern);
                if (score < bestScore) {
                    bestPattern = pattern;
                    bestScore = score;
                }
            }
            patterns.add(bestPattern);
        }

        return new Profile(Sequences.NUCLEOTIDES, patterns);
    }
}
