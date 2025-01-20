package com.example.firstProject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class RandomDoubles extends JFrame implements ActionListener {
    private JTextField textField1;
    private JTextField textField2;
    private JLabel resultLabel;
    private JLabel timeLabel;
    private JButton generateButton;
    private JButton clearButton;
    private JLabel errorLabel;

    public RandomDoubles() {
        setTitle("Random Doubles");
        setSize(570, 275);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(20, 20));
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel label1 = new JLabel("Enter minimum number:");
        textField1 = new JTextField(10);
        JLabel label2 = new JLabel("Enter maximum number:");
        textField2 = new JTextField(10);

        textField1.addActionListener(e -> textField2.requestFocus());
        textField2.addActionListener(e -> generateButton.doClick());

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        inputPanel.add(label1, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        inputPanel.add(textField1, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        inputPanel.add(label2, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.WEST;
        inputPanel.add(textField2, gbc);

        JPanel buttonPanel = new JPanel();
        generateButton = new JButton("Generate Random Number");
        generateButton.addActionListener(this);
        clearButton = new JButton("Clear");
        clearButton.addActionListener(this);
        buttonPanel.add(generateButton);
        buttonPanel.add(clearButton);

        errorLabel = new JLabel("");
        errorLabel.setForeground(Color.RED);
        errorLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel outputPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        resultLabel = new JLabel("Random Number: ");
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);
        timeLabel = new JLabel("Computation Time: ");
        timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        outputPanel.add(resultLabel);
        outputPanel.add(timeLabel);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.add(errorLabel);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(buttonPanel);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(outputPanel);

        add(inputPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == generateButton) {
            try {
                float min = Float.parseFloat(textField1.getText());
                float max = Float.parseFloat(textField2.getText());

                if (min > max) {
                    errorLabel.setText("Error: Minimum number cannot be greater than maximum number.");
                    resultLabel.setText("Random Number: ");
                    timeLabel.setText("Computation Time: ");
                    return;
                }

                long startTime = System.nanoTime();
                Random random = new Random();
                float randomNumber = min + random.nextFloat() * (max - min);
                long endTime = System.nanoTime();

                double computationTimeMillis = (endTime - startTime) / 1e6;

                resultLabel.setText("Random Number: " + randomNumber);
                timeLabel.setText(String.format("Computation Time: %.3f ms", computationTimeMillis));
                errorLabel.setText("");
            } catch (NumberFormatException ex) {
                errorLabel.setText("Please enter valid numbers.");
            }
        } else if (e.getSource() == clearButton) {
            textField1.setText("");
            textField2.setText("");
            resultLabel.setText("Random Number: ");
            timeLabel.setText("Computation Time: ");
            errorLabel.setText("");
            textField1.requestFocus();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RandomDoubles frame = new RandomDoubles();
            frame.setVisible(true);
        });
    }
}
