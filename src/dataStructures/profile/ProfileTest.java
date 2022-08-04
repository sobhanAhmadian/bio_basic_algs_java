package dataStructures.profile;

import org.junit.jupiter.api.Test;
import data.Sequences;

class ProfileTest {

    @Test
    public void test() {
        Profile profile = new Profile(Sequences.NUCLEOTIDES, Sequences.NF_KB);
        profile.printProfile();
        System.out.println("entropy          : " + profile.getEntropy(2));
        System.out.println("hamming distance : " + profile.getHammingDistance(Sequences.CONSENSUS_OF_NF_KB));
        System.out.println("consensus        : " + profile.getConsensus());
        System.out.println("probability of TCGTGGATTTCC : " + profile.probabilityOf("TCGTGGATTTCC"));
    }
}