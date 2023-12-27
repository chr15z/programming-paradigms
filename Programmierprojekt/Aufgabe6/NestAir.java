@SelfMade
@CreatedBy(Author.Hannah)
public class NestAir extends Nest{
    //Erzeugen eines Nests mit Nestnummer, Höhe, Breite und Leistung
    //bei Nest mit Heizung oder Volumen bei Nest mit Luftbefeuchter.
    private double waterVolume;

    @Zusicherung(vorbedingung = "height, width, material, waterVolume != null", nachbedingung = "height, width, und material werden von Nest übernommen und Watervolume wird gesetzt")
    public NestAir(double height, double width, double waterVolume, Material material) {
        super(height, width, material);
        this.waterVolume = waterVolume;
    }

    public double getWaterVolume(){
        return waterVolume;
    }
}
