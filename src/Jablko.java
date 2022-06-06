import java.awt.geom.Ellipse2D;

/**
 * Klasa reprezentująca jabłko o wielkości X na Y i współżędnych x i y
 */
public class Jablko {
	
	private double x;
	private double y;
    
	public static final int X = 10;	// wielkości jablka
	public static final int Y = 10;

	/**
	 * Konstruktor inicjalizujący jabłko o podanych współżędnych
	 * @param x współżędna x
	 * @param y współżędna y
	 */
    public Jablko(double x, double y) {
		this.x = x;
		this.y = y;
	}

    /**
	 * Metoda zwracajaca ksztalt jabłka
	 * @return ksztalt jablka
	*/
	public Ellipse2D.Double getKsztalt() {
		return new Ellipse2D.Double(x, y, X, Y);
	}

    /**
	 * Generuje nowe, losowe współżędne dla jabłka
	 * @param snake wąż (nowe współżędne muszą być różne od wsp. węża)
	 */
    public void nowaPozycjaJablka(Snake snake) {
		for (Ellipse2D.Double e : snake.getListeElementow()) {
			while (x == e.getMinX() && y == e.getMinY()) {
				x = losowyNumer();
				y = losowyNumer();
			}
		}
	}

	/**
	 * Generuje losowy numer potrzebny do utworzenia jabłka w innym miejscu
	 * @return losowa liczba mieszcząca się poza ciałem węża
	 */
	public double losowyNumer() {
		double numer = 1111;
		while (numer >= 400 || numer % Snake.X != 0) {
			numer = Math.random() * 1000;
			numer = (int) numer;
		}
		return numer;
	}
    
}
