//Ameisenart die alle Methoden von abstrakter Klasse Ant, überwintert aber nicht
public class BlueWarAnt extends Ant {
    public boolean winter = false;
    public static int erkennungsziffer = 2;

    //Vorbedingungen: Konstruktor muss die Parameter übergeben bekommen, die auch der Ant-Konstruktor hat
    public BlueWarAnt(Location blauerbau, int landscapeSize){
        super(blauerbau,landscapeSize);
    }
}
