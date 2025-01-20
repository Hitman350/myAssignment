package com.example.firstProject;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FactorialCalculator extends JFrame {
    private JTextField inputField;
    private JLabel loopResultLabel;
    private JLabel recursionResultLabel;
    private JLabel loopTimeLabel;
    private JLabel recursionTimeLabel;
    private JLabel errorMessageLabel;
    private JButton calculateButton;
    private JButton clearButton;

    public FactorialCalculator() {
        setTitle("Factorial Calculator");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(240, 248, 255));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        Border border = BorderFactory.createLineBorder(Color.GRAY, 1);
        Border paddingBorder = BorderFactory.createEmptyBorder(10, 10, 10, 10);

        JLabel inputLabel = new JLabel("Enter a number:");
        inputLabel.setFont(new Font("Senserif", Font.PLAIN, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(inputLabel, gbc);

        inputField = new JTextField(10);
        inputField.setFont(new Font("Arial", Font.PLAIN, 16));
        inputField.setBorder(BorderFactory.createCompoundBorder(border, paddingBorder));
        gbc.gridx = 1;
        panel.add(inputField, gbc);

        calculateButton = new JButton("Calculate");
        calculateButton.setFont(new Font("Arial", Font.BOLD, 16));
        calculateButton.setBackground(new Color(100, 149, 237));
        calculateButton.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(calculateButton, gbc);

        clearButton = new JButton("Clear");
        clearButton.setFont(new Font("Arial", Font.BOLD, 16));
        clearButton.setBackground(new Color(220, 20, 60));
        clearButton.setForeground(Color.WHITE);
        gbc.gridy = 2;
        panel.add(clearButton, gbc);

        JPanel resultPanel = new JPanel(new GridBagLayout());
        resultPanel.setBackground(new Color(255, 255, 255));
        resultPanel.setBorder(BorderFactory.createCompoundBorder(border, paddingBorder));
        GridBagConstraints resultGbc = new GridBagConstraints();
        resultGbc.insets = new Insets(5, 5, 5, 5);

        loopResultLabel = new JLabel("Loop Result: ");
        loopResultLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        resultGbc.gridx = 0;
        resultGbc.gridy = 0;
        resultPanel.add(loopResultLabel, resultGbc);

        recursionResultLabel = new JLabel("Recursion Result: ");
        recursionResultLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        resultGbc.gridy = 1;
        resultPanel.add(recursionResultLabel, resultGbc);

        loopTimeLabel = new JLabel("Loop Time: ");
        loopTimeLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        resultGbc.gridy = 2;
        resultPanel.add(loopTimeLabel, resultGbc);

        recursionTimeLabel = new JLabel("Recursion Time: ");
        recursionTimeLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        resultGbc.gridy = 3;
        resultPanel.add(recursionTimeLabel, resultGbc);

        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(resultPanel, gbc);

        errorMessageLabel = new JLabel("", JLabel.LEFT);
        errorMessageLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        errorMessageLabel.setForeground(Color.RED);
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(errorMessageLabel, gbc);

        calculateButton.addActionListener(new CalculateButtonListener());
        clearButton.addActionListener(new ClearButtonListener());

        add(panel);
    }

    public void focusOnInputField() {
        SwingUtilities.invokeLater(() -> inputField.requestFocusInWindow());
    }

    private class CalculateButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            errorMessageLabel.setText("");
            try {
                int number = Integer.parseInt(inputField.getText());
                if (number < 0 || number > 20) {
                    throw new IllegalArgumentException("Number must be between 0 and 20");
                }

                long startTime = System.nanoTime();
                long loopResult = factorialLoop(number);
                long loopTime = (System.nanoTime() - startTime) / 1000;

                startTime = System.nanoTime();
                long recursionResult = factorialRecursion(number);
                long recursionTime = (System.nanoTime() - startTime) / 1000;

                loopResultLabel.setText("Loop Result: " + loopResult);
                recursionResultLabel.setText("Recursion Result: " + recursionResult);
                loopTimeLabel.setText("Loop Time: " + loopTime + " µs");
                recursionTimeLabel.setText("Recursion Time: " + recursionTime + " µs");

            } catch (NumberFormatException ex) {
                errorMessageLabel.setText("Invalid input. Please enter an integer.");
            } catch (IllegalArgumentException ex) {
                errorMessageLabel.setText(ex.getMessage());
            }
        }
    }

    private class ClearButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            inputField.setText("");
            loopResultLabel.setText("Loop Result: ");
            recursionResultLabel.setText("Recursion Result: ");
            loopTimeLabel.setText("Loop Time: ");
            recursionTimeLabel.setText("Recursion Time: ");
            errorMessageLabel.setText("");
        }
    }

    private long factorialLoop(int n) {
        long result = 1;
        for (int i = 1; i <= n; i++) {
            result *= i;
        }
        return result;
    }

    private long factorialRecursion(int n) {
        if (n == 0) {
            return 1;
        } else {
            return n * factorialRecursion(n - 1);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FactorialCalculator calculator = new FactorialCalculator();
            calculator.setVisible(true);
            calculator.toFront();  
            calculator.setAlwaysOnTop(true);  
            calculator.setAlwaysOnTop(false);  
            calculator.requestFocus();  
            calculator.focusOnInputField();  
        });
    }
}
