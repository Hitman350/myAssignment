package com.example.firstProject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Permutations extends JFrame {

    private JTextField itemsField;
    private JTextField lengthField;
    private JTextArea resultArea;
    private JLabel countLabel;
    private JLabel timeLabel;
    private JLabel errorLabel;

    public Permutations() {
        setTitle("Permutations");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(5, 5));
        setSize(500, 600);

        setLocationRelativeTo(null);
        setResizable(false);

        JPanel topPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        topPanel.add(new JLabel("Items:"));
        itemsField = new JTextField();
        topPanel.add(itemsField);
        topPanel.add(new JLabel("# Per Selection:"));
        lengthField = new JTextField();
        topPanel.add(lengthField);
        add(topPanel, BorderLayout.NORTH);

        resultArea = new JTextArea(10, 30);
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);
        add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new GridLayout(4, 1, 5, 5));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        countLabel = new JLabel("# Permutations: 0");
        timeLabel = new JLabel("Time Taken: 0 ms");
        errorLabel = new JLabel("");
        errorLabel.setForeground(Color.RED);

        bottomPanel.add(countLabel);
        bottomPanel.add(timeLabel);
        bottomPanel.add(errorLabel);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton generateButton = new JButton("Generate");
        JButton clearButton = new JButton("Clear");
        buttonPanel.add(generateButton);
        buttonPanel.add(clearButton);
        bottomPanel.add(buttonPanel);

        add(bottomPanel, BorderLayout.SOUTH);

        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generatePermutations();
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFields();
            }
        });
    }

    private void generatePermutations() {
        String itemsInput = itemsField.getText().trim();
        String[] items = itemsInput.split("\\s+");
        String lengthInput = lengthField.getText().trim();

        resultArea.setText("");
        countLabel.setText("# Permutations: 0");
        timeLabel.setText("Time Taken: 0 ms");
        errorLabel.setText("");

        try {
            long startTime = System.currentTimeMillis();

            List<List<String>> permutations;
            if (lengthInput.isEmpty()) {
                permutations = getAllPermutations(items);
            } else {
                int length = Integer.parseInt(lengthInput);
                permutations = getPermutations(items, length);
            }

            for (List<String> permutation : permutations) {
                resultArea.append(String.join(" ", permutation) + "\n");
            }

            long endTime = System.currentTimeMillis();
            countLabel.setText("# Permutations: " + permutations.size());
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
        countLabel.setText("# Permutations: 0");
        timeLabel.setText("Time Taken: 0 ms");
        errorLabel.setText("");
    }

    private List<List<String>> getAllPermutations(String[] items) {
        List<List<String>> allPermutations = new ArrayList<>();
        for (int i = 1; i <= items.length; i++) {
            allPermutations.addAll(getPermutations(items, i));
        }
        return allPermutations;
    }

    private List<List<String>> getPermutations(String[] items, int length) {
        List<List<String>> results = new ArrayList<>();
        if (length > items.length || length <= 0) {
            return results;
        }
        generatePermutationsHelper(items, length, new ArrayList<>(), results);
        return results;
    }

    private void generatePermutationsHelper(String[] items, int length, List<String> current, List<List<String>> results) {
        if (current.size() == length) {
            results.add(new ArrayList<>(current));
            return;
        }

        for (String item : items) {
            if (current.contains(item)) continue;
            current.add(item);
            generatePermutationsHelper(items, length, current, results);
            current.remove(current.size() - 1);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Permutations app = new Permutations();
            app.setVisible(true);
        });
    }
}
