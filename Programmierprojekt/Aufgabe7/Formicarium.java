
@Zusicherung(nachbedingung = "A glass Container that contains none or one antColony",  author = Author.Hannah)
public abstract class Formicarium {
    private int price;
    private boolean free;
    private AntColony antType;
    private int size;

    @Zusicherung(vorbedingung = "Parameters except for antType must be != null, antType can be null because not every Formicarium is inhabited")
    public Formicarium(int price, boolean free, AntColony antType, int size) {
        this.price = price;
        this.free = free;
        this.antType = antType;
        this.size = size;
        setFree();
    }

    @Zusicherung(nachbedingung = "return boolean, regulated Formicarium only suitable for tropical Ants and checks if size is equal")
    public abstract boolean isSuitable(TropicalAnt antType);

    public abstract boolean isSuitable(EuropeanAnt antType);

    @Zusicherung(nachbedingung = "return boolean, regulated Formicarium only suitable for tropical Ants and checks if size is compatible")
    public abstract boolean isSemiSuitable(TropicalAnt antType);

    public abstract boolean isSemiSuitable(EuropeanAnt antType);

    @Zusicherung(nachbedingung = "deletes the relation to a Antcolony, antfarm == null")
    public void deleteAntAssignment() {
        this.antType = null;
        setFree();
    }


    @Zusicherung(nachbedingung = "returnes the Price of a Formicarium")
    public int getPrice() {
        return price;
    }

    @Zusicherung(nachbedingung = "returnes if Formicarium is free ")
    public boolean isFree() {
        return this.free;
    }

    @Zusicherung(nachbedingung = "prints out all the information of a Formicarium in the console")
    public abstract void printInfos();

    public abstract void printAntInfos();

    private void setFree(){
        if (antType == null) free = true;
        else free = false;
    }

}
