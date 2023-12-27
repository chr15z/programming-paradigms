import java.util.function.DoubleUnaryOperator;

public class Numeric  implements Calc<Double>, Rated<DoubleUnaryOperator, Numeric> {
    private double value;
    private DoubleUnaryOperator criterion;
    public Numeric(double value) {
        this.value = value;
    }

    //Vorbedingungen: input!=null
    //Nachbedingungen:gibt die Summe von dem Eingangsparameter und this.value als Double zurück
    @Override
    public Double sum(Double aDouble) {
        return this.value + aDouble;
    }
    //Vorbedingungen: input!=null
    //Nachbedingungen: bildet die Division von this.value und dem Eingangsparameter
    @Override
    public Double ratio(int i) {
        return this.value/i;
    }
    //Vorbedingungen: input!=null
    //Nachbedingungen: bildet den Größenvergleich zwischen this.value und dem Eingangsparameter
    @Override
    public boolean atLeast(Double aDouble) {
        return this.value >=aDouble;
    }
    //Vorbedingungen:input!=null
    //Beschreibung:ruft die abstrakte Methode im Parameter mit dem in this abgelegten double-
    //Wert auf
    //Nachbedingungen: packt den daraus resultierenden double-Wert in ein neues
    //Objekt von Numeric und gibt dieses zurück
    @Override
    public Numeric rated(DoubleUnaryOperator p) {
        double result = p.applyAsDouble(this.value);
        return new Numeric(result);
    }

    @Override
    public Numeric rated() {
        return null;
    }

    //Vorbedingungen: input!=null
    @Override
    public void setCriteroin(DoubleUnaryOperator p) {
        this.criterion = p;
    }

}
