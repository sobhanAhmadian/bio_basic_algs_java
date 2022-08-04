package content.motifFinding.greedySearch;

import content.motifFinding.MotifFinder;
import data.Sequences;
import dataStructures.profile.Profile;
import util.DnaSequenceUtility;

import java.util.Arrays;
import java.util.List;

public class GreedySearch implements MotifFinder {

    /**
     * @param sequences list of sequences you want to find motif
     * @param k         the length of motif
     * @return motifs that happened at list once in each of sequences
     */
    public List<String> findMotif(List<String> sequences, int k) {
        int t = sequences.size();
        String[] bestMotif = new String[t];
        for (int i = 0; i < t; i++) bestMotif[i] = sequences.get(i).substring(0, k);

        String[] motif = new String[t];
        List<String> patternsInFirstDna = DnaSequenceUtility.getAllPatterns(sequences.get(0), k, 0, false);
        for (String pattern :
                patternsInFirstDna) {
            motif[0] = pattern;
            for (int i = 1; i < t; i++) {
                Profile preProfile = new Profile(Sequences.NUCLEOTIDES, Arrays.copyOfRange(motif, 0, i), true);
                motif[i] = preProfile.getMostProbable(sequences.get(i));
            }
            Profile bestProfile = new Profile(Sequences.NUCLEOTIDES, bestMotif);
            Profile newProfile = new Profile(Sequences.NUCLEOTIDES, motif);
            if (newProfile.getHammingDistance(newProfile.getConsensus()) <
                    bestProfile.getHammingDistance(bestProfile.getConsensus())
            )
                bestMotif = Arrays.copyOf(motif, motif.length);
        }
        return List.of(bestMotif);
    }
}
