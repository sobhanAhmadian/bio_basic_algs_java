import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Test
    void findMostApproximateFrequentWordsAlg2() {
        for (String s :
                Main.findMFW("GCAATGAATAATCGGCAGCACGATGGCAGCAAATATGTATGCAGCAAATGCACGAATCGCGAATAATAATAATAATTATATGGCAAATAATCGATGAATTATCGGCAAATATGGCAAATCGATGTATTATCGGCAAATTATTATATGCGATGCGGCACGTATCGGCATATTATAATAATGCAAATAATCGCGAATTATTATATGAATCGTAT", 5, 3).getT()) {
            System.out.println(s);

        }
    }

    @Test
    void neighbors() {
        for (String n :
                Main.neighbors("GTTCCGTCAGGT", 3)) {
            System.out.println(n);
        }
    }

    @Test
    void replace() {
        assertEquals(Main.replace("atat", 1, 'c'), "acat");
        assertEquals(Main.replace("atat", 2, 'c'), "atct");
        assertEquals(Main.replace("atat", 3, 'c'), "atac");
    }
}