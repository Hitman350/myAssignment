package com.example.firstProject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProperDivisors extends JFrame {
    private JTextField inputField;
    private JTextArea resultArea;
    private JButton computeButton;
    private JLabel timeLabel;

    public ProperDivisors() {
        setTitle("Proper Divisors Finder");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel inputLabel = new JLabel("Enter a number: ");
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(inputLabel, gbc);

        inputField = new JTextField(10);
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(inputField, gbc);

        computeButton = new JButton("Compute");
        gbc.gridx = 2;
        gbc.gridy = 0;
        add(computeButton, gbc);

        resultArea = new JTextArea(15, 30);
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 3;
        add(scrollPane, gbc);

        timeLabel = new JLabel("Computation Time: ");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        add(timeLabel, gbc);

        computeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                findDivisors();
            }
        });
    }

    private void findDivisors() {
        resultArea.setText("");
        long startTime = System.nanoTime();
        try {
            int number = Integer.parseInt(inputField.getText());
            StringBuilder result = new StringBuilder();
            result.append("Proper Divisors of ").append(number).append(" are: \n");
            for (int i = 1; i <= number / 2; i++) {
                if (number % i == 0) {
                    result.append(i).append("\n");
                }
            }
            resultArea.setText(result.toString());
        } catch (NumberFormatException ex) {
            resultArea.setText("Please enter a valid integer.");
        }
        long endTime = System.nanoTime();
        long duration = (endTime - startTime)/1000000;
        timeLabel.setText("Computation Time: " + duration + " ms");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ProperDivisors().setVisible(true);
            }
        });
    }
}
