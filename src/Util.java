import java.util.ArrayList;
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

    public static int getMin(int[] array) {
        int min = Integer.MAX_VALUE;
        for (int element : array) {
            if (element <= min)
                min = element;
        }
        return min;
    }

    public static List<Integer> getAllMinIndexes(int[] array) {
        int min = getMin(array);
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < array.length; i++) {
            if (array[i] == min)
                list.add(i);
        }
        return list;
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
