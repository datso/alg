import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Discnt {

    private static String FILE_IN = "discnt.in";
    private static String FILE_OUT = "discnt.out";

    public static void main(String[] args) throws Exception {
        String inputFileName = FILE_IN;
        String outputFileName = FILE_OUT;

        if (args.length >= 2) {
            inputFileName = args[0];
            outputFileName = args[1];
        } else if (args.length > 1) {
            inputFileName = args[0];
        }

        // Step 1: Read input data
        BufferedReader br = new BufferedReader(new FileReader(new File(inputFileName)));
        int[] prices = parsePrices(br.readLine());
        float discount = (100 - Integer.parseInt(br.readLine())) / (float) 100;
        br.close();

        // Step 2: Sort prices
        insertionSort(prices);

        // Step 3: Detect best combination with provided discount
        float total = 0;
        int right = prices.length;
        for (int i=0; i < right; i++) {
            if (i > 0 && (i + 1) % 3 == 0) {
                total += ((float) prices[--right]) * discount;
            }
            if (i != right) {
                total += prices[i];
            }
        }

        // Step 4: Write result to file
        Files.write(Paths.get(outputFileName), String.format("%.2f", total).getBytes());
    }

    private static int[] parsePrices(String s) {
        String[] items = s.split(" ");
        int[] ints = new int[items.length];

        for (int i = 0; i < items.length; i++) {
            ints[i] = Integer.parseInt(items[i]);
        }

        return ints;
    }

    private static void insertionSort(int[] a) {
        for (int i=1; i < a.length; i++) {
            for (int k=i; k > 0 && a[k] < a[k-1]; k--) {
                int t = a[k];
                a[k] = a[k-1];
                a[k-1] = t;
            }
        }
    }

    private static void insertionSortWithBinSearch(int[] a) {
        for (int i=1; i < a.length; i++) {
            int index = insertionSortFindIndex(a, a[i], 0, i);

            for (int k=i; k > index; k--) {
                 int t = a[k-1];
                 a[k-1] = a[k];
                 a[k] = t;
            }
        }
    }

    private static int insertionSortFindIndexRecursion(int[] a, int item, int left, int right) {
        if (left == right) {
            return left;
        }

        int mid = left + ((right - left) / 2);

        if (a[mid] > item) {
            return insertionSortFindIndex(a, item, left, mid);
        } else {
            return insertionSortFindIndex(a, item, mid + 1, right);
        }
    }

    private static int insertionSortFindIndex(int[] a, int item, int left, int right) {
        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (item < a[mid]) {
                right = mid - 1;
            } else if (item > a[mid]) {
                left = mid + 1;
            } else {
                return mid;
            }
        }

        return left;
    }

}