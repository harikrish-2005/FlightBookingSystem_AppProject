import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class FlightBookingSystem extends JFrame {
    private JTextField flightNumberField;
    private JTextField passengerNameField;
    private JTextField departureField;
    private JTextField arrivalField;
    private JTextField mobileNumberField;
    private JTextField emailField;
    private JLabel priceLabel;
    private JButton bookButton;
    private JPanel seatPanel;

    private Map<String, Boolean> seatMap;
    private Map<String, JButton> seatButtons;
    private double totalPrice;

    public FlightBookingSystem() {
        setTitle("Flight Booking System");
        setSize(800, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(new Color(240, 248, 255)); // Light blue background
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel flightNumberLabel = new JLabel("Flight Number:");
        flightNumberLabel.setForeground(new Color(25, 25, 112)); // Midnight blue text
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(flightNumberLabel, gbc);

        flightNumberField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(flightNumberField, gbc);

        JLabel passengerNameLabel = new JLabel("Passenger Name:");
        passengerNameLabel.setForeground(new Color(25, 25, 112));
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(passengerNameLabel, gbc);

        passengerNameField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(passengerNameField, gbc);

        JLabel departureLabel = new JLabel("Departure:");
        departureLabel.setForeground(new Color(25, 25, 112));
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(departureLabel, gbc);

        departureField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(departureField, gbc);

        JLabel arrivalLabel = new JLabel("Arrival:");
        arrivalLabel.setForeground(new Color(25, 25, 112));
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(arrivalLabel, gbc);

        arrivalField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(arrivalField, gbc);

        JLabel mobileNumberLabel = new JLabel("Mobile Number:");
        mobileNumberLabel.setForeground(new Color(25, 25, 112));
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(mobileNumberLabel, gbc);

        mobileNumberField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 4;
        panel.add(mobileNumberField, gbc);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setForeground(new Color(25, 25, 112));
        gbc.gridx = 0;
        gbc.gridy = 5;
        panel.add(emailLabel, gbc);

        emailField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 5;
        panel.add(emailField, gbc);

        JLabel seatLabel = new JLabel("Seats:");
        seatLabel.setForeground(new Color(25, 25, 112));
        gbc.gridx = 0;
        gbc.gridy = 6;
        panel.add(seatLabel, gbc);

        seatPanel = new JPanel(new GridLayout(6, 6, 10, 10));
        seatPanel.setBackground(new Color(240, 248, 255));
        gbc.gridx = 1;
        gbc.gridy = 6;
        panel.add(seatPanel, gbc);

        seatMap = new HashMap<>();
        seatButtons = new HashMap<>();
        for (int row = 1; row <= 6; row++) {
            for (char col = 'A'; col <= 'F'; col++) {
                String seat = row + String.valueOf(col);
                JButton seatButton = new JButton(seat);
                seatButton.setBackground(Color.GREEN);
                seatButton.addActionListener(new SeatSelectionListener(seat));
                seatPanel.add(seatButton);
                seatMap.put(seat, false); // All seats are initially available
                seatButtons.put(seat, seatButton);
            }
        }

        JLabel priceTextLabel = new JLabel("Total Price:");
        priceTextLabel.setForeground(new Color(25, 25, 112));
        gbc.gridx = 0;
        gbc.gridy = 7;
        panel.add(priceTextLabel, gbc);

        priceLabel = new JLabel("0.00");
        priceLabel.setForeground(new Color(25, 25, 112));
        gbc.gridx = 1;
        gbc.gridy = 7;
        panel.add(priceLabel, gbc);

        bookButton = new JButton("Book Flight");
        bookButton.setBackground(new Color(70, 130, 180)); // Steel blue button
        bookButton.setForeground(Color.WHITE);
        gbc.gridx = 1;
        gbc.gridy = 8;
        panel.add(bookButton, gbc);

        bookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String flightNumber = generateRandomFlightNumber();
                String passengerName = passengerNameField.getText();
                String departure = departureField.getText();
                String arrival = arrivalField.getText();
                String mobileNumber = mobileNumberField.getText();
                String email = emailField.getText();

                StringBuilder bookedSeats = new StringBuilder();
                for (Map.Entry<String, Boolean> entry : seatMap.entrySet()) {
                    if (entry.getValue()) {
                        bookedSeats.append(entry.getKey()).append(" ");
                    }
                }

                if (bookedSeats.length() == 0) {
                    JOptionPane.showMessageDialog(null, "No seats selected. Please select at least one seat.");
                } else {
                    JOptionPane.showMessageDialog(null, "Flight booked successfully!\n" +
                            "Flight Number: " + flightNumber + "\n" +
                            "Passenger Name: " + passengerName + "\n" +
                            "Departure: " + departure + "\n" +
                            "Arrival: " + arrival + "\n" +
                            "Mobile Number: " + mobileNumber + "\n" +
                            "Email: " + email + "\n" +
                            "Seats: " + bookedSeats.toString() + "\n" +
                            "Total Price: " + String.format("%.2f", totalPrice));
                }
            }
        });

        add(panel);
    }

    private double calculatePrice(String seat) {
        // Example price calculation based on seat
        if (seat.startsWith("1") || seat.startsWith("2")) {
            return 150.00; // First two rows are more expensive
        } else {
            return 100.00; // Other seats are cheaper
        }
    }

    private String generateRandomFlightNumber() {
        Random random = new Random();
        int number = random.nextInt(9000) + 1000; // Generate a random 4-digit number
        return "FL" + number;
    }

    private class SeatSelectionListener implements ActionListener {
        private String seat;

        public SeatSelectionListener(String seat) {
            this.seat = seat;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (seatMap.get(seat)) {
                JOptionPane.showMessageDialog(null, "Seat " + seat + " is already booked. Please choose another seat.");
            } else {
                seatMap.put(seat, true);
                seatButtons.get(seat).setBackground(Color.RED);
                totalPrice += calculatePrice(seat);
                priceLabel.setText(String.format("%.2f", totalPrice));
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FlightBookingSystem().setVisible(true);
            }
        });
    }
}