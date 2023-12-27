
import java.util.*;

// Menge von Formicarien, Bestandteilen von Formicarien oder Instrumenten die mit Formicarien verwendet werden können
// Christoph Zeitler
public class FormicariumSet {

    private Map<FormicariumItem, Integer> map;
    private FormicariumItem lastEltementReturnt;

    //Vorbedinung: input != null;
    //Nachbedingung: initialisiert ein FormicariumSet-Objekt
    public FormicariumSet(FormicariumItem[] array) {
        map = new HashMap<>();
        for (FormicariumItem formicariumItem : array) {
            if (!map.containsKey(formicariumItem)){
                map.put(formicariumItem, 1);
            }
            else{
                map.put(formicariumItem, map.get(formicariumItem) + 1);
            }
        }
    }

    //Nachbedingung: gibt einen neuen Iterator zurück, der über alle Bestandteile des Formicariums iteriert
    public FormicariumItemIterator iterator(){
        LinkedList<Iterator> iteratorList = new LinkedList();
        for (FormicariumItem f: map.keySet()){
            iteratorList.add(f.iterator());
        }

        return new FormicariumItemIterator() {
            @Override
            public boolean hasNext() {
                for (Iterator iter: iteratorList){
                    if (iter.hasNext()) return true;
                }
                return false;
            }

            @Override
            public FormicariumItem next() {
                if (hasNext()){
                    for (Iterator iter: iteratorList){
                        if (iter.hasNext()){
                            lastEltementReturnt = (FormicariumItem) iter.next();
                            return lastEltementReturnt;
                        }
                    }

                }
                throw new NoSuchElementException();
            }
        };
    }

    // fügt ein neues Element hinzu, sofern das identische Element nicht schon vorhanden ist (gleiche
    // Elemente können jedoch mehrfach vorhanden sein)
    public void add(FormicariumItem formicariumItem){
        boolean detected = false;
        for(FormicariumItem item: map.keySet()){
            if (item.equals(formicariumItem)){
                detected = true;
                map.put(formicariumItem, map.get(formicariumItem) + 1);
                break;
            }
        }
        if (!detected) map.put(formicariumItem, 1);
    }

    // Retourniert die Anzahl der vorhandenen Elemente, die gleich dem zuletzt von next zurückgegebenen Element sind
    public int count(){
        if (map.containsKey(lastEltementReturnt)) return map.get(lastEltementReturnt);
        else return 0;
    }

    // Die Methode remove des Iterators (ohne Argument) verringert die Anzahl vorhandener Elemente
    // (gleich dem, das zuletzt von next zurückgegeben wurde) um 1
    public void remove(){
        if (map.containsKey(lastEltementReturnt)){
            map.put(lastEltementReturnt, map.get(lastEltementReturnt) - 1);
            if (map.get(lastEltementReturnt) == 0){
                map.remove(lastEltementReturnt);
            }
        }
    }

    // Vorbedinung: int i > 0
    // Nachbedingung Die Methode remove des Iterators (ohne Argument) verringert die Anzahl vorhandener Elemente
    // (gleich dem, das zuletzt von next zurückgegeben wurde) um i
    public void remove(int i){
        for (int j = 0; j < i; j++) {
            this.remove();
        }
    }

    public Map<FormicariumItem, Integer> getMap() {
        return map;
    }
}

