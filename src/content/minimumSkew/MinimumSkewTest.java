package content.minimumSkew;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.Util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class MinimumSkewTest {

    private String genome = "";

    @BeforeEach
    void setUp() {
        Scanner scanner;
        try {
            scanner = new Scanner(new File("src/res/EcoliGenome.txt"));
            genome = scanner.next().toLowerCase();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getAllSkew() {
        int[] skew = MinimumSkew.getAllSkew(genome);
        for (Integer i :
                Util.getAllMinIndexes(skew)) {
            System.out.printf("%d ", i);
        }
    }
}