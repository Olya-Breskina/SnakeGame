import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class GameField extends JPanel implements ActionListener {
    private final int SIZE = 320;// размер поля
    private final int DOT_SIZE = 16;// одна ячейка змейки и размер яблочка
    private final int ALL_DOTS = 400;// количество игровых единиц на поле
    //позиция по Х и У змейки по мах, змейка может занимать все игровое поле
    private final int[] x = new int[ALL_DOTS];
    private final int[] y = new int[ALL_DOTS];
    // направление змейки
    private final boolean left = false;
    private final boolean right = true;// двигается справа налево
    private final boolean up = false;
    private final boolean down = false;
    private Image dot;// игровая ячейка зеи
    private Image apple;
    //позиция по Х и У яблока
    private int appleX;
    private int appleY;
    private int dots;// размер змейки
    private Timer timer;// таймер
    private boolean inGame = true;// положение в игре

    public GameField() {
        setBackground(Color.black);// цвет поля
        loadImages();
        initGame();
    }

    public void initGame() {
        // начало игры
        dots = 3;// начальный размер змейки
        for (int i = 0; i < dots; i++) {
            x[i] = 48 - i * DOT_SIZE;
            y[i] = 48;
        }
        timer = new Timer(250, this);// частота обновления змейки на поле
        timer.start();//запуск таймера
        createApple();
    }

    public void createApple() {
        appleX = new Random().nextInt(20) * DOT_SIZE;
        appleY = new Random().nextInt(20) * DOT_SIZE;
    }

    public void loadImages() {
        // метод для загрузки картинов
        ImageIcon iia = new ImageIcon("apple.png");
        apple = iia.getImage();// инициализация яблока
        ImageIcon iid = new ImageIcon("dot.png");
        dot = iid.getImage();// инициализация блока змейки
    }

    public void move() {
        // движение змейки
        for (int i = dots; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        if (left) x[0] -= DOT_SIZE;
        if (right) x[0] += DOT_SIZE;
        if (up) y[0] -= DOT_SIZE;
        if (down) y[0] += DOT_SIZE;

    }

    @Override
    protected void paintComponent(Graphics g) {
        //перерисовка змейки  и яблока
        super.paintComponent(g);
        if (inGame) {
            g.drawImage(apple, appleX, appleY, this);
            for (int i = 0; i < dots; i++) {
                g.drawImage(dot, x[i], y[i], this);
            }
        }
    }

    public void checkApple() {
        //встреча с яблоком
        if (x[0] == appleX && y[0] == appleY) {
            dots++;
            createApple();
        }
    }

    public void checkCollisions() {
        //укусила сама себя
        for (int i = dots; i > 0; i--) {
            if (i > 4 && x[0] == x[i] && y[0] == y[i]) {
                inGame = false;
                break;
            }
        }
        // встреча с бортами
        if (x[0] > SIZE)    inGame = false;
        if (x[0] < 0)       inGame = false;
        if (y[0] > SIZE)    inGame = false;
        if (y[0] < 0)       inGame = false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame) {
            checkApple();
            checkCollisions();
            move();
        }
        repaint();
    }
}
