package com.example.firstProject;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class RomanNumerals extends JFrame {
    private JTextField romanInputField;
    private JTextField arabicOutputField;
    private JLabel timeLabel;
    private JLabel errorLabel;
    private JButton convertButton;
    private JButton clearButton;

    public RomanNumerals() {
        setTitle("Roman Numerals");
        setSize(500, 300); // Increased window size
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        setResizable(false);
        setLocationRelativeTo(null);

        Font font = new Font("Arial", Font.BOLD, 16); // Increased font size

        JPanel inputPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Input"));
        
        JLabel romanLabel = new JLabel("Enter Roman Numeral:");
        romanLabel.setFont(font);
        inputPanel.add(romanLabel);
        
        romanInputField = new JTextField();
        romanInputField.setFont(font);
        inputPanel.add(romanInputField);

        JLabel arabicLabel = new JLabel("Arabic Numeral Output:");
        arabicLabel.setFont(font);
        inputPanel.add(arabicLabel);
        
        arabicOutputField = new JTextField();
        arabicOutputField.setEditable(false);
        arabicOutputField.setFont(font);
        inputPanel.add(arabicOutputField);

        add(inputPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new BorderLayout(10, 10));
        timeLabel = new JLabel("Computation Time: ");
        timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        timeLabel.setFont(font);
        buttonPanel.add(timeLabel, BorderLayout.NORTH);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        convertButton = new JButton("Convert");
        clearButton = new JButton("Clear");
        
        convertButton.setFont(font);
        clearButton.setFont(font);
        
        buttons.add(convertButton);
        buttons.add(clearButton);
        buttonPanel.add(buttons, BorderLayout.CENTER);

        errorLabel = new JLabel("");
        errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        errorLabel.setForeground(Color.RED);
        errorLabel.setFont(new Font("Arial", Font.BOLD, 14)); // Slightly smaller for error messages
        errorLabel.setPreferredSize(new Dimension(errorLabel.getPreferredSize().width, 20));
        buttonPanel.add(errorLabel, BorderLayout.SOUTH);

        add(buttonPanel, BorderLayout.SOUTH);

        convertButton.addActionListener(e -> {
            errorLabel.setText("");
            String romanNumeral = romanInputField.getText().trim();
            if (romanNumeral.isEmpty()) {
                errorLabel.setText("Invalid input! Please enter a Roman numeral.");
                return;
            }

            long startTime = System.nanoTime();
            try {
                int arabicValue = convertRomanToArabic(romanNumeral);
                arabicOutputField.setText(String.valueOf(arabicValue));
            } catch (IllegalArgumentException ex) {
                errorLabel.setText("Invalid Roman numeral!");
            }
            long endTime = System.nanoTime();
            timeLabel.setText("Computation Time: " + (endTime - startTime) / 1000000.0 + " ms");
        });

        clearButton.addActionListener(e -> {
            romanInputField.setText("");
            arabicOutputField.setText("");
            timeLabel.setText("Computation Time: ");
            errorLabel.setText("");
            romanInputField.requestFocus();
        });

        setVisible(true);
    }

    private int convertRomanToArabic(String roman) {
        HashMap<Character, Integer> romanValues = new HashMap<>();
        romanValues.put('I', 1);
        romanValues.put('V', 5);
        romanValues.put('X', 10);
        romanValues.put('L', 50);
        romanValues.put('C', 100);
        romanValues.put('D', 500);
        romanValues.put('M', 1000);

        return evaluateParentheses(roman, romanValues);
    }

    private int evaluateParentheses(String roman, HashMap<Character, Integer> romanValues) {
        while (roman.contains("(")) {
            int closeIndex = roman.indexOf(')');
            int openIndex = roman.lastIndexOf('(', closeIndex);
            if (openIndex == -1 || closeIndex == -1) {
                throw new IllegalArgumentException("Invalid Roman numeral");
            }

            String insideParentheses = roman.substring(openIndex + 1, closeIndex);
            int insideValue = convertSimpleRoman(insideParentheses, romanValues);
            roman = roman.substring(0, openIndex) + repeatChar('M', insideValue) + roman.substring(closeIndex + 1);
        }

        return convertSimpleRoman(roman, romanValues);
    }

    private String repeatChar(char c, int count) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < count; i++) {
            builder.append(c);
        }
        return builder.toString();
    }

    private int convertSimpleRoman(String roman, HashMap<Character, Integer> romanValues) {
        int total = 0;
        int prevValue = 0;

        for (int i = roman.length() - 1; i >= 0; i--) {
            char c = roman.charAt(i);
            if (!romanValues.containsKey(c)) throw new IllegalArgumentException("Invalid Roman numeral");

            int currentValue = romanValues.get(c);
            if (currentValue < prevValue) {
                total -= currentValue;
            } else {
                total += currentValue;
            }
            prevValue = currentValue;
        }
        return total;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(RomanNumerals::new);
    }
}
