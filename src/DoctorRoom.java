import java.awt.*;

public class DoctorRoom {
    private int x, y, width, height;
    private boolean busy;

    public DoctorRoom(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void paint (Graphics graphics) {
        graphics.setColor(Color.RED);
        graphics.fillRect(
                this.x,
                this.y,
                this.width,
                this.height
        );
        if (this.busy) {
            graphics.setColor(Color.BLACK);
            graphics.setFont(new Font("Tahoma", Font.BOLD, 24));
            graphics.drawString("Busy", this.x, this.y);
        }
    }

    public int getX() {
        return x;
    }

    public int getWidth() {
        return width;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setBusy (boolean busy) {
        this.busy = busy;
    }
}
