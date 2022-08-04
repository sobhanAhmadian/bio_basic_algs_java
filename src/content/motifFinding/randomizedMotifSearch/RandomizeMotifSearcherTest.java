package content.motifFinding.randomizedMotifSearch;

import content.motifFinding.MotifFinder;
import content.motifFinding.greedySearch.GreedySearch;
import data.Sequences;
import dataStructures.profile.Profile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RandomizeMotifSearcherTest {

    List<String> dna;
    MotifFinder motifFinder;
    int k;

    @BeforeEach
    void setUp() {
        motifFinder = new RandomizeMotifSearcher();

        dna = new ArrayList<>();
        String all =
                "CGACTTAACTGTAACTAATCCCTGGCCGCCGGAGCGACTTCCATCCAGTAAAATGTGGAAGTAGAAAGGGGTAACGCTTGCACAATTGCGTTCAAAATTACACCACTAGTGGCGTTAATGTGACACTACTCGAAAGGGCAGTGTACGACCCCGACTTAACTGTAAC TAATCCCTGGCCGCCGGAGCGACTTCCATCCATTAATGGGTCGCTCCGTAAAATGTGGAAGTAGAAAGGGGTAACGCTTGCACAATTGCGTTCAAAATTACACCACTAGTGGCGTTAATGTGACACTACTCGAAAGGGCAGTGTACGACCCCGACTTAACTGTAAC GCCCAGACATTGCTGGTGGGCCGCCATTGCCTGTGGAATAGTGCACGTAGGATTGATGCCTACGACGTTTTGCGCTCGTATTCTTTTAGACACGCGCTCCGCATCCCGCCGGGTGACGTGGGTAATAAACGGCTAGACTATTAATTCTACCGGTCGTACCCCAATG GATCAAAATGACGTGACTCGCCCCAAGCATTCAAATCAGCAGTCCTTCATGATGAACAGGTGCTGTCGGTTCCCCATGACGCGCCTATCAAGAGAGTCATCGCATATGCTTGATCAGCCGGGAGCAGGAAAGGCGACGGTCGCTCCCCCACTATCTAGTAGAGATT TAGTCTGTCTCTAAACACGGTCCGGCGAGCTTAGATTGCCTACCTTTAGACGGTATTTCCGAGGGGCCCTCCCGCCGAAAAGGACCGTTGGAATACGATAGCGGCTATGGGTCTGCCCGGCATGAAAAACCTTTCCTTCATCAGTCACTGGTATGACCGTGACTAC TCTTGTATTCTATGGGCCCGGCACGATTTAGACGGTCATACCAATATCTTTAGCACGGAAATAGGGTAGTTCCCGACTCAGGCTTGTAACGCCTTTGTGTTCCCACACTCAGAAATGGACATAATAAGCGCTAATCTGTCAAGAAAGGTGTGGCTCCCGTTTCACA CATGAGCGGATTATTAGAGATTCGCTCCCCTTAATCCATAGACGCGACTAGGGATCATCCCGCTTATTATTAGAAATCAGGTTTCTGAGGTTAGTGCCTTTACGCTCATTGGGGAGGAATAAGCGGTTCGTATCACCAAGTTGGATTGTCTCGTTTCCCGTCCCCA GATAAATCTGGACGCACCTGCAGATGTGCGATGTAGAGGCATGAATGACTAATACGCGCATGCATATATACACAGTACAAGGTAACGCGCCCATATCAATATCGGCAGTTATTAGACGGTCGCAAAGTTCCCAGTACGTGAGAAGATTTGAGACCCTAGGCATATC GGTGAAGCGTTCATTTGTCGTAATATGAGGGCTAGGAACACATCAGCTGGAAGGAGGCTCGTACATTACTTAGCCATCTGGTAATTGGGTTAGCGCGTCGCTCCCTGAGTACTAAGAGCTCCAAGATCTAACTTAGGGTGCGCAGGGTGTCTTTGTCGCTGCCGCA TTCAAGAGGGGGCGATCCTTACCGTTATCTAATGGCCGACGTTAAGTTATACGTCAATGGTTTGAGGATTTAGACGGCGTCTCCTGTCCAGACATCGCCCACAGGTGTCGCTGTGCGACATATGGGCTGATGAAGCTCACTCAGCTGAAGCACTTCACGTACGACT CCCGTTCCAGCAGCCATACCGCTGCTCGGAGTTAGCCTGCAGCATTGGAACCGATCTGGACCCATGTTCACGTTCACTTAGGGAGTCGCTCCTGCGCGTATCTTCGATTGAAGGATCTGCCGACGCTGATACACCACTAGTACTCAATAATCGATACTCGCCGGCT CCTTTCACCTACTGTCGGCGGGGCTGTGGCATCTCATCAGTGAACGCCCGATAAACCCCAACAGGAAGCAACCCAAGGGGAGACTTGCTAAATCTGCTCAGCTGCGAGGACAGTTAGACGGTCGGAGCTACTTCGGGACCTTTACGAGATACGAAACCGAGCTTCC TATGCCACGGTCGCTCCTAGCTAAGTAGCATCCAAATGGCGACGCACAACCAACGGAAACGTGTGATTGCAGCTGGTGTCTCCCGTAACTGGAAAATTATCAAATGCTGAGATACTCGTGCTCCGTAAGGCGCAAACAACTTCTTCGGGGTATCAGTCCTAAATGG CACTATTTGAGGGTGCAACGAGGAAGGCCGAACCCTAAAGTAATTCAGAAGCTTTGTGTCACCAATACGTCTGTTTTCACGGAACGGTGGCAGCACGCCTTTCGGGCCTCGCCGGGAATTCCTATTCAGACTAGTGCTAGACGGTCGCTGTTTGAGTTTCAATGCG GGAGACGTCTCTTTAATATTATGAAGTGAACACGTGTCTGACAATGCCTATGGCAAGGAATGTGAAGCTAGTTAGACGTAAGCTCCTAACGCTGTTTATGTTGGGAAGCAATCGGGATGGGCTGGTCATTTGCAAGCAGGATTAAGACAAAGAATTGACGGCGTAT CGCTTTTAGGTTCGTCAAGCTGACAGACTTAAGTGCGGGCTCAGTTAAAAGCCCTATTTTTTCCGATGCTCTGCCGATAGCCAGTTAGACAACCGCTCCGACGGCGACGTTATAGTCTTCCAGCTCGTCCTAGGGGGTGATAGGCGAACGCGAACCACTCGGGCCT GCTTCCACGTGTTTGCTAAATCATGATATAAGATTCCACTACGGCAACTGCTTGCGATGCCCACCGGATCGCCAGCTCGATACGGTCCATACAGTTTACTTGGTCGCTCCTCGTTAGGGGCTACATTGAGCTACGTTGCCCTGTTGTCTGCATATGATGACGGACG CCCGATATGGTGTTGACCAAATATTTCAATGTACTTCAAGGTGGTGGTTTAGCGTTTGGCACAGGCGCGCGACACGACGGTTGTTGGTCTTATTCCCCGGTCGCTCCCCCCTGGCAAAGACTTATACGGAGTTTGTAGTAAACTGCTGGAATGCCCATAGATTCGT ACTTCCATTACGGTGCATTCATCCCGATATGGTTTATGTCTGTGGCCAAAGTGAACGTACGTGGGCTTCCTTAGACTCGCGCACCCCTCACGGGATTAAACTGCACATACTCAATGACTTGATTAGAACATCGCTCCAGGGTCTTTGGTGCTGGCGGGTCAGATTT TATCCGACGCGGAAGCCTTCTCGCGAGACCAAAGCGTGCCAAGGGAGAAAGACAGCTCATATTATGACTTGTTGTTAAGTAATGCGCTCCATCATCAATCGTCTACAGTGGGGGTGGATCTCTAGTTTGCAAATGGAATGGGTAGAGACGGTCGCTCTCCCGCAGT";
        Collections.addAll(dna, all.split(" "));
        k = 15;
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