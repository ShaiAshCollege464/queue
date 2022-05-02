import java.awt.*;

public class Patient {
    private int x, y, width, height, number;
    private static int count = 0;
    private boolean movingTowardsDoctorRoom;
    private Color color;

    public Patient(int x, int y, int width, int height, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.number = count;
        this.color = color;
        count++;
    }

    public int getX() {
        return x;
    }

    public int getWidth() {
        return width;
    }

    public void paint (Graphics graphics) {
        graphics.setColor(this.color);
        graphics.fillOval(this.x, this.y, this.width, this.height);
        graphics.setFont(new Font("Tahoma", Font.BOLD, 12));
        graphics.drawString(String.valueOf(this.number), this.x, this.y);
    }

    public void moveTowards (Patient nextInLine) {
        if (nextInLine != null && !nextInLine.movingTowardsDoctorRoom) {
            if (Math.abs(nextInLine.getX() - this.x) > 60) {
                this.x--;
            }
        }
    }

    public void moveTowards () {
        if (this.x > 10) {
            this.x--;
        }
    }

    public void moveTowards (DoctorRoom doctorRoom) {
        this.movingTowardsDoctorRoom = true;
        if (this.x > doctorRoom.getX()) {
            this.x--;
        } else if (this.x < doctorRoom.getX()) {
            this.x++;
        }
        if (this.y > doctorRoom.getY()) {
            this.y--;
        } else if (this.y < doctorRoom.getY()) {
            this.y++;
        }
    }

    public boolean reachedToDoctorRoom (DoctorRoom doctorRoom) {
        boolean reached = false;
        if (this.x == doctorRoom.getX() && this.y == doctorRoom.getY()) {
            reached = true;
        }
        return reached;

    }

    public boolean isMovingTowardsDoctorRoom() {
        return movingTowardsDoctorRoom;
    }
}
