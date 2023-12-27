
@Zusicherung(nachbedingung = "A AntColony that lives in a formicarium",  author = Author.Hannah)
public abstract class AntColony {

    private int size;

    public int getSize() {
        return size;
    }

    @Zusicherung(nachbedingung = "Vistior, Multimethods to get the right AntColony Typ return boolean, but overall checks if size and Type of Formicarium and Ant is equal")
    public abstract boolean isSuitable(Formicarium formicarium);

    @Zusicherung(nachbedingung = "Vistior, Multimethods to get the right AntColony Typ return boolean, but overall checks if size and Type of Formicarium and Ant is compatible")
    public abstract boolean isSemiSuitable(Formicarium formicarium);

    @Zusicherung(nachbedingung = "display the AntColony")
    public abstract void displayColony();

}
