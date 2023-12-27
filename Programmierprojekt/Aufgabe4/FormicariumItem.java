
// Formicarium itself, component of a formicarium, or a measuring device or tool that is used in conjunction with formicaria
public interface FormicariumItem extends Iterable {


    // gibt Objekt vom Typ Compatibility zurück (Wenn ein von Compatibility beschriebenes Umweltkriterium nicht relevant ist, wird dafür der größtmögliche Wertebereich angenommen.)
    // Nachbedingung:
    public Compatibility compatibility();



}
