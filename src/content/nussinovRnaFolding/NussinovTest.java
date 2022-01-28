package content.nussinovRnaFolding;

import dataStructures.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NussinovTest {

    String rna;

    @BeforeEach
    void setUp() {
        rna = "AGGUCUCUGACGUACAAGCUGUACGACCU";
    }

    @Test
    void fold() {
        Pair<double[][], int[][]> pair = Nussinov.fold(rna);
        double[][] f = pair.getT();
        int[][] b = pair.getE();
        for (int i = 0; i < rna.length(); i++) {
            for (int j = 0; j < rna.length(); j++) {
                System.out.printf("%5.1f", f[i][j]);
            }
            System.out.println();
        }

        System.out.println();

        for (int i = 0; i < rna.length(); i++) {
            for (int j = 0; j < rna.length(); j++) {
                System.out.printf("%3d", b[i][j]);
            }
            System.out.println();
        }
    }

    @Test
    void print() {
        Pair<double[][], int[][]> pair = Nussinov.fold(rna);
        int[][] b = pair.getE();
        String folding = Nussinov.print(0, rna.length() - 1, b);
        System.out.println(folding);
    }
}