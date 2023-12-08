package libcat.util;

public class Algorithm {
    public static int search(String[] array, String value) {
        int index = -1;

        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(value)) {
                System.out.println(array[i]);
                index = i;
            }
        }

        return index;
    }
}
