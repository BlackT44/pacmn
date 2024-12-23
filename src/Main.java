import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Pac Man");
        frame.setVisible(true);
        frame.setSize(400, 500);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        frame.add(panel);

        JPanel innerPanel = new JPanel();
        innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.Y_AXIS));
        panel.add(innerPanel);

        JLabel titleLabel = new JLabel("Pacman");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 80));
        innerPanel.add(titleLabel);

        JButton startButton = new JButton("Start Game");
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        startButton.addActionListener(e -> showDifficultyDialog(frame));
        innerPanel.add(startButton);

        JButton exitButton = new JButton("Exit");
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitButton.addActionListener(e -> System.exit(0));
        innerPanel.add(exitButton);
    }

    private static void showDifficultyDialog(JFrame parentFrame) {
        JDialog dialog = new JDialog(parentFrame, "Select Difficulty", true);
        dialog.setSize(300, 200);
        dialog.setLocationRelativeTo(parentFrame);

        JButton easyButton = new JButton("Easy");
        easyButton.addActionListener(e -> startGame(parentFrame, 1));
        JButton normalButton = new JButton("Normal");
        normalButton.addActionListener(e -> startGame(parentFrame, 2));
        JButton hardButton = new JButton("Hard");
        hardButton.addActionListener(e -> startGame(parentFrame, 3));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 1));
        buttonPanel.add(easyButton);
        buttonPanel.add(normalButton);
        buttonPanel.add(hardButton);

        dialog.add(buttonPanel);
        dialog.setVisible(true);
    }

    private static void startGame(JFrame parentFrame, int difficulty) {
        parentFrame.dispose();
        JFrame gameFrame = new JFrame("PacMan - Playing");
        Model game = new Model(difficulty);
        gameFrame.add(game);
        gameFrame.setSize(400, 500);
        gameFrame.setLocationRelativeTo(null);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setVisible(true);
    }
}


