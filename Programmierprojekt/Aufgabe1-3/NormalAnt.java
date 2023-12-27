// Ameisenart, Erbt alle Methoden von abstrakter Klasse Ant
public class NormalAnt extends Ant {
    public static int erkennungsziffer = 1;
    //Vorbedingungen: Konstruktor muss die Parameter übergeben bekommen, die auch der Ant-Konstruktor hat
    public NormalAnt(Location bau, int landscapeSize){
        super(bau,landscapeSize);
    }
}

