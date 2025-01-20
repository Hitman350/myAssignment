package com.example.firstProject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.toedter.calendar.JCalendar;

public class DaysOfTheWeek extends JFrame {
    private JCalendar calendar;
    private JTextField numberField;
    private JTextField dateField;
    private JTextArea resultArea;
    private JLabel errorLabel;
    private JLabel computationTimeLabel;

    public DaysOfTheWeek() {
        setTitle("Days Of The Week");
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel dateLabel = new JLabel("Enter date (mm-dd-yyyy):");
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(dateLabel, gbc);

        dateField = new JTextField(10);
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(dateField, gbc);

        calendar = new JCalendar();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        add(calendar, gbc);

        JLabel numberLabel = new JLabel("Enter number of years:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        add(numberLabel, gbc);

        numberField = new JTextField(5);
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(numberField, gbc);

        JButton calculateButton = new JButton("Calculate");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        add(calculateButton, gbc);

        errorLabel = new JLabel();
        errorLabel.setForeground(Color.RED);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        add(errorLabel, gbc);

        resultArea = new JTextArea(10, 30);
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        add(scrollPane, gbc);

        computationTimeLabel = new JLabel("Computation Time: ");
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        add(computationTimeLabel, gbc);

        calendar.addPropertyChangeListener("calendar", new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                Date selectedDate = calendar.getDate();
                SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
                dateField.setText(sdf.format(selectedDate));
            }
        });

        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateBirthdays();
            }
        });
    }

    private void calculateBirthdays() {
        errorLabel.setText("");
        long startTime = System.currentTimeMillis();
        
        try {
            Date birthDate = null;
            String dateInput = dateField.getText().trim();
            if (!dateInput.isEmpty()) {
                SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
                sdf.setLenient(false);
                birthDate = sdf.parse(dateInput);
            } else {
                birthDate = calendar.getDate();
            }

            int n = Integer.parseInt(numberField.getText());
            Calendar cal = Calendar.getInstance();
            cal.setTime(birthDate);

            SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE");
            StringBuilder result = new StringBuilder();

            for (int i = 0; i < n; i++) {
                cal.add(Calendar.YEAR, 1);
                result.append("Year ").append(i + 1).append(": ")
                .append(dayFormat.format(cal.getTime())).append("\n");
            }

            long endTime = System.currentTimeMillis();
            long computationTime = (endTime - startTime) * 1000;
            computationTimeLabel.setText("Computation Time: " + computationTime + " µs");

            resultArea.setText(result.toString());
        } catch (NumberFormatException ex) {
            errorLabel.setText("Please enter a valid number.");
        } catch (ParseException ex) {
            errorLabel.setText("Please enter a valid date in mm-dd-yyyy format.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new DaysOfTheWeek().setVisible(true);
            }
        });
    }
}
