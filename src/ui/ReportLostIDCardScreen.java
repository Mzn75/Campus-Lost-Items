package ui;

import models.IDCard;
import models.NetworkRequest;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ReportLostIDCardScreen extends JFrame {

    public ReportLostIDCardScreen() {
        // The Window
        setTitle("Report Lost ID Card");
        setSize(420, 310);
        setLocationRelativeTo(null);
        setResizable(false);
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 12));
        panel.setBackground(new Color(0xF7F6F3));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // Textboxes
        JTextField nameField = new JTextField();
        JTextField idField = new JTextField();
        JTextField locationField = new JTextField();
        JTextField phoneField = new JTextField();

        // Name Label
        panel.add(makeLabel("Name:"));
        panel.add(nameField);

        // ID Number Label
        panel.add(makeLabel("ID Number:"));
        panel.add(idField);

        // Location Label
        panel.add(makeLabel("Where Lost:"));
        panel.add(locationField);

        // Contact Label
        panel.add(makeLabel("Contact Number:"));
        panel.add(phoneField);

        //Submit Button
        JButton submitButton = makeSubmitButton();
        panel.add(new JLabel(""));
        panel.add(submitButton);

        // Action on Clicking
        submitButton.addActionListener(e -> {
            String phoneText = phoneField.getText();
            String userInput = idField.getText();
            int phoneNum;
            int id;

            try {
                phoneNum = Integer.parseInt(phoneText);
                id = Integer.parseInt(userInput);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter only numbers for your contact phone!", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            IDCard lostIDCard = new IDCard(
                    0,
                    "ID Card",
                    locationField.getText(),
                    phoneNum,
                    nameField.getText(),
                    id
            );

            NetworkRequest request = new NetworkRequest("REPORT_LOST", lostIDCard);
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

    // Buttons Design
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