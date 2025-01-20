package com.example.firstProject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class StatisticalFunction extends JFrame {
    private JTextField numberField;
    private JTextField truncateField;
    private JTextArea resultArea;
    private JButton generateButton;
    private JButton clearButton;
    private JLabel errorLabel;

    public StatisticalFunction() {
        setTitle("Statistics Generator");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        numberField = new JTextField(10);
        truncateField = new JTextField(10);
        resultArea = new JTextArea(15, 10);
        resultArea.setEditable(false);

        generateButton = new JButton("Generate");
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Statistics();
            }
        });

        clearButton = new JButton("Clear");
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFields();
            }
        });

        errorLabel = new JLabel("");
        errorLabel.setForeground(Color.RED);

        JPanel numberPanel = new JPanel();
        numberPanel.add(new JLabel("Enter number of elements:"));
        numberPanel.add(numberField);

        JPanel truncatePanel = new JPanel();
        truncatePanel.add(new JLabel("Enter number of values to be removed:"));
        truncatePanel.add(truncateField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.add(generateButton);
        buttonPanel.add(Box.createVerticalStrut(10)); // Add vertical gap
        buttonPanel.add(clearButton);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.add(numberPanel);
        inputPanel.add(truncatePanel);
        inputPanel.add(errorLabel);
        inputPanel.add(buttonPanel);

        add(inputPanel, BorderLayout.NORTH);
        add(new JScrollPane(resultArea), BorderLayout.CENTER);
    }

    private void Statistics() {
        try {
            errorLabel.setText(""); 

            int n = Integer.parseInt(numberField.getText());
            int x = Integer.parseInt(truncateField.getText());
            if (n < 1 || x < 0 || x * 2 >= n) {
                throw new NumberFormatException();
            }

            int[] values = new int[n];
            for (int i = 0; i < n; i++) {
                values[i] = (int) (Math.random() * 5) + 1 + (int) (Math.random() * 5) + 1;
            }

            Arrays.sort(values);
            int min = values[0];
            int max = values[values.length - 1];

            double mean = Mean(values, 0);
            double truncatedMean = Mean(values, x);

            double median;
            if (values.length % 2 == 0) {
                median = (values[values.length / 2 - 1] + values[values.length / 2]) / 2.0;
            } else {
                median = values[values.length / 2];
            }

            int mode = Mode(values);

            double sampleStdDev = Math.sqrt(SD(values, mean) / (values.length - 1));
            double populationStdDev = Math.sqrt(SD(values, mean) / (values.length));

            resultArea.setText("Generated " + n + " values\n");
            resultArea.append("Values: " + Arrays.toString(values) + "\n");
            resultArea.append("Minimum: " + min + "\n");
            resultArea.append("Maximum: " + max + "\n");
            resultArea.append("Mean: " + mean + "\n");
            resultArea.append("Truncated Mean: " + truncatedMean + "\n");
            resultArea.append("Median: " + median + "\n");
            resultArea.append("Mode: " + mode + "\n");
            resultArea.append("Sample Standard Deviation: " + sampleStdDev + "\n");
            resultArea.append("Population Standard Deviation: " + populationStdDev + "\n");

        } catch (NumberFormatException ex) {
            errorLabel.setText("Less than half of the values should be removed.");
        }
    }

    private void clearFields() {
        numberField.setText("");
        truncateField.setText("");
        resultArea.setText("");
        errorLabel.setText("");
    }

    private double Mean(int[] values, int x) {
        int sum = 0;
        for (int i = x; i < values.length - x; i++) {
            sum += values[i];
        }
        return sum / (values.length - 2 * x);
    }

    private int Mode(int[] values) {
        int mode = values[0];
        int store = 0;

        for (int i = 0; i < values.length; i++) {
            int count = 0;
            for (int j = 0; j < values.length; j++) {
                if (values[j] == values[i]) {
                    count++;
                }
            }
            if (count > store) {
                store = count;
                mode = values[i];
            }
        }
        return mode;
    }

    private double SD(int[] values, double mean) {
        double sum = 0;
        for (int i = 0; i < values.length; i++) {
            sum += Math.pow(values[i] - mean, 2);
        }
        return sum;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            StatisticalFunction frame = new StatisticalFunction();
            frame.setVisible(true);
        });
    }
}
