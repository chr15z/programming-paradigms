@CreatedBy(Author.Chris)
@SelfMade
 public class NestHeating extends Nest{
    // Erzeugen eines Nests mit Nestnummer, Höhe, Breite und Leistung
    //bei Nest mit Heizung oder Volumen bei Nest mit Luftbefeuchter.
    private int performance;

    @Zusicherung(vorbedingung = "height, width, material, performance != null", nachbedingung = "height, width, und material werden von Nest übernommen und Performance wird gesetzt")
    public NestHeating(double height, double width, int performance, Material material) {
        super(height, width, material);
        this.performance = performance;
    }

    public int getPerformance() {
        return performance;
    }
}
