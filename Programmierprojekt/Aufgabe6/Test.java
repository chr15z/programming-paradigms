import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


public class Test {
    public static void main(String[] args) {
        // A TEIL
        //Testcase Formicarium:
        Formicarium f1 = new Formicarium("f1", AntType.houseAnt);
        Formicarium f2 = new Formicarium("f2", AntType.fireAnt);
        f1.addNest(new NestAir(10,10,10,new SandMix(5)));
        f1.addNest(new NestAir(10,10,10,new GasConcreteSlab(2,2)));
        f1.addNest(new NestAir(10,10,10,new SandMix(5)));
        f1.addNest(new NestHeating(10,10,10,new SandMix(3)));
        f1.addNest(new NestHeating(10,10,10,new GasConcreteSlab(5,2)));
        f1.addNest(new NestHeating(10,10,10,new GasConcreteSlab(7,3)));
        /*
        f1.printAverageVolumeGas();
        f1.printAverageWeight();
        System.out.println(f1.averageVolumeNests());
        f2.printAverageVolumeGas();
        f2.printAverageWeight();
        System.out.println(f2.averageVolumeNests());
        */

        // B TEIL
        System.out.println("Kontext B");
        System.out.println();
        System.out.println("1.");
        List<Class<?>> allclasses = List.of(Institute.class, Formicarium.class, Author.class, Nest.class, NestAir.class, NestHeating.class, Material.class, SandMix.class, GasConcreteSlab.class, CreatedBy.class, Zusicherung.class, SelfMade.class);
        //1.Alle Selbstdefinierten:
        classListPrinter(selfDef(allclasses));


        //2. Zuordnung
        System.out.println("2.");
        classListPrinter( ClassFromAuthor(Author.Hannah, allclasses));
        classListPrinter( ClassFromAuthor(Author.Marlene, allclasses));
        classListPrinter( ClassFromAuthor(Author.Chris, allclasses));

        //3. Für jede Klasse und jedes Interface die Signaturen aller darin enthaltenen nicht-privaten Methoden und Konstruktoren
        // sowie alle dafür geltenden Zusicherungen
        System.out.println("3.");
        allZusicherungen(allclasses);
        System.out.println();

        //4. Anzahl der Klassen, Interfaces und Annotationen
        System.out.println("4.");
        System.out.println("Anzahl Hannah:" + ClassFromAuthor(Author.Hannah, allclasses).size()); //weil createdby nicht gezählt wird
        System.out.println("Anzahl Marlene:" + ClassFromAuthor(Author.Marlene, allclasses).size());
        System.out.println("Anzahl Chris:" + ClassFromAuthor(Author.Chris, allclasses).size());
        System.out.println();

        //5. Anzahl der Methoden und Konstruktoren
        System.out.println("5.");
        System.out.println(countMethodsAndConstructors(allclasses, Author.Hannah));
        System.out.println(countMethodsAndConstructors(allclasses, Author.Marlene));
        System.out.println(countMethodsAndConstructors(allclasses, Author.Chris));
        System.out.println();

        //6.Anzahl der Zusicherungen pro Personen
        System.out.println("6.");
       for (Author a: Author.values()){
           System.out.println(countZusicherungen(allclasses, a));
           }
    }
    private static int countMethodsAndConstructors(List<Class<?>> allclasses, Author responsiblePerson) {
        int methodCount = 0;
        int constructorCount = 0;

        System.out.println("Anzahl aller Methoden und Konstruktoren von " + responsiblePerson);

            for (Class<?> clazz : allclasses) {
                if (clazz.isAnnotationPresent(CreatedBy.class)) {
                    CreatedBy responsible = clazz.getAnnotation(CreatedBy.class);
                    if (responsible.value().equals(responsiblePerson)) {
                        // Zählen Sie Methoden und Konstruktoren
                        methodCount += clazz.getDeclaredMethods().length;
                        constructorCount += clazz.getDeclaredConstructors().length;
                    }
                }
            }
            return methodCount+constructorCount;
    }
    private static int countZusicherungen(List<Class<?>> allclasses, Author responsiblePerson) {
        int zsCount = 0;

        System.out.println("Anzahl aller Zusicherungen von " + responsiblePerson);

        for (Class<?> clazz : allclasses) {
            if (clazz.isAnnotationPresent(CreatedBy.class)) {
                CreatedBy responsible = clazz.getAnnotation(CreatedBy.class);
                if (responsible.value().equals(responsiblePerson)) {
                    for (Constructor<?> c : clazz.getDeclaredConstructors()) {
                        if (c.isAnnotationPresent(Zusicherung.class)) zsCount++;
                    }
                    for (Method m : clazz.getDeclaredMethods()) {
                        if (m.isAnnotationPresent(Zusicherung.class)) zsCount++;
                    }

                }
            }
        }
        return zsCount;
    }


