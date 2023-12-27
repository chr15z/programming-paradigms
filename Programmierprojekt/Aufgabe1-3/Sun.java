import codedraw.CodeDraw;
import java.awt.*;

// STYLE: Objektorientiert
// Ein Objekt, das eine Sonne darstellt.
// GOOD:    Klassenzusammenhalt hoch. Die Klasse ist fast ganz alleine für die Simulation der Sonne zuständig.
//          Sie simuliert einen Tag selber und zeichnet je nach Tageszeit die Sonne sogar eigenständig ein.
// BAD: Variablen wie der daycounter haben keine Funktion wurden aber nicht entfernt
public class Sun {
    private double timercounter;
    private CodeDraw cs;
    int zoom;
    int daySpeed;
    private static int daycounter; // BAD: Daycounter ist für die Logik im Programm überflüssig

    // Vorbedingung: input != null;
    // Nachbedingungen: Tempo der Sonne wird festgelegt, sowie das CodeDraw Objekt, auf welchem sie gezeichnet werden soll
    public Sun(CodeDraw cs, int zoom, int daySpeed) {
        this.daySpeed = daySpeed;
        this.cs = cs;
        this.zoom = zoom;
    }

    // Nachbedingungen: timercounter steigt pro Aufruf um daySpeed. Nach einem Tag erhöht sich daycounter
    public void increaseCounter() {
        this.timercounter = timercounter + daySpeed;
        double temp = timercounter % 25;
        this.timercounter = temp;
        if (timercounter == 24) daycounter++;
    }

    // Nachbedingungen: Sonne wird oben im Fenster eingezeichnet. Je nach Tageszeit verändert sich Farbe und Position
    public void drawSunOrMoon() {
        increaseCounter();
        int sunColorR;
        int sunColorG;
        int sunColorB = 0;

        if (timercounter < 6) { // Morgens - Sonne geht auf
            sunColorR = 150 + (int) (105 * timercounter / 6);
            sunColorG = 150 + (int) (105 * timercounter / 6);
            sunColorB = 150 - (int) (150 * timercounter / 6);
        } else if (timercounter >= 6 && timercounter <= 19) { // Vormittags - Sonne zeichnen (gelb)
            sunColorR = 255;
            sunColorG = 255;
            sunColorB = 0;
        } else { // Nachts - Mond zeichnen (grau)
            sunColorR = 255 - (int) (105 * (timercounter - 19) / 6);
            sunColorG = 255 - (int) (105 * (timercounter - 19) / 6);
            sunColorB = (int) (150 * (timercounter - 19) / 6);
        }

        cs.setColor(new Color(sunColorR, sunColorG, sunColorB)); // Farbe mit Alpha 128 (halbtransparent)
        int sunSize = zoom * 15;
        // X-Koordinate wird je nach Tageszeit verändert (Sonne startet Links und geht nach rechts)
        double sunMoveTempX = timercounter * (cs.getWidth() / 25) % (cs.getWidth() - 2 * zoom);
        cs.fillRectangle(sunMoveTempX - 2 * zoom, 2 * zoom, sunSize, sunSize);


        double sunRemoveTempX = (timercounter - 1) * (cs.getWidth() / 25) % (cs.getWidth());


        cs.setColor(Color.white);
        // Korrektur das das letzte bisschen von der sonne weggeht
        if (timercounter == 0)
            cs.fillRectangle(cs.getWidth() - (cs.getWidth() / 25), 2 * zoom, cs.getWidth() / 25, sunSize);
        // Sonne wird nach jedem Schritt übermalt
        cs.fillRectangle(sunRemoveTempX, 2 * zoom, cs.getWidth() / 25, sunSize);
    }

}
