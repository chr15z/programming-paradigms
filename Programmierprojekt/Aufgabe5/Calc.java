public interface Calc <R>{

    // Vorbedingungen: input != null
    // Nachbedingungen: gibt die Summe von this und dem Parameter zurück
    R sum(R other);

    // Vorbedingungen: input != null
    // Nachbedingungen: gibt das Ergebnis der Division von this durch den Parameter zurück
    R ratio(int divisor);

    // Vorbedingungen: input != null
    // Nachbedingungen: gibt genau dann true zurück, wenn this
    // größer oder gleich dem Parameter ist
    boolean atLeast(R value);
}
