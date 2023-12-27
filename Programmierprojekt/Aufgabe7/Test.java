public class Test {
    @Zusicherung(author = Author.Hannah)
    public static void main(String[] args) {
        //Testcases:

         /*
        Dabei sind Instanzen aller in der Lösung vorkommenden Typen
        zu erzeugen. Auch für Ameisenforschungsinstitute mit einer Inventarliste
        und Ameisenstaaten sind eigene Objekte zu erzeugen, und mindestens
        3 unterschiedliche Institute sind zu testen. Testfälle sind so zu gestal-
                ten, dass sich deklarierte Typen von Variablen im Allgemeinen von den
        dynamischen Typen ihrer Werte unterscheiden
                */

        //Testfälle:
        //min 3 Antinstitute:
        EuropeanAnt eant1 = new EuropeanAnt(1);
        EuropeanAnt eant2 = new EuropeanAnt(2);
        EuropeanAnt eant3 = new EuropeanAnt(3);

        TropicalAnt trop1 = new TropicalAnt(1);
        FormicariumUnregulated formiun1 = new FormicariumUnregulated(10, false, eant1, 1);
        FormicariumUnregulated formiun2 = new FormicariumUnregulated(15, false,eant2, 2);

        FormicariumRegulated formicarium1 = new FormicariumRegulated(20, false, eant1, 1);
        FormicariumRegulated formicarium2 = new FormicariumRegulated(30, false, eant2, 2);
        FormicariumRegulated formicarium3 = new FormicariumRegulated(100, false, eant3, 3);

        Inventorylist list1reg= new Inventorylist();
        list1reg.addForm(formicarium1);
        Inventorylist list2reg = new Inventorylist();
        list2reg.addForm(formicarium2);
        Inventorylist list3reg = new Inventorylist();
        list3reg.addForm(formicarium3);
        list3reg.addForm(formicarium1);

        Inventorylist listun1 = new Inventorylist();
        listun1.addForm(formiun1);
        listun1.addForm(formiun2);

        AntInstitute inst1 = new AntInstitute(list1reg);
        AntInstitute inst2 = new AntInstitute(list2reg);
        AntInstitute inst3 = new AntInstitute(list3reg);


        System.out.println("Testen welches Formicarium für einen Ameisenstaat geeignet ist: ");
        System.out.println();

        System.out.println("Liste enthält unreguliertes Form (size 1) und wird mit europäischer Ameise (size 1) verglichen:"); //soll nicht null sein
        System.out.println(listun1.assignForm(eant1));

        System.out.println("Liste enthält reguliertes Form (size 1) und wird mit europäischer Ameise (size 1) verglichen:"); //soll null sein, weil erguliert nur mit tropisch geht
        System.out.println(list1reg.assignForm(eant1));

        System.out.println("Liste enthält reguliertes Form (size 1) und wird mit tropischen Ameise (size 1) verglichen:");//sollte nicht null sein
        System.out.println(list1reg.assignForm(trop1));

        System.out.println();
        System.out.println("Preisabfragen:");

        //Pricefree
        if (listun1.priceFree()==25) success();else fail(); //sollte 25 zurückgeben
        if (list1reg.priceFree()==20)success();else fail(); //sollte 20 zurückgeben
        //Priceoccupied
        listun1.addForm(new FormicariumRegulated(13,true, null, 2));
        listun1.priceOccupied();
        list1reg.priceOccupied();

        System.out.println();
        System.out.println("Alle formicarien einer liste anzeigen:");
        list1reg.showFormicarium();

        System.out.println();
        System.out.println("Alle Ameisenstaaten von Institut 1:");
        inst1.getInventorylist().showAnts();
        System.out.println("Alle Formicarien von Institut 2:");
        inst2.getInventorylist().showFormicarium();
        System.out.println("Alle Ameisenstaaten von Institut 3:");
        inst3.getInventorylist().showAnts();


        System.out.println("Kontext B:");
        System.out.println("Anzahl der Aufrufe von assignForm: " + MethodCountAspect.getAssignFormCounter());
        System.out.println("Anzahl der Aufrufe aller Visitor-Methoden: " + MethodCountAspect.getVisitorMethodCounter());





        /*Aufgabenverteilung:
        Hannah Winkler:
        hat AntColony, AntInstitut, die TestCases erstellt und Kontext B versucht
        Marlene Lehotzki:
        Hat die Zusicherungen + Author, TropicalAnt und EuropeanAnt erstellt und auch Christoph bei der Erstellung von Inventorylist
        unterstützt
        Christoph Zeitler:
        Hat die Formicarien + FormicariumRegulated und FormicariumUngregulated erstellt und war Hauptverantwortlich für Inventorylist
         */
    }
    private static void success(){
        System.out.println("Test succeeded");
    }
    private static void fail(){
        System.out.println("Test failed");
    }
}
