import javax.swing.*;
import java.awt.*;

public class GameField extends JPanel {
    private final int SIZE = 320;// размер поля
    private final int DOT_SIZE = 16;// одна ячейка змейки и размер яблочка
    private final int ALL_DOTS = 400;// количество игровых единиц на поле
    private Image dot;// игровая ячейка зеи
    private Image apple;
    //позиция по Х и У яблока
    private int appleX;
    private  int appleY;

    //позиция по Х и У змейки по мах, змейка может занимать все игровое поле
    private int[] x = new int[ALL_DOTS];
    private int[] y = new int[ALL_DOTS];

    private int dots;// размер змейки
    private Timer timer;// таймер
    // направление змейки
    private boolean left = false;
    private boolean right = true;
    private boolean up = false;
    private boolean down = false;
    private boolean inGame = true;

    public GameField(){
        setBackground(Color.black);// цвет поля
        loadImages();
    }
    public void loadImages(){
        // метод для загрузки картинов
        ImageIcon iia = new ImageIcon("apple.png");
        apple = iia.getImage();// инициализация яблока
        ImageIcon iid = new ImageIcon("dot.png");
        dot = iid.getImage();// инициализация блока змейки
    }
}
