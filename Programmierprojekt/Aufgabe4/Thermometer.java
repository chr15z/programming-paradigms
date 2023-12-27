import java.util.NoSuchElementException;
import java.util.Objects;

// kan Teil eines Formicariums sein
// Marlene Lehotzki
public class Thermometer extends FormicariumPart implements Instrument {
    private int size;
    private Enum.Quality professionalQuality;
    private int minTemperature;

    private int maxTemperature;

    private double minHumidity;

    private double maxHumidity;

    public Thermometer (int size, int minTemperature, int maxTemperature, double minHumidity, double maxHumidity) {
        this.size = size;
        this.minTemperature = minTemperature;
        this.maxTemperature = maxTemperature;
        this.minHumidity = minHumidity;
        this.maxHumidity = maxHumidity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Thermometer that = (Thermometer) o;
        return size == that.size && minTemperature == that.minTemperature && maxTemperature == that.maxTemperature && Double.compare(that.minHumidity, minHumidity) == 0 && Double.compare(that.maxHumidity, maxHumidity) == 0 && professionalQuality == that.professionalQuality;
    }

    @Override
    public int hashCode() {
        return Objects.hash(size, professionalQuality, minTemperature, maxTemperature, minHumidity, maxHumidity);
    }

    @Override
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
                    return (FormicariumItem) (Thermometer.this);
                }
                throw new NoSuchElementException();
            }

        };
    }

    // Vorbedingung: enum Quality Variable that can be assigned a value
    // gibt die qualität des Objekts zurück
    public void quality() {
        this.professionalQuality = Enum.Quality.professional;
    }

    // Nachbedingung: gibt ein Objekt Compatibility zurück mit den überschneidenden Wertebereichen
    @Override
    public Compatibility compatibility() {
        Compatibility compatibility = new Compatibility(this);
        return compatibility.compatible(compatibility);
    }

}
