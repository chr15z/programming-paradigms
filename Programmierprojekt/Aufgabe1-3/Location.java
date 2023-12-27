import java.util.stream.IntStream;

//Repräsentiert entweder einen Ameisenbau (anfangs 5x5) oder eine Futterquelle (anfangs 3x3)
public class Location {
    private int food;
    private int x;
    private int y;
    private int size;
    private Simulation current;
    private int num; //Nummer der Location im Array
    private Rechner calculator = new Rechner();

    public Location(Simulation current, boolean isAntHill, int x, int y, int num) {
        this.x = x;
        this.y = y;
        this.current = current;
        this.num = num;
        if (isAntHill) { //erstelle einen Ameisenbau
            size = 5;
            food = 50;
        } else { //erstelle eine Futterstelle
            size = 3;
            food = 30;
        }
        updateLocation();
    }

    public int getX() {
        return x;
    }  //die X Koordinate des Amiesenbaus wird abgerufen

    public int getY() { //die Y Koordinate des Amiesenbaus wird abgerufen
        return y;
    }

    public int getSize() { //derzeitige Größe des Baus wird abgerufen
        return size;
    }

    public int getFood() {
        return food;
    }

    // STYLE funtkionale Programmierung
    //Nachbedingung Soll die Location auf dem Array updaten - Anfangs und nachdem sich die größe verändert
    public void updateLocation() {
        IntStream.range(0, num == 1 ? 3 : 5)
                .forEach(i ->
                        IntStream.range(0, num == 1 ? 3 : 5)
                                .forEach(j ->
                                        current.landscape[calculator.modulo(x + i, current.landscapeSize)][calculator.modulo(y + j, current.landscapeSize)] = 0
                                )
                );

        IntStream.range(0, size)
                .forEach(i ->
                        IntStream.range(0, size)
                                .forEach(j ->
                                        current.landscape[calculator.modulo(x + i, current.landscapeSize)][calculator.modulo(y + j, current.landscapeSize)] = num
                                )
                );
    }



    //Nachbedingung = erhöht food um 1 und returnt true falls sich der ameisenBau vergrößert
    public boolean increaseFood() {
        food++;
        if (food % 10 == 0){
            size++;
            updateLocation();
            current.updateFutterquellen();
            return true;
        }
        return false;
    }

    //Nachbedingung verringert food um 1 und returnt true falls sich die location auflöst
    public boolean decreaseFood() {
        food--;
        if (food % 10 == 0){
            size --;
            updateLocation();
            if (food == 0){
                //soll neue location spawnen
                current.erstelleFutterquelle();
                return true;
            }
        }
        return false;
    }

    // Nachbedingung Dient als Übersicht über die Größen der Baus am Terminal
    @Override
    public String toString() {
        String s = "";
        if (num == 1) s = "foodsource";
        else s = "AntHill";
        return "Capacity = " + food;
    }
}
