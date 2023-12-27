// Ein Formicarium oder ein möglicher Bestandteil eines Formicariums.
// Christoph Zeitler

public abstract class FormicariumPart implements FormicariumItem {
    protected int minTemperature;
    protected int maxTemperature;
    protected int minhumidity;
    protected int maxhumidity;
    protected int minSize;
    protected int maxSize;
    private Enum.Duration time;

    public int getminTemperature() {return this.minTemperature;}
    public int getMaxTemperature(){return this.maxTemperature;}
    protected int getminHumidity(){return this.minhumidity;}
    public int getMaxhumidity() {
        return maxhumidity;
    }
    public int getminSize(){return this.minSize;}
    public int getmaxSize(){return this.maxSize;}
    public Enum.Duration getTime(){return this.time;}
}

