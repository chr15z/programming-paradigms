import javax.naming.Name;
import javax.security.sasl.AuthorizeCallback;
import java.util.ArrayList;
import java.util.List;

@CreatedBy(Author.Hannah)
@Zusicherung(invariante = "der Name des Formicariums name ist unveränderlich")
public class Formicarium {
    private final String name;
    private List<Nest> nests;
    private AntType antTyp;

    @Zusicherung(vorbedingung = "antType, name != null", nachbedingung = "name, antType and nests(ArrayList) get initialised")
    public Formicarium(String name, AntType antType) {
        this.name = name;
        this.antTyp = antType;
        this.nests = new ArrayList<>();
    }

    public void setAntTyp(AntType antTyp) {
        this.antTyp = antTyp;
    }

    public AntType getAntTyp() {
        return this.antTyp;
    }

    public void deleteAntTyp() {
        this.antTyp = null;
    }

    @Zusicherung(vorbedingung = "nest != null", nachbedingung = "adds nest to the formicariumlist")
    public void addNest(Nest nest) {
        nests.add(nest);
    }

    @Zusicherung(vorbedingung = "nest != null", nachbedingung = "removes nest out of the formicariumlist")
    public void removeNest(Nest nest) {
        nests.remove(nest);
    }

    @Zusicherung(nachbedingung = "average Nestvolume all Nests in a Formicarium gets returned")
    public double averageVolumeNests() {
        double totalVolume = 0;
        for (Nest nest : nests) {
            totalVolume += nest.getVolume();
        }
        if (nests.isEmpty()) throw new ArithmeticException("Keine Nester im Formicarium: " + toString());
        return totalVolume / nests.size();
    }

    @Zusicherung(nachbedingung = "average Volume of all the NestAir-Nests in a Formicarium gets returned")
    public double averageVolumeNestsAir() {
        double totalVolume = 0;
        List<NestAir> list = getNestsAir();
        for (NestAir nest : list) {
            totalVolume += nest.getVolume();
        }
        if (list.isEmpty()) throw new ArithmeticException("Keine Nester im Formicarium: " + toString());
        return totalVolume / list.size();
    }

    @Zusicherung(nachbedingung = "average volume of the water container of all nests with humidifiers in a Formicarium gets returned")
    public double averageWatervolumeNestsAir() {
        double totalVolume = 0;
        List<NestAir> list = getNestsAir();
        for (NestAir nest : list) {
            totalVolume += nest.getWaterVolume();
        }
        if (list.isEmpty()) throw new ArithmeticException("Keine Nester im Formicarium: " + toString());
        return totalVolume / list.size();
    }

    @Zusicherung(nachbedingung = "average nest volume of all nests with heating in a Formicarium gets returned")
    public double averageVolumeNestsHeat() {
        double totalVolume = 0;
        List<NestHeating> list = getNestsHeating();
        for (NestHeating nest : list) {
            totalVolume += nest.getVolume();
        }
        if (list.isEmpty()) throw new ArithmeticException("Keine Nester im Formicarium: " + toString());
        return totalVolume / list.size();
    }

    @Zusicherung(nachbedingung = "average performance of all nests with heating in a Formicarium gets returnt")
    public int averagePerformanceNestsHeat() {
        int totalVolume = 0;
        List<NestHeating> list = getNestsHeating();
        for (NestHeating nest : list) {
            totalVolume += nest.getPerformance();
        }
        if (list.isEmpty()) throw new ArithmeticException("Keine Nester im Formicarium: " + toString());
        return totalVolume / list.size();
    }

    @Zusicherung(nachbedingung = "The average weight of the sand/clay mixture of all nests in a Formicarium - " +
            "all nests together and additionally broken down according to the type of nest climate control (heating or humidifier)")
    public void printAverageWeight() {
        double weightHeat = 0;
        double weightAir = 0;
        int heatCounter = 0;
        int airCounter = 0;
        for (Nest nest : nests) {
            if (nest.getMaterial().getClass() == SandMix.class) { // Durch alle Nests mit Sand-Lehm-Gemisch iterieren
                if (nest.getClass() == NestAir.class){
                    weightAir += (double) ((SandMix) nest.getMaterial()).getMeasurements();
                    airCounter++;
                }
                else{
                    weightHeat += ((double)((SandMix) nest.getMaterial()).getMeasurements());
                    heatCounter++;
                }
            }
        }

        if (airCounter == 0 || heatCounter == 0) throw new ArithmeticException("Zu wenig Nester im Formicarium: " + toString());
        System.out.println(toString() + ":\nDurchschnittsgewicht des Sand-Lehmgemisches aller Neste: " + (weightAir + weightHeat) / (airCounter + heatCounter)
                + "\nDurchschnittsgewicht des Sand-Lehmgemisches aller Nester mit Heizung: " + weightHeat / heatCounter
                + "\nDurchschnittsgewicht des Sand-Lehmgemisches aller Nester mit Luftbefeuchter: " + weightAir / airCounter);
    }

    @Zusicherung(nachbedingung = "The average volume of the aerated concrete slab of all nests in a Formicarium - " +
            "all nests together and additionally broken down according to the type of nest climate control (heating or humidifier)")
    public void printAverageVolumeGas() {
        double weightHeat = 0;
        double weightAir = 0;
        int heatCounter = 0;
        int airCounter = 0;
        for (Nest nest : nests) {
            if (nest.getMaterial().getClass() == GasConcreteSlab.class) { // Durch alle Nests mit Gasbetonplatte iterieren
                if (nest.getClass() == NestAir.class){
                    weightAir += (double) ((GasConcreteSlab) nest.getMaterial()).getMeasurements();
                    airCounter++;
                }
                else {
                    weightHeat += (double) ((GasConcreteSlab) nest.getMaterial()).getMeasurements();
                    heatCounter++;
                }
            }
        }
        if (airCounter == 0 || heatCounter == 0) throw new ArithmeticException("Zu wenig Nester im Formicarium: " + toString());
        System.out.println(toString() + ":\nDurchschnittsgewicht der Gasbetonplatte aller Neste: " + (weightAir + weightHeat) / (airCounter + heatCounter)
                + "\nDurchschnittsgewicht der Gasbetonplatte aller Nester mit Heizung: " + weightHeat / heatCounter
                + "\nDurchschnittsgewicht der Gasbetonplatte aller Nester mit Luftbefeuchter: " + weightAir / airCounter);
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return name;
    }

    private List<NestAir> getNestsAir() {
        List<NestAir> list = new ArrayList<>();
        for (Nest nest : nests) {
            if (nest.getClass() == NestAir.class) {
                list.add((NestAir) nest);
            }
        }
        return list;
    }

    private List<NestHeating> getNestsHeating() {
        List<NestHeating> list = new ArrayList<>();
        for (Nest nest : nests) {
            if (nest.getClass() == NestHeating.class) {
                list.add((NestHeating) nest);
            }
        }
        return list;
    }
}
@SelfMade
enum AntType {
    fireAnt,
    blueWarAnt,
    houseAnt
}