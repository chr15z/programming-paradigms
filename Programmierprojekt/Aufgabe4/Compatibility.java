//Erstellt Objekte die den Lebensraum für bestimmte andere Objekte beschreiben
//Autor: Hannah Winkler
public class Compatibility { //Erstellt Objekte die den Lebensraum für bestimmte andere Objekte beschreiben
    private int minSize;
    private int maxSize;
    private int minTemperature;
    private int maxTemperature;
    private double minHumidity;
    private double maxHumidity;
    public Enum.Duration time;
    public Enum.Duration maxTime;
    //basic instructor
    //Vorbedingungen: input!=null
    //Nachbedinungungen: gibt Compatibility Objekt zurück
    public Compatibility(int minSize, int maxSize, int minTemperature, int maxTemperature, double minHumidity, double maxHumidity) {
        this.minSize = minSize;
        this.maxSize = maxSize;
        this.minTemperature = minTemperature;
        this.maxTemperature = maxTemperature;
        this.minHumidity = minHumidity;
        this.maxHumidity = maxHumidity;
    }
    //instructor für formicariumpart objekte
    //Vorbedingungen: input!=null
    //Nachbedingungen: gibt ein Compatibilityobjekt zurück, das die Umweltbedingungen des übergebenen Objekts beschreibt
    public Compatibility(FormicariumPart givenobject) {
        this.minSize = givenobject.getminSize();
        this.maxSize = givenobject.getmaxSize();
        this.minTemperature = givenobject.getminTemperature();
        this.maxTemperature = givenobject.getMaxTemperature();
        this.minHumidity = givenobject.getminHumidity();
        this.maxHumidity = givenobject.getMaxhumidity();
        time = Enum.Duration.week;
        maxTime = Enum.Duration.month;
    }

    public Compatibility(Forceps forceps){
        this.minSize= forceps.getSize();
        this.maxSize=forceps.getSize();
        this.minTemperature = forceps.getMinTemperature();
        this.maxTemperature = forceps.getMaxTemperature();
        this.minHumidity=forceps.getMinHumidity();
        this.maxHumidity = forceps.getMaxHumidity();
    }

    // prüft ob ein lebensraum für eine ameise geeignet ist
    //Vorbedingungen: input!=null
    //Nachbedingungen: gibt "true" zurück wenn ein Lebensraum für eine bestimmte Ameisenart geeignet ist und "false", wenn es nicht so ist
    public boolean antcompatibility(Compatibility givenobject, Enum.Ant ant) {
        if (givenobject.getMinTemperature() > ant.getAntMaxTemperature()) return false;
        if(givenobject.getMaxTemperature()<ant.getAntMinTemperature())return false; //Problemzeile
        if (givenobject.getMinHumidity() > ant.getAntMaxHumidity()) return false;
        if (givenobject.getMinSize() > ant.getAntSize() * ant.getAntQuantity()) return false;
        return true;
    }
    // gibt überschneidende bedingungen zurück
    //Vorbedingungen: input!=null
    // Beschreibung:
    //- übernimmt kleineren Werte aus this und dem Argument bei time und maxime
    //- wählt bei übrigen Parametern die überschneidenden Werte aus,
    //  wenn sie sich nicht überschneiden wird eine errormeldung zurückgegebn
    //Nachbedingungen: gibt ein Objekt Compatibility zurück mit den überschneidenden Wertebereichen
    public Compatibility compatible(Compatibility givenobject) {
        int helpint = Math.min(this.time.getOrder(), givenobject.time.getOrder()); //übernimmt kleineren Werte aus this und dem Argument
        int helpint1 = Math.min(this.maxTime.getOrder(), givenobject.maxTime.getOrder()); //übernimmt kleineren Werte aus this und dem Argument
        Enum.Duration newtime = time.searchByOrder(helpint);
        Enum.Duration newMaxtime = time.searchByOrder(helpint);

        int minSize = (int) Math.max(givenobject.getMinSize(), this.minSize);
        int maxSize = (int) Math.min(givenobject.getMaxSize(), this.maxSize);
        int minTemperature = Math.max(givenobject.getMinTemperature(), this.minTemperature);
        int maxTemperature = Math.min(givenobject.getMaxTemperature(), this.maxTemperature);
        double minHumidity = Math.max(givenobject.getMinHumidity(), this.minHumidity);
        double maxHumidity = Math.max(givenobject.getMaxHumidity(), this.maxHumidity);
        int newSizehelp = (int) (givenobject.getMaxSize() - this.minSize);
        assert (givenobject.getMaxSize()>=this.minSize): "nicht kompatibel wegen größe";
        Compatibility result_object = new Compatibility(minSize, maxSize, minTemperature, maxTemperature, minHumidity, maxHumidity);
        return result_object;
    }
    //Vorbedingungen: input!=null
    //Beschreibung: Updated time, jedoch nicht wenn der übergebene Wert größer als maxtime ist
    //Nachbedingungen: Time geupdated, ist jetzt der übergebene Wert
    public void setTime(Enum.Duration time) {
        if (time.getOrder() <= maxTime.getOrder()) this.time = time;
    }

    public double getMinSize() {
        return minSize;
    }

    public double getMaxSize() {
        return maxSize;
    }

    public int getMinTemperature() {
        return this.minTemperature;
    }

    public int getMaxTemperature() {
        return this.maxTemperature;
    }

    public double getMinHumidity() {
        return minHumidity;
    }

    public double getMaxHumidity() {
        return maxHumidity;
    }

    public Enum.Duration getMaxTime() {
        return maxTime;
    } //bezieht sich auf nicht behebbare gründe

    public Enum.Duration getTime() {
        return time;
    }

}
