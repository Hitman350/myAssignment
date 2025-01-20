package com.example.firstProject;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class PasswordGenerator extends JFrame {
    private JCheckBox lowercaseCheck, uppercaseCheck, numbersCheck, specialCheck, requireLowercase, requireUppercase, requireNumbers, requireSpecial;
    private JTextField minLengthField, maxLengthField, customCharsField;
    private JTextArea passwordField;
    private JButton generateButton, resetButton;

    public PasswordGenerator() {
        setTitle("Password Generator");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));
        setResizable(false);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JPanel characterPanel = new JPanel(new GridLayout(5, 3, 5, 5));
        characterPanel.setBorder(BorderFactory.createTitledBorder("Character Options"));

        characterPanel.add(new JLabel("Parameter"));
        characterPanel.add(new JLabel("Allow"));
        characterPanel.add(new JLabel("Require"));

        characterPanel.add(new JLabel("Lowercase (abc):"));
        lowercaseCheck = new JCheckBox();
        requireLowercase = new JCheckBox();
        characterPanel.add(lowercaseCheck);
        characterPanel.add(requireLowercase);

        characterPanel.add(new JLabel("Uppercase (ABC):"));
        uppercaseCheck = new JCheckBox();
        requireUppercase = new JCheckBox();
        characterPanel.add(uppercaseCheck);
        characterPanel.add(requireUppercase);

        characterPanel.add(new JLabel("Numbers (123):"));
        numbersCheck = new JCheckBox();
        requireNumbers = new JCheckBox();
        characterPanel.add(numbersCheck);
        characterPanel.add(requireNumbers);

        characterPanel.add(new JLabel("Special (%$#):"));
        specialCheck = new JCheckBox();
        requireSpecial = new JCheckBox();
        characterPanel.add(specialCheck);
        characterPanel.add(requireSpecial);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(characterPanel, gbc);

        JPanel lengthPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        lengthPanel.setBorder(BorderFactory.createTitledBorder("Password Length"));

        lengthPanel.add(new JLabel("Min Length:"));
        minLengthField = new JTextField("");
        lengthPanel.add(minLengthField);

        lengthPanel.add(new JLabel("Max Length:"));
        maxLengthField = new JTextField("");
        lengthPanel.add(maxLengthField);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        mainPanel.add(lengthPanel, gbc);

        JPanel customCharPanel = new JPanel(new BorderLayout());
        customCharPanel.setBorder(BorderFactory.createTitledBorder("Custom Characters"));
        customCharsField = new JTextField();
        customCharPanel.add(customCharsField, BorderLayout.CENTER);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        mainPanel.add(customCharPanel, gbc);

        generateButton = new JButton("Generate");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        mainPanel.add(generateButton, gbc);

        passwordField = new JTextArea(1, 20);
        passwordField.setEditable(false);
        passwordField.setFont(new Font("SansSerif", Font.BOLD, 16));
        passwordField.setLineWrap(false);
        passwordField.setWrapStyleWord(false);

        JScrollPane passwordScrollPane = new JScrollPane(passwordField);
        passwordScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        passwordScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        mainPanel.add(passwordScrollPane, gbc);

        JLabel errorMessageLabel = new JLabel();
        errorMessageLabel.setForeground(Color.RED);
        errorMessageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        mainPanel.add(errorMessageLabel, gbc);

        JPanel resetPanel = new JPanel();
        resetButton = new JButton("Reset");
        resetPanel.add(resetButton);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        mainPanel.add(resetPanel, gbc);

        add(mainPanel, BorderLayout.CENTER);

        generateButton.addActionListener(e -> {
            errorMessageLabel.setText(""); 
            try {
                int minLength = Integer.parseInt(minLengthField.getText());
                int maxLength = Integer.parseInt(maxLengthField.getText());

                if (minLength <= 0 || maxLength <= 0 || minLength > maxLength) {
                    errorMessageLabel.setText("Invalid length values!");
                    return;
                }

                String password = generatePassword(minLength, maxLength);
                if (password.equals("Select at least one character type!")) {
                    errorMessageLabel.setText(password);
                } else {
                    passwordField.setText(password);
                }
            } catch (NumberFormatException ex) {
                errorMessageLabel.setText("Invalid length values!");
            }
        });

        resetButton.addActionListener(e -> resetFields(errorMessageLabel)); 

        setVisible(true);
    }

    private String generatePassword(int minLength, int maxLength) {
        Random random = new Random();
        int length = random.nextInt(maxLength - minLength + 1) + minLength;

        String lowercase = lowercaseCheck.isSelected() ? "abcdefghijklmnopqrstuvwxyz" : "";
        String uppercase = uppercaseCheck.isSelected() ? "ABCDEFGHIJKLMNOPQRSTUVWXYZ" : "";
        String numbers = numbersCheck.isSelected() ? "0123456789" : "";
        String special = specialCheck.isSelected() ? "!@#$%^&*()-_=+[]{}|;:'\",.<>?/`~" : "";

        String allowedChars = lowercase + uppercase + numbers + special + customCharsField.getText();
        if (allowedChars.isEmpty()) {
            return "Select at least one character type!";
        }

        StringBuilder password = new StringBuilder();

        if (requireLowercase.isSelected() && !lowercase.isEmpty()) {
            password.append(lowercase.charAt(random.nextInt(lowercase.length())));
        }
        if (requireUppercase.isSelected() && !uppercase.isEmpty()) {
            password.append(uppercase.charAt(random.nextInt(uppercase.length())));
        }
        if (requireNumbers.isSelected() && !numbers.isEmpty()) {
            password.append(numbers.charAt(random.nextInt(numbers.length())));
        }
        if (requireSpecial.isSelected() && !special.isEmpty()) {
            password.append(special.charAt(random.nextInt(special.length())));
        }

        while (password.length() < length) {
            password.append(allowedChars.charAt(random.nextInt(allowedChars.length())));
        }

        char[] passwordArray = password.toString().toCharArray();
        for (int i = 0; i < passwordArray.length; i++) {
            int randomIndex = random.nextInt(passwordArray.length);
            char temporary = passwordArray[i];
            passwordArray[i] = passwordArray[randomIndex];
            passwordArray[randomIndex] = temporary;
        }

        return new String(passwordArray);
    }

    private void resetFields(JLabel errorMessageLabel) {
        lowercaseCheck.setSelected(false);
        uppercaseCheck.setSelected(false);
        numbersCheck.setSelected(false);
        specialCheck.setSelected(false);
        requireLowercase.setSelected(false);
        requireUppercase.setSelected(false);
        requireNumbers.setSelected(false);
        requireSpecial.setSelected(false);
        minLengthField.setText("");
        maxLengthField.setText("");
        customCharsField.setText("");
        passwordField.setText("");
        errorMessageLabel.setText("");

        minLengthField.requestFocus();
    }

    public static void main(String[] args) {
        new PasswordGenerator();
    }
}
