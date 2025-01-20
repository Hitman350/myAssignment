package com.example.firstProject;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FibonacciCalculator extends JFrame {
    private JTextField inputField;
    private JTextArea loopResultTextArea;
    private JTextArea recursionResultTextArea;
    private JLabel loopTimeLabel;
    private JLabel recursionTimeLabel;
    private JLabel errorMessageLabel;

    public FibonacciCalculator() {
        setTitle("Fibonacci Calculator");
        setSize(700, 850);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setAlwaysOnTop(true);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(220, 220, 220));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        Border border = BorderFactory.createLineBorder(Color.BLACK, 2);
        Border paddingBorder = BorderFactory.createEmptyBorder(10, 10, 10, 10);

        JLabel titleLabel = new JLabel("Fibonacci Sequence Calculator");
        titleLabel.setFont(new Font("Violet", Font.BOLD, 20));
        titleLabel.setForeground(new Color(0, 100, 210));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        JLabel inputLabel = new JLabel("Enter number of terms:");
        inputLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(inputLabel, gbc);

        inputField = new JTextField(10);
        inputField.setFont(new Font("Arial", Font.PLAIN, 16));
        inputField.setBorder(BorderFactory.createCompoundBorder(border, paddingBorder));
        gbc.gridx = 1;
        panel.add(inputField, gbc);

        JButton calculateButton = new JButton("Calculate");
        calculateButton.setFont(new Font("Arial", Font.BOLD, 16));
        calculateButton.setBackground(new Color(34, 139, 34));
        calculateButton.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(calculateButton, gbc);

        JButton clearButton = new JButton("Clear");
        inputField.grabFocus();
        clearButton.setFont(new Font("Arial", Font.BOLD, 16));
        clearButton.setBackground(new Color(200, 20, 60));
        clearButton.setForeground(Color.WHITE);
        gbc.gridy = 3;
        panel.add(clearButton, gbc);

        JPanel loopResultPanel = new JPanel(new BorderLayout());
        loopResultPanel.setBackground(new Color(255, 255, 255));
        loopResultPanel.setBorder(BorderFactory.createCompoundBorder(border, paddingBorder));

        loopResultTextArea = new JTextArea(10, 30);
        loopResultTextArea.setEditable(false);
        JScrollPane loopScrollPane = new JScrollPane(loopResultTextArea);
        loopResultPanel.add(loopScrollPane, BorderLayout.CENTER);

        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(loopResultPanel, gbc);

        JPanel recursionResultPanel = new JPanel(new BorderLayout());
        recursionResultPanel.setBackground(new Color(255, 255, 255));
        recursionResultPanel.setBorder(BorderFactory.createCompoundBorder(border, paddingBorder));

        recursionResultTextArea = new JTextArea(10, 30);
        recursionResultTextArea.setEditable(false);
        JScrollPane recursionScrollPane = new JScrollPane(recursionResultTextArea);
        recursionResultPanel.add(recursionScrollPane, BorderLayout.CENTER);

        gbc.gridy = 5;
        panel.add(recursionResultPanel, gbc);

        loopTimeLabel = new JLabel("Loop Time: ");
        loopTimeLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridy = 6;
        panel.add(loopTimeLabel, gbc);

        recursionTimeLabel = new JLabel("Recursion Time: ");
        recursionTimeLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        gbc.gridy = 7;
        panel.add(recursionTimeLabel, gbc);

        errorMessageLabel = new JLabel("", JLabel.LEFT);
        errorMessageLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        errorMessageLabel.setForeground(Color.RED);
        gbc.gridy = 8;
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
                int terms = Integer.parseInt(inputField.getText());
                if (terms < 0 || terms > 50) {
                    throw new IllegalArgumentException("Number of terms must be between 0 and 50");
                }

                long startTime = System.nanoTime();
                String loopResult = fibonacciLoop(terms);
                long loopTime = (System.nanoTime() - startTime) / 1000;

                startTime = System.nanoTime();
                String recursionResult = fibonacciRecursion(terms);
                long recursionTime = (System.nanoTime() - startTime) / 1000;

                loopResultTextArea.setText(loopResult);
                recursionResultTextArea.setText(recursionResult);
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
            loopResultTextArea.setText("");
            recursionResultTextArea.setText("");
            loopTimeLabel.setText("Loop Time: ");
            recursionTimeLabel.setText("Recursion Time: ");
            errorMessageLabel.setText("");
        }
    }

    private String fibonacciLoop(int terms) {
        if (terms == 0) return "";
        if (terms == 1) return "0";

        StringBuilder result = new StringBuilder("0 1");
        long a = 0, b = 1;
        for (int i = 2; i < terms; i++) {
            long next = a + b;
            result.append(" ").append(next);
            a = b;
            b = next;
        }
        return result.toString();
    }

    private String fibonacciRecursion(int terms) {
        if (terms == 0) return "";
        if (terms == 1) return "0";
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < terms; i++) {
            result.append(fibonacciRecursionHelper(i)).append(" ");
        }
        return result.toString();
    }

    private long fibonacciRecursionHelper(int n) {
        if (n <= 1) return n;
        else return fibonacciRecursionHelper(n - 1) + fibonacciRecursionHelper(n - 2);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FibonacciCalculator calculator = new FibonacciCalculator();
            calculator.setVisible(true);
        });
    }
}
