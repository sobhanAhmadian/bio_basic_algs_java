package content.frequentWord;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.SequenceUtility;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class MostFrequentWordTest {

    String genome = "";
    String ori = "";

    @BeforeEach
    void setUp() {

        try {
            Scanner scanner = new Scanner(new File("src/res/VibrioGenome.txt"));
            genome = scanner.next().toLowerCase();
            scanner = new Scanner(new File("src/res/VibrioOri.txt"));
            ori = scanner.next();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    void findMfwWithoutMismatch() {
        System.out.println("k : count : ");
        for (int i = 3; i < 10; i++) {
            System.out.printf("%d : %5d : ", i, MostFrequentWord.findMFW(ori, i).getE());
            for (String word :
                    MostFrequentWord.findMFW(ori, i).getT()) {
                System.out.printf("%9s%s", word, " | ");
            }
            System.out.println();
        }
        System.out.println();
        System.out.println("cttgatcat and " +
                SequenceUtility.reverseComplement("cttgatcat") + " are complementary");
        System.out.println("cttgatcat positions in genome :\n" +
                SequenceUtility.getPatternPositions(genome, "cttgatcat", 0, false));
        System.out.println("atgatcaag positions in genome :\n" +
                SequenceUtility.getPatternPositions(genome, "atgatcaag", 0, false));
    }
}