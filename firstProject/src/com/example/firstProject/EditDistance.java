package com.example.firstProject;

import javax.swing.*;
import java.awt.*;

public class EditDistance extends JFrame {
    private JTextArea inputTextArea1, inputTextArea2, outputTextArea;
    private JLabel timeLabel;

    public EditDistance() {
        setTitle("Edit Distance");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout(10, 10));

        Font textFont = new Font("Arial", Font.PLAIN, 16);

        JPanel inputPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel input1Panel = new JPanel(new BorderLayout());
        JLabel inputLabel1 = new JLabel("Input Word 1:");
        inputTextArea1 = new JTextArea(3, 40);
        inputTextArea1.setFont(textFont);
        inputTextArea1.setLineWrap(true);
        inputTextArea1.setWrapStyleWord(true);
        input1Panel.add(inputLabel1, BorderLayout.NORTH);
        input1Panel.add(new JScrollPane(inputTextArea1), BorderLayout.CENTER);

        JPanel input2Panel = new JPanel(new BorderLayout());
        JLabel inputLabel2 = new JLabel("Input Word 2:");
        inputTextArea2 = new JTextArea(3, 40);
        inputTextArea2.setFont(textFont);
        inputTextArea2.setLineWrap(true);
        inputTextArea2.setWrapStyleWord(true);
        input2Panel.add(inputLabel2, BorderLayout.NORTH);
        input2Panel.add(new JScrollPane(inputTextArea2), BorderLayout.CENTER);

        inputPanel.add(input1Panel);
        inputPanel.add(input2Panel);

        add(inputPanel, BorderLayout.NORTH);

        JPanel outputPanel = new JPanel(new BorderLayout());
        outputPanel.setBorder(BorderFactory.createTitledBorder("Result"));

        outputTextArea = new JTextArea(5, 40);
        outputTextArea.setFont(textFont);
        outputTextArea.setLineWrap(true);
        outputTextArea.setWrapStyleWord(true);
        outputTextArea.setEditable(false);

        outputPanel.add(new JScrollPane(outputTextArea), BorderLayout.CENTER);

        add(outputPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());

        JPanel buttonPanel = new JPanel();
        JButton calculateButton = new JButton("Calculate");
        JButton clearButton = new JButton("Clear");
        buttonPanel.add(calculateButton);
        buttonPanel.add(clearButton);

        timeLabel = new JLabel("Time taken: ");
        timeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        bottomPanel.add(timeLabel, BorderLayout.NORTH);
        bottomPanel.add(buttonPanel, BorderLayout.CENTER);

        add(bottomPanel, BorderLayout.SOUTH);

        calculateButton.addActionListener(e -> calculateEditDistance());
        clearButton.addActionListener(e -> clearFields());

        setVisible(true);
    }

    private void calculateEditDistance() {
        String word1 = inputTextArea1.getText().trim();
        String word2 = inputTextArea2.getText().trim();

        if (word1.isEmpty() || word2.isEmpty()) {
            outputTextArea.setText("Please enter both words.");
            return;
        }

        long startTime = System.nanoTime();

        StringBuilder steps = new StringBuilder();
        int editDistance = computeEditDistance(word1, word2, steps);

        long endTime = System.nanoTime();
        timeLabel.setText("Time taken: " + (endTime - startTime) / 1000000.0 + " ms");

        outputTextArea.setText("Edit Distance: " + editDistance + "\nSteps:\n" + steps.toString());
    }

    private int computeEditDistance(String word1, String word2, StringBuilder steps) {
        int prefixLength = 0;
        while (prefixLength < word1.length() && prefixLength < word2.length() && word1.charAt(prefixLength) == word2.charAt(prefixLength)) {
            prefixLength++;
        }
        word1 = word1.substring(prefixLength);
        word2 = word2.substring(prefixLength);

        while (!word1.isEmpty() && !word2.isEmpty() && word1.charAt(word1.length() - 1) == word2.charAt(word2.length() - 1)) {
            word1 = word1.substring(0, word1.length() - 1);
            word2 = word2.substring(0, word2.length() - 1);
        }

        return computeEditDistanceRecursive(word1, word2, steps, 0);
    }

    private int computeEditDistanceRecursive(String word1, String word2, StringBuilder steps, int position) {
        if (word1.isEmpty()) {
            for (int i = 0; i < word2.length(); i++) {
                steps.append("Insert '").append(word2.charAt(i)).append("' at position ").append(position + i + 1).append("\n");
            }
            return word2.length();
        }

        if (word2.isEmpty()) {
            for (int i = 0; i < word1.length(); i++) {
                steps.append("Remove '").append(word1.charAt(i)).append("' from position ").append(position + 1).append("\n");
            }
            return word1.length();
        }

        if (word1.charAt(0) == word2.charAt(0)) {
            return computeEditDistanceRecursive(word1.substring(1), word2.substring(1), steps, position + 1);
        }

        steps.append("Remove '").append(word1.charAt(0)).append("' from position ").append(position + 1).append("\n");
        steps.append("Insert '").append(word2.charAt(0)).append("' at position ").append(position + 1).append("\n");
        
        return computeEditDistanceRecursive(word1.substring(1), word2.substring(1), steps, position + 1) + 2;
    }

    private void clearFields() {
        inputTextArea1.setText("");
        inputTextArea2.setText("");
        outputTextArea.setText("");
        timeLabel.setText("Time taken: ");
        inputTextArea1.requestFocus();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(EditDistance::new);
    }
}
