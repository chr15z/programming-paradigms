interface Part<P extends Part<P, R>, R extends QualityValue> extends Rated<P, R>{

    // Nachbedingung: Die Art des Bestandteils wird durch die von toString zurückgegebene Zeichenkette beschrieben.
    String toString();

}
