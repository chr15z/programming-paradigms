import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

//ein mit Substrat gefüllter Plastik oder Glas Behälter, der teil eines Formicariums ist
// Marlene Lehotzki
public class Arena extends FormicariumPart {

    private Enum.Substrate substrate;
    private boolean glassContainer;
    private Nest nest; //kann null oder initialisiert sein (je nachdem ob ein Nest in der Arena enthalten ist)
    private AntFarm antFarm;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Arena arena = (Arena) o;
        if( glassContainer == arena.glassContainer && substrate == arena.substrate
                && Objects.equals(nest, arena.nest) && Objects.equals(antFarm, arena.antFarm)){

               return true;
        }else { return false;}
    }

    @Override
    public int hashCode() {
        return Objects.hash(substrate, glassContainer, nest, antFarm);
    }

    public Arena(boolean glassContainer, int minSize, int maxSize, Nest nest) {
        this.glassContainer = glassContainer;
        this.minSize = minSize;
        this.maxSize = maxSize;
        this.nest = nest;
        AntFarm antFarm = null;
    }

    // Vorbedingung = AntFarm != null
    //Wenn es ein Nest in der Arena gibt, müssen die beiden Substrate gleich sein
    public void setAntFarm(AntFarm newFarm) {
        this.substrate = antFarm.getSubstrate();
        antFarm = newFarm;
        substrate = antFarm.getSubstrate();
    }

    @Override
    public Compatibility compatibility() {
        Compatibility compatibility = new Compatibility(this);
        return compatibility.compatible(compatibility);
    }

    public Nest getNest() {
        return nest;
    }


    @Override
    public Iterator<FormicariumItem> iterator() {
        Iterator nestIterator;
        if (nest != null) {
            nestIterator = nest.iterator();
        } else nestIterator = null;

        return new FormicariumItemIterator() {
            boolean hasNext = true;

            @Override
            public boolean hasNext() {
                return hasNext || nestIterator.hasNext();
            }

            @Override
            public FormicariumItem next() {
                if (hasNext) {
                    hasNext = false;
                    return (FormicariumItem) (Arena.this);
                }
                if (nestIterator.hasNext()) return (FormicariumItem) nestIterator.next();
                throw new NoSuchElementException();
            }
        };
    }


}
