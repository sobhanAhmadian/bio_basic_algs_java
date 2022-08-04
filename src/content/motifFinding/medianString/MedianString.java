package content.motifFinding.medianString;

import content.motifFinding.MotifFinder;
import data.Sequences;
import dataStructures.profile.Profile;
import util.DnaSequenceUtility;

import java.util.ArrayList;
import java.util.List;

public class MedianString implements MotifFinder {

    private List<String> findBestMotif(List<String> sequences, int k, String consensus) {
        List<String> patterns = new ArrayList<>();
        for (String sequence :
                sequences) {
            String bestPattern = sequence.substring(0, k);
            int bestScore = DnaSequenceUtility.hammingDistance(consensus, bestPattern);
            int n = sequence.length() - k + 1;
            for (int i = 0; i < n; i++) {
                String pattern = sequence.substring(i, i + k);
                int score = DnaSequenceUtility.hammingDistance(consensus, pattern);
                if (score < bestScore) {
                    bestPattern = pattern;
                    bestScore = score;
                }
            }
            patterns.add(bestPattern);
        }

        return patterns;
    }

    @Override
    public List<String> findMotif(List<String> sequences, int k) {
        String bestPattern = "A".repeat(Math.max(0, k));
        List<String> bestMotif = findBestMotif(sequences, k, bestPattern);
        for (int i = 0; i < Math.pow(4, k); i++) {
            String pattern = DnaSequenceUtility.numberToPattern(i, k);
            List<String> motif = findBestMotif(sequences, k, pattern);

            if (new Profile(Sequences.NUCLEOTIDES, motif).getHammingDistance(pattern) < new Profile(Sequences.NUCLEOTIDES, bestMotif).getHammingDistance(bestPattern)) {
                bestPattern = pattern;
                bestMotif = motif;
            }
        }
        return bestMotif;
    }
}
