// Helping class that contains all the enumerations
//Autor: Hannah Winkler
public class Enum<T extends Enum<Q>> {

    enum Reasons{ //Zählt die möglichen Gründe für zeitliche Einschränkung auf, die entweder behebbar oder nicht behebbar sind
        poison(false, Duration.week),
        oxygen(false, Duration.month),
        toosmallforoffspring(true, Duration.year),
        resources(true, Duration.week);

        private final boolean fixable;
        private final Duration time;
        Reasons(boolean fixable, Duration time) {
            this.fixable = fixable;
            this.time = time;
        }
        public boolean getFixable(){return this.fixable;}//gibt zurück ob der jeweilige Grund behebbar ist oder nicht
        public Duration getDuration(){return this.time;}
    }
    enum Duration{ //verschiedene Zeitspannen, die durch eine int "order" aufsteigend sortiert sind
        week(0),
        month(1),
        year(2),
        unlimited(3);
        private final int order;
        Duration(int order) {
            this.order = order;
        }
        public int getOrder(){
            return this.order;
        }
        public Duration searchByOrder(int i){
            switch (i){
                case 0: return week;
                case 1: return month;
                case 2: return year;
                case 3: return unlimited;
            }
            return null;
        }
    }
    enum Quality{
        professional,
        semiprofessional,
        nonprofessional
    }

    enum Substrate{
        Sand,
        Kies,
        Erde
    }
    //Verschiedene Ameisenarten mit allen benötigten Attributen (Temperatur, Größe...)
    enum Ant{
        typ0(0, 100, 20, 25, 20, 30),
        typ1(1, 200, 30, 35, 40, 50),
        typ2(2, 20, 10,50, 10, 20);
        private final int antsize;
        private final int antQuantity;
        private final double antMinHumidity;
        private final double antMaxHumidity;
        private final int antMinTemperature;
        private final int antMaxTemperature;


        Ant(int antsize, int antquantity, int antMinTemperature, int antMaxTemperature, double antMinHumidity, double antMaxHumidity) {
            this.antsize=antsize;
            this.antQuantity = antquantity;
            this.antMinTemperature = antMinTemperature;
            this.antMaxTemperature = antMaxTemperature;
            this.antMinHumidity = antMinHumidity;
            this.antMaxHumidity =antMaxHumidity;
        }
        public int getAntSize(){return antsize;}
        public int getAntQuantity(){return antQuantity;}
        public int getAntMinTemperature(){return antMinTemperature;}
        public int getAntMaxTemperature() {
            return antMaxTemperature;
        }

        public double getAntMaxHumidity() {
            return antMaxHumidity;
        }

        public double getAntMinHumidity() {
            return antMinHumidity;
        }
    }
}
