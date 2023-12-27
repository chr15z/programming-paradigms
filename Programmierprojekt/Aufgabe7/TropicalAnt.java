
@Zusicherung(nachbedingung = "a specific Ant Species that can only survive in a regulated formicarium", author = Author.Marlene)
public class TropicalAnt extends AntColony {
    private int size; // 1 small, 2 medium, 3 big

    public TropicalAnt(int size) {
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
        System.out.println("Tropical Ant of the size" + size);
    }


}
