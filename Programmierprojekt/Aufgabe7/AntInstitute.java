
@Zusicherung(nachbedingung = "an Object that represents an Antresearchinstitute",  author = Author.Hannah)
public class AntInstitute {

    private Inventorylist inventorylist;

    public AntInstitute(Inventorylist inventorylist) {
        this.inventorylist = inventorylist;
    }

    public Inventorylist getInventorylist() {
        return inventorylist;
    }


}
