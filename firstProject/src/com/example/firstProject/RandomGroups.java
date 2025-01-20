package com.example.firstProject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RandomGroups extends JFrame {

    private JTextField groupSizeField;
    private JTextField lengthField;
    private JTextField arrayField;
    private JTextArea resultArea;
    private JLabel timeLabel;
    private JLabel errorLabel;

    public RandomGroups() {
        setTitle("Random Groups");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setLayout(new BorderLayout(10, 10));
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        inputPanel.add(new JLabel("Please Enter Array Length:"));
        lengthField = new JTextField();
        inputPanel.add(lengthField);

        inputPanel.add(new JLabel("Please Enter Numbers to Fill the Array:"));
        arrayField = new JTextField();
        inputPanel.add(arrayField);

        inputPanel.add(new JLabel("Please Enter Group Size:"));
        groupSizeField = new JTextField();
        inputPanel.add(groupSizeField);

        add(inputPanel, BorderLayout.NORTH);

        resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Arial", Font.PLAIN, 16));
        JScrollPane scrollPane = new JScrollPane(resultArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Random Group"));
        add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        errorLabel = new JLabel(" ");
        errorLabel.setForeground(Color.RED);
        bottomPanel.add(errorLabel);

        timeLabel = new JLabel("Computation time:");
        bottomPanel.add(timeLabel);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton generateButton = new JButton("Generate");
        JButton clearButton = new JButton("Clear");

        generateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateRandomGroup();
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearFields();
            }
        });

        buttonPanel.add(generateButton);
        buttonPanel.add(clearButton);

        bottomPanel.add(buttonPanel);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void generateRandomGroup() {
        String groupSizeText = groupSizeField.getText().trim();
        String lengthText = lengthField.getText().trim();
        String arrayText = arrayField.getText().trim();

        resultArea.setText("");
        errorLabel.setText(" ");
        timeLabel.setText("Computation time:");

        try {
            int groupSize = Integer.parseInt(groupSizeText);
            int length = Integer.parseInt(lengthText);

            if (groupSize < 0) {
                errorLabel.setText("Error: Group size cannot be negative.");
                return;
            }
            if (length < 0) {
                errorLabel.setText("Error: Array length cannot be negative.");
                return;
            }

            String[] arrayElements = arrayText.split(",");

            if (arrayElements.length != length) {
                errorLabel.setText("Error: Number of elements does not match the specified length.");
                return;
            }

            if (groupSize > length) {
                errorLabel.setText("Error: Group size cannot exceed array length.");
                return;
            }

            List<Integer> array = new ArrayList<>();
            for (String element : arrayElements) {
                array.add(Integer.parseInt(element.trim()));
            }

            long startTime = System.nanoTime();
            Collections.shuffle(array);
            List<Integer> randomGroup = array.subList(0, groupSize);
            long endTime = System.nanoTime();

            double timeTaken = (endTime - startTime) / 1_000_000.0;
            resultArea.setText(randomGroup.toString());
            timeLabel.setText(String.format("Computation time: %.3f ms", timeTaken));
        } catch (NumberFormatException ex) {
            errorLabel.setText("Error: Invalid input! Please enter valid integers.");
        }
    }

    private void clearFields() {
        groupSizeField.setText("");
        lengthField.setText("");
        arrayField.setText("");
        resultArea.setText("");
        errorLabel.setText(" ");
        timeLabel.setText("Computation time:");

        lengthField.requestFocus();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RandomGroups app = new RandomGroups();
            app.setVisible(true);
        });
    }
}
