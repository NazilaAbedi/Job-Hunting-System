package jobportal.presentation.gui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

final class Theme {
    static final Color BG = new Color(246, 248, 252);
    static final Color SURFACE = Color.WHITE;
    static final Color TEXT = new Color(26, 32, 44);
    static final Color MUTED = new Color(100, 116, 139);
    static final Color PRIMARY = new Color(79, 70, 229);
    static final Color PRIMARY_DARK = new Color(67, 56, 202);
    static final Color SOFT = new Color(238, 242, 255);
    static final Color BORDER = new Color(226, 232, 240);
    static final Color SUCCESS = new Color(5, 150, 105);

    private Theme() {}

    static void install() {
        UIManager.put("Panel.background", BG);
        UIManager.put("Label.foreground", TEXT);
        UIManager.put("TextField.font", new Font("SansSerif", Font.PLAIN, 14));
        UIManager.put("PasswordField.font", new Font("SansSerif", Font.PLAIN, 14));
        UIManager.put("Button.font", new Font("SansSerif", Font.BOLD, 13));
        UIManager.put("Table.font", new Font("SansSerif", Font.PLAIN, 13));
        UIManager.put("Table.rowHeight", 36);
        UIManager.put("Table.gridColor", BORDER);
        UIManager.put("TableHeader.font", new Font("SansSerif", Font.BOLD, 12));
        UIManager.put("TableHeader.background", new Color(248, 250, 252));
        UIManager.put("TableHeader.foreground", MUTED);
    }

    static Border cardBorder() {
        return BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER, 1, true),
                BorderFactory.createEmptyBorder(18, 18, 18, 18));
    }

    static JLabel label(String text, int size, int style, Color color) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("SansSerif", style, size));
        l.setForeground(color);
        return l;
    }

    static JButton primaryButton(String text) {
        JButton b = new JButton(text);
        b.setForeground(Color.WHITE);
        b.setBackground(PRIMARY);
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createEmptyBorder(11, 18, 11, 18));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return b;
    }

    static JButton secondaryButton(String text) {
        JButton b = new JButton(text);
        b.setForeground(TEXT);
        b.setBackground(SURFACE);
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER, 1, true),
                BorderFactory.createEmptyBorder(10, 16, 10, 16)));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return b;
    }
}

