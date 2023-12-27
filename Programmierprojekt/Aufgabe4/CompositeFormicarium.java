import java.util.NoSuchElementException;

// Ein Formicarium, was änderbar ist
// Christoph Zeitler
public class CompositeFormicarium extends Formicarium {

    // Vorbedinung: input != null;
    // Nachbedingung: initialisiert ein CompositeFormicarium-Objekt
    public CompositeFormicarium(int temperature, double humitidity, int time, Enum.Duration maxTime, Thermometer thermometer) {
        super(temperature, humitidity, time, maxTime, thermometer);
    }

    // Entfernt den zuletzt von next zurückgegebenen Bestandteil wenn mehr als ein
    // Bestandteil vorhanden ist, sonst wird eine Ausnahme ausgelöst.
    public void remove() {
        if (list.size() > 1) {
            FormicariumPart formicariumPart = list.iterator().next();
            for (FormicariumPart f : list) {
                if (f.equals(formicariumPart)) {
                    list.remove(f);
                    break;
                }
            }
        } else throw new NoSuchElementException("Es sind weniger als 2 Elemente vorhanden!");
    }

    // Vorbedinung: input != null;
    // Nachbedingung: Fügt einen Bestandteil hinzu und returnt true, sofern er mit dem Formicarium kompatibel ist
    // und nicht schon mit derselben Identität vorkommt (nicht-idente gleiche Bestandteile können mehrfach vorkommen).
    // Kompatibilität ist gegeben, wenn compatible aus Compatibility angewandt auf die Umweltbeschreibungen des
    // Formicariums und neuen Bestandteils keine Ausnahme auslöst.
    public boolean add(FormicariumPart formicariumPart) {
        Compatibility temp = new Compatibility(this);
        if (temp.compatible(new Compatibility(formicariumPart)) != null) { //TODO: Identität überprüfen fehlt
            if (formicariumPart.getClass() == Arena.class) { // Arena kann ein zusätzliches Nest enthalten
                if (((Arena) formicariumPart).getNest() != null) list.add(((Arena) formicariumPart).getNest());
            }
            list.add(formicariumPart);
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
