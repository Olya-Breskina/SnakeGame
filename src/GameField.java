import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GameField extends JPanel implements ActionListener {
    private static final int SIZE = 320;// размер поля
    private static final int DOT_SIZE = 16;// одна ячейка змейки и размер яблочка
    private static final int WIDTH = 21;
    private static final int HEIGHT = 21;
    private static final int ALL_DOTS = 400;// количество игровых единиц на поле
    //позиция по Х и У змейки по мах, змейка может занимать все игровое поле
    private final int[] x = new int[ALL_DOTS];
    private final int[] y = new int[ALL_DOTS];
    public int eatenApples = 0;// количество съеденных яблок
    // направление змейки
    private boolean left = false;
    private boolean right = true;// двигается справа налево
    private boolean up = false;
    private boolean down = false;
    private Image dot;// игровая ячейка зеи
    private Image apple;
    //позиция по Х и У яблока
    private int appleX;
    private int appleY;
    private int dots;// размер змейки
    private Timer timer;// таймер
    private boolean inGame = true;// положение в игре

    public void start() {
        setBackground(Color.black);// цвет поля
        eatenApples=0;
        loadImages();
        initGame();
        inGame = true;
        FieldKeyListener listener = new FieldKeyListener();
        this.setFocusable(true);
        this.requestFocus();// перехват клавиатуры игрой
        this.addKeyListener(listener);
    }
    public void stop() {//
        setBackground(Color.blue);
        inGame = false;
        if (timer != null && timer.isRunning()) {
            timer.stop();
        }
    }
    public void initGame() {
        // начало игры
        dots = 2;// начальный размер змейки
        for (int i = 0; i < dots; i++) {
            x[i] = 48 - i * DOT_SIZE;
            y[i] = 48;
        }
        if (timer !=null && timer.isRunning()) {
            timer.restart();
        } else {
            timer = new Timer(250, this);// частота обновления змейки на поле
            timer.start();//запуск таймера
        }
        createApple();
    }
    public void createApple() {
        // яблоко может быть под змейкой(((
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
        } else {
            stop();
            drawGameOver(g);
        }
        //сетка
        for (int i = 0; i < WIDTH * DOT_SIZE; i = i + DOT_SIZE) {
            g.setColor(Color.DARK_GRAY);
            g.drawLine(i, 0, i, HEIGHT * DOT_SIZE);
        }

        for (int z = 0; z < HEIGHT * DOT_SIZE; z += DOT_SIZE) {
            g.setColor(Color.DARK_GRAY);
            g.drawLine(0, z, WIDTH * DOT_SIZE, z);
        }
    }

    private void drawGameOver(Graphics g) {
        String str = "Game Over";
        g.setColor(Color.white);
        g.setFont(new Font("arial",Font.PLAIN,20));
        g.drawString(str, 125, SIZE / 3);
        String str1="счет: "+eatenApples;
        g.setColor(Color.white);
        g.setFont(new Font("arial",Font.PLAIN,15));
        g.drawString(str1, 125, SIZE / 2);
    }
    public void checkApple() {
        //встреча с яблоком
        if (x[0] == appleX && y[0] == appleY) {
            dots++;
            eatenApples++;
            createApple();
        }
    }

    public void checkCollisions() {
        //укусила сама себя, игра закончена
        for (int i = dots; i > 0; i--) {
            if (i > 4 && x[0] == x[i] && y[0] == y[i]) {
                inGame = false;
                break;
            }
        }
        // встреча с бортами
        if ((x[0] > SIZE) || (x[0] < 0) || (y[0] > SIZE) || (y[0] < 0)) inGame = false;
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

    class FieldKeyListener extends KeyAdapter {
        //обработка нажатия клавиш
        @Override
        public void keyPressed(KeyEvent e) {
            super.keyPressed(e);
            int key = e.getKeyCode();//  какая клавиша нажата
            if (key == KeyEvent.VK_LEFT && !right) {
                left = true;
                up = false;
                down = false;
            }
            if (key == KeyEvent.VK_RIGHT && !left) {
                right = true;
                up = false;
                down = false;
            }

            if (key == KeyEvent.VK_UP && !down) {
                right = false;
                up = true;
                left = false;
            }
            if (key == KeyEvent.VK_DOWN && !up) {
                right = false;
                down = true;
                left = false;
            }
        }
    }
}
