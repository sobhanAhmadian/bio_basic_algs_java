package content.motifFinding.greedySearch;

import content.motifFinding.MotifFinder;
import dataStructures.profile.Profile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import data.Sequences;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class GreedySearchTest {

    Profile profile;
    String sequence;
    List<String> dna;
    MotifFinder motifFinder;
    int k;

    @BeforeEach
    void setUp() {

        motifFinder = new GreedySearch();

        sequence = "ACCTGTTTATTGCCTAAGTTCCGAACAAACCCAATATAGCCCGAGGGCCT";
        profile = new Profile(Sequences.NUCLEOTIDES, new double[][]{
                {0.2, 0.2, 0.3, 0.2, 0.3},
                {0.4, 0.3, 0.1, 0.5, 0.1},
                {0.3, 0.3, 0.5, 0.2, 0.4},
                {0.1, 0.2, 0.1, 0.1, 0.2}
        });

        dna = new ArrayList<>();

        String all = "GGCGTTCAGGCA AAGAATCAGTCA CAAGGAGTTCGC CACGTCAATCAC CAATAATATTCG";
        Collections.addAll(dna, all.split(" "));
        k = 3;
    }

    @Test
    void getMostProbable() {
        String mostProbable = ((GreedySearch) motifFinder).getMostProbable(profile, sequence);
        System.out.println(mostProbable);
    }

    @Test
    void findMotif() {
        List<String> motif = motifFinder.findMotif(dna, k);
        for (String pattern :
                motif) {
            System.out.println(pattern);
        }
    }
}