import javax.swing.*;

public class Window extends JFrame {
    public static final int WINDOW_WIDTH = 750;
    public static final int WINDOW_HEIGHT = 500;

    public static void main(String[] args) {
        new Window();
    }

    public Window () {
        this.add(new HospitalScene(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT));
        this.setLayout(null);
        this.setResizable(false);
        this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}
