
// STYLE: Funktionale Programmierung
// Ein Hilfsobjekt welches wichtige Rechenoperationen beinhaltet


public class Rechner {
    int landscapesize;

    public Rechner(){

    }
    public Rechner(int landscapesize){
        this.landscapesize = landscapesize;
    }

    // STYLE Funktionale Programmierung
    //Vorbedingung divident, divisor !=null
    //Nachbedingung ein Modulo rechner welcher negative wie postive Zahlen behandelt
    public int modulo(int dividend, int divisor) {
            return dividend < 0
                    ? (divisor - Math.abs(dividend)) % divisor
                    : dividend % divisor;
        }

    //Vorbedingung x1,y1,x2,y2 != null
    //Nachbedingung Differenz in den x- und y-Koordinaten berechnen
    public double calculateDistance(double x1, double y1, double x2, double y2) {
        double x = Math.min(Math.abs(x2 - x1), modulo((int) (Math.abs(landscapesize - x2 + x1)), landscapesize));
        double y = Math.min(Math.abs(y2 - y1), modulo((int) (Math.abs(landscapesize - y2 + y1)), landscapesize));

        // Den Satz des Pythagoras anwenden, um die Distanz zu berechnen
        double distance = Math.sqrt(x * x + y * y);
        return distance;
    }
}