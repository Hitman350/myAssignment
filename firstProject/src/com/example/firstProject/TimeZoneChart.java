package com.example.firstProject;

import javax.swing.*;
import com.toedter.calendar.JDateChooser;
import java.awt.*;
import java.util.*;
import java.text.SimpleDateFormat;

public class TimeZoneChart extends JFrame {
    private JDateChooser dateChooser;
    private JComboBox<String> timeZone1ComboBox;
    private JComboBox<String> timeZone2ComboBox;
    private JTable comparisonTable;

    public TimeZoneChart() {
        setTitle("Time Zone Charts");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout(10, 10));

        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        dateChooser = new JDateChooser();
        dateChooser.setDate(new Date());
        dateChooser.getDateEditor().addPropertyChangeListener(e -> updateComparison());
        inputPanel.add(new JLabel("Date:"), gbc);
        gbc.gridx++;
        inputPanel.add(dateChooser, gbc);

        String[] timeZones = getCommonTimeZonesWithOffsets();
        timeZone1ComboBox = new JComboBox<>(timeZones);
        timeZone2ComboBox = new JComboBox<>(timeZones);

        gbc.gridx = 0;
        gbc.gridy++;
        inputPanel.add(new JLabel("Time Zone 1:"), gbc);
        gbc.gridx++;
        inputPanel.add(timeZone1ComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        inputPanel.add(new JLabel("Time Zone 2:"), gbc);
        gbc.gridx++;
        inputPanel.add(timeZone2ComboBox, gbc);

        timeZone1ComboBox.addActionListener(e -> updateComparison());
        timeZone2ComboBox.addActionListener(e -> updateComparison());

        add(inputPanel, BorderLayout.NORTH);

        comparisonTable = new JTable();
        comparisonTable.setFont(new Font("Arial", Font.PLAIN, 14));
        comparisonTable.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(comparisonTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(scrollPane, BorderLayout.CENTER);

        timeZone1ComboBox.setSelectedItem("Asia/Kolkata (GMT+05:30)");
        timeZone2ComboBox.setSelectedItem("America/New_York (GMT-05:00)");

        updateComparison();
    }

    private void updateComparison() {
        Date selectedDate = dateChooser.getDate();
        String timeZone1 = extractTimeZoneId((String) timeZone1ComboBox.getSelectedItem());
        String timeZone2 = extractTimeZoneId((String) timeZone2ComboBox.getSelectedItem());

        TimeZone tz1 = TimeZone.getTimeZone(timeZone1);
        TimeZone tz2 = TimeZone.getTimeZone(timeZone2);

        String[] columnNames = {timeZone1, timeZone2};
        String[][] data = new String[24][2];

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Calendar cal = Calendar.getInstance(tz1);
        cal.setTime(selectedDate);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);

        for (int i = 0; i < 24; i++) {
            sdf.setTimeZone(tz1);
            data[i][0] = sdf.format(cal.getTime());

            sdf.setTimeZone(tz2);
            data[i][1] = sdf.format(cal.getTime());

            cal.add(Calendar.HOUR_OF_DAY, 1);
        }

        comparisonTable.setModel(new javax.swing.table.DefaultTableModel(data, columnNames));

        Calendar localCal = Calendar.getInstance();
        int currentHour = localCal.get(Calendar.HOUR_OF_DAY);
        comparisonTable.setRowSelectionInterval(currentHour, currentHour);
    }

    private String[] getCommonTimeZonesWithOffsets() {
        String[] commonZones = {
            "Asia/Tokyo", "Asia/Shanghai", "Asia/Singapore", "Asia/Kolkata", "Australia/Sydney",
            "Asia/Dhaka", "Asia/Bangkok", "Asia/Kuala_Lumpur", "Asia/Tokyo",
            "America/New_York", "America/Chicago", "America/Denver", "America/Los_Angeles",
            "Europe/London", "Europe/Paris", "Europe/Berlin", "Europe/Moscow",
            "Pacific/Guam", "Pacific/Fiji", "Pacific/Auckland",
        };

        java.util.List<String> zonesWithOffsets = new ArrayList<>();
        for (String zoneId : commonZones) {
            TimeZone tz = TimeZone.getTimeZone(zoneId);
            int offset = tz.getRawOffset() + (tz.inDaylightTime(new Date()) ? tz.getDSTSavings() : 0);
            String offsetString = String.format("GMT%+03d:%02d", offset / (60 * 60 * 1000), Math.abs(offset / (60 * 1000) % 60));
            zonesWithOffsets.add(zoneId + " (" + offsetString + ")");
        }
        return zonesWithOffsets.toArray(new String[0]);
    }

    private String extractTimeZoneId(String displayString) {
        return displayString.split(" \\(")[0];
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> {
            TimeZoneChart app = new TimeZoneChart();
            app.setVisible(true);
        });
    }
}
