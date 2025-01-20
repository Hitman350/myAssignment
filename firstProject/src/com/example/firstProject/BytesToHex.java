package com.example.firstProject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.charset.StandardCharsets;

public class BytesToHex extends JFrame {
    private JTextField inputField;
    private JTextArea byteOutputArea;
    private JTextArea hexOutputArea;
    private JTextArea recoveredTextArea;
    private JLabel errorLabel;
    private JLabel timeLabel;

    public BytesToHex() {
        setTitle("Bytes to Hex Converter");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(5, 5)); 
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel inputPanel = new JPanel(new GridLayout(1, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JLabel inputLabel = new JLabel("Enter Text:");
        inputField = new JTextField();
        inputPanel.add(inputLabel);
        inputPanel.add(inputField);

        add(inputPanel, BorderLayout.NORTH);

        JPanel outputPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        outputPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        byteOutputArea = new JTextArea();
        byteOutputArea.setEditable(false);
        byteOutputArea.setBorder(BorderFactory.createTitledBorder("Byte Representation"));

        hexOutputArea = new JTextArea();
        hexOutputArea.setEditable(false);
        hexOutputArea.setBorder(BorderFactory.createTitledBorder("Hexadecimal Representation"));

        recoveredTextArea = new JTextArea();
        recoveredTextArea.setEditable(false);
        recoveredTextArea.setBorder(BorderFactory.createTitledBorder("Recovered Text"));

        outputPanel.add(byteOutputArea);
        outputPanel.add(hexOutputArea);
        outputPanel.add(recoveredTextArea);

        add(outputPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout(5, 5));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        errorLabel = new JLabel(" ");
        errorLabel.setForeground(Color.RED);
        bottomPanel.add(errorLabel, BorderLayout.NORTH);

        timeLabel = new JLabel("Computation Time:");
        bottomPanel.add(timeLabel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 5));
        JButton convertButton = new JButton("Convert to Hex");
        JButton recoverButton = new JButton("Recover Text");
        JButton clearButton = new JButton("Clear");

        convertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                convertToHex();
            }
        });

        recoverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                recoverText();
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFields();
            }
        });

        buttonPanel.add(convertButton);
        buttonPanel.add(recoverButton);
        buttonPanel.add(clearButton);

        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void convertToHex() {
        String inputText = inputField.getText().trim();
        clearErrors();

        if (inputText.isEmpty()) {
            errorLabel.setText("Error: Please enter some text!");
            return;
        }

        try {
            long startTime = System.nanoTime();

            byte[] byteArray = inputText.getBytes(StandardCharsets.UTF_8);
            StringBuilder byteBuilder = new StringBuilder();
            for (byte b : byteArray) {
                byteBuilder.append(b).append(" ");
            }

            String hexRepresentation = stringToHex(inputText);
            long endTime = System.nanoTime();

            byteOutputArea.setText(byteBuilder.toString().trim());
            hexOutputArea.setText(hexRepresentation);
            double timeTaken = (endTime - startTime) / 1000000.0;
            timeLabel.setText(String.format("Computation Time: %.3f ms", timeTaken));
        } catch (Exception ex) {
            errorLabel.setText("Error: Unable to convert text to hex.");
        }
    }

    private void recoverText() {
        String hexText = hexOutputArea.getText().trim();
        clearErrors();

        if (hexText.isEmpty()) {
            errorLabel.setText("Error: No hexadecimal data to recover!");
            return;
        }

        try {
            long startTime = System.nanoTime();
            String recoveredText = hexToString(hexText);
            long endTime = System.nanoTime();

            recoveredTextArea.setText(recoveredText);
            double timeTaken = (endTime - startTime) / 1000000.0;
            timeLabel.setText(String.format("Computation Time: %.3f ms", timeTaken));
        } catch (Exception ex) {
            errorLabel.setText("Error: Invalid hexadecimal string!");
        }
    }

    private void clearFields() {
        inputField.setText("");
        byteOutputArea.setText("");
        hexOutputArea.setText("");
        recoveredTextArea.setText("");
        clearErrors();
        timeLabel.setText("Computation Time:");

        inputField.requestFocus();
    }

    private void clearErrors() {
        errorLabel.setText(" ");
    }

    private String stringToHex(String input) {
        byte[] byteArray = input.getBytes(StandardCharsets.UTF_8);
        StringBuilder hexBuilder = new StringBuilder();
        for (byte b : byteArray) {
            hexBuilder.append(String.format("%02X", b));
        }
        return hexBuilder.toString();
    }

    private String hexToString(String hexString) {
        int length = hexString.length();
        byte[] byteArray = new byte[length / 2];
        for (int i = 0; i < length; i += 2) {
            byteArray[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4)
                    + Character.digit(hexString.charAt(i + 1), 16));
        }
        return new String(byteArray, StandardCharsets.UTF_8);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BytesToHex app = new BytesToHex();
            app.setVisible(true);
        });
    }
}
