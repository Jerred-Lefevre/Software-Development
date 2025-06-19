import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Exercise16_1 extends JFrame {
    private JLabel messageLabel = new JLabel("Programming is fun");
    private JPanel colorPanel = new JPanel();
    private JPanel movePanel = new JPanel();
    private JPanel messagePanel = new JPanel(null); // for manual layout
    private int x = 0; // initial x position
    private final int y = 50; // fixed y position

    public Exercise16_1() {
        setTitle("Exercise 16-1");
        setSize(400, 200);
        setLayout(new BorderLayout());

        // Set fixed size for message panel
        messagePanel.setPreferredSize(new Dimension(400, 100));
        add(messagePanel, BorderLayout.CENTER);

        // Set up message label
        messageLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        messageLabel.setSize(messageLabel.getPreferredSize()); // Sets correct width/height

        // Center the label
        x = (400 - messageLabel.getWidth()) / 2;
        messageLabel.setLocation(x, y);
        messagePanel.add(messageLabel);

        // Color radio buttons
        JRadioButton red = new JRadioButton("Red");
        JRadioButton yellow = new JRadioButton("Yellow");
        JRadioButton black = new JRadioButton("Black");
        JRadioButton orange = new JRadioButton("Orange");
        JRadioButton green = new JRadioButton("Green");

        ButtonGroup group = new ButtonGroup();
        group.add(red);
        group.add(yellow);
        group.add(black);
        group.add(orange);
        group.add(green);

        colorPanel.add(red);
        colorPanel.add(yellow);
        colorPanel.add(black);
        colorPanel.add(orange);
        colorPanel.add(green);
        add(colorPanel, BorderLayout.NORTH);

        red.addActionListener(e -> messageLabel.setForeground(Color.RED));
        yellow.addActionListener(e -> messageLabel.setForeground(Color.YELLOW));
        black.addActionListener(e -> messageLabel.setForeground(Color.BLACK));
        orange.addActionListener(e -> messageLabel.setForeground(Color.ORANGE));
        green.addActionListener(e -> messageLabel.setForeground(Color.GREEN));

        // Arrow buttons
        JButton leftButton = new JButton("←");
        JButton rightButton = new JButton("→");
        movePanel.add(leftButton);
        movePanel.add(rightButton);
        add(movePanel, BorderLayout.SOUTH);

        // Move text left
        leftButton.addActionListener(e -> {
            if (x > 10) {
                x -= 10;
                messageLabel.setLocation(x, y);
            }
        });

        // Move text right
        rightButton.addActionListener(e -> {
            if (x + messageLabel.getWidth() < messagePanel.getWidth() - 10) {
                x += 10;
                messageLabel.setLocation(x, y);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Exercise16_1 frame = new Exercise16_1();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}
