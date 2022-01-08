package util;

public class VectorUtil {

    private VectorUtil() {
    }

    public static double cosignSimilarity(double[] x, double[] y) {
        double xSize = 0;
        for (Double a :
                x) {
            xSize += a * a;
        }
        xSize = Math.sqrt(xSize);

        double ySize = 0;
        for (Double a :
                y) {
            ySize += a * a;
        }
        ySize = Math.sqrt(ySize);

        double innerProduct = 0;
        for (int i = 0; i < x.length; i++) {
            innerProduct += x[i] * y[i];
        }

        return innerProduct / (xSize * ySize);
    }
}
