import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CountdownTimer extends JFrame {
    private JTextField timerField = new JTextField("Enter seconds", 10);
    private Timer timer;
    private int remainingSeconds;
    private boolean clearedPlaceholder = false;

    public CountdownTimer() {
        super("Exercise16_21");

        timerField.setFont(new Font("SansSerif", Font.BOLD, 48));
        timerField.setHorizontalAlignment(JTextField.CENTER);

        setLayout(new BorderLayout());
        add(timerField, BorderLayout.CENTER);

        // Clear placeholder on first focus
        timerField.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (!clearedPlaceholder) {
                    timerField.setText("");
                    clearedPlaceholder = true;
                }
            }
        });

        timerField.addActionListener(e -> {
            String text = timerField.getText().trim();
            System.out.println("Input text: '" + text + "'");  // Debug line

            if (timer != null && timer.isRunning()) {
                // Stop running timer and reset for new input
                timer.stop();
                timerField.setEditable(true);
                timerField.setText("");
                clearedPlaceholder = false;
                timerField.requestFocus();
                return;
            }

            // Only parse if input is digits
            if (!text.matches("\\d+")) {
                // Clear field if input is not a positive integer
                timerField.setText("");
                clearedPlaceholder = false;
                return;
            }

            remainingSeconds = Integer.parseInt(text);
            if (remainingSeconds <= 0) {
                timerField.setText("Enter positive number");
                clearedPlaceholder = false;
                return;
            }
            startCountdown();
        });

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 150);
        setLocationRelativeTo(null);
    }

    private void startCountdown() {
        timerField.setEditable(false);
        timerField.setText(String.valueOf(remainingSeconds));

        timer = new Timer(1000, e -> {
            remainingSeconds--;
            if (remainingSeconds >= 0) {
                timerField.setText(String.valueOf(remainingSeconds));
            } else {
                timer.stop();
                Toolkit.getDefaultToolkit().beep();
                timerField.setText("Time's up! Press Enter to restart");
                timerField.setEditable(true);
                clearedPlaceholder = false;
            }
        });
        timer.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CountdownTimer frame = new CountdownTimer();
            frame.setVisible(true);
        });
    }
}
