package ui;

import models.ElectronicDevice;
import models.NetworkRequest;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ReportFoundElectronicScreen extends JFrame {

    public ReportFoundElectronicScreen() {

        // The Window
        setTitle("Report Found Electronic Device");
        setSize(420, 370);
        setLocationRelativeTo(null);
        setResizable(false);
        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 12));
        panel.setBackground(new Color(0xF7F6F3));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // Textboxes
        JTextField typeField = new JTextField();
        JTextField brandField = new JTextField();
        JTextField modelField = new JTextField();
        JTextField locationField = new JTextField();
        JTextField phoneField = new JTextField();

        // Device Type Label
        panel.add(makeLabel("Device Type:"));
        panel.add(typeField);

        // Brand Label
        panel.add(makeLabel("Brand:"));
        panel.add(brandField);

        // Model Label
        panel.add(makeLabel("Model:"));
        panel.add(modelField);

        // Location Label
        panel.add(makeLabel("Where found:"));
        panel.add(locationField);

        // Contact Label
        panel.add(makeLabel("Contact Number:"));
        panel.add(phoneField);

        // Submit Button
        JButton submitButton = makeSubmitButton();
        panel.add(new JLabel(""));
        panel.add(submitButton);

        // Action on Clicking
        submitButton.addActionListener(e -> {
            String phoneText = phoneField.getText();
            int phoneNum;

            try {
                phoneNum = Integer.parseInt(phoneText);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter only numbers for your contact phone!", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            ElectronicDevice foundDevice = new ElectronicDevice(
                    0,
                    "Device",
                    locationField.getText(),
                    phoneNum,
                    brandField.getText(),
                    modelField.getText(),
                    typeField.getText()
            );

            NetworkRequest request = new NetworkRequest("REPORT_FOUND", foundDevice);
            sendToServer(request);
        });

        setContentPane(panel);
    }

    // Labels Design
    private JLabel makeLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Segoe UI emoji", Font.BOLD, 14));
        lbl.setForeground(new Color(0x1A1A1A));
        return lbl;
    }

    // Submit Button Design
    private JButton makeSubmitButton() {
        JButton btn = new JButton("Submit to Server");
        btn.setFont(new Font("Segoe UI emoji", Font.BOLD, 14));
        btn.setBackground(new Color(0x0F6E56));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    // Send to Server
    private void sendToServer(NetworkRequest request) {
        try (Socket socket = new Socket("localhost", 7500);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            out.writeObject(request);
            String serverResponse = (String) in.readObject();
            JOptionPane.showMessageDialog(null, serverResponse, "Server Message", JOptionPane.INFORMATION_MESSAGE);
            this.dispose();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Could not connect! Is your server running on port 7500?", "Connection Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}