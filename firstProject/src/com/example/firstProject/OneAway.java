package com.example.firstProject;

import javax.swing.*;
import java.awt.*;

public class OneAway extends JFrame {
    private JTextArea inputTextArea1, inputTextArea2, outputTextArea;
    private JLabel timeLabel;

    public OneAway() {
        setTitle("One Away");
        setSize(800, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout(10, 10));

        Font textFont = new Font("Arial", Font.PLAIN, 16);

        JPanel inputPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel input1Panel = new JPanel(new BorderLayout());
        JLabel inputLabel1 = new JLabel("Input String 1:");
        inputTextArea1 = new JTextArea(3, 40);
        inputTextArea1.setFont(textFont);
        inputTextArea1.setLineWrap(true);
        inputTextArea1.setWrapStyleWord(true);
        input1Panel.add(inputLabel1, BorderLayout.NORTH);
        input1Panel.add(new JScrollPane(inputTextArea1), BorderLayout.CENTER);

        JPanel input2Panel = new JPanel(new BorderLayout());
        JLabel inputLabel2 = new JLabel("Input String 2:");
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

        outputTextArea = new JTextArea(3, 40);
        outputTextArea.setFont(textFont);
        outputTextArea.setLineWrap(true);
        outputTextArea.setWrapStyleWord(true);
        outputTextArea.setEditable(false);

        outputPanel.add(new JScrollPane(outputTextArea), BorderLayout.CENTER);

        add(outputPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());

        JPanel buttonPanel = new JPanel();
        JButton checkButton = new JButton("Check");
        JButton clearButton = new JButton("Clear");
        buttonPanel.add(checkButton);
        buttonPanel.add(clearButton);

        timeLabel = new JLabel("Time taken: ");
        timeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        bottomPanel.add(timeLabel, BorderLayout.NORTH);
        bottomPanel.add(buttonPanel, BorderLayout.CENTER);

        add(bottomPanel, BorderLayout.SOUTH);

        checkButton.addActionListener(e -> checkOneAway());
        clearButton.addActionListener(e -> clearFields());

        setVisible(true);
    }

    private void checkOneAway() {
        String str1 = inputTextArea1.getText();
        String str2 = inputTextArea2.getText();

        if (str1.isEmpty() || str2.isEmpty()) {
            outputTextArea.setText("Please enter both input strings.");
            return;
        }

        long startTime = System.nanoTime();

        boolean result = isOneAway(str1, str2);

        long endTime = System.nanoTime();
        timeLabel.setText("Time taken: " + (endTime - startTime) / 1000000.0 + " ms");

        outputTextArea.setText(result ? "TRUE" : "FALSE");
    }

    private boolean isOneAway(String str1, String str2) {
        int len1 = str1.length();
        int len2 = str2.length();
        if (Math.abs(len1 - len2) > 1) {
            return false;
        }

        String shorter = len1 < len2 ? str1 : str2;
        String longer = len1 < len2 ? str2 : str1;

        int index1 = 0, index2 = 0;
        boolean difference = false;

        while (index1 < shorter.length() && index2 < longer.length()) {
            if (shorter.charAt(index1) != longer.charAt(index2)) {
                if (difference) {
                    return false;
                }
                difference = true;

                if (len1 == len2) {
                    index1++;
                }
            } else {
                index1++;
            }
            index2++;
        }

        return true;
    }

    private void clearFields() {
        inputTextArea1.setText("");
        inputTextArea2.setText("");
        outputTextArea.setText("");
        timeLabel.setText("Time taken: ");
        inputTextArea1.requestFocus();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(OneAway::new);
    }
}
