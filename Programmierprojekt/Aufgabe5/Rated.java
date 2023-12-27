public interface Rated <P,R>{

    // Vorbedingungen: input != null
    // Nachbedingungen: Der Parameter p wird in this abgelegt
    void setCriteroin(P p);

    // Vorbedingungen: input != null
    // Nachbedingungen: Ein Aufruf gibt eine Beurteilung von this hinsichtlich des Kriteriums p zurück.
    // Das Ergebnis hängt nur von unveränderlichen Werten in this und p ab. Eine Methodenausführung lässt this und p unverändert.
    R rated(P p);

    // Nachbedingungen: Das Ergebnis hängt nur von unveränderlichen Werten in this und dem zuletzt
    // gesetzten Kriterium ab. Eine Methodenausführung lässt this unverändert.
    R rated();
}
