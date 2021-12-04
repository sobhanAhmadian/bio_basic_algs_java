package content.minimumSkew;

public class MinimumSkew {

    public static int[] getAllSkew(String sequence) {
        sequence = sequence.toLowerCase();
        int[] skew = new int[sequence.length() + 1];
        skew[0] = 0;
        for (int i = 1; i < sequence.length() + 1; i++) {
            if (sequence.charAt(i - 1) == 'g') skew[i] = skew[i - 1] + 1;
            else if (sequence.charAt(i - 1) == 'c') skew[i] = skew[i - 1] - 1;
            else skew[i] = skew[i - 1];
        }
        return skew;
    }
}
