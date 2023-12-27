
// STYLE: Parallele Programmierung
// Ein Objekt welches Gamesimulationen parallel ausführen lässt

public class GameThread extends Thread {
    Game game;
    public GameThread(Game game) {
        this.game = game;
    }

    // Nachbedingung: es werden 3 Ameisensimulationen geleichzeitig auf verschiedenen Threads ausgelöst
    @Override
    public void run() {
        game.execute();
        try {
            Thread.sleep(1000); // wait miliseconds before the next thread gets fired
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}