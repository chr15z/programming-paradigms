import java.util.NoSuchElementException;
import java.util.Objects;

// Eine Form eines Ameisennests zusätzlich bestehend aus einem Substrat
// Christoph Zeitler
public class AntFarm extends Nest{

    private Enum.Substrate substrate;

    //Vorbedinung: input != null;
    //Nachbedingung: initialisiert ein Antfarm-Objekt
    public AntFarm(int temperature, double humitidity, int time, Enum.Duration maxTime, Enum.Substrate substrate) {
        super(temperature, humitidity, time, maxTime);
        this.substrate = substrate;
    }


    public Enum.Substrate getSubstrate() {
        return substrate;
    }

    //Nachbedingung: gibt einen neuen Iterator zurück, der über alle Bestandteile der Antfarm iteriert
    public FormicariumItemIterator iterator(){
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
                    return (FormicariumItem) (AntFarm.this);
                }
                throw new NoSuchElementException();
            }

        };
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AntFarm antFarm = (AntFarm) o;
        return substrate == antFarm.substrate && super.equals(o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(substrate);
    }
}
