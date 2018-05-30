
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

/**
 Glowna klasa, w ktorej wykonuje sie gra
 */
public class Game extends JPanel implements KeyListener, ActionListener
{
    /**
     Metoda nieuzywana ale niezbedna do implementacji
     */
    public void keyTyped(KeyEvent e)
    {
    }
    /**
     Metoda nieuzywana ale niezbedna do implementacji
     */
    public void keyReleased(KeyEvent e)
    {
    }
    /**
     obraz z tytulem gry, ktory wyswietlany jest u gory okna
     */
    private ImageIcon title = new ImageIcon("assets/title.png");  //wczytywanie obrazow tla
    /**
     tlo pola gry
     */
    private ImageIcon gamearea = new ImageIcon("assets/gamearea.png");
    /**
     tlo okna
     */
    private ImageIcon background = new ImageIcon("assets/tlo.jpg");

    /**
     * pozycja x klockow weza
     */
    private int[] snakex = new int[704];
    /**
     * pozycja y klockow weza
     */
    private int[] snakey = new int[704];

    /**
     * Czy waz porusza sie w gore ?
     */
    private boolean up = false;
    /**
     * Czy waz porusza sie w dol ?
     */
    private boolean down = false;
    /**
     * Czy waz porusza sie w lewo ?
     */
    private boolean left = false;
    /**
     * Czy waz porusza sie w prawo ?
     */
    private boolean right = true;

    /**
     * Grafika glowy odwroconej w gore
     */
    private ImageIcon headup = new ImageIcon("assets/glowagora.png");
    /**
     * Grafika glowy odwroconej w dol
     */
    private ImageIcon headdown = new ImageIcon("assets/glowadol.png");
    /**
     * Grafika glowy odwroconej w lewo
     */
    private ImageIcon headleft = new ImageIcon("assets/glowalewo.png");
    /**
     * Grafika glowy odwroconej w prawo
     */
    private ImageIcon headright = new ImageIcon("assets/glowaprawo.png");
    /**
     * Grafika ciala weza
     */
    private ImageIcon body = new ImageIcon("assets/body.png");
    /**
     * Grafika obiektu do zbierania
     */
    private ImageIcon fruit = new ImageIcon("assets/cd.png");
    /**
     * Dlugosc weza
     */
    public int snakelenght = 3; //dlugosc weza
    /**
     * Rozmiar jednego klocka weza
     */
    private int snakebodysize = 25; //rozmiar jednego "klocka" weza
    /**
     * Wynik punktowy
     */
    public int score = 0; //wynik punktowy

    /**
     * Domyslne pozycje X w ktorych moze pojawic sie znajdzka
     */
    private int[] fruitx={15,40,65,90,115,140,165,190,215,240,265,290,315,340,365,390,415,440,465,490,515,540,565,590,615,640,665,690,715,740,765,790,815};
    /**
     * Domyslne pozycje Y w ktorych moze pojawic sie znajdzka
     */
    private int[] fruity={130,155,180,205,230,255,280,305,330,355,380,405,430,455,480,505,530,555,580,605,630,655,680};

    private Random rand = new Random();
    /**
     * Wylosowana pozycja X dla znajdzki
     */
    private int fruitxpos = rand.nextInt(32); //wylosowana pozycja x dla znajdzki
    /**
     * Wylosowana pozycja Y dla znajdzki
     */
    private int fruitypos = rand.nextInt(22); //wylosowana pozycja y dla znajdzki


    public Timer time;
    /**
     * Opoznienie - kontroluje szybkosc poruszania
     */
    public int delay = 150; //kontroluje szybkosc poruszania
    /**
     * Okresla poziomy szybkosci wyswietlane w grze
     */
    public int speed = 0; //szybkosc w poziomach
    /**
     * Czy waz sie porusza ?
     */
    public boolean moves = false; //czy waz sie porusza?
///////////////////////////////////////


