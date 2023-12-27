import java.util.NoSuchElementException;

// Bestandteil eines Formicariums, kann nur für kurze Zeit alleine als Formicarium dienen, beherbergt Königin,
// auf dauer ohne arena nicht überlebensfähig. Bei kleinen Formicarien ist das Nest einer Arena untergebracht,
// bei größeren ist das Nest oft eigenständiger Behälter
// Christoph Zeitler
public class Nest extends Formicarium{

    //Vorbedinung: input != null;
    //Nachbedingung: initialisiert ein Nestobjekt
    public Nest(int temperature, double humitidity, int time, Enum.Duration maxTime, Thermometer thermometer){
        super(temperature, humitidity, time, maxTime, thermometer);
    }

    //Vorbedinung: input != null;
    //Nachbedingung: initialisiert ein Nestobjekt ohne Thermometer
    public Nest(int temperature, double humitidity, int time, Enum.Duration maxTime){
        super(temperature, humitidity, time, maxTime, null);
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    //Nachbedingung: gibt einen neuen Iterator zurück, der über alle Bestandteile des Nests iteriert
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
                    return (FormicariumItem) (Nest.this);
                }
                throw new NoSuchElementException();
            }

        };
    }
}
