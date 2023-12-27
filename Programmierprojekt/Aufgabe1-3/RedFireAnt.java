
// Ameisenart Erbt alle Methoden von abstrakter Klasse Ant
public class RedFireAnt extends Ant {

    public static int erkennungsziffer = 3;
    //Vorbedindungen: Konstruktor muss die Parameter übergeben bekommen, die auch der Ant-Konstruktor hat
    public RedFireAnt(Location redanthill, int landscapeSize){
        super(redanthill,landscapeSize);
    }

}