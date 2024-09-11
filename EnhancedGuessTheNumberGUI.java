import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class EnhancedGuessTheNumberGUI extends JFrame {
    private JTextField guessField;
    private JLabel feedbackLabel;
    private JButton submitButton;
    private JButton resetButton;
    private JComboBox<String> difficultyComboBox;
    private int targetNumber;
    private int attempts;
    private int maxNumber;

    public EnhancedGuessTheNumberGUI() {
        // Set up the window (JFrame)
        setTitle("Guess the Number Game");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(5, 1));

        // Create UI components
        JLabel instructionLabel = new JLabel("Guess a number:", SwingConstants.CENTER);
        guessField = new JTextField();
        feedbackLabel = new JLabel("Select a difficulty and enter your guess.", SwingConstants.CENTER);
        feedbackLabel.setOpaque(true);  // Allows background color to be changed

        submitButton = new JButton("Submit");
        resetButton = new JButton("Reset");

        // Create a dropdown for difficulty selection
        String[] difficulties = {"Easy (1-50)", "Medium (1-100)", "Hard (1-200)"};
        difficultyComboBox = new JComboBox<>(difficulties);
        difficultyComboBox.setSelectedIndex(1);  // Set default difficulty to Medium

        // Set initial target number based on the default difficulty
        setDifficulty("Medium (1-100)");

        // Add action listeners for buttons
        submitButton.addActionListener(new SubmitButtonListener());
        resetButton.addActionListener(new ResetButtonListener());

        // Add components to the frame
        add(instructionLabel);
        add(difficultyComboBox);
        add(guessField);
        add(feedbackLabel);
        add(submitButton);
        add(resetButton);

        // Disable reset button at the start
        resetButton.setEnabled(false);

        // Make the window visible
        setVisible(true);
    }

    // ActionListener for the Submit button
    private class SubmitButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int guess = Integer.parseInt(guessField.getText());
                attempts++;

                if (guess < targetNumber) {
                    feedbackLabel.setText("Too low! Try again.");
                    feedbackLabel.setBackground(Color.YELLOW);
                } else if (guess > targetNumber) {
                    feedbackLabel.setText("Too high! Try again.");
                    feedbackLabel.setBackground(Color.ORANGE);
                } else {
                    feedbackLabel.setText("Correct! You guessed it in " + attempts + " attempts.");
                    feedbackLabel.setBackground(Color.GREEN);
                    submitButton.setEnabled(false);  // Disable the submit button after a correct guess
                    resetButton.setEnabled(true);  // Enable reset button
                    setTitle("Victory! You guessed the number!");
                }
            } catch (NumberFormatException ex) {
                feedbackLabel.setText("Please enter a valid number.");
                feedbackLabel.setBackground(Color.RED);
            }
        }
    }

    // ActionListener for the Reset button
    private class ResetButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Reset game state
            setDifficulty((String) difficultyComboBox.getSelectedItem());
            guessField.setText("");
            feedbackLabel.setText("Game reset. Enter your guess.");
            feedbackLabel.setBackground(null);  // Reset background color
            attempts = 0;
            submitButton.setEnabled(true);  // Enable submit button
            resetButton.setEnabled(false);  // Disable reset button
            setTitle("Guess the Number Game");
        }
    }

    // Method to set the difficulty level and adjust the number range
    private void setDifficulty(String difficulty) {
        Random random = new Random();
        switch (difficulty) {
            case "Easy (1-50)":
                maxNumber = 50;
                break;
            case "Medium (1-100)":
                maxNumber = 100;
                break;
            case "Hard (1-200)":
                maxNumber = 200;
                break;
        }
        targetNumber = random.nextInt(maxNumber) + 1;
    }

    // Main method to run the game
    public static void main(String[] args) {
        new EnhancedGuessTheNumberGUI();
    }
}
