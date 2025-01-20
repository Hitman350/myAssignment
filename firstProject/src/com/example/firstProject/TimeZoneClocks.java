package com.example.firstProject;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

public class TimeZoneClocks extends JFrame {

    private JLabel sydneyTimeLabel;
    private JLabel parisTimeLabel;
    private JLabel londonTimeLabel;
    private JLabel tokyoTimeLabel;
    private JLabel newYorkTimeLabel;

    public TimeZoneClocks() {
        setTitle("World Clock");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setResizable(false);
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        sydneyTimeLabel = createTimeLabel();
        parisTimeLabel = createTimeLabel();
        londonTimeLabel = createTimeLabel();
        tokyoTimeLabel = createTimeLabel();
        newYorkTimeLabel = createTimeLabel();

        mainPanel.add(createTimePanel("Sydney: ", sydneyTimeLabel));
        mainPanel.add(createTimePanel("Paris: ", parisTimeLabel));
        mainPanel.add(createTimePanel("London: ", londonTimeLabel));
        mainPanel.add(createTimePanel("Tokyo: ", tokyoTimeLabel));
        mainPanel.add(createTimePanel("New York: ", newYorkTimeLabel));
        
        add(mainPanel, BorderLayout.CENTER);

        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                updateTimes();
            }
        }, 0, 1000);
    }

    private JLabel createTimeLabel() {
        JLabel label = new JLabel();
        label.setFont(new Font("Arial", Font.PLAIN, 18));
        label.setBorder(new EmptyBorder(5, 10, 5, 10));
        return label;
    }

    private JPanel createTimePanel(String city, JLabel timeLabel) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(5, 0, 5, 0));
        JLabel cityLabel = new JLabel(city);
        cityLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(cityLabel, BorderLayout.WEST);
        panel.add(timeLabel, BorderLayout.CENTER);
        return panel;
    }

    private void updateTimes() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        
        sydneyTimeLabel.setText(getTime(sdf, "Australia/Sydney"));
        parisTimeLabel.setText(getTime(sdf, "Europe/Paris"));
        londonTimeLabel.setText(getTime(sdf, "Europe/London"));
        tokyoTimeLabel.setText(getTime(sdf, "Asia/Tokyo"));
        newYorkTimeLabel.setText(getTime(sdf, "America/New_York"));

        repaint();
    }

    private String getTime(SimpleDateFormat sdf, String timeZone) {
        sdf.setTimeZone(TimeZone.getTimeZone(timeZone));
        return sdf.format(new Date());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                TimeZoneClocks worldClock = new TimeZoneClocks();
                worldClock.setVisible(true);
            }
        });
    }
}
