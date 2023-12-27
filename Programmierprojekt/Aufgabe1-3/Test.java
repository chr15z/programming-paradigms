// STYLE: Parallel
// Testdatei, die mehrere Ameisensimulationen parallel startet
public class Test {
    public static void main(String[] args) {
        int zoom = 5;               // Größe eines Quadrats
        int landscapeSize = 120;    // Größe des Spielfelds
        int dayspeed = 1;           // Dauer eines Tages: 0.1 normal - 1 sehr schnell
        int ants = 100;             // Anzahl der Ameisen pro Armee

        for (int i = 0; i < 3; i++) {
            Game game = new Game(zoom, landscapeSize, dayspeed, ants);
            GameThread tread = new GameThread(game);
            tread.start();
        }
    }
}

//NOTE: Aufgabenzuteilung

//TODO: Hannah Winkler
//Kommentare in: Ant (+Subklassen), Location, Interaction
//Verschiedene Ameisenstaaten (interface/abstract class) rote ameisen zb doppelt so schnell
//Überwinterung simulieren: Gitter blau
//Blaue Ameisen überwintern nicht

//TODO: Christoph Zeitler
//Kommentare in: Sun, Test, ScentTrail
//Move-Methode in Simulation
//Eigene Klasse für Futter und Bau wo def wird dass bau wachsen kann usw
//Futterquellen 3X3 felder, Bau auf 5x5 vergrößern
//Ameisen-Konstruktor
//Verschiedene smell-Arrays und updateSmell, welches die Arrays updated je nach Lage
//Simulationsdurchlauf erstellt, Simulationsparameter angepasst und Simulation visualisiert

//TODO: Marlene Lehotzki
//Kommentare in: Simulation, Game, Calculator
//Sun korrigieren und Thread in Game
//Move-Methode in Simulation
//Rechner-Klasse Abstand zw zwei Punkten berechnen und korrektes modulo
//Tag und Nacht simulieren mit kleiner sonne
//Verschiedene Duftspuren Futter, verschiedene Ameisen

//TODO: diese Woche
/*
    STYLE: objektorientiert, prozedrual, funktional(mind 100 Zeilen), parallel(mind 100 Zeilen)
    NOTE: organisatorische kommentare
    Alle Methoden kommentieren: Vorbedingungen, Nachbedingungen, Invarianten und History-Constraints
    ERROR: Inkonsistenzen mit mögliche Ursachen sowie nötige Programmänderungen zur Fehlerkorrektur zu beschreiben
    GOOD/BAD: mindestens 3 stellen (Objektkopplung schwach, Klassenzusammenhalt hoch, dynamischem Binden)
    Variablen Namen auf englisch / Kommentare auf deutsch

    Fragen an Tutor:
 */



