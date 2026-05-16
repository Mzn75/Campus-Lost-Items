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

public class ReportFoundScreen extends JFrame {

    public ReportFoundScreen() {
        // Different Title for this specific window!
        setTitle("Report a Found Item");
        setSize(400, 400);
        setLocationRelativeTo(null);

        setLayout(new GridLayout(6, 2, 10, 10));

        JLabel typeLabel = new JLabel("  Device Type (Phone/Laptop):");
        JTextField typeField = new JTextField();

        JLabel brandLabel = new JLabel("  Brand (e.g., Xiaomi):");
        JTextField brandField = new JTextField();

        JLabel modelLabel = new JLabel("  Model (e.g., Redmi):");
        JTextField modelField = new JTextField();

        JLabel locationLabel = new JLabel("  Where did you find it?:"); // Different question!
        JTextField locationField = new JTextField();

        JLabel phoneLabel = new JLabel("  Your Contact Number:");
        JTextField phoneField = new JTextField();

        JButton submitButton = new JButton("Submit to Server");

        add(typeLabel); add(typeField);
        add(brandLabel); add(brandField);
        add(modelLabel); add(modelField);
        add(locationLabel); add(locationField);
        add(phoneLabel); add(phoneField);
        add(new JLabel(""));
        add(submitButton);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String phoneText = phoneField.getText();
                int phoneNum = 0;

                //Input validation for the phone number
                try {
                    phoneNum = Integer.parseInt(phoneText);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null,
                            "Please enter only numbers for your contact phone!",
                            "Input Error",
                            JOptionPane.ERROR_MESSAGE);
                    return; //Stop running if they typed letters
                }

                ElectronicDevice foundDevice = new ElectronicDevice(
                        0,
                        "Found " + typeField.getText(),
                        locationField.getText(),
                        phoneNum,
                        brandField.getText(),
                        modelField.getText(),
                        typeField.getText()
                );

                NetworkRequest request = new NetworkRequest("REPORT_FOUND", foundDevice);
                sendToServer(request);
            }
        });
    }

    private void sendToServer(NetworkRequest request) {
        try (Socket socket = new Socket("localhost", 7500);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

            out.writeObject(request);
            String serverResponse = (String) in.readObject();
            JOptionPane.showMessageDialog(this, serverResponse, "Server Message", JOptionPane.INFORMATION_MESSAGE);
            this.dispose();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Could not connect! Is your server running on port 7500?", "Connection Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}