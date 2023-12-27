@CreatedBy(Author.Chris)
@SelfMade
public class SandMix implements Material{
    private double weight;
    @Zusicherung(vorbedingung = "weight != null", nachbedingung = "weight gets initialised")
    public SandMix(double weight){
        this.weight = weight;
    }

    @Zusicherung(nachbedingung = "weight gets returnt")
    public Object getMeasurements() {
        return weight;
    }
}
