package com.example.firstProject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PrimeNumberChecker extends JFrame implements ActionListener {
    private JTextField inputField;
    private JButton checkButton;
    private JButton clearButton;
    private JTextArea resultArea;
    private JLabel timeLabel;

    public PrimeNumberChecker() {
        setTitle("Prime Number Checker");
        setSize(400, 300); // Increased the size to accommodate the result area
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        JLabel promptLabel = new JLabel("Enter a number: ");
        inputField = new JTextField(10);
        checkButton = new JButton("Check");
        clearButton = new JButton("Clear");
        resultArea = new JTextArea(5, 20); // Set preferred size
        resultArea.setEditable(false);
        resultArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        JScrollPane scrollPane = new JScrollPane(resultArea);
        timeLabel = new JLabel("Computation time: ");

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        add(promptLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(inputField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(checkButton, gbc);

        gbc.gridy = 2;
        add(scrollPane, gbc);

        gbc.gridy = 3;
        add(clearButton, gbc);

        gbc.gridy = 4;
        add(timeLabel, gbc);

        checkButton.addActionListener(this);
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFields();
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            long startTime = System.nanoTime();

            int number = Integer.parseInt(inputField.getText());
            boolean isPrime = isPrime(number);

            long endTime = System.nanoTime();
            long duration = (endTime - startTime) / 1000;

            if (isPrime) {
                resultArea.setText(number + " is a prime number.");
            } else {
                resultArea.setText(number + " is not a prime number.");
            }
            timeLabel.setText("Computation time: " + duration + " µs");
        } catch (NumberFormatException ex) {
            resultArea.setText("Please enter a valid integer.");
            timeLabel.setText("Computation time: ");
        }
    }

    private boolean isPrime(int num) {
        if (num <= 1) return false;
        for (int i = 2; i <= num / 2; i++) {
            if (num % i == 0) return false;
        }
        return true;
    }

    private void clearFields() {
        inputField.setText("");
        resultArea.setText("");
        timeLabel.setText("Computation time: ");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PrimeNumberChecker().setVisible(true);
            }
        });
    }
}
