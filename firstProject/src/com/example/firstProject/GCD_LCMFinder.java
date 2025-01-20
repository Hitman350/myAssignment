package com.example.firstProject;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GCD_LCMFinder extends JFrame {

    private JTextField num1Field;
    private JTextField num2Field;
    private JLabel gcdLabel;
    private JLabel lcmLabel;
    private JLabel gcdTimeLabel;
    private JLabel lcmTimeLabel;
    private JLabel errorLabel1;
    private JLabel errorLabel2;

    public GCD_LCMFinder() {
        setTitle("GCD and LCM Calculator");
        setSize(400, 450);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Input Numbers", TitledBorder.LEFT, TitledBorder.TOP));
        GridBagConstraints gbcInput = new GridBagConstraints();
        gbcInput.insets = new Insets(5, 5, 5, 5);

        gbcInput.gridx = 0;
        gbcInput.gridy = 0;
        gbcInput.anchor = GridBagConstraints.EAST;
        inputPanel.add(new JLabel("Number 1:"), gbcInput);

        num1Field = new JTextField(10);
        gbcInput.gridx = 1;
        gbcInput.anchor = GridBagConstraints.WEST;
        inputPanel.add(num1Field, gbcInput);

        gbcInput.gridx = 0;
        gbcInput.gridy = 1;
        gbcInput.anchor = GridBagConstraints.EAST;
        inputPanel.add(new JLabel("Number 2:"), gbcInput);

        num2Field = new JTextField(10);
        gbcInput.gridx = 1;
        gbcInput.anchor = GridBagConstraints.WEST;
        inputPanel.add(num2Field, gbcInput);

        errorLabel1 = new JLabel("");
        errorLabel1.setForeground(Color.RED);
        gbcInput.gridx = 0;
        gbcInput.gridy = 2;
        gbcInput.gridwidth = 2;
        gbcInput.anchor = GridBagConstraints.CENTER;
        inputPanel.add(errorLabel1, gbcInput);

        errorLabel2 = new JLabel("");
        errorLabel2.setForeground(Color.RED);
        gbcInput.gridy = 3;
        inputPanel.add(errorLabel2, gbcInput);

        JButton calculateButton = new JButton("Calculate");
        gbcInput.gridy = 4;
        gbcInput.anchor = GridBagConstraints.CENTER;
        inputPanel.add(calculateButton, gbcInput);

        JPanel resultPanel = new JPanel(new GridBagLayout());
        resultPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Results", TitledBorder.LEFT, TitledBorder.TOP));
        GridBagConstraints gbcResult = new GridBagConstraints();
        gbcResult.insets = new Insets(5, 5, 5, 5);

        gcdLabel = new JLabel("GCD: ");
        gbcResult.gridx = 0;
        gbcResult.gridy = 0;
        gbcResult.gridwidth = 2;
        resultPanel.add(gcdLabel, gbcResult);

        lcmLabel = new JLabel("LCM: ");
        gbcResult.gridy = 1;
        resultPanel.add(lcmLabel, gbcResult);

        gcdTimeLabel = new JLabel("GCD Time: ");
        gbcResult.gridy = 2;
        resultPanel.add(gcdTimeLabel, gbcResult);

        lcmTimeLabel = new JLabel("LCM Time: ");
        gbcResult.gridy = 3;
        resultPanel.add(lcmTimeLabel, gbcResult);

        JButton clearButton = new JButton("Clear");
        gbcResult.gridy = 4;
        gbcResult.gridwidth = 2;
        resultPanel.add(clearButton, gbcResult);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(inputPanel, gbc);

        gbc.gridy = 1;
        add(resultPanel, gbc);

        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateGcdLcm();
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFields();
            }
        });
    }

    private void calculateGcdLcm() {
        boolean valid = true;
        errorLabel1.setText("");
        errorLabel2.setText("");

        try {
            int num1 = Integer.parseInt(num1Field.getText());
        } catch (NumberFormatException ex) {
            errorLabel1.setText("Please enter a valid integer for Number 1");
            valid = false;
        }

        try {
            int num2 = Integer.parseInt(num2Field.getText());
        } catch (NumberFormatException ex) {
            errorLabel2.setText("Please enter a valid integer for Number 2");
            valid = false;
        }

        if (!valid) {
            return;
        }

        int num1 = Integer.parseInt(num1Field.getText());
        int num2 = Integer.parseInt(num2Field.getText());

        long startTime = System.nanoTime();
        int gcd = gcd(num1, num2);
        long endTime = System.nanoTime();
        long gcdTime = (endTime - startTime) / 1000;

        startTime = System.nanoTime();
        int lcm = lcm(num1, num2, gcd);
        endTime = System.nanoTime();
        long lcmTime = (endTime - startTime) / 1000;

        gcdLabel.setText("GCD: " + gcd);
        lcmLabel.setText("LCM: " + lcm);
        gcdTimeLabel.setText("GCD Time: " + gcdTime + " µs");
        lcmTimeLabel.setText("LCM Time: " + lcmTime + " µs");
    }

    private void clearFields() {
        num1Field.setText("");
        num2Field.setText("");
        errorLabel1.setText("");
        errorLabel2.setText("");
        gcdLabel.setText("GCD: ");
        lcmLabel.setText("LCM: ");
        gcdTimeLabel.setText("GCD Time: ");
        lcmTimeLabel.setText("LCM Time: ");
    }

    private int gcd(int num1, int num2) {
        int ln;
        int sn;
        int r;
        if (num2 > num1) {
            ln = num2;
            sn = num1;
        } else {
            ln = num1;
            sn = num2;
        }
        while (sn != 0) {
            r = sn;
            sn = ln % sn;
            ln = r;
        }
        return ln;
    }

    private int lcm(int a, int b, int gcd) {
        return (a * b) / gcd;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GCD_LCMFinder().setVisible(true);
            }
        });
    }
}
