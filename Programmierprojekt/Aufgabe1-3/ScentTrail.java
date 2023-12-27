import java.util.Arrays;

// STYLE: Objektorientiert
// Ein Objekt, das alle Duftspuren der Simulation erzeugt und updatet.
public class ScentTrail {
    public int[][] landscape;
    public double[][] smellArray;
    public double[][] blackArray;
    public double[][] redArray;
    public double[][] blueArray;
    private int landscapeSize;
    private Rechner rechner;

    // Vorbedingung: input != null;
    // Nachbedingungen: alle Objektvariablen werden mit der korrekten größe initialisisert
    public ScentTrail(int[][] landscape, int landscapeSize) {
        this.landscapeSize = landscapeSize;
        this.landscape = landscape;
        this.rechner = new Rechner(landscapeSize);
        this.smellArray = new double[landscapeSize][landscapeSize];
        this.blackArray = new double[landscapeSize][landscapeSize];
        this.redArray = new double[landscapeSize][landscapeSize];
        this.blueArray = new double[landscapeSize][landscapeSize];
    }

    // BAD: Methode ist überflüssig, weil sie in der Simulation nicht verwendet wird
    // Vorbedingung: input != null;
    // Nachbedingung: Gleiches Ziel, wie checkScent nur ohne die Koordinaten des eigenen Ameisenbaus zu verwenden
    /*
    public int chekScentNatural(Ant ant, int[][] bewegungsoffset) {
        int currentX = ant.getX();
        int currentY = ant.getY();
        int richtung = (int) (Math.random() * 5);
        double duftigsterWert = 0;
        if (!ant.hasFutter()) { // Futterstelle suchen
            for (int i = 0; i < bewegungsoffset.length; i++) {
                if (landscape[rechner.modulo(currentX + bewegungsoffset[i][0], landscapeSize)][rechner.modulo(currentY + bewegungsoffset[i][1], landscapeSize)] == 2) {
                    richtung = i;
                    break;
                } else if (smellArray[rechner.modulo(currentX + bewegungsoffset[i][0], smellArray.length)][rechner.modulo(currentY + bewegungsoffset[i][1], smellArray.length)] >= 0.6) {
                    // wenn ein Futterduft gefunden wurde
                    double temp = smellArray[rechner.modulo(currentX + bewegungsoffset[i][0], smellArray.length)][rechner.modulo(currentY + bewegungsoffset[i][1], smellArray.length)];
                    if (duftigsterWert < temp || duftigsterWert == 0) {
                        duftigsterWert = temp;
                        richtung = i;
                    }
                }
            }

        } else { // Futterbau suchen
            double strongestSmell = 0;
            for (int i = 0; i < bewegungsoffset.length; i++) {

                // wenn die Ameise einen FutterBau gefunden hat und kein Futter bei sich hat soll sie zum Futterbau
                int x = rechner.modulo(currentX + bewegungsoffset[i][0], landscapeSize);
                int y = rechner.modulo(currentY + bewegungsoffset[i][1], landscapeSize);
                if (landscape[x][y] == 1 && !ant.hasFutter()) {
                    // Ameise geht dem stärksten Duft ihrer Art nach um so den Bau zu finden
                    richtung = i;
                    break;
                }

                if (ant.hasFutter()) { // wenn sie futter hat will sie ihre duftspurfinden und heim

                    if (ant.getClass() == NormalAnt.class) {
                        if (landscape[x][y] == 2) { // Ameise hat Futter und ihren Bau gefunden
                            richtung = i;
                            break;
                        }
                        if (i == 0) { // bei der ersten iteration wird eine Random Richtung wird gewählt wenn kein starker duft in der Nähe ist
                            strongestSmell = blackArray[x][y];
                            richtung = (int) (Math.random() * bewegungsoffset.length);
                        }
                        if (blackArray[x][y] >= 0.2 && blackArray[x][y] >= strongestSmell) {
                            richtung = i;
                            strongestSmell = blackArray[x][y];
                        }
                    }


                    if (ant.getClass() == RedFireAnt.class) {
                        if (landscape[x][y] == 3) { // Ameise hat Futter und ihren Bau gefunden
                            richtung = i;
                            break;
                        }
                        if (i == 0) { // bei der ersten iteration wird eine Random Richtung wird gewählt wenn kein starker duft in der Nähe ist
                            strongestSmell = redArray[x][y];
                            richtung = (int) (Math.random() * bewegungsoffset.length);
                        }
                        if (redArray[x][y] >= 0.2 && redArray[x][y] >= strongestSmell) {
                            richtung = i;
                            strongestSmell = redArray[x][y];
                        }
                    }

                    if (ant.getClass() == BlueWarAnt.class) {
                        if (landscape[x][y] == 4) { // Ameise hat Futter und ihren Bau gefunden
                            richtung = i;
                            break;
                        }
                        if (i == 0) { // bei der ersten iteration wird eine Random Richtung wird gewählt wenn kein starker duft in der Nähe ist
                            strongestSmell = blueArray[x][y];
                            richtung = (int) (Math.random() * bewegungsoffset.length);
                        }
                        if (blueArray[x][y] >= 0.2 && blueArray[x][y] >= strongestSmell) {
                            richtung = i;
                            strongestSmell = blueArray[x][y];
                        }
                    }

                } else { //Ameise hat kein Futter und rennt da hin wo am meisten futterduft ist
                    if (i == 0) { // eine Random Richtung wird gewählt wenn kein starker duft in der Nähe ist
                        strongestSmell = smellArray[x][y];
                        richtung = (int) (Math.random() * bewegungsoffset.length);
                    }
                    if (smellArray[x][y] >= 0.2 && smellArray[x][y] >= strongestSmell) {
                        richtung = i;
                        strongestSmell = blackArray[x][y];
                    }
                }

            }
        }
        return richtung;
    }
    */
    // Vorbedingung: input != null;
    // Nachbedingung: sucht die ideale Richtung in welche sich die Ameise bewegen soll.
    // Wenn die Ameise Futter bei sich trägt geht sie Richtung Bau, sonst sucht sie nach einer Futterstelle, bzw. Futterduft
    public int chekScent(Ant ant, int[][] bewegungsoffset) {
        int currentX = ant.getX();
        int currentY = ant.getY();
        int richtung = (int) (Math.random() * 5);
        double duftigsterWert = 0;
        if (!ant.hasFutter()) { // Futterstelle suchen
            for (int i = 0; i < bewegungsoffset.length; i++) {
                if (landscape[rechner.modulo(currentX + bewegungsoffset[i][0], landscapeSize)][rechner.modulo(currentY + bewegungsoffset[i][1], landscapeSize)] == 2) {
                    richtung = i;
                    break;
                } else if (smellArray[rechner.modulo(currentX + bewegungsoffset[i][0], smellArray.length)][rechner.modulo(currentY + bewegungsoffset[i][1], smellArray.length)] >= 0.6) {
                    // wenn ein Futterduft gefunden wurde
                    double temp = smellArray[rechner.modulo(currentX + bewegungsoffset[i][0], smellArray.length)][rechner.modulo(currentY + bewegungsoffset[i][1], smellArray.length)];
                    if (duftigsterWert < temp || duftigsterWert == 0) {
                        duftigsterWert = temp;
                        richtung = i;
                    }
                }
            }

        } else { // Ameisenbau suchen
            int winnerX = rechner.modulo(currentX + bewegungsoffset[richtung][0], landscapeSize);
            int winnerY = rechner.modulo(currentX + bewegungsoffset[richtung][1], landscapeSize);
            for (int i = 0; i < bewegungsoffset.length; i++) {
                if (i == 0) {
                    richtung = 0;
                }
                //X Koordinate mit aktueller potenzieller Richtung
                int tempX = rechner.modulo(currentX + bewegungsoffset[i][0], landscapeSize);
                int tempY = rechner.modulo(currentY + bewegungsoffset[i][1], landscapeSize);
                //X Koordinate mit bis jetzt bester Richtung
                if (rechner.calculateDistance(ant.getHome().getX(), ant.getHome().getY(), tempX, tempY) <
                        rechner.calculateDistance(ant.getHome().getX(), ant.getHome().getY(), winnerX, winnerY)) {
                    winnerX = tempX;
                    winnerY = tempY;
                    richtung = i;
                }
            }

        }
        return richtung;
    }


