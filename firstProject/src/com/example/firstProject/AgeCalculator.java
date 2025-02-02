package com.example.firstProject;

import javax.swing.*;
import com.toedter.calendar.JDateChooser;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

public class AgeCalculator extends JFrame implements ActionListener {
    private JDateChooser dateChooser;
    private JLabel ageLabel;
    private JLabel computationTimeLabel;
    private JButton clearButton;

    public AgeCalculator() {
        setTitle("Age Calculator");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);
        setLocationRelativeTo(null);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new GridLayout(2, 1));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel promptLabel = new JLabel("Select your birthdate:", SwingConstants.CENTER);
        dateChooser = new JDateChooser();

        topPanel.add(promptLabel);
        topPanel.add(dateChooser);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel buttonPanel = new JPanel();
        JButton calculateButton = new JButton("Calculate Age");
        clearButton = new JButton("Clear");
        calculateButton.addActionListener(this);
        clearButton.addActionListener(this);
        buttonPanel.add(calculateButton);
        buttonPanel.add(clearButton);

        bottomPanel.add(buttonPanel, BorderLayout.NORTH);

        ageLabel = new JLabel("", SwingConstants.CENTER);
        ageLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        computationTimeLabel = new JLabel("", SwingConstants.CENTER);
        computationTimeLabel.setFont(new Font("Arial", Font.PLAIN, 14));

        bottomPanel.add(ageLabel, BorderLayout.CENTER);
        bottomPanel.add(computationTimeLabel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);
        add(bottomPanel, BorderLayout.CENTER);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Calculate Age")) {
            Date selectedDate = dateChooser.getDate();

            if (selectedDate != null) {
                long startTime = System.nanoTime(); 
                
                LocalDate birthdate = selectedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                LocalDate today = LocalDate.now();

                if (birthdate.isAfter(today)) {
                    ageLabel.setForeground(Color.RED);
                    ageLabel.setText("Error: Birthdate cannot be in the future.");
                    computationTimeLabel.setText(""); 
                } else {
                    Period age = Period.between(birthdate, today);

                    long endTime = System.nanoTime(); 
                    double computationTime = (endTime - startTime) / 1e6; 

                    ageLabel.setForeground(Color.BLACK);
                    ageLabel.setText("You are " + age.getYears() + " years, " + age.getMonths() + " months, and " + age.getDays() + " days old.");
                    
                    computationTimeLabel.setForeground(Color.BLACK);
                    computationTimeLabel.setText("Computation time: " + String.format("%.3f", computationTime) + " ms");
                }
            } else {
                ageLabel.setForeground(Color.RED);
                ageLabel.setText("Please select a valid date.");
                computationTimeLabel.setText(""); 
            }
        } else if (e.getActionCommand().equals("Clear")) {
            dateChooser.setDate(null);
            ageLabel.setText("");
            computationTimeLabel.setText("");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(AgeCalculator::new);
    }
}
