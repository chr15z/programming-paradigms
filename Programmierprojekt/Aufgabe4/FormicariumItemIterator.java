import java.util.Iterator;

public interface FormicariumItemIterator extends Iterator<FormicariumItem> {

    // Gibt 'true' zurück, wenn die Iteration noch Elemente hat.
    boolean hasNext();

    // Gibt das nächste Element in der Iteration zurück.
    // Wirft eine 'java.util.NoSuchElementException', wenn die Iteration keine weiteren Elemente enthält.
    FormicariumItem next();

}
