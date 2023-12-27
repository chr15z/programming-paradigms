import codedraw.CodeDraw;
import java.awt.*;
import java.util.Timer;

// STYLE = Prozedural und Objektorientiert
// Ein Objekt welches eine Ameisensimulation darstellt
// BAD:  Methoden die nicht gebraucht wurden wurden nicht
// gelöscht

public class Game {
    int zoom;
    int landscapeSize;
    int dayspeed;
    int ants;
    public int balkenhöhe = 20;
    Timer timer = new Timer();
    Simulation current;
    boolean isWinter;
    int simulationSteps;
    boolean end;

    // Vorbedingung: zoom > 0, landscapeSize > 0, dayspeed > 0, ants > 0
    public Game(int zoom, int landscapeSize, int dayspeed, int ants) {
        this.zoom = zoom;
        this.landscapeSize = landscapeSize;
        this.dayspeed = dayspeed;
        this.ants = ants;
        current = new Simulation(landscapeSize, zoom);
        isWinter = false;
        simulationSteps = 0;
        end = false;
    }

    //Nachbedingung: hier wird "ein Spiel" / "eine Simulation" ausgeführt
    public void execute() {
        CodeDraw cs = new CodeDraw(landscapeSize * zoom, (landscapeSize + balkenhöhe) * zoom);
        Sun sun = new Sun(cs, zoom, dayspeed);

        //int blackXY = (int) (Math.random() * landscapeSize / 4);
        int blackXY = (int) (landscapeSize / 4);
        //int blueXY = (int) (Math.random() * landscapeSize / 4) + landscapeSize * 3 / 4;
        int blueXY = (int) (landscapeSize * 3 / 4);
        //int redX = (int) (Math.random() * landscapeSize / 4) + landscapeSize * 3 / 4;
        int redX = (int) (landscapeSize * 3 / 4);
        //int redY = (int) (Math.random() * landscapeSize / 4);
        int redY = (int) (landscapeSize / 4);

        Location black = new Location(current, true, blackXY, blackXY, 2);
        Location red = new Location(current, true, redX, redY, 3);
        Location blue = new Location(current, true, blueXY, blueXY, 4);
        int antSpeciesConter = 3;

        //GOOD: Beispiel für dynamsiches Binden
        Ant[] antsArray = new Ant[ants * 3];
        for (int i = 0; i < antsArray.length; i++) {
            if (i % antSpeciesConter == 0) {
                antsArray[i] = new RedFireAnt(red, landscapeSize);
            } else if (i % antSpeciesConter == 1) {
                antsArray[i] = new BlueWarAnt(blue, landscapeSize);
            } else if (i % antSpeciesConter == 2) {
                antsArray[i] = new NormalAnt(black, landscapeSize);
            }
        }

        current.updateSmell(antsArray, isWinter);
        while (simulationSteps < 1000){
            for (int i = 0; i < landscapeSize; i++) { //10 Simulationsschritte auf einmal anzeigen
                if (i % 10 == 0) drawLandscape(cs, sun, zoom, current, antsArray);
                current.move(antsArray, isWinter);
                current.updateSmell(antsArray, isWinter);
                simulationSteps++;
                if (simulationSteps == 750) isWinter = true;
                if (simulationSteps == 1000) isWinter = false;
            }
        }
        //System.out.println("Ergebnis nach " + simulationSteps + " Simulationsschritten: \nDer schwarze Bau: " + black.toString() + "\nDer rote Bau: " + red.toString() + "\nDer blaue Bau: " + blue.toString());
        cs.close();
        /*long startTime = System.currentTimeMillis();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                long currentTime = System.currentTimeMillis();
                if (currentTime - startTime >= 65000){ //nach 65 sec canceln
                    timer.cancel();
                    end = true;
                    System.out.println("Ergebnis nach " + simulationSteps + " Simulationsschritten: \nDer schwarze Bau: " + black.toString() + "\nDer rote Bau: " + red.toString() + "\nDer blaue Bau: " + blue.toString());
                    cs.close();
                }
                else {

                    for (int i = 0; i < landscapeSize; i++) { //10 Simulationsschritte auf einmal anzeigen
                        if (i % 10 == 0) drawLandscape(cs, sun, zoom, current, antsArray);
                        current.move(antsArray, isWinter);
                        current.updateSmell(antsArray, isWinter);
                        simulationSteps++;
                        if (simulationSteps == 270 * 3) isWinter = true;
                        if (simulationSteps == 270 * 4) isWinter = false;
                    }
                }
            }

        }, 0, 10);*/
    }

