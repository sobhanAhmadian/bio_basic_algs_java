import java.util.Collection;
import java.util.List;

public class Util {

    public static int getMax(int[] array) {
        int max = Integer.MIN_VALUE;
        for (int element : array) {
            if (element >= max)
                max = element;
        }
        return max;
    }

    public static int getMax(Collection<Integer> array) {
        int max = Integer.MIN_VALUE;
        for (int element : array) {
            if (element >= max)
                max = element;
        }
        return max;
    }

    public static void removeDuplicates(List<String> list) {
        int i = 0;
        while (i++ < list.size()) {
            for (int j = i; j < list.size(); j++) {
                if (list.get(j).equals(list.get(i - 1)))
                    list.remove(j--);
            }
        }
    }
}
