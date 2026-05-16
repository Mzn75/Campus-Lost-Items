package ui;

import javax.swing.*;
import java.awt.*;

public class MainScreen extends JFrame {

    public MainScreen() {
        setTitle("Campus Lost & Found");
        setSize(420, 420);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel(new GridLayout(2, 1, 0, 12));
        panel.setBackground(new Color(0xF7F6F3));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        JButton lostButton  = createButton("🔍  I'm looking for an item",  new Color(0xDC2626));
        JButton foundButton = createButton("📦  I found a lost item",       new Color(0x0F6E56));

        lostButton.addActionListener(e -> new LostCategoryScreen().setVisible(true));
        foundButton.addActionListener(e -> new FoundCategoryScreen().setVisible(true));

        panel.add(lostButton);
        panel.add(foundButton);
        setContentPane(panel);
    }

    private JButton createButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI emoji", Font.BOLD, 15));
        btn.setForeground(Color.WHITE);
        btn.setBackground(color);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setOpaque(true);
        return btn;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainScreen().setVisible(true));
    }
}