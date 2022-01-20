package content.motifFinding.bruteForce;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BruteForceMotifFindingTest {

    List<String> dna;
    int k = 5;
    int d = 1;

    @BeforeEach
    void setUp() {
        String genome = "TATGCTACTAGTCTGGATTACTACA CCGCTTACGAGTGGGCGAATAGGAA TCCTTTACCAATGTTAAGTGACACA ATGGACAACTCAGGATATGTTACGA TACAAAGTTCAGCAGGGCTGCCCTC GCATCTACAAATGCCCTCGTTTGTA";
        dna = new ArrayList<>();
        dna.addAll(Arrays.asList(genome.split(" ")));
    }

    @Test
    void motifEnumeration() {
        List<String> motifs = BruteForceMotifFinding.motifEnumeration(dna, k, d);
        for (String motif :
                motifs) {
            System.out.print(motif + " ");
        }
    }
}