package content.needlemanWunsch;

import dataStructures.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NeedlemanWunschTest {

    private Score score;
    private double d = 1;
    private String x;
    private String y;

    @BeforeEach
    void setUp() {
        List<Character> characters = new ArrayList<>();
        characters.add('A');
        characters.add('T');
        characters.add('C');
        characters.add('G');
        double[][] scores = new double[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (i==j) scores[i][j] = 1;
                else scores[i][j] = -1;
            }
        }
        score = new Score(characters, scores);

        x = "GATTACA";
        y = "GCATGCG";
    }

    @Test
    void align() {
        Pair<double[][], char[][]> pair = NeedlemanWunsch.align(x, y, d, score);
        double[][] f = pair.getT();
        char[][] b = pair.getE();
        for (int i = 0; i <= x.length(); i++) {
            for (int j = 0; j <= y.length(); j++) {
                System.out.printf("%5.1f ", f[i][j]);
            }
            System.out.println();
        }
        System.out.println();
        for (int i = 1; i <= x.length(); i++) {
            for (int j = 1; j <= y.length(); j++) {
                System.out.printf("%3c ", b[i][j]);
            }
            System.out.println();
        }
    }

    @Test
    void print() {
        Pair<double[][], char[][]> pair = NeedlemanWunsch.align(x, y, d, score);
        char[][] b = pair.getE();
        Pair<String, String> results = NeedlemanWunsch.print(x, y, b);
        System.out.println(results.getE());
        System.out.println(results.getT());
    }
}