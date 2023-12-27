@Zusicherung(nachbedingung = "a specific Ant Species",  author = Author.Marlene)
public class EuropeanAnt extends AntColony {

    private int size;

    public EuropeanAnt(int size) {
        this.size = size;
    }

    @Zusicherung(nachbedingung = "Vistior Mulitmethods to get the right AntColony Typ return boolean, but overall checks if size and Type of Formicarium and Ant is equal")
    public boolean isSuitable(Formicarium formicarium) {
        return formicarium.isSuitable(this);
    }

    @Zusicherung(nachbedingung = "Vistior Multimethods to get the right AntColony Typ return boolean, but overall checks if size and Type of Formicarium and Ant is compatible")
    public boolean isSemiSuitable(Formicarium formicarium) {
        return formicarium.isSemiSuitable(this);
    }

    @Override
    public int getSize() {
        return size;
    }

    @Zusicherung(nachbedingung = "display the AntColony")
    public void displayColony() {
        System.out.println("European Ant of the size" + size);
    }
}
