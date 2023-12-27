//enumeration zur Beschreibung der verschiedenen Werte, mit einem Int value, um die Reihenfolge zu erleichtern
enum QualityValue{

    professional(3),
    semiProfessional(2),
    hobby(1),
    notsuitable(0);
    private final int order;
    QualityValue(int order) {
        this.order = order;
    }
    //Nachbedingungen:gibt die Ordnungsinteger des jeweilgen Enums an
    public int getOrder() {
        return this.order;
    }

    //Vorbedingungen: 0<=i<=3
    //Nachbedinungen: gibt das zur übergebenen Ordnungszahl i passende enum zurück
    public QualityValue getByOrder(int i){
        switch (i){
            case 3: return professional;
            case 2: return semiProfessional;
            case 1: return hobby;
            case 0:return notsuitable;
        }
        return null;
    }

}
public class Quality implements Calc<QualityValue> {
    private QualityValue quality;

    public Quality(QualityValue quality) {
        this.quality = quality;
    } //Konstruktor

    //Vorbedingungen: input!=null
    //Nachbedingungen: gibt den kleineren Wert von this und dem Parameter zurück
    @Override
    public QualityValue sum(QualityValue other) {
        return (this.quality.compareTo(other) <= 0) ? this.quality : other;
    }

    //Nachbedingungen: gibt this.quality zurück
    @Override
    public QualityValue ratio(int divisor) {
        return this.quality;
    }

    //Vorbedingungen: input!=null
    //Nachbedingungen:gibt true zurück, wenn der Wert von this größergleich ist als der Wert des Parameters
    @Override
    public boolean atLeast(QualityValue value) {
        return this.quality.getOrder() >= value.getOrder();
    }
}

