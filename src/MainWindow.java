import javax.swing.*;

public class MainWindow extends JFrame {
    /*
   Окно JFrame
   В библиотеке Swing описан класс JFrame, представляющий собой окно с рамкой и строкой заголовка
   (с кнопками «Свернуть», «Во весь экран» и «Закрыть»).
   Оно может изменять размеры и перемещаться по экрану.
     */
    public MainWindow(){
        setTitle("Змейка");//название
        setSize(320,345);//  размеры окна
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//действие с окном при нажатие на крестик
        setLocation(400,200);// располодение на экране
        add(new GameField());//игровое поле
        setVisible(true);// видимосто окна
    }
    public static void main(String[] args){
        MainWindow mw=new MainWindow();
    }
}
