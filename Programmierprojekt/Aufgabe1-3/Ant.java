import java.util.Random;
// STYLE: Objektorientiert
// Ein Objekt, dass eine Ameise darstellt.

public abstract class Ant {
    private boolean feed;
    private int y;
    private int x;
    private Blickrichtung blickRichtung;
    private State current;
    private int landscapeSize;
    private Location home;
    private Rechner calculator;

    // STYLE: Prozedural
    // Vorbedingungen: input != null;
    // Nachbedingungen: Ameisen haben Startkoordinaten und eine Blickrichtung
    // Ameisen werden am Anfang jeder Simulation in zufällig, aber in einem gewissen Radius um den jeweiligen Bau platziert.
    // Die Ameisen befinden sich anfangs im Zustand "Erkundung" und ihnen wird eine zufällige Blickrichtung zugewiesen.
    public Ant(Location anthill, int landscapeSize) {
        this.landscapeSize = landscapeSize;
        calculator = new Rechner(landscapeSize);
        this.feed = false;
        home = anthill;
        int antcoordinates = (int) (Math.random() * 4);
        if (antcoordinates == 0) {
            x = (int) (anthill.getX() + Math.random() * 3);
            y = (int) (anthill.getY() + Math.random() * 3);
        } else if (antcoordinates == 1) {
            x = (int) (anthill.getX() - Math.random() * 3);
            y = (int) (anthill.getY() + Math.random() * 3);
        } else if (antcoordinates == 2) {
            x = (int) (anthill.getX() + Math.random() * 3);
            y = (int) (anthill.getY() - Math.random() * 3);
        } else {
            x = (int) (anthill.getX() - Math.random() * 3);
            y = (int) (anthill.getY() - Math.random() * 3);
        }
        x = Math.max(x, 0);
        x = Math.min(x, landscapeSize - 1);
        y = Math.max(y, 0);
        y = Math.min(y, landscapeSize - 1);

        current = State.Erkundung;
        int randomDirection = new Random().nextInt(4);
        switch (randomDirection) {
            case 0 -> blickRichtung = Blickrichtung.Up;
            case 1 -> blickRichtung = Blickrichtung.Down;
            case 2 -> blickRichtung = Blickrichtung.Left;
            case 3 -> blickRichtung = Blickrichtung.Right;
        }
        Random zufall = new Random();
    }

    //Nachbedingung: setzt es nicht neu, sondern updatet den aktuellen Punkt
    public void setX(int x) {
        this.x = calculator.modulo(this.x + x, landscapeSize);
    }
    //Nachbedingung: setzt es nicht neu, sondern updatet den aktuellen Punkt
    public void setY(int y) {
        this.y = calculator.modulo(this.y + y, landscapeSize);
    }
    public int getX() {
        return x;
    } //X Koordinate wird abgerufen

    public int getY() {
        return y;
    } //Y Koordinate wird abgerufen

    public Blickrichtung getBlickrichtung() {
        return blickRichtung;
    } //Blickrichtung der Ameise wird abgerufen

    public State getZustand() {
        return current;
    } //Zustand der Ameise wird abgerufen

    public void setZustand(State neuerState) {
        this.current = neuerState;
    } //Ameise befindet sich nach Aufruf in neuem Zustand

    public void setFeed(Boolean temp) {
        this.feed = temp;
    }

    public boolean hasFutter() {
        return feed;
    }

    public void setBlickRichtung(Blickrichtung blickRichtung) {
        this.blickRichtung = blickRichtung;
    } //Blickrichtung der Ameise wird gesetzt

    @Override
    //Nachbedingung Eigenschaften der Ameise werden  am Terminal geprintet
    public String toString() {
        return "ant{" +
                "feed=" + feed +
                ", y=" + y +
                ", x=" + x +
                ", blickRichtung=" + blickRichtung +
                ", current=" + current +
                ", landscapeSize=" + landscapeSize +
                '}';
    }

    public Location getHome() {
        return home;
    }


    public boolean increaseHome() { //Nachbedingung: Returnt true, falls sich der Bau der Ameise vergrößert
        if(home.increaseFood()) return true;
        return false;
    }
}

enum State { //Auflistung der möglichen Zustände, in der sich die Ameise befindet
    Futterbringung,
    Futtersuche,
    Erkundung
}

enum Blickrichtung { //Auflistung der möglichen Blickrichtungen, in die sich die Ameise wenden kann
    Up,
    Down,
    Left,
    Right
}