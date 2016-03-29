package cas2xb3_A3_robichaud_NR;

public class InsertionSort {
    public static void sort(Double[] a) {
        int N = a.length;
        for (int i = 0; i < N; i++) {
            for (int j = i; j > 0 && less(a[j], a[j-1]); j--) {
                exch(a, j, j-1);
            }
        }

    }
    private static boolean less(Double v, Double w) {
        return v.compareTo(w) < 0;
    }
    private static void exch(Object[] a, int i, int j) {
        Object s = a[i];
        a[i] = a[j];
        a[j] = s;
    }
}
