package com.example.firstProject;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ArmstrongNumberFinder extends JFrame {

    private JPanel contentPane;
    private JTextField textField;
    private JTextArea resultArea;
    private JLabel timeLabel;
    private JLabel errorLabel;

    public static void main(String[] args) {
        ArmstrongNumberFinder frame = new ArmstrongNumberFinder();
        frame.setVisible(true);
    }

    public ArmstrongNumberFinder() {
        setTitle("Armstrong Number Finder");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 500, 450);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblMaxNumber = new JLabel("Enter the maximum number:");
        lblMaxNumber.setBounds(30, 30, 200, 25);
        contentPane.add(lblMaxNumber);

        textField = new JTextField();
        textField.setBounds(240, 30, 200, 25);
        contentPane.add(textField);
        textField.setColumns(10);

        JButton ArmstrongNumberbtn = new JButton("Find Armstrong Numbers");
        ArmstrongNumberbtn.setBounds(150, 70, 200, 30);
        contentPane.add(ArmstrongNumberbtn);

        errorLabel = new JLabel("");
        errorLabel.setForeground(Color.RED);
        errorLabel.setBounds(30, 110, 420, 25);
        contentPane.add(errorLabel);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(30, 140, 420, 180);
        contentPane.add(scrollPane);

        resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        scrollPane.setViewportView(resultArea);

        JButton clearButton = new JButton("Clear");
        clearButton.setBounds(150, 330, 200, 30); // Centered the clear button
        contentPane.add(clearButton);

        timeLabel = new JLabel("Computation Time: ");
        timeLabel.setBounds(30, 370, 420, 25);
        contentPane.add(timeLabel);

        ArmstrongNumberbtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                findArmstrongNumbers();
            }
        });

        clearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                clearFields();
            }
        });
    }

    private void findArmstrongNumbers() {
        try {
            errorLabel.setText(""); 
            int maxNumber = Integer.parseInt(textField.getText());
            long startTime = System.currentTimeMillis();
            StringBuilder result = new StringBuilder();

            for (int num = 1; num <= maxNumber; num++) {
                if (isArmstrongNumber(num)) {
                    result.append(num).append("\n");
                }
            }

            long endTime = System.currentTimeMillis();
            long computationTime = (endTime - startTime)*1000;

            resultArea.setText(result.toString());
            timeLabel.setText("Computation Time: " + computationTime + " µs");
        } catch (NumberFormatException ex) {
            errorLabel.setText("Please enter a valid number.");
        }
    }

    private boolean isArmstrongNumber(int number) {
        int originalNumber = number;
        int sum = 0;
        int numberOfDigits = String.valueOf(number).length();

        while (number != 0) {
            int digit = number % 10;
            sum += Math.pow(digit, numberOfDigits);
            number /= 10;
        }

        return sum == originalNumber;
    }

    private void clearFields() {
        textField.setText("");
        resultArea.setText("");
        timeLabel.setText("Computation Time: ");
        errorLabel.setText("");
    }
}
