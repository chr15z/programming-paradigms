import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

// RatedSet bei dem fü X und P wird der selben Typ eingesetzt wird
import java.util.*;

class CompatibilitySetIterator<T> implements Iterator<T> {
    private Set<T> entries;
    private Set<T> criteria;
    private Iterator<T> iterator;
    public T currentEntry;

    public CompatibilitySetIterator(Set<T> entries, Set<T> criteria) {
        this.entries = entries;
        this.criteria = criteria;
        this.iterator = entries.iterator();
        this.currentEntry = null;
    }

    @Override
    public boolean hasNext() {
        while (iterator.hasNext()) {
            T entry = iterator.next();
            if (criteria.contains(entry)) {
                currentEntry = entry;
                return true;
            }
        }
        return false;
    }

    @Override
    public T next() {
        if (currentEntry == null && !hasNext()) {
            throw new NoSuchElementException();
        }
        T result = currentEntry;
        currentEntry = null;
        return result;
    }

    @Override
    public void remove() {
        iterator.remove();
    }
}

class CompatibilitySet<T extends Rated<T, Double>, R> implements RatedSet<T, T, Double> {
    private Set<T> entries;
    private Set<T> criteria;

    public CompatibilitySet() {
        this.entries = new HashSet<>();
        this.criteria = new HashSet<>();
    }

    @Override
    public void add(T entry) {
        entries.add(entry);
    }

    @Override
    public void addCriterion(T criterion) {
        criteria.add(criterion);
    }

    @Override
    public Iterator<T> iterator(T p, Double r) {
        return new CompatibilitySetIterator<>(entries, criteria) {
            @Override
            public boolean hasNext() {
                while (super.hasNext()) {
                    T entry = super.next();
                    if (entry.rated(p) >= r) {
                        currentEntry = entry;
                        return true;
                    }
                }
                return false;
            }
        };
    }

    @Override
    public Iterator<T> iterator(Double r) {
        double sum = 0;
        int count = 0;
        for (T entry : entries) {
            sum += entry.rated(entry);
            count++;
        }
        double average = count > 0 ? sum / count : 0;

        return new CompatibilitySetIterator<>(entries, criteria) {
            @Override
            public boolean hasNext() {
                while (super.hasNext()) {
                    T entry = super.next();
                    if (entry.rated(entry) >= average) {
                        currentEntry = entry;
                        return true;
                    }
                }
                return false;
            }
        };
    }

    @Override
    public Iterator<T> criterions() {
        return criteria.iterator();
    }

    @Override
    public Iterator<T> iterator() {
        return entries.iterator();
    }
}
