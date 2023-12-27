import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;


public class StatSet<X extends Rated<P, R>, P, R> implements RatedSet<X, P, R> {
    private Set<X> entries = new HashSet<>();
    private P currentCriterion;
    private StringBuilder statisticsBuilder = new StringBuilder();

    @Override
    public void add(X entry) {
        if (entries.contains(entry)) {
            statisticsBuilder.append("add: Entry already exists.\n");
        } else {
            entries.add(entry);
            statisticsBuilder.append("add: Entry added.\n");
        }
    }

    @Override
    public void addCriterion(P criterion) {
        currentCriterion = criterion;
        statisticsBuilder.append("addCriterion: Criterion set.\n");
    }

    @Override
    public Iterator<X> iterator(P p, R r) {
        return new Iterator<X>() {
            private Iterator<X> iterator = entries.iterator();
            private X nextEntry;

            {
                findNext();
            }

            private void findNext() {
                while (iterator.hasNext()) {
                    X entry = iterator.next();
                    if (entry.rated(p).equals(r) || entry.rated(p).hashCode() >= r.hashCode()) {
                        nextEntry = entry;
                        return;
                    }
                }
                nextEntry = null;
            }

            @Override
            public boolean hasNext() {
                return nextEntry != null;
            }

            @Override
            public X next() {
                if (nextEntry == null) {
                    throw new IllegalStateException("No more elements");
                }
                X current = nextEntry;
                nextEntry = null;
                findNext();
                statisticsBuilder.append("iterator(p, r): Iterated over an entry.\n");
                return current;
            }

            @Override
            public void remove() {
                iterator.remove();
                statisticsBuilder.append("iterator(p, r): Removed the last iterated entry.\n");
            }
        };
    }

    @Override
    public Iterator<X> iterator(R r) {
        return iterator(currentCriterion, r);
    }

    @Override
    public Iterator<X> criterions() {
        return new Iterator<X>() {
            private Iterator<X> iterator = entries.iterator();
            private X nextEntry;

            {
                findNext();
            }

            private void findNext() {
                while (iterator.hasNext()) {
                    X entry = iterator.next();
                    if (entry.rated().equals(currentCriterion)) {
                        nextEntry = entry;
                        return;
                    }
                }
                nextEntry = null;
            }

            @Override
            public boolean hasNext() {
                return nextEntry != null;
            }

            @Override
            public X next() {
                if (nextEntry == null) {
                    throw new IllegalStateException("No more elements");
                }
                X current = nextEntry;
                nextEntry = null;
                findNext();
                statisticsBuilder.append("criterions: Iterated over an entry.\n");
                return current;
            }

            @Override
            public void remove() {
                iterator.remove();
                statisticsBuilder.append("criterions: Removed the last iterated entry.\n");
            }
        };
    }

    @Override
    public Iterator<X> iterator() {
        return entries.iterator();
    }

    // Nachbedingung: gibt Informationen zur Anzahl aller bisher erfolgten Aufrufe alle Methoden in diesem Objekt als Zeichenkette zurück
    // (jede Methode einzeln aufgelistet), einschließlich der Aufrufe der Methoden in den dazu gehörenden Iteratoren
    public String statistics() {
        return statisticsBuilder.toString();
    }

    // Nachbedingung: Zwei Objekte von StatSet werden als gleich betrachtet,
    // wenn sie identische Einträge jeder Art enthalten, ungeachtet der Reihenfolge
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        StatSet<?, ?, ?> statSet = (StatSet<?, ?, ?>) obj;
        return entries.equals(statSet.entries);
    }

    @Override
    public int hashCode() {
        return entries.hashCode();
    }
}
