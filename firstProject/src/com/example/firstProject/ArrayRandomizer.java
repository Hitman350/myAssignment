package com.example.firstProject;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.Collections;

public class ArrayRandomizer extends JFrame {
    private JTextField sizeField, inputField;
    private JTextArea originalArray, randomizedArray;
    private JLabel errorLabel;
    private JButton randomizeButton;

    public ArrayRandomizer() {
        setTitle("Array Randomizer");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 1));

        add(new JLabel("Enter array size:"));
        sizeField = new JTextField();
        add(sizeField);

        add(new JLabel("Enter array elements (comma-separated):"));
        inputField = new JTextField();
        add(inputField);

        randomizeButton = new JButton("Randomize");
        add(randomizeButton);

        originalArray = new JTextArea();
        randomizedArray = new JTextArea();
        JPanel arrayPanel = new JPanel(new GridLayout(1, 2));
        arrayPanel.add(new JScrollPane(originalArray));
        arrayPanel.add(new JScrollPane(randomizedArray));
        add(arrayPanel);

        errorLabel = new JLabel();
        errorLabel.setForeground(Color.RED);
        add(errorLabel);

        randomizeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                randomizeArray();
            }
        });
    }

    private void randomizeArray() {
        errorLabel.setText("");
        String sizeStr = sizeField.getText().trim();
        String inputStr = inputField.getText().trim();

        try {
            int size = Integer.parseInt(sizeStr);
            if (size <= 0) {
                throw new IllegalArgumentException("Array size must be a positive integer.");
            }

            String[] elements = inputStr.split(",");
            if (elements.length != size) {
                throw new IllegalArgumentException("Number of elements doesn't match the specified size.");
            }

            Integer[] array = new Integer[size];
            for (int i = 0; i < size; i++) {
                array[i] = Integer.parseInt(elements[i].trim());
            }

            originalArray.setText("Original Array:\n" + Arrays.toString(array));

            Collections.shuffle(Arrays.asList(array));
            randomizedArray.setText("Randomized Array:\n" + Arrays.toString(array));

        } catch (NumberFormatException e) {
            errorLabel.setText("Invalid input: Please enter valid numbers.");
        } catch (IllegalArgumentException e) {
            errorLabel.setText(e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ArrayRandomizer().setVisible(true);
            }
        });
    }
}
