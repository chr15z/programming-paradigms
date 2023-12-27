import java.util.NoSuchElementException;

public class Arena implements Part<Arena, QualityValue> {
    private int volume;
    private QualityValue qualityEnum;
    private Arena criteroin;

    //Vorbedingung: input != null
    //Nachbedingung: Erstellt ein Objekt des Typs Arena
    public Arena(int volume, QualityValue qualityEnum){
        this.volume = volume;
        this.qualityEnum = qualityEnum;
    }

    // Nachbedingung: Gibt das Fassungsvermögen der Arena (das ist im Wesentlichen ein Gefäß) in Liter zurück
    public int volume(){
        return volume;
    }

    // Vorbedingungen: input != null
    // Nachbedingungen: Der Parameter arena wird in this abgelegt
    @Override
    public void setCriteroin(Arena arena) {
        criteroin = arena;
    }

    // Vorbedingungen: input != null
    // Nachbedingungen: Ein Aufruf gibt eine Beurteilung von this hinsichtlich des Kriteriums arena zurück.
    // Das Ergebnis hängt nur von unveränderlichen Werten in this und arena ab. Eine Methodenausführung lässt this und arena unverändert.
    @Override
    public QualityValue rated(Arena arena) {
        if (arena.equals(this)) return qualityEnum;
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
