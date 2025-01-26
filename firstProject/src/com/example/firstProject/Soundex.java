package com.example.firstProject;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Soundex extends JFrame {
    private JTextArea inputTextArea, outputTextArea;
    private JLabel timeLabel;

    public Soundex() {
        setTitle("Soundex");
        setSize(700, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        setResizable(false);

        Font textFont = new Font("Arial", Font.PLAIN, 16);

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JLabel inputLabel = new JLabel("Enter Name:");
        inputTextArea = new JTextArea(3, 40);
        inputTextArea.setFont(textFont);
        inputTextArea.setLineWrap(true);
        inputTextArea.setWrapStyleWord(true);
        inputPanel.add(inputLabel, BorderLayout.NORTH);
        inputPanel.add(new JScrollPane(inputTextArea), BorderLayout.CENTER);
        add(inputPanel, BorderLayout.NORTH);

        JPanel outputPanel = new JPanel(new BorderLayout());
        outputPanel.setBorder(BorderFactory.createTitledBorder("Soundex Code"));
        outputTextArea = new JTextArea(1, 40);
        outputTextArea.setFont(textFont);
        outputTextArea.setLineWrap(true);
        outputTextArea.setWrapStyleWord(true);
        outputTextArea.setEditable(false);
        outputPanel.add(new JScrollPane(outputTextArea), BorderLayout.CENTER);
        add(outputPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        JPanel buttonPanel = new JPanel();
        JButton processButton = new JButton("Generate");
        JButton clearButton = new JButton("Clear");
        buttonPanel.add(processButton);
        buttonPanel.add(clearButton);
        timeLabel = new JLabel("Computation Time: ");
        timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        timeLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 10, 0));
        bottomPanel.add(timeLabel, BorderLayout.NORTH);
        bottomPanel.add(buttonPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        processButton.addActionListener(e -> generateSoundexCode());
        clearButton.addActionListener(e -> clearFields());
        setVisible(true);
    }

    private void generateSoundexCode() {
        String name = inputTextArea.getText().trim().toUpperCase();
        if (name.isEmpty()) {
            outputTextArea.setText("Please enter a name.");
            outputTextArea.setForeground(Color.RED);
            return;
        }
        boolean hasLetter = name.chars().anyMatch(Character::isLetter);
        if (!hasLetter) {
            outputTextArea.setText("Input must contain at least one letter.");
            outputTextArea.setForeground(Color.RED);
            return;
        }
        long startTime = System.nanoTime();
        String soundexCode = computeSoundex(name);
        long endTime = System.nanoTime();
        timeLabel.setText("Computation Time: " + (endTime - startTime) / 1000000.0 + " ms");
        outputTextArea.setForeground(Color.BLACK);
        outputTextArea.setText(soundexCode);
    }

    private String computeSoundex(String name) {
        if (name.isEmpty()) return "";
        Map<Character, Character> encodingMap = new HashMap<>();
        encodingMap.put('B', '1');
        encodingMap.put('F', '1');
        encodingMap.put('P', '1');
        encodingMap.put('V', '1');
        encodingMap.put('C', '2');
        encodingMap.put('G', '2');
        encodingMap.put('J', '2');
        encodingMap.put('K', '2');
        encodingMap.put('Q', '2');
        encodingMap.put('S', '2');
        encodingMap.put('X', '2');
        encodingMap.put('Z', '2');
        encodingMap.put('D', '3');
        encodingMap.put('T', '3');
        encodingMap.put('L', '4');
        encodingMap.put('M', '5');
        encodingMap.put('N', '5');
        encodingMap.put('R', '6');
        char firstLetter = name.charAt(0);
        StringBuilder encoded = new StringBuilder();
        encoded.append(firstLetter);
        char previousDigit = '\0';
        for (int i = 1; i < name.length(); i++) {
            char c = name.charAt(i);
            if ("AEIOUYHW".indexOf(c) >= 0) {
                previousDigit = '\0';
                continue;
            }
            char digit = encodingMap.getOrDefault(c, '\0');
            if (digit != '\0' && digit != previousDigit) {
                encoded.append(digit);
                previousDigit = digit;
            }
        }
        while (encoded.length() < 4) {
            encoded.append('0');
        }
        return encoded.length() > 4 ? encoded.substring(0, 4) : encoded.toString();
    }

    private void clearFields() {
        inputTextArea.setText("");
        outputTextArea.setText("");
        timeLabel.setText("Computation Time: ");
        inputTextArea.requestFocus();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Soundex::new);
    }
}
