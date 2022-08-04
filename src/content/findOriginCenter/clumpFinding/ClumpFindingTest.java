package content.findOriginCenter.clumpFinding;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

class ClumpFindingTest {

    String genome = "";

    @BeforeEach
    void setUp() {
        try {
            Scanner scanner = new Scanner(new File("src/res/EcoliGenome.txt"));
            genome = scanner.next().toLowerCase();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    void findClump() {
        String sequenceForClumpFinding = genome;
        int i = 0;
        for (String word :
                ClumpFinding.findClump(sequenceForClumpFinding, 9,0, false, 500, 3)) {
            System.out.printf("%s ", word);
            i++;
        }
        System.out.println(i);
    }
}