package content.needlemanWunsch;

import dataStructures.Pair;

public final class NeedlemanWunsch {

    private NeedlemanWunsch() {
    }

    public static Pair<double[][], char[][]> align(String x, String y, double d, Score score) {
        int m = x.length() + 1;
        int n = y.length() + 1;
        double[][] f = new double[m][n];
        char[][] b = new char[m][n]; // ignore element zero for both sides
        f[0][0] = 0;
        for (int i = 1; i < m; i++) f[i][0] = f[i - 1][0] - d;
        for (int j = 1; j < n; j++) f[0][j] = f[0][j - 1] - d;
        for (int i = 1; i < n; i++) {
            for (int j = 1; j < m; j++) {
                double match = f[i - 1][j - 1] + score.getScore(x.charAt(i - 1), y.charAt(j - 1));
                double delete = f[i - 1][j] - d;
                double insert = f[i][j - 1] - d;
                if (match > delete && match > insert) {
                    f[i][j] = match;
                    b[i][j] = 'd';
                } else if (delete > match && delete > insert) {
                    f[i][j] = delete;
                    b[i][j] = 'u';
                } else {
                    f[i][j] = insert;
                    b[i][j] = 'l';
                }
            }
        }
        return new Pair<>(f, b);
    }

    private static void print(String x, String y, StringBuilder ax, StringBuilder ay, char[][] b, int i, int j) {
        if (i == 0 && j == 0) return;
        switch (b[i][j]) {
            case 'd':
                print(x, y, ax, ay, b, i - 1, j - 1);
                ax.append(x.charAt(i - 1));
                ay.append(y.charAt(j - 1));
                break;
            case 'u':
                print(x, y, ax, ay, b, i - 1, j);
                ax.append(x.charAt(i - 1));
                ay.append('-');
                break;
            case 'l':
                print(x, y, ax, ay, b, i, j - 1);
                ax.append('-');
                ay.append(y.charAt(j - 1));
                break;
        }
    }

    public static Pair<String, String> print(String x, String y, char[][] b) {
        StringBuilder ax = new StringBuilder();
        StringBuilder ay = new StringBuilder();
        print(x, y, ax, ay, b, x.length(), y.length());
        return new Pair<>(ax.toString(), ay.toString());
    }
}
