import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Objects;

// Terrarium mit allen dazu gehörenden Bestandteilen, können änderbar sein
// Christoph Zeitler
public class Formicarium extends FormicariumPart implements FormicariumItem {

    protected LinkedList<FormicariumPart> list;
    protected int temperature;
    protected double humitidity;
    protected int time;
    protected Enum.Duration maxTime;
    protected Thermometer thermometer;

    //Vorbedingung: input != null;
    //Nachbedingung: initialisiert ein Formicarium-Objekt
    public Formicarium(int temperature, double humitidity, int time, Enum.Duration maxTime, Thermometer thermometer){
        list = new LinkedList<>();
        this.temperature = temperature;
        this.humitidity = humitidity;
        this.time = time;
        this.maxTime = maxTime;
        this.thermometer = thermometer; //TODO: muss überprüft werden ob das überhaupt ins Formicarium passt (muss auch in die list)
        list.add(thermometer);
    }

    //Nachbedingung: gibt einen neuen Iterator zurück, der über alle Bestandteile des Formicariums iteriert
    public FormicariumItemIterator iterator(){
        LinkedList<Iterator> iteratorList = new LinkedList();
        for (FormicariumPart f: list){
            iteratorList.add(f.iterator());
        }

        return new FormicariumItemIterator() {
            boolean hasNext = true;
            @Override
            public boolean hasNext() {
                for (Iterator iter: iteratorList){
                    if (iter.hasNext()) return true;
                }
                return hasNext;
            }

            @Override
            public FormicariumItem next() {
                if (hasNext()){
                    for (Iterator iter: iteratorList){
                        if (iter.hasNext()) return (FormicariumItem) iter.next();
                    }
                    if (hasNext){
                        hasNext = false;
                        return (FormicariumItem) (Formicarium.this);
                    }

                }
                throw new NoSuchElementException();
            }
        };


    }

    //Nachbedingung: gibt ein Objekt vom Typ Compatibility zurück, das die vom Formicarium gebotenen Umweltbedingungen beschreibt.
    public Compatibility compatibility (){
        //return new Compatibility(this);
        return  null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Formicarium that = (Formicarium) o;
        return temperature == that.temperature && Double.compare(that.humitidity, humitidity) ==
                0 && time == that.time && Objects.equals(list, that.list) && maxTime == that.maxTime && Objects.equals(thermometer, that.thermometer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(list, temperature, humitidity, time, maxTime, thermometer);
    }

    public LinkedList<FormicariumPart> getList() {
        return list;
    }
}

