package com.example.firstProject;

import javax.swing.*;
import com.toedter.calendar.JDateChooser;
import java.awt.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class CalculatingDuration extends JFrame {
    private JDateChooser startDateChooser;
    private JSpinner startTimeSpinner;
    private JDateChooser endDateChooser;
    private JSpinner endTimeSpinner;
    private JTextPane resultPane;
    private JButton calculateButton;
    private JButton resetButton;

    public CalculatingDuration() {
        setTitle("Calculating Duration");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout(10, 10));

        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);

        startDateChooser = new JDateChooser();
        startDateChooser.setDate(new Date());
        startDateChooser.setPreferredSize(new Dimension(200, 30));
        startTimeSpinner = new JSpinner(new SpinnerDateModel(new Date(), null, null, Calendar.MINUTE));
        JSpinner.DateEditor startTimeEditor = new JSpinner.DateEditor(startTimeSpinner, "HH:mm");
        startTimeSpinner.setEditor(startTimeEditor);
        startTimeSpinner.setPreferredSize(new Dimension(100, 30));

        inputPanel.add(new JLabel("Start Date:"), gbc);
        gbc.gridx++;
        inputPanel.add(startDateChooser, gbc);
        gbc.gridx++;
        inputPanel.add(new JLabel("Start Time:"), gbc);
        gbc.gridx++;
        inputPanel.add(startTimeSpinner, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        endDateChooser = new JDateChooser();
        endDateChooser.setDate(new Date());
        endDateChooser.setPreferredSize(new Dimension(200, 30));
        endTimeSpinner = new JSpinner(new SpinnerDateModel(new Date(), null, null, Calendar.MINUTE));
        JSpinner.DateEditor endTimeEditor = new JSpinner.DateEditor(endTimeSpinner, "HH:mm");
        endTimeSpinner.setEditor(endTimeEditor);
        endTimeSpinner.setPreferredSize(new Dimension(100, 30));

        inputPanel.add(new JLabel("End Date:"), gbc);
        gbc.gridx++;
        inputPanel.add(endDateChooser, gbc);
        gbc.gridx++;
        inputPanel.add(new JLabel("End Time:"), gbc);
        gbc.gridx++;
        inputPanel.add(endTimeSpinner, gbc);

        calculateButton = new JButton("Calculate");
        resetButton = new JButton("Clear");

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.EAST;
        inputPanel.add(calculateButton, gbc);
        gbc.gridx = 2;
        gbc.anchor = GridBagConstraints.WEST;
        inputPanel.add(resetButton, gbc);

        add(inputPanel, BorderLayout.NORTH);

        resultPane = new JTextPane();
        resultPane.setEditable(false);
        resultPane.setFont(new Font("Arial", Font.PLAIN, 16));
        add(new JScrollPane(resultPane), BorderLayout.CENTER);

        calculateButton.addActionListener(e -> calculateTimeDifference());
        resetButton.addActionListener(e -> resetFields());
    }

    private void calculateTimeDifference() {
        Date startDate = startDateChooser.getDate();
        Date endDate = endDateChooser.getDate();

        if (startDate == null || endDate == null) {
            setErrorText("Please select both start and end dates.");
            return;
        }

        Calendar startCal = Calendar.getInstance();
        startCal.setTime(startDate);
        Calendar endCal = Calendar.getInstance();
        endCal.setTime(endDate);

        Calendar startTimeCal = Calendar.getInstance();
        startTimeCal.setTime((Date) startTimeSpinner.getValue());
        startCal.set(Calendar.HOUR_OF_DAY, startTimeCal.get(Calendar.HOUR_OF_DAY));
        startCal.set(Calendar.MINUTE, startTimeCal.get(Calendar.MINUTE));

        Calendar endTimeCal = Calendar.getInstance();
        endTimeCal.setTime((Date) endTimeSpinner.getValue());
        endCal.set(Calendar.HOUR_OF_DAY, endTimeCal.get(Calendar.HOUR_OF_DAY));
        endCal.set(Calendar.MINUTE, endTimeCal.get(Calendar.MINUTE));

        if (endCal.before(startCal)) {
            setErrorText("End date and time must be after start date and time.");
            return;
        }

        long startTime = System.nanoTime();

        long differenceMillis = endCal.getTimeInMillis() - startCal.getTimeInMillis();
        long differenceHours = differenceMillis / (1000 * 60 * 60);

        TimeZone utc = TimeZone.getTimeZone("UTC");
        SimpleDateFormat utcFormat = new SimpleDateFormat("EEEE, yyyy-MM-dd HH:mm");
        utcFormat.setTimeZone(utc);

        String startUTC = utcFormat.format(startCal.getTime());
        String endUTC = utcFormat.format(endCal.getTime());

        long utcDifferenceMillis = differenceMillis;
        long utcDifferenceHours = utcDifferenceMillis / (1000 * 60 * 60);

        long endTime = System.nanoTime();
        double computationTime = (endTime - startTime) / 1e6;

        DecimalFormat df = new DecimalFormat("0.000");

        StringBuilder result = new StringBuilder();
        result.append("Selected Times:\n");
        result.append("Start: ").append(startCal.getTime()).append("\n");
        result.append("End: ").append(endCal.getTime()).append("\n");
        result.append("Time Difference: ").append(differenceHours).append(" hours\n\n");
        result.append("Start in UTC: ").append(startUTC).append("\n");
        result.append("End in UTC: ").append(endUTC).append("\n");
        result.append("Time Difference (UTC): ").append(utcDifferenceHours).append(" hours\n\n");
        result.append("Computation Time: ").append(df.format(computationTime)).append(" ms");

        setNormalText(result.toString());
    }

    private void setErrorText(String text) {
        resultPane.setForeground(Color.RED);
        resultPane.setText(text);
    }

    private void setNormalText(String text) {
        resultPane.setForeground(Color.BLACK);
        resultPane.setText(text);
    }

    private void resetFields() {
        startDateChooser.setDate(new Date());
        startTimeSpinner.setValue(new Date());
        endDateChooser.setDate(new Date());
        endTimeSpinner.setValue(new Date());
        setNormalText("");
        startDateChooser.requestFocus();
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> {
            CalculatingDuration app = new CalculatingDuration();
            app.setVisible(true);
        });
    }
}
