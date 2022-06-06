import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Klasa reprezentująca węża jako listę kół, każdy X na Y pikseli 
 */
public class Snake {
    
    // wielkość pojedynczego elementu węża
	public static final int X = 20;
	public static final int Y = 20;
	
	private Plansza plansza;
	private List<Ellipse2D.Double> elementyWeza;
	private Kierunek kierunek;

	private Ellipse2D.Double temp;
	private Ellipse2D.Double tylek;

	private boolean koniec = false;

    /**
	 * Konstruuje objekt wąż z domyślnymi elementami
	 * @param plansza prostokątna przestrzeń, po której wąż może się poruszać
	 */
    public Snake(Plansza plansza) {
		this.plansza = plansza;
		inicjujElementyWeza();
	}

	/**
	 * Metoda inicjująca elementy węża
	 */
    private void inicjujElementyWeza() {
		elementyWeza = Collections.synchronizedList(new ArrayList<Ellipse2D.Double>());
		elementyWeza.add(new Ellipse2D.Double(260, 260        , X, Y));
		elementyWeza.add(new Ellipse2D.Double(260, 260 + Y    , X, Y));
		elementyWeza.add(new Ellipse2D.Double(260, 260 + 2 * Y, X, Y));
		elementyWeza.add(new Ellipse2D.Double(260, 260 + 3 * Y, X, Y));
	}

    /**
	 * Metoda zmieniająca kierunek węża
	 * @param kierunek nowy kierunek (UP, DOWN, LEFT, RIGHT)
	 */
	public void zmienKierunek(Kierunek kierunek) {
		this.kierunek = kierunek;
	}

    /**
	 * Metoda sprawdzająca, czy wąż zjadł jabłko, czy uderzył w siebie samego
	 */
	public void sprawdzWeza() {
		Ellipse2D.Double glowa = elementyWeza.get(0);
		Jablko jablko = plansza.getJablko();
		
		// udezrył w siebie
		for (int i = 1; i < elementyWeza.size(); i++) {
			if (glowa.getMinX() == elementyWeza.get(i).getMinX()
					&& glowa.getMinY() == elementyWeza.get(i).getMinY()) {
				koniec = true;
				return;
			}
		}
		// zjadł jabłko
		if (glowa.getMinX() == jablko.getKsztalt().getMinX()
				&& glowa.getMinY() == jablko.getKsztalt().getMinY()) {
			jablko.nowaPozycjaJablka(this);
			elementyWeza.add(tylek); // :D
		}
	}

	/**
	 * Metoda sprawdzająca, czy wąż zjadł jabłko, żeby dodać punkt
	 * @return prawda jeśli zjadł, fałsz jeśli nie
	 */
	public boolean czyZdobylPunkt() {
		Ellipse2D.Double glowa = elementyWeza.get(0);
		Jablko jablko = plansza.getJablko();

		// zjadł jabłko
		if (glowa.getMinX() == jablko.getKsztalt().getMinX()
				&& glowa.getMinY() == jablko.getKsztalt().getMinY()) {
			return true;
		}
		return false;
	}

    /**
	 * Metoda do poruszania wężem w podanym kierunku
	 */
	public void ruch() {
		switch (kierunek) {
		case GORA:
			ruchElementow();
			// głowa węża:
			elementyWeza.set(0, new Ellipse2D.Double(elementyWeza.get(0).getMinX(),
					elementyWeza.get(0).getMinY() - Y, X, Y));
			if (elementyWeza.get(0).getMinY() < 0) {
				koniec = true;
			}
			break;

        case DOL:
			ruchElementow();
			// głowa węża:
			elementyWeza.set(0, new Ellipse2D.Double(elementyWeza.get(0).getMinX(),
					elementyWeza.get(0).getMinY() + Y, X, Y));
			if (elementyWeza.get(0).getMaxY() > plansza.getBounds().getMaxY()) {
				koniec = true;
			}
			break;

		case LEWO:
			ruchElementow();
			// głowa węża:
			elementyWeza.set(0, new Ellipse2D.Double(
					elementyWeza.get(0).getMinX() - X, elementyWeza.get(0)
							.getMinY(), X, Y));
			if (elementyWeza.get(0).getMinX() < 0) {
				koniec = true;
			}
			break;

		case PRAWO:
			ruchElementow();
			// głowa węża:
			elementyWeza.set(0, new Ellipse2D.Double(
					elementyWeza.get(0).getMinX() + X, elementyWeza.get(0)
							.getMinY(), X, Y));
			if (elementyWeza.get(0).getMaxX() > plansza.getBounds().getMaxX()) {
				koniec = true;
			}
			break;

		default:
			new Exception("Nie ma takiego kierunku!").printStackTrace();
			break;
		}
	}

    /**
	 * Metoda pobierająca listę elementów, z których składa się wąż
	 * @return lista zawierająca kolejne elementy tworzące węża
	 */
	public List<Ellipse2D.Double> getListeElementow() {
		return elementyWeza;
	}

	/**
	 * Metoda przemieszczająca na planszy elementy węża
	 */
    private void ruchElementow() {
		for (int i = elementyWeza.size() - 1; i > 0; i--) {
			if (i == elementyWeza.size() - 1) {
				tylek = (Ellipse2D.Double) elementyWeza.get(i).clone();
			}
			temp = (Ellipse2D.Double) elementyWeza.get(i - 1).clone();
			elementyWeza.set(i, temp);
		}
	}

    /**
	 * Metoda pozwalająca określić koniec gry
	 * @return true jeśli koniec gry, false, jeśli nie
	 */
	public boolean CzyKoniecGry() {
		return koniec;
	}
}
