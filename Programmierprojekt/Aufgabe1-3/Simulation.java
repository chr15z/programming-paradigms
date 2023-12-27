// STYLE: Objektorientiert, Prozdedural
// Ein Objekt, welches die Ameisen sowie den Anmeisenbau nach einem Schritt updated / bewegt
// BAD: Es hätten mehr Modularisierungseinheiten eingebaut werden sollen in der Move Methode da diese unübersichtlich ist

public class Simulation {
    public int landscapeSize;
    public int[][] landscape;
    private Location[] futterquellen;
    public ScentTrail duftspur;
    private Rechner rechner = new Rechner();

    // Vorbedingung: LandscapeSize, zoom != null
    // Nachbedingung: futterquellen ins array "einzeichnen"
    //0 = leeres feld, 1 = (3-10) 3x3 futterquellen , 2 = schwarzer Bau, 3 = roter Bau, 4 = blauer Bau
    public Simulation(int landscapeSize, int zoom) {
        this.landscapeSize = landscapeSize;
        landscape = new int[landscapeSize][landscapeSize];

        duftspur = new ScentTrail(landscape, landscapeSize);

        futterquellen = new Location[3 + (int) (Math.random() * 7)]; //3-10 futterquellen
        for (int i = 0; i < futterquellen.length; i++) {
            futterquellen[i] = new Location(this, false, (int) (Math.random() * landscapeSize), (int) (Math.random() * landscapeSize), 1);
            landscape[futterquellen[i].getX()][futterquellen[i].getY()] = 1;
        }
    }

    //Nachbedingung: wenn eine Futterstelle "leer" ist kann eine andere erstellt werden
    public void erstelleFutterquelle() {
        for (int i = 0; i < futterquellen.length; i++) {
            if (futterquellen[i].getSize() == 0)
                futterquellen[i] = new Location(this, false, (int) (Math.random() * landscapeSize), (int) (Math.random() * landscapeSize), 1);
            futterquellen[i].updateLocation();
        }
        updateFutterquellen();
    }

    //Nachbedingung: die Futterquelle wird kleiner wenn Ameisen von ihr zehren
    public void updateFutterquellen() {
        for (int i = 0; i < futterquellen.length; i++) {
            for (int j = 0; j < futterquellen[i].getSize(); j++) {
                landscape[rechner.modulo(futterquellen[i].getX() + j, landscapeSize)][rechner.modulo(futterquellen[i].getY() + j, landscapeSize)] = 1;
            }
        }
    }


    //Vorbedingung: array != null
    //Nachbedingung: soll ameisen 1 schritt in die jeweilig für sie richtige Richtung gehen lassen
    // STYLE: prozedural
    public void move(Ant[] array, boolean winter) {
        for (Ant ant : array) {
            if(ant.getClass()== BlueWarAnt.class || !winter) { //nur blaue ameisen können sich im Winter bewegen
                geheEinenSchritt(ant);
                if(ant.getClass()== RedFireAnt.class) geheEinenSchritt(ant); //rote dürfen 2 mal gehen
            }
            if (ant.getZustand() == State.Erkundung) {
                //Sie bevorzugt die Richtungen mit schwacher oder keiner Duftspur.

                //Wenn sie ein Nachbarfeld mit einer sehr starken Duftspur findet oder auf eine Ameise trifft, die Futter mit sich trägt,
                //wechselt sie in den Zustand „Futtersuche“.

                // Ameiseninteraktion mit einer Anderen Ameise


                // Wenn sie auf eine Futterquelle stößt, nimmt sie Futter auf und wechselt in den Zustand „Futterbringung“.
                if (landscape[ant.getX()][ant.getY()] == 1) {
                    for (int i = 0; i < futterquellen.length; i++) { //soll food verringern wenn eine Ameise auf das Feld kommt
                        for (int j = 0; j < futterquellen[i].getSize(); j++) {
                            for (int k = 0; k < futterquellen[i].getSize(); k++) {

                                if (rechner.modulo(futterquellen[i].getX() + j, landscapeSize) == ant.getX() && rechner.modulo(futterquellen[i].getY() + k, landscapeSize) == ant.getY()) {
                                    futterquellen[i].decreaseFood();
                                }
                            }
                        }
                    }
                    ant.setFeed(true);
                    ant.setZustand(State.Futterbringung);
                }
            } else if (ant.getZustand() == State.Futtersuche) {

                if (landscape[ant.getX()][ant.getY()] == 1) {
                    ant.setFeed(true);
                    ant.setZustand(State.Futterbringung);
                }

            } else if (ant.getZustand() == State.Futterbringung) {
                //Die Ameise trägt Futter mit sich. Bevorzugt werden Richtungen mit möglichst starker Duftspur
                //Wenn die Ameise auf den Bau stößt, legt sie das Futter ab und wechselt in den Zustand „Futtersuche“.
                if (landscape[ant.getX()][ant.getY()] == 2 && ant.getClass() == NormalAnt.class) {
                    ant.setFeed(false);
                    ant.setZustand(State.Futtersuche);
                    ant.increaseHome();
                }
                if (landscape[ant.getX()][ant.getY()] == 3 && ant.getClass() == RedFireAnt.class) {
                    ant.setFeed(false);
                    ant.setZustand(State.Futtersuche);
                    ant.increaseHome();
                }
                if (landscape[ant.getX()][ant.getY()] == 4 && ant.getClass() == BlueWarAnt.class) {
                    ant.setFeed(false);
                    ant.setZustand(State.Futtersuche);
                    ant.increaseHome();
                }

            }

        }
    }