    // STYLE: Objektorientiert + Funktional
    // Vorbedingung: input != null;
    // Nachbedingung: Duft der Arrays wird aktualisiert, eine Ameise mit Futter versprüht smellArray-Duft
    // zusätzlich versprüht jede Ameisensorte einen eigenen Geruch
    public void updateSmell(Ant[] ameisenArray) {
        Arrays.stream(ameisenArray).forEach(ant -> {
            int x = ant.getX();
            int y = ant.getY();
            if (ant.hasFutter()) smellArray[x][y] += 0.1;
            if (ant.getClass() == NormalAnt.class) blackArray[x][y] += 0.02;
            if (ant.getClass() == RedFireAnt.class) redArray[x][y] += 0.02;
            if (ant.getClass() == BlueWarAnt.class) blueArray[x][y] += 0.02;
        });
        for (int i = 0; i < smellArray.length; i++) {
            for (int j = 0; j < smellArray[i].length; j++) { // 3 Threads das smell array übergeben und paralell die berechnung bearbeiten

                boolean isEmpty = true;
                for (Ant ant : ameisenArray) { // wenn eine ameise auf dem Feld ist soll sich der Duft nicht verringern
                    if (smellArray[i][j] == smellArray[ant.getX()][ant.getY()] && ant.hasFutter()) {
                        isEmpty = false;
                        break;
                    }
                }
                if (isEmpty) smellArray[i][j] *= 0.98; // sonst lässt der Duft nach
            }
        }
        // STYLE: Prozedural + Parallel
        for (int i = 0; i < 3; i++) {
            SmellThread smellThread = new SmellThread();
            if (i==0) smellThread.updateSmell(blackArray, ameisenArray, NormalAnt.class);
            else if (i==1)smellThread.updateSmell(redArray, ameisenArray,RedFireAnt.class);
            else smellThread.updateSmell(blueArray, ameisenArray, BlueWarAnt.class);
            smellThread.start();
        }
    }
}
