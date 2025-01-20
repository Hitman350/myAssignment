package com.example.firstProject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SumsOfMultiples extends JFrame {
    private JTextField inputField;
    private JLabel resultLabel;
    private JLabel timeLabel;

    public SumsOfMultiples() {
        setTitle("Sums of Multiples");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel inputLabel = new JLabel("Enter a number:");
        inputLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        inputField = new JTextField(10);

        JButton calculateButton = new JButton("Calculate");
        calculateButton.addActionListener(new CalculateButtonListener());
        calculateButton.setFont(new Font("Arial", Font.BOLD, 16));
        calculateButton.setBackground(new Color(100, 149, 237));
        calculateButton.setForeground(Color.WHITE);

        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(new ClearButtonListener());
        clearButton.setFont(new Font("Arial", Font.BOLD, 16));
        clearButton.setBackground(new Color(220, 20, 60));
        clearButton.setForeground(Color.WHITE);

        resultLabel = new JLabel("Result: ");
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);

        timeLabel = new JLabel("Computation Time: ");
        timeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(inputLabel, gbc);

        gbc.gridx = 1;
        panel.add(inputField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        panel.add(calculateButton, gbc);

        gbc.gridy = 2;
        panel.add(clearButton, gbc);

        gbc.gridy = 3;
        panel.add(resultLabel, gbc);

        gbc.gridy = 4;
        panel.add(timeLabel, gbc);

        add(panel);
    }

    private class CalculateButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                long startTime = System.nanoTime();

                int number = Integer.parseInt(inputField.getText());
                if (number < 0) {
                    resultLabel.setForeground(Color.RED);
                    resultLabel.setText("Invalid input: Enter a positive number.");
                } else {
                    int result = calculateSumsOfMultiples(number);
                    resultLabel.setForeground(Color.BLACK);
                    resultLabel.setText("Result: " + result);
                }

                long endTime = System.nanoTime();
                long duration = (endTime - startTime) / 1000; 
                timeLabel.setForeground(Color.BLACK);
                timeLabel.setText("Computation Time: " + duration + " µs");

            } catch (NumberFormatException ex) {
                resultLabel.setForeground(Color.RED);
                resultLabel.setText("Invalid input: Please enter a valid integer.");
            }
        }

        private int calculateSumsOfMultiples(int n) {
            int m = n / 3;
            int k = n / 5;
            int l = n / 15;

            int sum = (3*m*(m + 1))/2 + (5*k*(k+1))/2 - (15*l*(l+1))/2; 

            return sum;
        }
    }

    private class ClearButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            inputField.setText("");
            resultLabel.setText("Result: ");
            timeLabel.setText("Computation Time: ");
            resultLabel.setForeground(Color.BLACK);
            timeLabel.setForeground(Color.BLACK);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SumsOfMultiples calculator = new SumsOfMultiples();
            calculator.setVisible(true);
        });
    }
}
