import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Test {
    public static int seed = 187;                       //bei aufeinanderfolgenden Ausführungen von random() erhalten wir denselben Satz von Zufallszahlen
    public static int m = 25;                           //Anzahl Ameisen, Städte
    public static double q0 = 0.9;                      //Wahrscheinlichkeit, das der beste Weg genommen wird
    public static double alpha = 1;                     //Einfluss der Pheromonstärke
    public static double beta = 2;                      //Einfluss der Entfernung zwischen den Städten
    public static double p = 0.15;                      //Faktor der Abschwächung der Pheromonstärke
    private static double[][] distance = new double[m][m]; //Distanzen zwischen Städten
    private static double[][] pi = new double[m][m];  //pheromon spuren
    public static Random random = new Random(seed);
    public static int numberOfIterations = 10000;
    public static double t0;

    public static void main(String[] args) {
        double[][] distancetest = new double[m][m];
        generateDistancesRecursive(distancetest, 0, 0, m);
        double t0long = 1 / (m * calculateNearestNeighborTourLength(distancetest));
        t0 = Math.floor(t0long * 1e6) / 1e6;
        generateDistancesRecursive(distance, 0, 0, m);
        Arrays.stream(pi)
                .forEach(row -> Arrays.fill(row, t0));

        final int[][] bestResult = {new int[m + 1]};
        final double[] bestResultLength = {Double.MAX_VALUE};
        IntStream.range(0, numberOfIterations)//ruft iteration() numberOfIterations mal auf
                .mapToObj(t -> {
                    int[] temp = iteration(t);
                    double tempLength = IntStream.range(0, m)
                            .mapToDouble(i -> distance[temp[i] - 1][temp[i + 1] - 1])
                            .sum();
                    return new Object[]{temp, tempLength, t + 1};
                })
                .filter(data -> (double) data[1] < bestResultLength[0]) //falls die neue iteration ein besseres ergebnis liefert wird geprintet + geupdatet
                .forEach(data -> {
                    bestResult[0] = (int[]) data[0];
                    bestResultLength[0] = (double) data[1];
                    printOutput((int) data[2], bestResultLength[0], bestResult[0]);
                    globalPheromoneUpdate(bestResult[0]);
                });
        System.out.println("terminated");
    }

    public static int[] iteration(int t) {
        Random random = new Random(seed);
        double[][] lokalPi = pi;
        int[][] currentAntTraces = IntStream.range(0, m) // Zeile: Ameisen, Spalte: Stadt. Zeile 0 bildet die Rundreise von Ameise 0 ab
                .mapToObj(i -> {
                    int[] temp = new int[m + 1]; // + 1 weil 25 Städte aber eine rundreise besteht aus 26 Städten
                    temp[0] = random.nextInt(m + 1);
                    return temp;
                })
                .toArray(int[][]::new);
        boolean[][] visited = IntStream.range(0, m) // Zeile: Ameisen, Spalte: Stadt. Feld [0][0] gibt an ob Ameise 0 schon die erste Stadt besucht hat
                .mapToObj(i -> {
                    boolean[] temp = new boolean[m];
                    temp[currentAntTraces[i][0] - 1] = true;
                    return temp;
                })
                .toArray(boolean[][]::new);

        IntStream.range(1, m) //Alle Städte außer die erste und letzte werden gefunden (für alle Ameisen)
                .forEach(j -> {
                    int copyJ = j;

                    IntStream.range(0, m)
                            .forEach(ant -> {
                                int[] temp = currentAntTraces[ant];
                                int city = findPossibleCity(visited, ant, currentAntTraces, lokalPi, copyJ);
                                temp[copyJ] = city;
                            });

                    IntStream.range(0, m)
                            .forEach(ant -> {
                                boolean[] temp = visited[ant];
                                temp[currentAntTraces[ant][copyJ] - 1] = true;
                            });
                });


        IntStream.range(0, m)// erste City wird auf letzte City gesetzt damit sich der kreis schließt
                .mapToObj(i -> {
                    int[] temp = currentAntTraces[i];
                    temp[m] = temp[0];
                    return temp;
                })
                .toArray(int[][]::new);

        double[] tourLength = IntStream.range(0, m) //beinhaltet die Länge aller Touren
                .mapToDouble(i ->
                        IntStream.range(0, m - 1)
                                .mapToDouble(j ->
                                        distance[currentAntTraces[i][j] - 1][currentAntTraces[i][j + 1] - 1]) //currentAntTraces[i][j] -> currentAntTraces[i][j + 1]
                                .sum())
                .toArray();


        int shortestRoutIndex = IntStream.range(0, tourLength.length) //Sucht den Index der kürzesten Route in tourLength
                .reduce((i, j) -> tourLength[i] < tourLength[j] ? i : j)
                .orElse(-1);
        return currentAntTraces[shortestRoutIndex];
    }

    private static double calculateNearestNeighborTourLength(double[][] distances) {
        return Arrays.stream(distances)
                .mapToDouble(cityDistances -> Arrays.stream(cityDistances)
                        .filter(distance -> distance != 0 && distance != Integer.MAX_VALUE)
                        .min()
                        .orElse(0))
                .sum();
    }

    public static double actionChoiceRule(double[][] pi, double[][] distance, int i, int j, List<Integer> unvisitedCities) {
        double zähler = Math.pow(pi[i][j], alpha) * Math.pow(1 / distance[i][j], beta);
        double nenner = unvisitedCities.stream()
                .filter(city -> unvisitedCities.contains(j))
                .mapToDouble(city -> Math.pow(pi[i][j] - 1, alpha) * Math.pow(1 / (distance[i][j] - 1), beta))
                .sum();

        zähler = pi[i][j];
        nenner = pi[i][j] + 1/distance[i][j];
        return zähler / nenner;
    }

    public static boolean isPossible(boolean[][] visited, int ant, int city) {
        if (!visited[ant][city]) return true;
        return false;
    }

    public static int findPossibleCity(boolean[][] visited, int ant, int[][] currentAntTraces, double[][] localpi, int i) { //i = aktuelle stadt
        List<Integer> unvisitedCities = IntStream.range(0, visited.length)//Liste aller noch nicht besuchten Städte
                .filter(x -> !visited[ant][x])
                .boxed()
                .collect(Collectors.toList());
        int result = (Math.random() < q0) ? //mit wahrscheinlichkeit q0 wird die stadt mit maximalen pi * 1/distance gewählt
                unvisitedCities.stream()
                        .max((a, b) -> Double.compare(localpi[i][a] * 1.0 / distance[i][a], localpi[i][b] * 1.0 / distance[i][b]))
                        .orElse(unvisitedCities.get(0)) :
                unvisitedCities.stream()//sonst wird die actionChoiceRule angewendet
                        .filter(city -> isPossible(visited, ant, city) && Math.random() < actionChoiceRule(localpi, distance, i, city, unvisitedCities))
                        .findFirst()
                        .orElse(unvisitedCities.get(unvisitedCities.size() - 1));

        localPheromoneUpdate(i, result, p, t0, localpi);
        return result + 1;
    }

    private static void localPheromoneUpdate(int i, int j, double p, double t0, double[][] localpi) {
        localpi[i][j] = localpi[j][i] = (1 - p) * localpi[i][j] + p * t0;
    }

    private static int findCityWithMaxValue(boolean[][] visited, int i, double[][] localpi) {

        double[] result = IntStream.range(0, distance.length)
                .filter(j -> j != i && distance[i][j] != 0 && isPossible(visited, i, j))
                .mapToObj(j -> new double[]{j, localpi[i][j] * (1 - distance[i][j])})
                .max(Comparator.comparingDouble(arr -> arr[1]))
                .orElse(new double[]{-1, -9999999});

        return (int) result[0];
    }

    private static void globalPheromoneUpdate(int[] localpheroUpdate) {
        AtomicInteger counter = new AtomicInteger(0);

        IntStream.range(0, pi.length - 1) //die beste tsp lösung updatet/erhöht das globale pi um * (1+p)
                .flatMap(i -> IntStream.range(0, pi[i].length - 1)
                        .filter(j -> (localpheroUpdate[counter.get()] == i && localpheroUpdate[counter.get() + 1] == j) ||
                                (localpheroUpdate[counter.get()] == j && localpheroUpdate[counter.get() + 1] == i))
                        .peek(j -> {
                            int currentCounter = counter.getAndIncrement();
                            pi[i][j] = pi[j][i] *= (1 + p);
                        }))
                .forEach(j -> {});
    }

    private static void generateDistancesRecursive(double[][] matrix, int i, int j, int numberOfCities) {
    //füllt das distanz array mit random Werten von 0 bis 10,  symmetrisch
        if (i < numberOfCities) {
            if (j < numberOfCities) {
                if (i != j) {
                    int distance = random.nextInt(10) + 1;
                    matrix[i][j] = distance;
                    matrix[j][i] = distance;
                }
                generateDistancesRecursive(matrix, i, j + 1, numberOfCities);
            } else {
                generateDistancesRecursive(matrix, i + 1, i + 2, numberOfCities);
            }
        }
    }

    public static void printOutput(int iteration, double bestResultLength, int[] bestResult) {
        System.out.println("Iteration Nummer " + iteration + ":\ndie Tour: ");
        String result = Arrays.stream(bestResult)
                .mapToObj(Integer::toString)
                .collect(Collectors.joining(" "));
        System.out.println(result);
        System.out.println("ist unsere beste Lösung mit einer Länge von: " + bestResultLength);
        System.out.print("Die Entfernungen zwischen des Städte lauten: ");
        IntStream.range(0, m)
                .forEach(i -> System.out.print(
                        bestResult[i] + " --(" + distance[bestResult[i] - 1][bestResult[i + 1] - 1] + ")-> "
                ));

        System.out.println(bestResult[m]);
        System.out.println("Die im Testlauf verwendeten Parameter: " + tooString());
        System.out.println();
    }

    public static String tooString() {
        return "[m = " + m + "; q0 = " + q0 + "; alpha = " + alpha + "; beta = " + beta + "; p = " + p + "; Anzahl Iterationen = " + numberOfIterations + "]";
    }

    //Hannah: localPheromoneUpdate, generateDistancesRecursive, isPossible, calculateNearestNeighborTourLength, findCityWithMaxValue
    //Marlene: globalPheromoneUpdate, main, findPossibleCity
    //Chris: iteration, printOutput, tooString, main, findPossibleCity
}
