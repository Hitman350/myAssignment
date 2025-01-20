package com.example.firstProject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Combinations extends JFrame {

    private JTextField itemsField;
    private JTextField lengthField;
    private JTextArea resultArea;
    private JLabel countLabel;
    private JLabel timeLabel;
    private JLabel errorLabel;

    public Combinations() {
        // Frame setup
        setTitle("Combinations");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(5, 5));
        setSize(500, 600); 

        // Top panel for inputs
        JPanel topPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        topPanel.add(new JLabel("Items:"));
        itemsField = new JTextField();
        topPanel.add(itemsField);
        topPanel.add(new JLabel("# Per Selection:"));
        lengthField = new JTextField();
        topPanel.add(lengthField);
        add(topPanel, BorderLayout.NORTH);

        // Center panel for results
        resultArea = new JTextArea(10, 30);
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);
        add(scrollPane, BorderLayout.CENTER);

        // Bottom panel for controls
        JPanel bottomPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        countLabel = new JLabel("# Combinations: 0");
        timeLabel = new JLabel("Time Taken: 0 ms");
        errorLabel = new JLabel("");
        errorLabel.setForeground(Color.RED); // Display error message in red

        bottomPanel.add(countLabel);
        bottomPanel.add(timeLabel);
        bottomPanel.add(errorLabel);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton generateButton = new JButton("Go");
        JButton clearButton = new JButton("Clear");
        buttonPanel.add(generateButton);
        buttonPanel.add(clearButton);
        bottomPanel.add(buttonPanel);

        add(bottomPanel, BorderLayout.SOUTH);

        // Button action listeners
        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateCombinations();
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFields();
            }
        });
    }

    private void generateCombinations() {
        String itemsInput = itemsField.getText().trim();
        String[] items = itemsInput.split("\\s+");
        String lengthInput = lengthField.getText().trim();

        resultArea.setText(""); // Clear previous results
        countLabel.setText("# Combinations: 0");
        timeLabel.setText("Time Taken: 0 ms");
        errorLabel.setText(""); // Clear previous error messages

        try {
            long startTime = System.currentTimeMillis();

            if (lengthInput.isEmpty()) {
                throw new IllegalArgumentException("Length is required for combinations.");
            }

            int length = Integer.parseInt(lengthInput);
            if (length <= 0 || length > items.length) {
                throw new IllegalArgumentException("Invalid length. Must be between 1 and the number of items.");
            }

            List<List<String>> combinations = getCombinations(items, length);

            // Display results
            for (List<String> combination : combinations) {
                resultArea.append(String.join(" ", combination) + "\n");
            }

            long endTime = System.currentTimeMillis();
            countLabel.setText("# Combinations: " + combinations.size());
            timeLabel.setText("Time Taken: " + (endTime - startTime) + " ms");
        } catch (NumberFormatException ex) {
            errorLabel.setText("Error: Length must be a valid integer.");
        } catch (IllegalArgumentException ex) {
            errorLabel.setText("Error: " + ex.getMessage());
        } catch (Exception ex) {
            errorLabel.setText("Error: An unexpected error occurred.");
        }
    }

    private void clearFields() {
        itemsField.setText("");
        lengthField.setText("");
        resultArea.setText("");
        countLabel.setText("# Combinations: 0");
        timeLabel.setText("Time Taken: 0 ms");
        errorLabel.setText("");
    }

    private List<List<String>> getCombinations(String[] items, int length) {
        List<List<String>> results = new ArrayList<>();
        generateCombinationsHelper(items, length, 0, new ArrayList<>(), results);
        return results;
    }

    private void generateCombinationsHelper(String[] items, int length, int start, List<String> current, List<List<String>> results) {
        if (current.size() == length) {
            results.add(new ArrayList<>(current));
            return;
        }

        for (int i = start; i < items.length; i++) {
            current.add(items[i]);
            generateCombinationsHelper(items, length, i + 1, current, results);
            current.remove(current.size() - 1);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Combinations app = new Combinations();
            app.setVisible(true);
        });
    }
}

