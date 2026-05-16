package ui;

import javax.swing.*;
import java.awt.*;

public class FoundCategoryScreen extends JFrame {

    public FoundCategoryScreen() {
        setTitle("What kind of item did you find?");
        setSize(420, 420);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel(new GridLayout(3, 1, 0, 12));
        panel.setBackground(new Color(0xF7F6F3));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        JButton electronicButton = createButton("📱  Electronic Device");
        JButton jewelryButton = createButton("💍  Jewelry");
        JButton idButton = createButton("💳  ID Card");

        electronicButton.addActionListener(e -> {
            new ReportFoundElectronicScreen().setVisible(true);
            dispose(); });

        jewelryButton.addActionListener(e -> {
            new ReportFoundJewelryScreen().setVisible(true);
            dispose(); });

        idButton.addActionListener(e -> {
            new ReportFoundIDCardScreen().setVisible(true);
            dispose(); });

        panel.add(electronicButton);
        panel.add(jewelryButton);
        panel.add(idButton);
        setContentPane(panel);
    }

    private JButton createButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI emoji", Font.BOLD, 20));
        btn.setForeground(Color.WHITE);
        btn.setBackground(new Color(0xe4ac00));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setOpaque(true);
        return btn;
    }
}