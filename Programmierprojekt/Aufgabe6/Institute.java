import java.util.ArrayList;
import java.util.List;

@Zusicherung(invariante = "Jedes Institut hat einen unveränderlichen Namen")
@CreatedBy(Author.Chris)
public class Institute {
    private final String name;
    private List<Formicarium> formicariaList;

    @Zusicherung(vorbedingung = "name != null", nachbedingung = "name and formicatiaList gets initialized")
    public Institute(String name) {
        this.name = name;
        this.formicariaList = new ArrayList<>();
    }

    @Zusicherung(vorbedingung = "formicarium != null", nachbedingung = "new formicarium gets added to formicariaList")
    public void addFormicarium(Formicarium formicarium) {
        formicariaList.add(formicarium);
    }

    @Zusicherung(vorbedingung = "formicarium != null, formicariumList.length > 0", nachbedingung = "formicarium gets removed from formicariaList")
    public void removeFormicarium(Formicarium formicarium) {
        formicariaList.remove(formicarium);
    }
    @Zusicherung(nachbedingung = "All Formicarium in formicariumList get displayed")
    public void displayAllFormicaria() {
        System.out.println("Formicaria in " + name + ":");
        for (Formicarium formicarium : formicariaList) {
            System.out.println("Formicarium Name: " + formicarium.getName());
        }
    }
}
