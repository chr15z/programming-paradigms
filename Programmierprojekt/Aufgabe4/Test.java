import java.sql.SQLOutput;

public class Test {

    public static void main(String[] args){

        //Compatibility (geeignete Objekte für Ants vs nicht geeignete)
        //typ0:
        int antsize = 0;
        int antquantity = 100;
        int antMinTemperature = 20;
        int antMaxTemperature = 25;
        double antMinHumidity = 20;
        double antMaxHumidity = 30;
        Thermometer thermometer = new Thermometer(1, antMinTemperature, antMaxTemperature, antMinHumidity, antMaxHumidity);
        //CompositeFormicarium welches geeignet ist für typ0
        CompositeFormicarium compositeFormicarium = new CompositeFormicarium(antMaxTemperature,antMaxHumidity, 0, Enum.Duration.week, thermometer);
        Nest nest = new Nest(antMaxTemperature,antMaxHumidity, 0, Enum.Duration.week, thermometer);
        Arena arena = new Arena(false, 10,20,nest);
        Forceps forceps = new Forceps(0,true, 0,0,0,0);

        //Erklärung für Compatibility testcases:
        //Um zu testen, ob ein Objekt o mit einer Ameisenart kompatibel ist,
        // müssen wir zuerst ein Compatibilityobjekt erstellen mit dem Übergabeparameter o und
        //dann die Methode compatible(o, ant) aufrufen

        System.out.println("Compatibility Testcase with Thermometer:");
        Thermometer testt = new Thermometer(10,0,6,0,100);
        Compatibility testcomp1 = new Compatibility(testt);
        boolean meanttofail = true;
        if (testcomp1.antcompatibility(testcomp1,Enum.Ant.typ2) != meanttofail) success();

        System.out.println(("Compatibility Testcase with Forceps:"));
        Forceps testforceps = new Forceps(10, true, 0,100,0, 100);
        Compatibility testcomp2 = new Compatibility(testforceps);
        meanttofail = false;
        if(testcomp2.antcompatibility(testcomp2, Enum.Ant.typ2)!=meanttofail)success();

        //CompositeFormicarium add und remove
        System.out.println("----------------------\nCompositeFormicarium Testcase");
        if (compositeFormicarium.add(arena)) success();
        else fail();

        //FormicariumIterator, Use case: CompositeFormicarium, 2xNest, Arena, Thermometer
        FormicariumItemIterator iterator = compositeFormicarium.iterator();
        System.out.println("----------------------\nFormicariumIterator Testcase");
        while(iterator.hasNext()){
            System.out.println(iterator.next());
        }

        //FormicariumSet, Use case Einkaufsliste: forceps, 2x antfarm, nest
        Nest nest1 = new Nest(antMaxTemperature,antMaxHumidity, 0, Enum.Duration.week, thermometer);
        Forceps forceps1 = new Forceps(0,true, 0,0,0,0);
        AntFarm antFarm1 = new AntFarm(0,0,0, Enum.Duration.week, Enum.Substrate.Kies);
        AntFarm antFarm2 = new AntFarm(0,0,0, Enum.Duration.week, Enum.Substrate.Kies);
        FormicariumItem[] array = new FormicariumItem[5];
        array[0] = nest1;
        array[1] = forceps1;
        array[2] = antFarm1;
        array[3] = antFarm2;
        array[4] = antFarm2;
        FormicariumSet set = new FormicariumSet(array);
        FormicariumItemIterator setIterator = set.iterator();
        System.out.println("----------------------\nFormicariumSet Testcase");

        while(setIterator.hasNext()){
            System.out.println(setIterator.next());
        }

        //add Methode:
        Nest nest1equal = new Nest(antMaxTemperature,antMaxHumidity, 0, Enum.Duration.week, thermometer);
        Nest nest2 = new Nest(antMaxTemperature,antMaxHumidity, 1, Enum.Duration.week, thermometer);
        if (set.getMap().get(nest1) == 1) success();
        else fail();
        set.add(nest1);
        if (set.getMap().get(nest1) == 2) success();
        else fail();
        set.add(nest1equal);
        if (set.getMap().get(nest1) == 3) success();
        else fail();
        set.add(nest2);
        if (set.getMap().get(nest1) == 3) success();
        else fail();


        //Equalsmethode
        System.out.println("----------------------\nEquals Testcase");
        Arena arena4 = new Arena(false, 10,20,nest);
        Arena arena5 = new Arena(false, 10,20,nest);
        if (arena4.equals(arena5)) success();
        else fail();
        if (nest1.equals(nest1equal))success();
        else fail();
        if (!nest1.equals(nest2))success();
        else fail();


    }

    private static void success(){
        System.out.println("Test successful!");
    }
    private static void fail(){
        System.out.println("Test failed!");
    }
}
/*
Begründungen für Vererbungen:

Antfarm:         erbt von Nest, da eine Antfarm als eine Form von Nest beschrieben wird.
Arena:           erbt von FormicariumPart, da eine Arena alleine nicht zum Überleben reicht -
                 deshlab ist sie kein Formicarium, sondern nur ein Bestandteil davon.
Compatibility:   erbt von keiner Klasse, da es als einzige Klasse niemals ein physisches Objekt darstellt.
CompositeFormicarium: erbt von Formicarium, da es ein Formicarium ist mit der Besonderheit, dass es änderbar ist.
Forceps:         erbt von Instrument und FormicariumItem. Die Pinzette ist ein Instrument, das im Zusammenhang mit Formicarien verwendet wird,
                 jedoch kein Bestandteil eines Formicariums - Instrument ist als Werkzeug definiert, das gleichzeitig ein FormicariumItem ist.
Formicarium:     erbt von FormicariumPart, da Formicariumpart auch ein Formicarium sein kann und somit alle Methoden und Variablen beeinhaltet, die ein Objekt von Formicarium benötigt.
Formicariumitem: erbt nicht, da es sowohl ein FormicariumPart, als auch ein Werkzeug sein kann.
Formicariumpart: erbt von Formicariumitem und ist (nach Formicariumitem) die Klasse, die an die meisten anderen klassen vererbt,
                 da die meisten Klassen entweder ein Formicarium oder ein Bestandteil davon sein können.
FormicariumSet:  erbt nicht, sondern wird als Objekt genutzt um ein Set zu erzeugen (Dieses Objekt kann so zB als Einkaufsliste verwendet werden)
Instrument:      erbt nicht von ForicariumPart, da ein Instrument auch nicht im Zusammmenhang mit einem Formicarium verwendet werden kann.
Nest:            erbt von Formicarium, da ein Nest - wenn auch nur für begrenzte Zeit - selbst als formicarium dienen kann und deshlab alle Methoden, Variablen, ect. davon benötigt.
Thermometer:     erbt von Instrument, da es ein Werkzeug ist, das Bestandteil eines Formicariums sein kann - Instrument ist als Werkzeug definiert,
                 das ein Bestandteil eines Formicariums sein kann. Ebenfalls erbt es von Formicariumpart (möglicher Bestandteil eines Formicariums).

 */

/*
Marlene Lehotzki:
    Arena, Forceps, FormicariumItem, Instrument, Thermometer, alle equals Methoden implementiert

Hannah Winkler:
    Compatibility implementiert, alle Enumerations erstellt und in die jeweilogen Klassen eingebaut, Kommentare verfasst in Test

Christoph Zeitler:
    Formicarium, CompositeFormicarium, Antfarm, Nest, FormicariumItemIterator, FormicariumSet implementiert
 */