    private static void classListPrinter(List<Class<?>> allclasses){
        for (Class<?> clazz : allclasses) {
            System.out.println(clazz.getName());
        }
        System.out.println();
    }
    private static List<Class<?>> ClassFromAuthor(Author author, List<Class<?>> allclasses){
        List<Class<?>> classes = new ArrayList<>();
        System.out.println("Klassen, Interfaces, Annotationen erstellt von " + author + ":");
        for (Class<?> clazz : allclasses) {
            if (clazz.isAnnotationPresent(CreatedBy.class)) {
                CreatedBy createdByAuthor = clazz.getAnnotation(CreatedBy.class);
                if (createdByAuthor.value().equals(author)) {
                    classes.add(clazz);
                }
            }
        }
        if (author.equals(Author.Hannah))classes.add(CreatedBy.class);
        return classes;
    }
    private static List<Class<?>> selfDef(List<Class<?>> allclasses){
        System.out.println("Alle selbstdefinierten Klassen, Interfaces und Annotationen:");
        List<Class<?>> classes = new ArrayList<>();
        for (Class<?> clazz : allclasses) {
            if (clazz.isAnnotationPresent(SelfMade.class)) {
                classes.add(clazz);
            }
        }
        System.out.println("selfDef"); //muss so ausgegeben werden
        return classes;
    }

    private static void allZusicherungen (List<Class<?>> allclasses) {
        for (Class<?> clazz : allclasses) {
            zusicherungen(clazz);
        }
    }

    private static void zusicherungen(Class<?> klasseORInterface) {
        System.out.println("Invarianten und History Constraints von " + klasseORInterface.toString() +" :");
        Zusicherung zsklasse = klasseORInterface.getAnnotation(Zusicherung.class);
            if (zsklasse != null) {
                System.out.println("Invarianten: " + zsklasse.invariante());
                System.out.println("History Constraints: " + zsklasse.historyConstraints());
                System.out.println();
            }
        System.out.println("Alle Zusicherungen und Signaturen der public Methoden / Konstruktoren von " + klasseORInterface.toString() +" :");

        for (Constructor<?> c : klasseORInterface.getDeclaredConstructors()) {
            if (c.isAnnotationPresent(Zusicherung.class)) {
                Zusicherung zsMethode = c.getAnnotation(Zusicherung.class);
                if (zsMethode != null) {
                    System.out.println("public " + c.getName() + " " );
                    System.out.println("Vorbedingung: " + zsMethode.vorbedingung());
                    System.out.println("Nachbedingung: " + zsMethode.nachbedingung());
                    System.out.println();
                }
            }

        }
        for (Method m : klasseORInterface.getDeclaredMethods()) {
            if (m.isAnnotationPresent(Zusicherung.class)) {
                Zusicherung zusicherungAnnotation = m.getAnnotation(Zusicherung.class);
                if (zusicherungAnnotation != null) {
                    System.out.println("public " + m.getName() + " " );
                    System.out.println("Vorbedingung: " + zusicherungAnnotation.vorbedingung());
                    System.out.println("Nachbedingung: " + zusicherungAnnotation.nachbedingung());
                    System.out.println();


                }
            }
        }
    }
}



    //Wir brauchen eine Methode die durch alle Klassen in unserem Ordner iteriert,
        //und eine Liste mit Klassen, die die Annotation @selfmade hat
        //maybe können wir eine methode machen die eine annotation als parameter nimmt




    //Notes für uns:
    //Keine Kommentare! Nur annotationen
    //Führen Sie zumindest einen Testfall ein, bei dem eine statistische Auswertung
    //ohne entsprechende Vorkehrungen eine Exception aufgrund einer Division durch 0 auslösen würde.


    //Selbst definierte Klassen/Interfaces: Nest, NestAir, NestHeating, Materia
    //Hannah Winkler:
    //Christoph Zeitler:
    //Marlene Lehotzki:

