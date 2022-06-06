import java.io.*;

/**
 * Klasa reprezentująca zdobyte punkty
 */
public class Punkty implements Runnable {
    
    public static final int DELAY = 220;

    public Snake snake;
    int punkty = 0;
    boolean CzyZjadl = false;
    
    /**
     * Konstruktor klasy Punkty 
     * @param snake wąż, którego punkty mają być liczone
     */
    public Punkty(Snake snake) {
        this.snake = snake;
    }

    /**
     * Metoda dodająca punkty do puli
     * @param ilosc ilość punktów, która ma być dodana
     */
    public void dodajPunkty(int ilosc) {
        punkty = punkty + ilosc;
    }

    /**
     * Metoda zmieniająca ilość zdobytych punktów
     * @param ilosc ilość punktów, która ma być przypisana do puli
     */
    public void setPunkty(int ilosc) {
        punkty = ilosc;
    }

    /**
     * Metoda zapisująca wszystkie zdobyte punkty do pliku tekstowego
     * @param punkty zdobyte punkty do zapisania
     * @throws IOException wyjątek wejścia/wyjścia
     */
    public void zapiszWynikDoPliku(int punkty) throws IOException {
        FileWriter fw = new FileWriter("leaderboard.txt", true);
        String str_punkty = punkty + "\n";
        fw.write(str_punkty);    
        fw.close();  
    }

    /**
	 * Czyści terminal
	 */
    public static void wyczyscEkran() {
        // użycie kodu ucieczkowego ANSI
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    @Override
    public void run() {
        try {
            while (true) {
                CzyZjadl = snake.czyZdobylPunkt();

                if (snake.CzyKoniecGry()) {
                    wyczyscEkran();
                    System.out.println("----------------------------------");
		            String output = "Wszytkie uzyskane punkty = " + punkty;
		            System.out.println(output);
                    System.out.println("----------------------------------");
                    try {
                        zapiszWynikDoPliku(punkty);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
					Thread.currentThread().interrupt();
				}
                if (CzyZjadl == true) {
                    dodajPunkty(1);
                    wyczyscEkran();
                    System.out.println("Punkty:\t" + punkty);
                }
                Thread.sleep(DELAY);
            }
        } catch (InterruptedException ex) {
            System.out.println("KONIEC!");
            setPunkty(0);
        }
    }
}