    // Vorbedigung: Ant != null
    // Nachbedingung die X und Y Position sowie die Blickrichtung der Ameise werden abgedated
    private void geheEinenSchritt(Ant ant) {
        int[][] bewegungsOffsetsOben = {
                {1, 0},     // Rechts
                {1, -1},    // Rechts oben
                {0, -1},    // Oben
                {-1, -1},   // Links oben
                {-1, 0}     // Links
        };
        int[][] bewegungsOffsetsUnten = {
                {1, 0},     // Rechts
                {1, 1},     // Rechts unten
                {0, 1},     // Unten
                {-1, 1},    // Links unten
                {1, 0}      // Links
        };
        int[][] bewegungsOffsetsRechts = {
                {0, 1},     // Unten
                {1, 1},     // Rechts unten
                {1, 0},     // Rechts
                {1, -1},    // Rechts oben
                {0, -1},    // Oben
        };
        int[][] bewegungsOffsetsLinks = {
                {0, 1},     // Unten
                {-1, 1},    // Links unten
                {1, 0},     // Links
                {-1, -1},   // Links oben
                {0, -1},    // Oben
        };

        if (ant.getBlickrichtung() == Blickrichtung.Up) {
            int richtung = checkeDuft(ant, bewegungsOffsetsOben);
            int[] schritt = bewegungsOffsetsOben[richtung];
            ant.setX(schritt[0]);
            ant.setY(schritt[1]);
            if (richtung == 0) ant.setBlickRichtung(Blickrichtung.Right);
            if (richtung == 4) ant.setBlickRichtung(Blickrichtung.Left);
        }

        if (ant.getBlickrichtung() == Blickrichtung.Down) {
            int richtung = checkeDuft(ant, bewegungsOffsetsUnten);
            int[] schritt = bewegungsOffsetsUnten[richtung];
            ant.setX(schritt[0]);
            ant.setY(schritt[1]);
            if (richtung == 0) ant.setBlickRichtung(Blickrichtung.Right);
            if (richtung == 4) ant.setBlickRichtung(Blickrichtung.Left);
        }

        if (ant.getBlickrichtung() == Blickrichtung.Right) {
            int richtung = checkeDuft(ant, bewegungsOffsetsRechts);
            int[] schritt = bewegungsOffsetsRechts[richtung];
            ant.setX(schritt[0]);
            ant.setY(schritt[1]);
            if (richtung == 0) ant.setBlickRichtung(Blickrichtung.Down);
            if (richtung == 4) ant.setBlickRichtung(Blickrichtung.Up);
        }

        if (ant.getBlickrichtung() == Blickrichtung.Left) {
            int richtung = checkeDuft(ant, bewegungsOffsetsLinks);
            int[] schritt = bewegungsOffsetsLinks[richtung];
            ant.setX(schritt[0]);
            ant.setY(schritt[1]);
            if (richtung == 0) ant.setBlickRichtung(Blickrichtung.Down);
            if (richtung == 4) ant.setBlickRichtung(Blickrichtung.Up);
        }
    }

    // Vorbedingung: ant != null, bewegungsoffset != null
    // Nachbedingung: Es soll ein Integer zurückgegebn werden der alle Duftspuren um die Ameise analysiert und aufgrund dessen
    // feststellt welche Richtung die beste für die Ameise ist >> Werte zwischen 0 und bewegungsoffset.length
    private int checkeDuft(Ant ant, int[][] bewegungsoffset) {
        return duftspur.chekScent(ant, bewegungsoffset);
    }


    // Vorbedingung: ameisenArray != null
    // der Duft auf den Feldern wird pro Simulationsschritt weniger, außer eine Ameise ist drauf
    public void updateSmell(Ant[] ameisenArray, boolean isWinter) {
        if (!isWinter) //wenn kein winter ist soll der smell geupdated werden
            duftspur.updateSmell(ameisenArray);
    }
}