    /**
     * Konstruktor w ktorym powolujemy listenery oraz timer
     */
    public Game()
    {
        addKeyListener(this); //listener na klawiature
        //czy mozna sie "skurpic" na oknie
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        time = new Timer(delay, this); //inicjalizuje czas
        time.start();
    }
    /**
     * Metoda ktora rysuje wszystko w obrebie naszego okna
     * @param g Obiekt Graphics ktory sluzy do rysowanie w oknie
     */
    public void paint (Graphics g)
    {
        //poczatkowe ustawienia weza
        System.out.println("x = "+snakex[0]+" y = "+snakey[0]);
        if(moves == false)
        {
            snakex[2] = 15;
            snakex[1] = 40;
            snakex[0] = 65;
            snakey[2] = 130;
            snakey[1] = 130;
            snakey[0] = 130;

        }

        ////gamearea
        g.setColor(Color.BLACK);
        //rysujemy tlo
        background.paintIcon(this,g,0,0);
        //rysujemy title gry:
        g.drawRect(4,10,846,92);
        title.paintIcon(this , g, 5,11);
        //rysujemy granice gry :
        g.drawRect(14,130,825,575);
        //pole gry
        gamearea.paintIcon(this,g,15,131);
        //scores
        g.setColor(Color.WHITE);
        g.setFont(new Font("arial", Font.BOLD, 20));
        g.drawString("Score : " + score , 15, 125);
        g.drawString("Speed : " + speed/10 , 140, 125);
        ///////////////////////////////////////////////////
        //glowa w prawo bo waz zaczyna isc w prawo
        headright.paintIcon(this,g, snakex[0],snakey[0]);


        //rysowanie weza
        for(int a = 0; a<snakelenght; a++)
        {
            //jesli a=0 to jest to glowa - rysujemy ja w zaleznosci od kierunki
            if(a==0 && right)
            {
                headright.paintIcon(this,g, snakex[a],snakey[a]);
            }
            if(a==0 && left)
            {
                headleft.paintIcon(this,g, snakex[a],snakey[a]);
            }
            if(a==0 && down)
            {
                headdown.paintIcon(this,g, snakex[a],snakey[a]);
            }
            if(a==0 && up)
            {
                headup.paintIcon(this,g, snakex[a],snakey[a]);
            }
            //jesli a!=0 to reszte ciala rysujemy juz jednym blokiem
            if(a!=0)
            {
                body.paintIcon(this,g, snakex[a],snakey[a]);
            }
        }

        //rysowanie znajdzki - jesli glowe wejdzie na znajdzke to
        if(fruitx[fruitxpos] == snakex[0] && fruity[fruitypos] == snakey[0])
        {
            score = score + 10; //dodaj score
            snakelenght++; //zwieksz dlugosc
            //losowanie nowego polozenia
            fruitxpos = rand.nextInt(32);
            fruitypos = rand.nextInt(22);
            //jesli owoc chce sie pojawic w miejscu gdzie jest cialo weza - przeprowadzamy dodatkowe losowanie
            for(int i = 0; i<snakelenght; i++) {
                if (snakex[i] == fruitxpos && snakey[i] == fruitypos)
                {
                    fruitxpos = rand.nextInt(32);
                    fruitypos = rand.nextInt(22);
                }
            }

            //zwiekszanie predkosci weza z kazdym zebranym owocem
            if(delay!= 0) {
                delay = delay - 5;
                speed++;
                time.restart();
            }
        }
        fruit.paintIcon(this,g,fruitx[fruitxpos],fruity[fruitypos]); //narysowanie nowej znajdzki

        //jesli glowa weza wejdzie na czesc swojego ciala to game over
        for(int b = 1; b<snakelenght; b++)
        {
            if(snakex[b] == snakex[0] && snakey[b] == snakey[0])
            {
                right = false;
                left = false;
                up = false;
                down = false;
                g.drawString("GAME OVER" , 310, 380);
                g.drawString("ESC TO RESTART" , 310, 400);

            }
        }
    }

