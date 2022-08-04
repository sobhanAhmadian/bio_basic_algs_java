package content.motifFinding.randomizedMotifSearch;

import content.motifFinding.MotifFinder;
import data.Sequences;
import dataStructures.profile.Profile;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomizeMotifSearcher implements MotifFinder {
    @Override
    public List<String> findMotif(List<String> sequences, int k) {
        int times = 10000;
        List<String> bestMotif = randomizeSearch(sequences, k);
        for (int i = 0; i < times; i++) {
            List<String> newMotif = randomizeSearch(sequences, k);

            Profile preProfile = new Profile(Sequences.NUCLEOTIDES, bestMotif);
            Profile newProfile = new Profile(Sequences.NUCLEOTIDES, newMotif);
            if (newProfile.getHammingDistance(newProfile.getConsensus()) <
                    preProfile.getHammingDistance(preProfile.getConsensus())
            )
                bestMotif = newMotif;
        }
        return bestMotif;
    }
    
    private List<String> randomizeSearch(List<String> sequences, int k) {
        List<String> bestMotif = new ArrayList<>();
        Random random = new Random();
        for (String sequence :
                sequences) {
            int start = random.nextInt(sequence.length() - k + 1);
            bestMotif.add(sequence.substring(start, start + k));
        }

        while (true) {
            Profile preProfile = new Profile(Sequences.NUCLEOTIDES, bestMotif, true);
            List<String> newMotif = new ArrayList<>();
            for (String sequence :
                    sequences) {
                newMotif.add(preProfile.getMostProbable(sequence));
            }

            preProfile = new Profile(Sequences.NUCLEOTIDES, bestMotif);
            Profile newProfile = new Profile(Sequences.NUCLEOTIDES, newMotif);
            if (newProfile.getHammingDistance(newProfile.getConsensus()) <
                    preProfile.getHammingDistance(preProfile.getConsensus())
            )
                bestMotif = newMotif;
            else
                break;
        }

        return bestMotif;
    }
}
