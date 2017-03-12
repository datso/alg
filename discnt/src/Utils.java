import java.util.Random;

public class Utils {

    public static double[] generateRandomArray(int n){
        double[] list = new double[n];
        Random random = new Random();

        for (int i = 0; i < n; i++) {
            list[i] = random.nextInt(1000000);
        }

        return list;
    }

}
