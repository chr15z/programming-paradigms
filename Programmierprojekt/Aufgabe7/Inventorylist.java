import java.util.LinkedList;

@Zusicherung(nachbedingung = "A List hat contains every Formicarium in an Institute", author = Author.Chris)
public class Inventorylist {

    private LinkedList<Formicarium> list;

    public Inventorylist() {
        list = new LinkedList<>();
    }


    @Zusicherung(nachbedingung = "adds a new Formicarium to the List")
    public void addForm(Formicarium formicarium) {
        this.list.add(formicarium);
    }

    @Zusicherung(vorbedingung = "list.length > 0, list != null", nachbedingung = "removes a defect Formicarium from the List")
    public void deleteForm(Formicarium formicarium) {
        this.list.remove(formicarium);
    }


    @Zusicherung(vorbedingung = "ant != null", nachbedingung = "returnes a suitable Formicarium preferable one with the exact same size else return null")
    public Formicarium assignForm(AntColony ant) {
        for (Formicarium formi : list) {
            if (ant.isSuitable(formi)) return formi;
        }
        for (Formicarium formi : list) {
            if (ant.isSemiSuitable(formi)) return formi;
        }
        return null;
    }


    @Zusicherung(vorbedingung = "formicarium != null", nachbedingung = "deletes the relation of the Formicarium to its AntColony and adds the empty Formicarium to the List")
    public void returnForm(Formicarium formicarium) {
        formicarium.deleteAntAssignment();
        list.add(formicarium);
    }

    @Zusicherung(nachbedingung = "returns sum of prices of all Formicarien in the List")
    public int  priceFree() {
        int sum = 0;
        for (Formicarium formi : list) {
            sum += formi.getPrice();
        }
      return sum;
    }

    @Zusicherung(nachbedingung = "prints out the sum of the Prices of all Formicarium that are currently occupied")
    public void priceOccupied() {
        int sum = 0;
        for (Formicarium formi : list) {
            if (formi.isFree()) {
                sum += formi.getPrice();
            }
        }
        System.out.println("Sum of all occupied Formicarium-Prices in the Inventory list:  " + sum);
    }

    @Zusicherung(nachbedingung = "prints out all the Formicarium in the Inventory list")
    public void showFormicarium() {
        for (Formicarium formi : list) {
            formi.printInfos();
            System.out.println();
        }
    }

    @Zusicherung(nachbedingung = "displays all the antColonies of an institute with all the information especially in which Formicarium the live")
    public void showAnts() {
        for (Formicarium formi : list) {
            formi.printAntInfos();
            System.out.println();
        }
    }
}
