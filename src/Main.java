import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.awt.*;
/**
 Klasa w ktorej powolujemy do zycia okno, muzyke oraz menubar
 */
public class Main {
    /**
     Metoda main, w ktorej tworzy sie okno oraz menu. Odtwarza sie tez w niej muzyka
     */
    public static void main(String[] args) {
        /**
         Obiekt klasy ktora stworzylismy - Game
         */
        Game game = new Game();
       //tworzenie okna
        /**
         Nowe okno
         */
        JFrame okno = new JFrame("Snake"); //tytul okna
        okno.setBounds(10,10,860,780); //granice okna
        okno.setResizable(false); //czy mozna zmieniac rozmiar?
        okno.setVisible(true); //czy jest widoczne?
        okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //domyslna akcja przycisku X
        okno.add(game); //dodajemy do okna nasza klase game
        ///muzyka :
        /**
         W tym obiekcie odtwarza sie muzyka
         */
        new JFXPanel(); //niezbedne
        Media music = new Media(new File("assets/music.mp3").toURI().toString()); //sciezka do muzyki
        MediaPlayer mediaPlayer = new MediaPlayer(music); //stworz nowy media player
        mediaPlayer.setVolume(0.5); //glosnosc
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); //zapetlenie
        mediaPlayer.play(); //odtwarzanie


        //menusy :
        /**
         Menu u gory okna
         */
        JMenuBar menubar = new JMenuBar(); //tworzenie nowego menubara
        JMenu menu = new JMenu("Game"); //nowe menu
        JMenuItem exit = new JMenuItem("Exit"); //nowa pozycja w menu
        exit.setToolTipText("Quit the game"); //opis pozycji
        exit.addActionListener((ActionEvent event) -> {  //co sie dzieje jak ja wcisniemy
            System.exit(0);
        });
        //ponizej analogicznie:
        JMenu speed = new JMenu("Speed"); //ustawienia predkosci
        JMenuItem zero = new JMenuItem("0");
        zero.addActionListener((ActionEvent event) -> {
            game.delay = 150; //ustawia opoznienie czasu
            game.speed = 0; //ustawia poziom przedkosci
            game.time.restart(); //restartuje czas w grze
        });
        JMenuItem jeden = new JMenuItem("1");
        jeden.addActionListener((ActionEvent event) -> {
            game.delay = 100;
            game.speed = 10;
            game.time.restart();
            System.out.println("Set delay : " + game.delay + " Set speed : " + game.speed/10);
        });
        JMenuItem dwa = new JMenuItem("2");
        dwa.addActionListener((ActionEvent event) -> {
            game.delay = 50;
            game.speed = 20;
            game.time.restart();
            System.out.println("Set delay : " + game.delay + " Set speed : " + game.speed/10);
        });
        JMenuItem trzy = new JMenuItem("3");
        trzy.addActionListener((ActionEvent event) -> {
            game.delay = 0;
            game.speed = 30;
            game.time.restart();
            System.out.println("Set delay : " + game.delay + " Set speed : " + game.speed/10);
        });

        JMenuItem restart = new JMenuItem("Restart"); //przycisk sluzacy do restartu gry
        restart.setToolTipText("Restart the game");
        restart.addActionListener((ActionEvent event) -> {
            game.moves = false;
            game.snakelenght = 3;
            game.score = 0;
            game.speed = 0;
            game.delay = 150;
            game.time.restart();
            game.repaint();

        });

        //dodajemy opcje do poszczegolnych menu
        speed.add(zero);
        speed.add(jeden);
        speed.add(dwa);
        speed.add(trzy);
        menu.add(speed);
        menu.add(restart);
        menu.add(exit);

        menubar.add(menu);
        okno.setJMenuBar(menubar); //ustawiamy menubar naszego okna
        okno.validate();
        okno.repaint();
    }














}
