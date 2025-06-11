import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

public class PentagonAnimation extends JPanel {
    private final Polygon pentagon;
    private final Point2D.Double[] points;  // Pentagon vertices
    private int currentEdge = 0;
    private double t = 0;  // Parameter from 0 to 1 for interpolation along current edge
    private final double step = 0.01;

    private boolean running = true;

    public PentagonAnimation() {
        // Define pentagon vertices
        pentagon = new Polygon();
        int centerX = 200, centerY = 200, radius = 100;
        for (int i = 0; i < 5; i++) {
            double angle = Math.toRadians(90 + i * 72);
            int x = centerX + (int)(radius * Math.cos(angle));
            int y = centerY + (int)(radius * Math.sin(angle));
            pentagon.addPoint(x, y);
        }

        points = new Point2D.Double[5];
        for (int i = 0; i < 5; i++) {
            points[i] = new Point2D.Double(pentagon.xpoints[i], pentagon.ypoints[i]);
        }

        // Timer for animation updates (~60 FPS)
        Timer timer = new Timer(16, e -> {
            if (running) {
                moveAlongEdge();
                repaint();
            }
        });
        timer.start();

        // Mouse listener to pause/resume on left/right click
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    running = false; // Pause
                } else if (SwingUtilities.isRightMouseButton(e)) {
                    running = true;  // Resume
                }
            }
        });

        setPreferredSize(new Dimension(400, 400));
        setBackground(Color.WHITE);
    }

    private void moveAlongEdge() {
        t += step;
        if (t >= 1) {
            t = 0;
            currentEdge = (currentEdge + 1) % points.length;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();

        // Draw pentagon outline
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(2));
        g2.drawPolygon(pentagon);

        // Calculate current position of rectangle on edge by linear interpolation
        Point2D.Double p1 = points[currentEdge];
        Point2D.Double p2 = points[(currentEdge + 1) % points.length];
        double x = p1.x + (p2.x - p1.x) * t;
        double y = p1.y + (p2.y - p1.y) * t;

        // Calculate opacity (alpha) oscillates from 0.2 to 1.0 along each edge
        float alpha = (float)(0.2 + 0.8 * Math.abs(Math.sin(Math.PI * t)));

        // Draw the rectangle with changing opacity
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g2.setColor(Color.RED);

        int rectWidth = 40;
        int rectHeight = 20;

        // Center rectangle on (x,y)
        int rectX = (int)(x - rectWidth / 2);
        int rectY = (int)(y - rectHeight / 2);
        g2.fillRect(rectX, rectY, rectWidth, rectHeight);

        g2.dispose();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Rectangle on Pentagon Animation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new PentagonAnimation());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
