@CreatedBy(Author.Marlene)
@SelfMade
public class GasConcreteSlab implements Material{
    private double height;
    private double width;

    @Zusicherung(vorbedingung = "height, width != null", nachbedingung = "height and width get initialised")
    public GasConcreteSlab(double height, double width){
        this.height = height;
        this.width = width;
    }


    private double getGasVolume(){
        return height * width * 2;
    }

    @Zusicherung(nachbedingung = "gas-volume gets returnt")
    public Object getMeasurements(){
        return getGasVolume();
    }
}
