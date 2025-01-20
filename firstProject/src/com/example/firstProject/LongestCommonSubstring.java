package com.example.firstProject;

import javax.swing.*;
import java.awt.*;

public class LongestCommonSubstring extends JFrame {
    private JTextArea inputTextArea1, inputTextArea2, outputTextArea;
    private JLabel timeLabel;

    public LongestCommonSubstring() {
        setTitle("Longest Common Substring Finder");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        setResizable(false);
        setLocationRelativeTo(null);

        Font textFont = new Font("Arial", Font.PLAIN, 16);

        JPanel inputPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel input1Panel = new JPanel(new BorderLayout());
        JLabel inputLabel1 = new JLabel("Input String 1:");
        inputTextArea1 = new JTextArea(5, 40);
        inputTextArea1.setFont(textFont);
        inputTextArea1.setLineWrap(true);
        inputTextArea1.setWrapStyleWord(true);
        input1Panel.setBorder(BorderFactory.createTitledBorder(""));
        input1Panel.add(inputLabel1, BorderLayout.NORTH);
        input1Panel.add(new JScrollPane(inputTextArea1), BorderLayout.CENTER);

        JPanel input2Panel = new JPanel(new BorderLayout());
        JLabel inputLabel2 = new JLabel("Input String 2:");
        inputTextArea2 = new JTextArea(5, 40);
        inputTextArea2.setFont(textFont);
        inputTextArea2.setLineWrap(true);
        inputTextArea2.setWrapStyleWord(true);
        input2Panel.setBorder(BorderFactory.createTitledBorder(""));
        input2Panel.add(inputLabel2, BorderLayout.NORTH);
        input2Panel.add(new JScrollPane(inputTextArea2), BorderLayout.CENTER);

        inputPanel.add(input1Panel);
        inputPanel.add(input2Panel);

        add(inputPanel, BorderLayout.NORTH);

        JPanel outputPanel = new JPanel(new BorderLayout());
        outputPanel.setBorder(BorderFactory.createTitledBorder("Longest Common Substring"));

        outputTextArea = new JTextArea(5, 40);
        outputTextArea.setFont(textFont);
        outputTextArea.setLineWrap(true);
        outputTextArea.setWrapStyleWord(true);
        outputTextArea.setEditable(false);

        outputPanel.add(new JScrollPane(outputTextArea), BorderLayout.CENTER);

        add(outputPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());

        JPanel buttonPanel = new JPanel();
        JButton findButton = new JButton("Find");
        JButton clearButton = new JButton("Clear");
        buttonPanel.add(findButton);
        buttonPanel.add(clearButton);

        timeLabel = new JLabel("Time taken: ");
        timeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        bottomPanel.add(timeLabel, BorderLayout.NORTH);
        bottomPanel.add(buttonPanel, BorderLayout.CENTER);

        add(bottomPanel, BorderLayout.SOUTH);

        findButton.addActionListener(e -> findLongestCommonSubstring());
        clearButton.addActionListener(e -> clearFields());

        setVisible(true);
    }

    private void findLongestCommonSubstring() {
        String str1 = inputTextArea1.getText();
        String str2 = inputTextArea2.getText();

        if (str1.isEmpty() || str2.isEmpty()) {
            outputTextArea.setText("Please enter both input strings.");
            return;
        }

        long startTime = System.nanoTime();

        String longestSubstring = getLongestCommonSubstring(str1, str2);

        long endTime = System.nanoTime();
        timeLabel.setText("Time taken: " + (endTime - startTime) / 1_000_000.0 + " ms");

        outputTextArea.setText(longestSubstring);
    }

    private String getLongestCommonSubstring(String str1, String str2) {
        int maxLength = 0;
        String longestSubstring = "";

        for (int i = 0; i < str1.length(); i++) {
            for (int j = i + 1; j <= str1.length(); j++) {
                String substring = str1.substring(i, j);
                if (str2.contains(substring) && substring.length() > maxLength) {
                    maxLength = substring.length();
                    longestSubstring = substring;
                }
            }
        }

        return maxLength > 0 ? longestSubstring : "No common substring found.";
    }

    private void clearFields() {
        inputTextArea1.setText("");
        inputTextArea2.setText("");
        outputTextArea.setText("");
        timeLabel.setText("Time taken: ");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LongestCommonSubstring::new);
    }
}
