package com.example.firstProject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class RandomItem extends JFrame {
    private JTextField lengthField;
    private JTextField numbersField;
    private JButton submitLengthButton;
    private JButton submitNumbersButton;
    private JButton clearButton;
    private JLabel resultLabel;
    private JLabel errorLabel;
    private JLabel timeLabel;
    private int arrayLength;
    private long startTime;

    public RandomItem() {
        setTitle("Random Items");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Enter array length:"), gbc);

        lengthField = new JTextField(10);
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(lengthField, gbc);

        submitLengthButton = new JButton("Submit Length");
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        add(submitLengthButton, gbc);

        submitLengthButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    arrayLength = Integer.parseInt(lengthField.getText());
                    errorLabel.setText("");
                    createNumberField(arrayLength);
                } catch (NumberFormatException ex) {
                    errorLabel.setText("Error: Please enter a valid number for the array length.");
                }
            }
        });

        resultLabel = new JLabel("");
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        add(resultLabel, gbc);

        errorLabel = new JLabel("");
        errorLabel.setForeground(Color.RED);
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        add(errorLabel, gbc);

        clearButton = new JButton("Clear");
        gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        add(clearButton, gbc);

        clearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lengthField.setText("");
                numbersField.setText("");
                resultLabel.setText("");
                errorLabel.setText("");
                timeLabel.setText("");
            }
        });

        timeLabel = new JLabel("Computation time: ");
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        add(timeLabel, gbc);

        setVisible(true);
    }

    private void createNumberField(int length) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        add(new JLabel("Enter numbers separated by commas:"), gbc);

        numbersField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        add(numbersField, gbc);

        submitNumbersButton = new JButton("Submit Numbers");
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        add(submitNumbersButton, gbc);

        submitNumbersButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startTime = System.nanoTime();
                checkAndDisplayRandomNumber();
            }
        });

        revalidate();
        repaint();
    }

    private void checkAndDisplayRandomNumber() {
        String[] numString = numbersField.getText().split(",");
        if (numString.length != arrayLength) {
            errorLabel.setText("Number of inputs does not match array length.");
            resultLabel.setText("");
            timeLabel.setText("");
            return;
        } else {
            errorLabel.setText("");
        }

        int[] num = new int[numString.length];
        for (int i = 0; i < numString.length; i++) {
            num[i] = Integer.parseInt(numString[i].trim());
        }

        Random rand = new Random();
        int randomIndex = rand.nextInt(num.length);
        int randomNumber = num[randomIndex];

        long endTime = System.nanoTime();
        long duration = (endTime - startTime)/1000;

        resultLabel.setText("Random Number: " + randomNumber);
        timeLabel.setText("Computation Time: " + duration + " µs");
    }

    public static void main(String[] args) {
        long startTime = System.nanoTime();
        new RandomItem();
        long endTime = System.nanoTime();
        long duration = endTime - startTime;
        System.out.println("GUI Setup Time: " + duration + " ns");
    }
}
