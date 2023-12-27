import java.util.NoSuchElementException;
import java.util.Objects;

//kein Teil von einem Fromicarium aber kann damit benutzt werden
// Marlene Lehotzki
public class Forceps implements Instrument, FormicariumItem{

    private Compatibility compatibility;
    private int size;
    private boolean compatibleF;

    private int minTemperature;

    private int maxTemperature;

    private double minHumidity;

    private double maxHumidity;
    private Enum.Quality professionalQuality;

    public Forceps(int size, boolean compatibleF, int minTemperature,int maxTemperature, int minHumidity, int maxHumidity) {
        this.size = size;
        int minSize = (int) ((int) size*0.8);
        int maxSize = (int) ((int) size*1.2);
        this.compatibleF = compatibleF;
        this.compatibility = new Compatibility(minSize,maxSize,minTemperature,maxTemperature,minHumidity,maxHumidity);
        this.maxTemperature =maxTemperature;
        this.minTemperature = minTemperature;
        this.minHumidity = minHumidity;
        this.maxHumidity = maxHumidity;
    }

    // Vorbedingung: enum Quality Variable that can be assigned a value
    // Tells how professional the instrument is
    public void quality() {
        this.professionalQuality = Enum.Quality.professional;
    }

    public int getMaxTemperature(){return this.maxTemperature;}

    public int getMinTemperature() {
        return this.minTemperature;
    }

    public double getMaxHumidity() {
        return this.maxHumidity;
    }

    public double getMinHumidity() {
        return minHumidity;
    }
    public int getSize(){
        return this.size;
    }
    //Vorbedingung: Compatibility !=0
    // Nachbedingung: Gibt Compatibility Object zurück
    public Compatibility compatibility() {
        Compatibility compatibilityResult = compatibility.compatible(compatibility);
        return compatibilityResult;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Forceps forceps = (Forceps) o;
        return size == forceps.size && compatibleF == forceps.compatibleF && minTemperature == forceps.minTemperature && maxTemperature == forceps.maxTemperature && Double.compare(forceps.minHumidity, minHumidity) == 0 && Double.compare(forceps.maxHumidity, maxHumidity) == 0 && Objects.equals(compatibility, forceps.compatibility) && professionalQuality == forceps.professionalQuality;
    }

    @Override
    public int hashCode() {
        return Objects.hash(compatibility, size, compatibleF, minTemperature, maxTemperature, minHumidity, maxHumidity, professionalQuality);
    }

    public FormicariumItemIterator iterator() {
        return new FormicariumItemIterator() {
            boolean hasNext = true;

            @Override
            public boolean hasNext() {
                return hasNext;
            }

            @Override
            public FormicariumItem next() {
                if (hasNext) {
                    hasNext = false;
                    return (FormicariumItem) (Forceps.this);
                }
                throw new NoSuchElementException();
            }

        };
    }


}
