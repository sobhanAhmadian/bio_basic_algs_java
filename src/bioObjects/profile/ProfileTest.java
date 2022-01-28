package bioObjects.profile;

import org.junit.jupiter.api.Test;
import res.Sequences;

class ProfileTest {

    @Test
    public void test() {
        Profile profile = new Profile(Sequences.NUCLEOTIDES, Sequences.NF_KB);
        profile.printProfile();
        System.out.println(profile.getEntropy(2));
        System.out.println(profile.getHammingDistance(Sequences.CONSENSUS_OF_NF_KB));
    }
}