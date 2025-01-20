package com.example.firstProject;

import javax.swing.*;
import java.awt.*;

public class EditDistance extends JFrame {
    private JTextArea inputTextArea1, inputTextArea2, outputTextArea;
    private JLabel timeLabel;

    public EditDistance() {
        setTitle("Edit Distance Calculator");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        Font textFont = new Font("Arial", Font.PLAIN, 16);

        // Input Panel
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

        // Output Panel
        JPanel outputPanel = new JPanel(new BorderLayout());
        outputPanel.setBorder(BorderFactory.createTitledBorder("Result"));

        outputTextArea = new JTextArea(5, 40);
        outputTextArea.setFont(textFont);
        outputTextArea.setLineWrap(true);
        outputTextArea.setWrapStyleWord(true);
        outputTextArea.setEditable(false);

        outputPanel.add(new JScrollPane(outputTextArea), BorderLayout.CENTER);

        add(outputPanel, BorderLayout.CENTER);

        // Bottom Panel
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

        // Button Actions
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

        int[][] dp = computeEditDistance(word1, word2);
        String steps = reconstructSteps(word1, word2, dp);

        long endTime = System.nanoTime();
        timeLabel.setText("Time taken: " + (endTime - startTime) / 1_000_000.0 + " ms");

        int editDistance = dp[word1.length()][word2.length()];
        outputTextArea.setText("Edit Distance: " + editDistance + "\nSteps:\n" + steps);
    }

    private int[][] computeEditDistance(String word1, String word2) {
        int m = word1.length();
        int n = word2.length();

        int[][] dp = new int[m + 1][n + 1];

        // Initialize base cases
        for (int i = 0; i <= m; i++) {
            dp[i][0] = i;
        }
        for (int j = 0; j <= n; j++) {
            dp[0][j] = j;
        }

        // Fill DP table
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1]; // No change needed
                } else {
                    dp[i][j] = 1 + Math.min(dp[i - 1][j - 1], // Replace
                            Math.min(dp[i - 1][j], // Remove
                                    dp[i][j - 1])); // Insert
                }
            }
        }

        return dp;
    }

    private String reconstructSteps(String word1, String word2, int[][] dp) {
        StringBuilder steps = new StringBuilder();

        int i = word1.length();
        int j = word2.length();

        while (i > 0 || j > 0) {
            if (i > 0 && j > 0 && word1.charAt(i - 1) == word2.charAt(j - 1)) {
                i--;
                j--;
            } else if (i > 0 && dp[i][j] == dp[i - 1][j] + 1) {
                steps.append("Remove '").append(word1.charAt(i - 1)).append("' from position ").append(i).append("\n");
                i--;
            } else if (j > 0 && dp[i][j] == dp[i][j - 1] + 1) {
                steps.append("Insert '").append(word2.charAt(j - 1)).append("' at position ").append(i + 1).append("\n");
                j--;
            } else {
                steps.append("Replace '").append(word1.charAt(i - 1)).append("' with '").append(word2.charAt(j - 1)).append("' at position ").append(i).append("\n");
                i--;
                j--;
            }
        }

        return steps.reverse().toString();
    }

    private void clearFields() {
        inputTextArea1.setText("");
        inputTextArea2.setText("");
        outputTextArea.setText("");
        timeLabel.setText("Time taken: ");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(EditDistance::new);
    }
}

