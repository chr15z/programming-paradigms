public class SmellThread extends Thread {

    public void updateSmell(double[][] scentArray, Ant[] ameisenarray, Class<?> klasse) {
        for (int i = 0; i < scentArray.length; i++) {
            for (int j = 0; j < scentArray[i].length; j++) {

                boolean isEmpty = true;
                for (Ant ant : ameisenarray) {
                    if (i == ant.getX() && j == ant.getY() && ant.getClass() == klasse) {
                        isEmpty = false;
                        break;
                    }
                }
                if (isEmpty) scentArray[i][j] *= 0.98;
            }
        }
        try {
            Thread.sleep(10); // wait miliseconds before the next thread gets fired
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}

