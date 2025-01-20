package com.example.firstProject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PerfectNumbers extends JFrame {
    private JTextField inputField;
    private JButton calculateButton;
    private JPanel resultPanel;
    private JButton clearButton;
    private JLabel timeLabel;

    public PerfectNumbers() {
        setTitle("Perfect Numbers Finder");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputField = new JTextField(10);
        calculateButton = new JButton("Calculate");

        inputPanel.add(new JLabel("Enter a number: "));
        inputPanel.add(inputField);
        inputPanel.add(calculateButton);

        add(inputPanel, BorderLayout.NORTH);

        resultPanel = new JPanel();
        resultPanel.setLayout(new FlowLayout());
        JScrollPane scrollPane = new JScrollPane(resultPanel);
        add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));

        timeLabel = new JLabel("Computation Time: ");
        timeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        bottomPanel.add(timeLabel);

        clearButton = new JButton("Clear");
        clearButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        bottomPanel.add(clearButton);

        add(bottomPanel, BorderLayout.SOUTH);

        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayPerfectNumbers();
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearResults();
            }
        });
    }

    private void displayPerfectNumbers() {
        resultPanel.removeAll();

        try {
            int number = Integer.parseInt(inputField.getText());
            if (number <= 0) {
                throw new NumberFormatException();
            }

            long startTime = System.currentTimeMillis();

            for (int i = 1; i <= number; i++) {
                if (isPerfectNumber(i)) {
                    JLabel numberLabel = new JLabel(String.valueOf(i));
                    numberLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
                    numberLabel.setOpaque(true);
                    numberLabel.setBackground(Color.CYAN);
                    numberLabel.setPreferredSize(new Dimension(50, 30));
                    numberLabel.setHorizontalAlignment(SwingConstants.CENTER);
                    resultPanel.add(numberLabel);
                }
            }

            long endTime = System.currentTimeMillis();
            long duration = (endTime - startTime) * 1000;

            timeLabel.setText("Computation Time: " + duration + " µs");

        } catch (NumberFormatException ex) {
            JLabel errorLabel = new JLabel("Invalid Input. Please enter a positive integer.");
            errorLabel.setForeground(Color.RED);
            resultPanel.add(errorLabel);
            timeLabel.setText("Computation Time: ");
        }

        resultPanel.revalidate();
        resultPanel.repaint();
    }

    private boolean isPerfectNumber(int num) {
        int sum = 1; 
        for (int i = 2; i <= num / 2; i++) {
            if (num % i == 0) {
                sum += i;
            }
        }
        return sum == num && num != 1;
    }

    private void clearResults() {
        inputField.setText("");
        inputField.requestFocusInWindow();
        resultPanel.removeAll();
        timeLabel.setText("Computation Time: ");
        resultPanel.revalidate();
        resultPanel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new PerfectNumbers().setVisible(true);
            }
        });
    }
}
