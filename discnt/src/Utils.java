import java.util.Random;

public class Utils {

    public static int[] generateRandomArray(int n){
        int[] list = new int[n];
        Random random = new Random();

        for (int i = 0; i < n; i++) {
            list[i] = random.nextInt(1000000);
        }

        return list;
    }

}
