import javax.swing.*;
import java.awt.*;

public class MoveBall {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Move the Ball");
        BallPanel panel = new BallPanel();

        // Create buttons
        JButton btnLeft = new JButton("Left");
        JButton btnRight = new JButton("Right");
        JButton btnUp = new JButton("Up");
        JButton btnDown = new JButton("Down");

        // Button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnLeft);
        buttonPanel.add(btnRight);
        buttonPanel.add(btnUp);
        buttonPanel.add(btnDown);

        // Button actions
        btnLeft.addActionListener(e -> panel.moveLeft());
        btnRight.addActionListener(e -> panel.moveRight());
        btnUp.addActionListener(e -> panel.moveUp());
        btnDown.addActionListener(e -> panel.moveDown());

        // Frame layout
        frame.setLayout(new BorderLayout());
        frame.add(panel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
