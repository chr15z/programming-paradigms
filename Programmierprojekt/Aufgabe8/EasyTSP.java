import java.util.Arrays;
import java.util.stream.IntStream;

public class EasyTSP {
    public static double easyTSP(double[][] distance) {
        return easyTSPHelper(distance, 0, 0);
    }
    private static double easyTSPHelper(double[][] distance, int currentIndex, double length) {
        if (currentIndex >= distance.length) {
            return length;
        }
        double[] intArray = distance[currentIndex];
        double minValue = Arrays.stream(intArray)
                .filter(value -> value > 0)
                .min()
                .orElseThrow(() -> new RuntimeException("Kein minimaler Wert größer als Null gefunden"));

        int minIndex = IntStream.range(0, intArray.length)
                .filter(j -> intArray[j] == minValue)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Kein minimaler Wert nicht im Array gefunden"));

        IntStream.range(0, distance.length)
                .forEach(j -> distance[j][minIndex] = 0);

        return easyTSPHelper(distance, currentIndex + 1, length + minValue);
    }
}