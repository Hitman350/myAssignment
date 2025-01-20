import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import com.toedter.calendar.JDateChooser;
import java.awt.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.TimeZone;

public class sample extends JFrame {
    private JComboBox<String> timeZone1ComboBox;
    private JComboBox<String> timeZone2ComboBox;
    private JDateChooser dateChooser;
    private JTable table;
    private DefaultTableModel tableModel;
    private String[] timeZones;

    public sample() {
        setTitle("Time Zone Chart");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        timeZones = TimeZone.getAvailableIDs();
        String[] timeZonesWithOffsets = new String[timeZones.length];
        for (int i = 0; i < timeZones.length; i++) {
            TimeZone tz = TimeZone.getTimeZone(timeZones[i]);
            int offset = tz.getRawOffset();
            int hours = offset / 3600000;
            int minutes = Math.abs((offset / 60000) % 60);
            String offsetString = String.format("GMT%+d:%02d", hours, minutes);
            timeZonesWithOffsets[i] = timeZones[i] + " (" + offsetString + ")";
        }

        timeZone1ComboBox = new JComboBox<>(timeZonesWithOffsets);
        timeZone2ComboBox = new JComboBox<>(timeZonesWithOffsets);

        dateChooser = new JDateChooser();
        dateChooser.setDateFormatString("yyyy-MM-dd");

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(7, 7, 7, 7); 

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        controlPanel.add(new JLabel("Select Date:"), gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        controlPanel.add(dateChooser, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        controlPanel.add(new JLabel("Time Zone 1:"), gbc);
        
        gbc.gridx = 1;
        gbc.gridwidth = 1;
        controlPanel.add(timeZone1ComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        controlPanel.add(new JLabel("Time Zone 2:"), gbc);
        
        gbc.gridx = 1;
        gbc.gridwidth = 1;
        controlPanel.add(timeZone2ComboBox, gbc);

        add(controlPanel, BorderLayout.NORTH);

        tableModel = new DefaultTableModel(new String[]{"Time Zone 1", "Time Zone 2"}, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        timeZone1ComboBox.addActionListener(e -> updateTable());
        timeZone2ComboBox.addActionListener(e -> updateTable());
        dateChooser.addPropertyChangeListener("date", e -> updateTable());

        updateTable();
    }

    private void updateTable() {
        String timeZone1 = extractTimeZone((String) timeZone1ComboBox.getSelectedItem());
        String timeZone2 = extractTimeZone((String) timeZone2ComboBox.getSelectedItem());
        Date selectedDate = dateChooser.getDate();
        if (selectedDate == null) {
            selectedDate = new Date();
        }
        LocalDate localDate = selectedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        tableModel.setRowCount(0); 

        for (int hour = 0; hour < 24; hour++) {
            LocalDateTime dateTime1 = LocalDateTime.of(localDate, LocalTime.of(hour, 0));
            ZonedDateTime zdt1 = dateTime1.atZone(ZoneId.of(timeZone1));
            ZonedDateTime zdt2 = zdt1.withZoneSameInstant(ZoneId.of(timeZone2));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            tableModel.addRow(new Object[]{zdt1.format(formatter), zdt2.format(formatter)});
        }

        LocalDateTime now = LocalDateTime.now();
        LocalTime localTime = now.toLocalTime().truncatedTo(ChronoUnit.HOURS);
        int currentHour = localTime.getHour();
        table.setRowSelectionInterval(currentHour, currentHour);
    }

    private String extractTimeZone(String comboBoxItem) {
        return comboBoxItem.split(" ")[0];
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            sample frame = new sample();
            frame.setVisible(true);
        });
    }
}
