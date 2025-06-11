import javax.swing.*;
import java.awt.*;

public class BallPanel extends JPanel {
    private int x = 180; // Initial x position
    private int y = 120; // Initial y position
    private final int radius = 20;
    private final int step = 10;

    public BallPanel() {
        setBackground(Color.WHITE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Fill the ball with light gray
        g2.setColor(Color.WHITE);
        g2.fillOval(x, y, radius * 2, radius * 2);

        // Draw the black outline
        g2.setColor(Color.BLACK);
        g2.drawOval(x, y, radius * 2, radius * 2);
    }

    public void moveLeft() {
        if (x - step >= 0) {
            x -= step;
            repaint();
        }
    }

    public void moveRight() {
        if (x + 2 * radius + step <= getWidth()) {
            x += step;
            repaint();
        }
    }

    public void moveUp() {
        if (y - step >= 0) {
            y -= step;
            repaint();
        }
    }

    public void moveDown() {
        if (y + 2 * radius + step <= getHeight()) {
            y += step;
            repaint();
        }
    }
}
