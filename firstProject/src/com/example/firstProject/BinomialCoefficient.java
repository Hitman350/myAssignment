package com.example.firstProject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BinomialCoefficient extends JFrame {
    private JTextField nField;
    private JTextField kField;
    private JLabel resultLabel;
    private JLabel timeLabel;

    public BinomialCoefficient() {
        setTitle("Binomial Coefficient");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel nLabel = new JLabel("Enter n:");
        nLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        nField = new JTextField(10);

        JLabel kLabel = new JLabel("Enter k:");
        kLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        kField = new JTextField(10);

        JButton calculateButton = new JButton("Calculate");
        calculateButton.addActionListener(new CalculateButtonListener());

        resultLabel = new JLabel("Result: ");
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);

        timeLabel = new JLabel("Computation time: ");
        timeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(new ClearButtonListener());

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(nLabel, gbc);

        gbc.gridx = 1;
        panel.add(nField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(kLabel, gbc);

        gbc.gridx = 1;
        panel.add(kField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        panel.add(calculateButton, gbc);

        gbc.gridy = 3;
        panel.add(resultLabel, gbc);

        gbc.gridy = 4;
        panel.add(clearButton, gbc);

        gbc.gridy = 5;
        panel.add(timeLabel, gbc);

        add(panel);
    }

    private class CalculateButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                int n = Integer.parseInt(nField.getText());
                int k = Integer.parseInt(kField.getText());

                if (n < 0 || k < 0 || k > n) {
                    resultLabel.setForeground(Color.RED);
                    resultLabel.setText("Invalid input: Ensure 0 <= k <= n.");
                } else {
                    long startTime = System.nanoTime();
                    long result = Coefficient(n, k);
                    long endTime = System.nanoTime();
                    long duration = (endTime - startTime) / 1000;

                    resultLabel.setForeground(Color.BLACK);
                    resultLabel.setText("Result: " + result);
                    timeLabel.setText("Time: " + duration + " µs");
                }
            } catch (NumberFormatException ex) {
                resultLabel.setForeground(Color.RED);
                resultLabel.setText("Invalid input: Please enter integers.");
            }
        }

        private long factorial(int num) {
            long fac = 1;
            for (int i = 1; i <= num; i++) {
                fac *= i;
            }
            return fac;
        }

        private long Coefficient(int n, int k) {
            long Factorial_of_n = factorial(n);
            long Factorial_of_k = factorial(k);
            long Factorial_of_nMinusk = factorial(n - k);
            return Factorial_of_n / (Factorial_of_k * Factorial_of_nMinusk);
        }
    }

    private class ClearButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            nField.setText("");
            kField.setText("");
            resultLabel.setForeground(Color.BLACK);
            resultLabel.setText("Result: ");
            timeLabel.setText("Time: ");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BinomialCoefficient calculator = new BinomialCoefficient();
            calculator.setVisible(true);
        });
    }
}
