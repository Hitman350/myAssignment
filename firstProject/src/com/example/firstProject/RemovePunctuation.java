package com.example.firstProject;

import javax.swing.*;
import java.awt.*;

public class RemovePunctuation extends JFrame {
    private JTextArea inputArea;
    private JTextArea outputArea;
    private JLabel timeLabel;
    private JLabel errorLabel;
    private JButton processButton;
    private JButton clearButton;

    public RemovePunctuation() {
        setTitle("Remove Punctuation");
        setSize(600, 450); 
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        setResizable(false);
        setLocationRelativeTo(null);

        JPanel ioPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        ioPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel inputPanel = new JPanel(new BorderLayout());
        JLabel inputLabel = new JLabel("Input String:");
        inputLabel.setFont(new Font("Arial", Font.BOLD, 14));
        inputPanel.add(inputLabel, BorderLayout.NORTH);
        inputArea = new JTextArea(5, 20);
        inputArea.setFont(new Font("Arial", Font.PLAIN, 16)); 
        inputArea.setLineWrap(true);
        inputArea.setWrapStyleWord(true);
        JScrollPane inputScroll = new JScrollPane(inputArea);
        inputScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        inputScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        inputPanel.add(inputScroll, BorderLayout.CENTER);
        ioPanel.add(inputPanel);

        JPanel outputPanel = new JPanel(new BorderLayout());
        JLabel outputLabel = new JLabel("Output String:");
        outputLabel.setFont(new Font("Arial", Font.BOLD, 14)); 
        outputPanel.add(outputLabel, BorderLayout.NORTH);
        outputArea = new JTextArea(5, 20);
        outputArea.setFont(new Font("Arial", Font.PLAIN, 16)); 
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);
        outputArea.setEditable(false);
        JScrollPane outputScroll = new JScrollPane(outputArea);
        outputScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        outputScroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        outputPanel.add(outputScroll, BorderLayout.CENTER);
        ioPanel.add(outputPanel);

        add(ioPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new BorderLayout(10, 10));

        timeLabel = new JLabel("Computation Time: ");
        timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        timeLabel.setFont(new Font("Arial", Font.BOLD, 12));
        buttonPanel.add(timeLabel, BorderLayout.NORTH);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        processButton = new JButton("Process");
        clearButton = new JButton("Clear");
        buttons.add(processButton);
        buttons.add(clearButton);
        buttonPanel.add(buttons, BorderLayout.CENTER);

        errorLabel = new JLabel("");
        errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        errorLabel.setForeground(Color.RED);
        errorLabel.setFont(new Font("Arial", Font.PLAIN, 12)); 
        buttonPanel.add(errorLabel, BorderLayout.SOUTH);

        add(buttonPanel, BorderLayout.SOUTH);

        processButton.addActionListener(e -> {
            errorLabel.setText(""); 
            String inputText = inputArea.getText().trim();

            if (inputText.isEmpty()) {
                errorLabel.setText("Please enter a string!");
                return;
            }

            long startTime = System.nanoTime();
            String result = removePunctuation(inputText);
            long endTime = System.nanoTime();

            outputArea.setText(result);
            timeLabel.setText("Computation Time: " + (endTime - startTime) / 1000000.0 + " ms");
        });

        clearButton.addActionListener(e -> {
            inputArea.setText("");
            outputArea.setText("");
            timeLabel.setText("Computation Time: ");
            errorLabel.setText("");

            inputArea.requestFocus();
        });

        setVisible(true);
    }

    private String removePunctuation(String input) {
        return input.replaceAll("\\p{Punct}", "");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(RemovePunctuation::new);
    }
}

