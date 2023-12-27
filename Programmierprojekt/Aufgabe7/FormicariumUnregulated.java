
@Zusicherung(nachbedingung = "A glass Container Formicarium that can not control the temperatur and humidity", author = Author.Chris)
public class FormicariumUnregulated extends Formicarium {

    private int price;
    private boolean free;
    private AntColony antType;
    private int size;

    @Zusicherung(vorbedingung = "Parameters except for antType must be != null, antType can be null because not every Formicarium is inhabited")
    public FormicariumUnregulated(int price, boolean free, AntColony antType, int size) {
        super(price, free, antType, size);
        this.price = price;
        this.free = free;
        this.antType = antType;
        this.size = size;
        setFree();
    }

    @Zusicherung(nachbedingung = "return boolean, regulated Formicarium only suitable for tropical Ants and checks if size is equal")
    public boolean isSuitable(EuropeanAnt antType) {
        return size == antType.getSize();
    }

    public boolean isSuitable(TropicalAnt antType) {
        return false;
    }

    @Zusicherung(nachbedingung = "return boolean, regulated Formicarium only suitable for tropical Ants and checks if size is compatible")
    public boolean isSemiSuitable(TropicalAnt antType) {
        return false;
    }

    public boolean isSemiSuitable(EuropeanAnt antType) {
        return size - 1 == antType.getSize();
    }

    public void printInfos() {
        System.out.println(toString() + "\nAntColony: " + antType);
    }

    public void printAntInfos() {
        if (antType != null) {
            System.out.println("AntColony: " + antType + "\nlives in: " + toString());
        }

    }

    @Override
    public String toString() {
        return "Unregulated Formicarium \n" + "Size: " + size + "\nis inhabited: " + free;
    }

    private void setFree(){
        if (antType == null) free = true;
        else free = false;
    }

}