    // Vorbedingung: cs!= null, sun != null, zoom > 0, current != null, ameisen != null
    // Nachbedingung: Es wird das CD Fenster gezeichnet
    private void drawLandscape(CodeDraw cs, Sun sun, int zoom, Simulation current, Ant[] ameisen) {

        for (int i = 0; i < landscapeSize * zoom; i += zoom) {

            int duftI = i / zoom; // Index für das duft-Array in 1er-Schritten
            int landscapeJ = 0;
            for (int j = 0; j < landscapeSize * zoom; j += zoom) {

                int duftJ = j / zoom;
                landscapeJ = j + balkenhöhe * zoom; // Index für das Landscape Array das erst später beginnt nach dem Anzeigebalken

                if(!isWinter){
                    cs.setColor(Color.white); //das Feld übermalen
                    cs.fillRectangle(i, landscapeJ, zoom, zoom);
                }
                else{
                    cs.setColor(new Color(120, 217, 250));
                    cs.drawRectangle(i, landscapeJ, zoom, zoom);
                }
                // Hausameise Duft array
                double blackDuftTemp = current.duftspur.blackArray[duftI][duftJ];
                if (blackDuftTemp != 0) {
                    if (blackDuftTemp > 1) blackDuftTemp = 1;
                    int gray = (int) (blackDuftTemp * 150);
                    cs.setColor(new Color(255 - gray, 255 - gray, 255 - gray)); //schwach: (255, 255, 255) stark: (255, 0, 0)
                    cs.fillRectangle(i, landscapeJ, zoom, zoom);
                }

                // BlaueKampfAmeise Duft array
                double blueDuftTemp = current.duftspur.blueArray[duftI][duftJ];
                if (blueDuftTemp != 0) {
                    if (blueDuftTemp > 1) blueDuftTemp = 1;
                    int blue = (int) (blueDuftTemp * 150);
                    cs.setColor(new Color(255 - blue, 255 - blue, 255));
                    cs.fillRectangle(i, landscapeJ, zoom, zoom);
                }

                // RoteFeuer Ameise
                double redDuftTemp = current.duftspur.redArray[duftI][duftJ];
                if (redDuftTemp != 0) {
                    if (redDuftTemp > 1) redDuftTemp = 1;
                    int red = (int) (redDuftTemp * 150);
                    cs.setColor(new Color(255, 255 - red, 255 - red)); //schwach: (255, 255, 255) stark: (255, 0, 0)
                    cs.fillRectangle(i, landscapeJ, zoom, zoom);
                }

                // Food Duft array
                double foodDuftTemp = current.duftspur.smellArray[duftI][duftJ];
                if (foodDuftTemp != 0) {
                    if (foodDuftTemp > 1) foodDuftTemp = 1;
                    int green = (int) (foodDuftTemp * 150);
                    cs.setColor(new Color(255 - green, 255, 255 - green)); //schwach: (255, 255, 255) stark: (255, 0, 0)
                    cs.fillRectangle(i, landscapeJ, zoom, zoom);
                }

                if (current.landscape[duftI][duftJ] == 1) { //Futterquelle
                    cs.setColor(new Color(125, 43, 17));
                    cs.fillRectangle(i, landscapeJ, zoom, zoom);
                }
                if (current.landscape[duftI][duftJ] == 2) { //schwarzer Bau
                    cs.setColor(new Color(30, 30, 30));
                    cs.fillSquare(i, landscapeJ, zoom * 2);
                }
                if (current.landscape[duftI][duftJ] == 3) { //roter Bau
                    cs.setColor(new Color(200, 0, 0));
                    cs.fillSquare(i, landscapeJ, zoom * 2);
                }
                if (current.landscape[duftI][duftJ] == 4) { //blauer Bau
                    cs.setColor(new Color(0, 0, 200)); //Ameisenbau
                    cs.fillSquare(i, landscapeJ, zoom * 2);
                }
            }
        }
        int ybalkenhöhe = balkenhöhe * zoom;
        for (Ant ant : ameisen) {
            //soll ameisen setzen
            if (ant.getClass() == NormalAnt.class) cs.setColor(new Color(0, 0, 0));
            else if (ant.getClass() == BlueWarAnt.class) {
                cs.setColor(new Color(0, 0, 255));
            } else if (ant.getClass() == RedFireAnt.class) {
                cs.setColor(new Color(255, 0, 0));

            }
            cs.fillCircle(ant.getX() * zoom + zoom / 2, (ant.getY() * zoom + zoom / 2) + ybalkenhöhe, zoom / 3);
        }

        for (int i = 0; i < landscapeSize; i++) {
            for (int j = 0; j < balkenhöhe; j++) {
                cs.setColor(Color.white);
                cs.fillRectangle(i, j, zoom, zoom);
            }
        }
        // sun gets drawn
        sun.drawSunOrMoon();

        if (simulationSteps % 1000 < 250){
            cs.setColor(Color.green);
            cs.drawText(5,5,"Frühling");
        }
        if (simulationSteps % 1000 < 500 && simulationSteps % 1000 >= 250){
            cs.setColor(Color.orange);
            cs.drawText(5,5,"Sommer");
        }
        if (simulationSteps % 1000 < 750 && simulationSteps % 1000 >= 500){
            cs.setColor(Color.red);
            cs.drawText(5,5,"Herbst");
        }
        if (simulationSteps % 1000 >= 750){
            cs.setColor(Color.blue);
            cs.drawText(5,5,"Winter");
        }


        cs.show(300);
    }
}