    /**
     * Metoda ktora okresla co sie dzieje po nacisnieciu danego przycisku
     * @param e Obiekt KayEvent ktory sluzy do rozpoznawania klawiszy
     */
    public void keyPressed(KeyEvent e)
    {
        //klawisze kierunkowe
        if(e.getKeyCode() == KeyEvent.VK_RIGHT) //w prawo
        {
            moves = true;
            //sprawia ze waz nie skreca "w siebie"
            if(!left)
            {
                right = true;
                left = false;
            }else
            {
                right = false;
                left = true;
            }
            up = false;
            down = false;
        }
        if(e.getKeyCode() == KeyEvent.VK_LEFT) //w lewo
        {
            moves = true;
            //sprawia ze waz nie skreca "w siebie"

            if(!right)
            {
                left = true;
                right = false;
            }else
            {
                left = false;
                right = true;
            }

            up = false;
            down = false;
        }

        if(e.getKeyCode() == KeyEvent.VK_UP) //w gore
        {
            moves = true;
            //sprawia ze waz nie skreca "w siebie"

            if(!down)
            {
                up = true;
                down = false;
            }else
            {
                up = false;
                down = true;
            }

            left = false;
            right = false;

        }

        if(e.getKeyCode() == KeyEvent.VK_DOWN) //w dol
        {
            moves = true;
            //sprawia ze waz nie skreca "w siebie"

            if(!up)
            {
                down = true;
                up = false;
            }else
            {
                down = false;
                up = true;
            }

            left = false;
            right = false;
        }

        if(e.getKeyCode() == KeyEvent.VK_SPACE) //spacja to pauza - raczej nie uzywane w grze
        {
            if(time.isRunning()) {
                time.stop();
            }else{
                time.start();
            }
        }
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE) //esc to restart
        {
            moves = false;
            down = false;
            up = false;
            left = false;
            right = true;
            snakelenght = 3;
            score = 0;
            speed = 0;
            delay = 150;
            time.restart();
        }
        repaint(); //wczytuje od nowa tekstury
    }

    /**
     * Metoda ktora wykonuje sie kiedy nastapi jakakolwiek akcja
     * @param e Obiekt ActionEvent ktory sluzy do rozpoznania akcji
     */
    public void actionPerformed(ActionEvent e)
    {
        time.start();
        //logika poruszania sie weza :
        //kazda komorka tablicy, zamienia sie miejscami z poprzedzajaca, az do glowy, ktora porusza sie o odleglosc jednego klocka w danym kierunku
        if(right) //jesli snake rusze sie w prawo to
        {
            //poruszanie sie snejka w prawo
            for(int r = snakelenght -1 ; r >= 0; r--)
            {
                snakey[r+1] = snakey[r]; //pozycje y przepisujemy
            }
            for(int r = snakelenght; r>=0; r--)
            {
                if(r==0)
                {
                    snakex[r] = snakex[r] + snakebodysize; //przesuwamy glowe w prawo o dlugosc klocka
                }
                else
                {
                    snakex[r] = snakex[r-1]; //reszte wartosci przepisujemy
                }
                //jesli wychodzi za prawa granice
                if(snakex[r] > 815)
                {
                    snakex[r] = 15;
                }
            }
            repaint();
        }
        if(left)
        {
            //poruszanie sie snejka w lewo
            for(int r = snakelenght -1 ; r >= 0; r--)
            {
                snakey[r+1] = snakey[r]; //pozycje y przepisujemy
            }
            for(int r = snakelenght; r>=0; r--)
            {
                if(r==0)
                {
                    snakex[r] = snakex[r] - snakebodysize; //przesuwamy glowe w lewo
                }
                else
                {
                    snakex[r] = snakex[r-1]; //reszte przepisujemy
                }
                //jesli wychodzi za lewa granice
                if(snakex[r] < 15)
                {
                    snakex[r] = 815;
                }
            }
            repaint();
        }
        if(up)
        {
            for(int r = snakelenght -1 ; r >= 0; r--)
            {
                snakex[r+1] = snakex[r];
            }
            for(int r = snakelenght; r>=0; r--)
            {
                if(r==0)
                {
                    snakey[r] = snakey[r] - snakebodysize;
                }
                else
                {
                    snakey[r] = snakey[r-1];
                }
                //jesli wychodzi za gorna granice -- rozne wartosci nwm czemu
                if(snakey[r] < 130)
                {
                    snakey[r] = 680;
                }
            }
            repaint();
        }
        if(down)
        {
            for(int r = snakelenght -1 ; r >= 0; r--)
            {
                snakex[r+1] = snakex[r];
            }
            for(int r = snakelenght; r>=0; r--)
            {
                if(r==0)
                {
                    snakey[r] = snakey[r] + snakebodysize;
                }
                else
                {
                    snakey[r] = snakey[r-1];
                }
                //jesli wychodzi za dolna granice
                if(snakey[r] > 680)
                {
                    snakey[r] = 130;
                }
            }
            repaint();
        }
    }
}
