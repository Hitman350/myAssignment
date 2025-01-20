package com.example.firstProject;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CompressedString extends JFrame implements ActionListener {
    private JTextField inputField;
    private JLabel resultLabel;
    private JLabel timeLabel;

    public CompressedString() {
        setTitle("String Compressor");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 10, 5, 10);

        JLabel inputLabel = new JLabel("Enter a string:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(inputLabel, gbc);

        inputField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(inputField, gbc);

        JButton compressButton = new JButton("Compress");
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(compressButton, gbc);

        resultLabel = new JLabel("Compressed String: ");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 3;
        add(resultLabel, gbc);

        timeLabel = new JLabel("Computation Time: ");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 3;
        add(timeLabel, gbc);

        compressButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String input = inputField.getText();
        long startTime = System.nanoTime();
        String compressed = compressString(input);
        long endTime = System.nanoTime();
        long computationTime = (endTime - startTime)/1000;

        resultLabel.setText("Compressed String: " + compressed);
        timeLabel.setText("Computation Time: " + computationTime + " µs");
    }

    private String compressString(String str) {
        if (str == null || str.isEmpty()) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        int count = 1;

        for (int i = 0; i < str.length() - 1; i++) {
            if (str.charAt(i) == str.charAt(i + 1)) {
                count++;
            } else {
                sb.append(str.charAt(i)).append(count);
                count = 1;
            }
        }

        sb.append(str.charAt(str.length() - 1)).append(count);

        return sb.toString();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CompressedString compress = new CompressedString();
            compress.setVisible(true);
        });
    }
}
