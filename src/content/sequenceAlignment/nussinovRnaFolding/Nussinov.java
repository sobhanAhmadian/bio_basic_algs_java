package content.sequenceAlignment.nussinovRnaFolding;

import dataStructures.Pair;
import util.Util;

public class Nussinov {

    private Nussinov() {
    }

    public static Pair<double[][], int[][]> fold(String rna) {
        double[][] f = new double[rna.length()][rna.length()];
        int[][] b = new int[rna.length()][rna.length()];
        int n = rna.length();

        for (int i = n - 1; i >= 0; i--) {
            for (int j = 0; j < n; j++) {
                if (j <= i + 1) {
                    f[i][j] = 0;
                    continue;
                }
                double match = f[i + 1][j - 1] + score(rna.charAt(i), rna.charAt(j));

                double sub = 0;
                int k = -1;
                for (int index = i + 1; index < j; index++) {
                    double temp = f[i][index] + f[index + 1][j];
                    if (temp >= sub) {
                        sub = temp;
                        k = index;
                    }
                }

                double[] array = new double[]{match, sub};
                double max = Util.getMax(array);
                f[i][j] = max;
                if (match == max) b[i][j] = -1;
                else b[i][j] = k;
            }
        }
        return new Pair<>(f, b);
    }

    public static String print(int i, int j, int[][] b) {
        if (i >= j) return ".";
        if (b[i][j] == -1) return "(" + print(i + 1, j - 1, b) + ")";
        else return print(i, b[i][j], b) + print(b[i][j] + 1, j, b);
    }

    private static double score(char n1, char n2) {

        switch (n1) {
            case 'C':
                if (n2 == 'G')
                    return 3;
                else
                    return Integer.MIN_VALUE;
            case 'G':
                if (n2 == 'C')
                    return 3;
                else if (n2 == 'U')
                    return 1;
                else
                    return Integer.MIN_VALUE;
            case 'A':
                if (n2 == 'U')
                    return 2;
                else
                    return Integer.MIN_VALUE;
            case 'U':
                if (n2 == 'A')
                    return 2;
                else if (n2 == 'G')
                    return 1;
                else
                    return Integer.MIN_VALUE;
            default:
                return Integer.MIN_VALUE;
        }
    }
}
