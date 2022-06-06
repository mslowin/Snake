import javax.swing.*;
import java.awt.GridBagLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Klasa reprezentująca okno aplikacji
 */
public class OknoAplikacji extends JFrame {
    private Plansza plansza;
	private Thread thread;
	private Thread thread2;
	private Snake snake;

    // aktualny kierunek węża
	private Kierunek kierunek = Kierunek.GORA;
	private boolean rozpoczeto = false;

    /**
	 * Konstruktor inicjalizujący GUI, wątek odpowiedzialny za gre i ramkę samej gry 
	 */
	public OknoAplikacji() {
		wyczyscEkran();
		wyswietlTabliceWynikow();
		inicjujElementyOkna();
		inicjujGre();
		inicjujRamke();	
	}

	/**
	 * Metoda wyswietlająca w terminalu tablicę wyników pobraną z pliku
	 */
	private void wyswietlTabliceWynikow() {
		try {
			FileReader fr = new FileReader("leaderboard.txt");
			BufferedReader br = new BufferedReader(fr);
			String buffer;
			String tekst;
			List<Integer> ListaWszystkichPunktow = new ArrayList<>();

			while((buffer = br.readLine()) != null) {
				int numer = Integer.parseInt(buffer);
				ListaWszystkichPunktow.add(numer);
			}
			Set<Integer> set = new HashSet<>(ListaWszystkichPunktow);
			ListaWszystkichPunktow.clear();
			ListaWszystkichPunktow.addAll(set);
			Collections.sort(ListaWszystkichPunktow);
			Collections.reverse(ListaWszystkichPunktow);
			
			int ilosc_wynikow = ListaWszystkichPunktow.size();
			if(ilosc_wynikow > 5)
				ilosc_wynikow = 5;
			
			System.out.println("Top " + ilosc_wynikow + " najlepsze wyniki: ");
			System.out.println("------------------------------------");

			for(int i = 1; i <= ilosc_wynikow; i++)
			{
				tekst = i + ". \t" + ListaWszystkichPunktow.get(i - 1) + " zjedzonych jabłuszek";
				System.out.println(tekst);
			}
			System.out.println("------------------------------------");
			br.close();
			fr.close();
		} catch (FileNotFoundException e) {
			System.err.println(e);
			JOptionPane.showMessageDialog(getParent(),"Nie ma pliku: leaderboard");
		} catch (IOException e) {
			System.err.println(e);
		}

	}

	/**
	 * Metoda inicjująca planszę i jej elementy
	 */
    private void inicjujElementyOkna() {
		setLayout(new GridBagLayout());
		addKeyListener(new ObslugaKlawiatury());

		plansza = new Plansza();
		add(plansza);
	}
	
	/**
	 * Metoda inicjująca całą grę (również wątki)
	 */
	private void inicjujGre() {
		snake = new Snake(plansza);
		Runnable run = new Gra(plansza, snake, this);
		Runnable run2 = new Punkty(snake);
		thread = new Thread(run);
		thread2 = new Thread(run2);
	}

	/**
	 * Rozpoczyna nową gre
	*/
	public void rozpocznijGre() {
		wyczyscEkran();
		rozpoczeto = true;
		thread.start();
		thread2.start();
	}

	/**
	 * Czyści terminal
	 */
	public static void wyczyscEkran() {
		// użycie kodu ucieczkowego ANSI
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

	/**
	 * Kończy grę i pozwala zadecydować o ewentualnej kontynuacji za pomocą dialog boxa
	 */
	public void koniecGry() {

		// Wyswietlenie okienka w przypadku przegranej
		int tak_nie = JOptionPane.showConfirmDialog(this, "Chcesz zagrc jeszcze raz?", "KONIEC GRY!", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE);
		
		switch (tak_nie) {
			case JOptionPane.OK_OPTION:
				wyczyscEkran();
				wyswietlTabliceWynikow();
				
				kierunek = Kierunek.GORA;
				rozpoczeto = false;
				snake = new Snake(plansza);
				plansza.repaint();
				Runnable run = new Gra(plansza, snake, this);
				thread = null;
				thread = new Thread(run);

				Runnable run2 = new Punkty(snake);
				thread2 = null;
				thread2 = new Thread(run2);

				break;
			case JOptionPane.CANCEL_OPTION:
				System.exit(0);
				break;
			default:
				JOptionPane.showMessageDialog(getParent(),"Cos poszlo nie tak :( \nUruchom aplikacje jeszcze raz");
				break;
		}
	}
	
	/**
	 * Inicjalizuje ramkę (tytuł, rozmiar, itd.)
	 */
	private void inicjujRamke() {
		pack();
		setTitle("Snake");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		setResizable(false);
		setVisible(true);
	}

	/**
	 * Wyłapuje naciśnięcia z klawiatury i reaguje w przypadku naciśnięcia którejś ze strzałek
	 */
	private class ObslugaKlawiatury extends KeyAdapter {
		
		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_UP) {
				if (kierunek == Kierunek.DOL) return;
				if (!rozpoczeto) rozpocznijGre();
				if (snake != null) {
					snake.zmienKierunek(Kierunek.GORA);
					kierunek = Kierunek.GORA;
				}
			}
			
			if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				if (kierunek == Kierunek.GORA) return;
				if (!rozpoczeto) rozpocznijGre();
				if (snake != null) {
					snake.zmienKierunek(Kierunek.DOL);
					kierunek = Kierunek.DOL;
				}
			}
			
			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				if (kierunek == Kierunek.PRAWO) return;
				if (!rozpoczeto) rozpocznijGre();
				if (snake != null) {
					snake.zmienKierunek(Kierunek.LEWO);
					kierunek = Kierunek.LEWO;
				}
			}
			
			if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				if (kierunek == Kierunek.LEWO) return;
				if (!rozpoczeto) rozpocznijGre();
				if (snake != null) {
					snake.zmienKierunek(Kierunek.PRAWO);
					kierunek = Kierunek.PRAWO;
				}
			}
		}
	}
}
