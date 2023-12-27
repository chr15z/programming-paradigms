
@SelfMade
@CreatedBy(Author.Chris)
@Zusicherung(invariante = "jede Nestnummer num kommt nur einmal vor", historyConstraints = "die Nestnummer muss positiv aufsteigend sein, um 1 größer als die davor")
public abstract class Nest {
    private static int lastAssignedNumber = 0;
    private int num;
    private double height;
    private double width;
    private double volume;
    private Material material;

    @Zusicherung(vorbedingung = "height, width, material != null", nachbedingung = "Nestnummer, height, width und material wird gesetzt und volumen berechnet")
    public Nest(double height, double width, Material material){
        lastAssignedNumber++;
        this.num = lastAssignedNumber;
        this.height = height;
        this.width = width;
        volume = 2 * height * width;
        this.material = material;
    }

    public int getNum() {
        return num;
    }

    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }

    public double getVolume() {
        return volume;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }
}
