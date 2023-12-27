import java.util.NoSuchElementException;

public class Nest implements Part<Nest, QualityValue> {
    private int antSize;
    private QualityValue qualityEnum;
    private Nest criteroin;

    //Vorbedingung: input != null
    //Nachbedingung: Erstellt ein Objekt des Typs Nest
    public Nest(int antSize, QualityValue qualityEnum){
        this.antSize = antSize;
        this.qualityEnum = qualityEnum;
    }

    // Nachbedingung: Gibt die empfohlene Durchschnittsgröße von Ameisen in Millimetern zurück, für die das Nest geeignet ist.
    public int antSize(){
        return antSize;
    }

    // Vorbedingungen: input != null
    // Nachbedingungen: Der Parameter nest wird in this abgelegt
    @Override
    public void setCriteroin(Nest nest) {
        criteroin = nest;
    }

    // Vorbedingungen: input != null
    // Nachbedingungen: Ein Aufruf gibt eine Beurteilung von this hinsichtlich des Kriteriums nest zurück.
    // Das Ergebnis hängt nur von unveränderlichen Werten in this und nest ab. Eine Methodenausführung lässt this und arena unverändert.
    @Override
    public QualityValue rated(Nest nest) {
        if (nest.equals(this)) return qualityEnum;
        return QualityValue.notsuitable;
    }

    // Nachbedingungen: Das Ergebnis hängt nur von unveränderlichen Werten in this und dem zuletzt
    // gesetzten Kriterium ab. Eine Methodenausführung lässt this unverändert.
    @Override
    public QualityValue rated() {
        if (criteroin == null) throw new NoSuchElementException("Erst muss ein criteroin gesetzt werden!");
        return rated(criteroin);
    }

    // Nachbedingung: Die Art des Bestandteils wird durch die von toString zurückgegebene Zeichenkette beschrieben.
    @Override
    public String toString() {
        return qualityEnum + "";
    }
}
