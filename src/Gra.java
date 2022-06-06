/**
 * Klasa odpowiedzialna za samą grę, lub dokładniej za wprawianie węża w ruch
 */
public class Gra implements Runnable {

	// Czas w milisekundach pomiędzy kolejnymi ruchami węża
	public static final int DELAY = 200;

	private OknoAplikacji okno;
	private Plansza plansza;
	private Snake snake;
	private Jablko jablko;
	
	/**
	 * Konstruuje nowy objekt gry, który może być użyty przy tworzeniu wątku
	 * @param plansza prostokątna przestrzeń, po której wąż może się poruszać
	 * @param snake obiekt typu wąż
	 * @param okno ramka gry, w której wywołana będzie metoda końca gry
	 */
	public Gra(Plansza plansza, Snake snake, OknoAplikacji okno) {
		// jablko tworzone jest wzgledem pozycji weza
		jablko = new Jablko((Snake.X + 4 * Snake.X), (Snake.Y + 4 * Snake.Y));
		this.okno = okno;
		this.snake = snake;
		this.plansza = plansza;

		this.plansza.setWaz(snake.getListeElementow());
		this.plansza.setJablko(jablko);
	}

    @Override
	public void run() {
		try {
			while (true) {
				snake.sprawdzWeza();
				snake.ruch();
				
				if (snake.CzyKoniecGry()) {
					Thread.currentThread().interrupt();
				}
				if (!Thread.currentThread().isInterrupted()) {
					plansza.repaint();
				}
				Thread.sleep(DELAY);
			}
		} catch (InterruptedException ex) {
			okno.koniecGry();
			
		}
	}
    
}
