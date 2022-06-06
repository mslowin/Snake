import java.util.List;
import javax.swing.*;
import java.awt.geom.Ellipse2D;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 * Klasa reprezentująca plansze (SZEROKOSC x WYSOKOSC) jako zbiór elementów węża i jabłko
 */
public class Plansza extends JPanel {

    public static final int SZEROKOSC = 500;
	public static final int WYSOKOSC = 500;

    private List<Ellipse2D.Double> elementyWeza;
	private Jablko jablko;

	/**
	 * Konstruuje obiekt typu Plansza i ustawia jego wielkość i kolor
	 */
    public Plansza() {
		setPreferredSize(new Dimension(SZEROKOSC, WYSOKOSC));
		setBackground(Color.BLACK);
	}

	/**
	 * Metoda zmieniająca elementy węża
	 * @param elementyWeza lista zawierajaca elementy węża
	 */
	public void setWaz(List<Ellipse2D.Double> elementyWeza) {
		this.elementyWeza = elementyWeza;
	}
	
	/**
	 * Metoda zmieniająca jabłko
	 * @param jablko obiekt typu jabłko
	 */
	public void setJablko(Jablko jablko) {
		this.jablko = jablko;
	}
	
	/**
	 * Metoda pobierająca jabłko
	 * @return obiekt typu Jablko
	 */
	public Jablko getJablko() {
		return jablko;
	}

	@Override
	public void paintComponent(Graphics grafika) {
		super.paintComponent(grafika);
		Graphics2D grafika2 = (Graphics2D) grafika;
		
		// Rysowanie jabłka na środku trajektorii 
		grafika2.setPaint(Color.RED);
		grafika2.fillOval((int) jablko.getKsztalt().getMinX() + (Jablko.X/2), // wspolzedna x
						  (int) jablko.getKsztalt().getMinY() + (Jablko.Y/2), // wspolzedna y
						  (int) Jablko.X,  // szerokosc jablka
						  (int) Jablko.Y); // wysokosc jablka
		
		// Rysowanie elementów ciała węża
		grafika2.setPaint(new Color(34, 136, 42)); // ciemny zielony
		for (Ellipse2D e : elementyWeza) {
			grafika2.fill(e);
		}
		
		// Rysowanie głowy węża
		grafika2.setPaint(new Color(12, 234, 14));  // jasny zielony
		grafika2.fill(elementyWeza.get(0));
	}
}
