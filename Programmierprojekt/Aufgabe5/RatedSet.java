import java.util.Iterator;

public interface RatedSet<X extends Rated<P, R>, P, R> extends Iterable<X> {

    // Vorbedingungen: input != null
    // Nachbedingung: Falls der Container noch kein identisches Objekt als Eintrag enthält, wird es
    // eingefügt, aber wenn ein identisches Objekt zuvor schon mittels add eingefügt wurde, wird es nicht noch einmal eingefügt.
    void add(X entry);

    // Vorbedingungen: input != null
    // Nachbedingung: wie add abgesehen davon, dass es um Einträge vom Typ P
    // geht und keine identischen Objekte, die mittles addCriterion eingefügt werden, mehrfach vorkommen dürfen.
    void addCriterion(P criterion);

    // Vorbedingungen: input != null
    // Nachbedingung:  läuft in beliebiger Reihenfolge über alle Einträge x im Container, die mittels add eingefügt wurden
    // und für die x.rated(p) ein Ergebnis liefert, das größer oder gleich r ist.
    // Größenvergleiche erfolgen durch eine Methode aus Calc. Die Methode remove im Iterator muss so implementiert sein,
    // dass der zuletzt von next zurückgegebene Eintrag aus dem Container entfernt wird.
    Iterator<X> iterator(P p, R r);

    // Nachbedingung:  läuft in beliebiger Reihenfolge über alle Einträge x im Container, die mittels add eingefügt wurden und
    // für die der Durchschnitt aller durch x.rated(p) ermittelten Werte (für alle mittels addCriterion eingefügten Einträge p im Container)
    // größer oder gleich r ist. Größenvergleiche und Berechnungen von Durchschnittswerten
    // (Summenbildung mittels sum, Division durch die Anzahl aufsummierter Werte mittels ratio) erfolgen durch Methoden aus Calc.
    // Die Methode remove im Iterator muss den zuletzt von next zurückgegebenen Eintrag aus dem Container entfernen
    Iterator<X> iterator(R r);

    // Nachbedingung: läuft in beliebiger Reihenfolge über alle Einträge im Container, die mittels addCriterion eingefügt wurden.
    // remove im Iterator muss so implementiert sein, dass der zuletzt von next zurückgegebene Eintrag aus dem Container entfernt wird.
    Iterator<X> criterions();


    // Iterator (definiert in Iterable)
    // Vorbedingungen: input != null
    // Nachbedingung:  läuft in beliebiger Reihenfolge über alle Einträge im Container, die mittels add eingefügt wurden.
    // Die Methode remove im Iterator muss so implementiert sein,
    // dass der zuletzt von next zurückgegebene Eintrag aus dem Container entfernt wird.
}