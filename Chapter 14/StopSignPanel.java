import javax.swing.*;
import java.awt.*;
import java.awt.geom.Path2D;

public class StopSignPanel extends JPanel {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // Anti-aliasing for smoother shapes
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Define center and size
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        int radius = 100;

        // Create octagon
        Path2D octagon = new Path2D.Double();
        for (int i = 0; i < 8; i++) {
            double angle = Math.toRadians(22.5 + i * 45); // 360 / 8 = 45° per side
            int x = (int) (centerX + radius * Math.cos(angle));
            int y = (int) (centerY + radius * Math.sin(angle));
            if (i == 0)
                octagon.moveTo(x, y);
            else
                octagon.lineTo(x, y);
        }
        octagon.closePath();

        // Fill octagon in red
        g2.setColor(Color.RED);
        g2.fill(octagon);

        // Draw "STOP" in white
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("SansSerif", Font.BOLD, 48));
        FontMetrics fm = g2.getFontMetrics();
        String text = "STOP";
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getAscent();
        g2.drawString(text, centerX - textWidth / 2, centerY + textHeight / 4);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("STOP Sign");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.add(new StopSignPanel());
        frame.setVisible(true);
    }
}
