package com.hospital.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.table.JTableHeader;
import javax.swing.plaf.basic.BasicScrollBarUI;

/**
 * Central design system for the Hospital Appointment
 * and Queue Management System.
 *
 * All Swing screens should use this class for consistent
 * colors, typography, spacing and component styling.
 */
public final class AppTheme {

    private AppTheme() {
    }

    // =========================================================
    // BRAND COLORS
    // =========================================================

    public static final Color NAVY =
            new Color(15, 35, 64);

    public static final Color NAVY_DARK =
            new Color(9, 24, 45);

    public static final Color NAVY_LIGHT =
            new Color(27, 55, 91);

    public static final Color BLUE =
            new Color(37, 99, 235);

    public static final Color BLUE_DARK =
            new Color(29, 78, 216);

    public static final Color BLUE_LIGHT =
            new Color(219, 234, 254);

    public static final Color CYAN =
            new Color(14, 165, 233);

    public static final Color WHITE =
            Color.WHITE;

    public static final Color BACKGROUND =
            new Color(245, 247, 250);

    public static final Color SURFACE =
            Color.WHITE;

    public static final Color SURFACE_ALT =
            new Color(248, 250, 252);

    public static final Color BORDER =
            new Color(226, 232, 240);

    public static final Color TEXT =
            new Color(15, 23, 42);

    public static final Color TEXT_SECONDARY =
            new Color(71, 85, 105);

    public static final Color TEXT_MUTED =
            new Color(100, 116, 139);

    public static final Color SUCCESS =
            new Color(22, 163, 74);

    public static final Color SUCCESS_LIGHT =
            new Color(220, 252, 231);

    public static final Color WARNING =
            new Color(217, 119, 6);

    public static final Color WARNING_LIGHT =
            new Color(254, 243, 199);

    public static final Color DANGER =
            new Color(220, 38, 38);

    public static final Color DANGER_LIGHT =
            new Color(254, 226, 226);

    public static final Color PURPLE =
            new Color(124, 58, 237);

    public static final Color PURPLE_LIGHT =
            new Color(237, 233, 254);

    // =========================================================
    // FONTS
    // =========================================================

    public static final Font FONT_DISPLAY =
            new Font("Segoe UI", Font.BOLD, 30);

    public static final Font FONT_H1 =
            new Font("Segoe UI", Font.BOLD, 24);

    public static final Font FONT_H2 =
            new Font("Segoe UI", Font.BOLD, 19);

    public static final Font FONT_H3 =
            new Font("Segoe UI", Font.BOLD, 16);

    public static final Font FONT_BODY =
            new Font("Segoe UI", Font.PLAIN, 14);

    public static final Font FONT_BODY_BOLD =
            new Font("Segoe UI", Font.BOLD, 14);

    public static final Font FONT_SMALL =
            new Font("Segoe UI", Font.PLAIN, 12);

    public static final Font FONT_SMALL_BOLD =
            new Font("Segoe UI", Font.BOLD, 12);

    public static final Font FONT_BUTTON =
            new Font("Segoe UI", Font.BOLD, 13);

    // =========================================================
    // DIMENSIONS
    // =========================================================

    public static final int SIDEBAR_WIDTH = 250;

    public static final int FIELD_HEIGHT = 44;

    public static final int BUTTON_HEIGHT = 44;

    public static final int CARD_RADIUS = 16;

    public static final int SMALL_RADIUS = 10;

    // =========================================================
    // BORDERS
    // =========================================================

    public static Border inputBorder() {
        return BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER, 1),
                BorderFactory.createEmptyBorder(0, 13, 0, 13)
        );
    }

    public static Border textAreaBorder() {
        return BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER, 1),
                BorderFactory.createEmptyBorder(10, 13, 10, 13)
        );
    }

    public static Border cardBorder() {
        return BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER, 1),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        );
    }

    // =========================================================
    // GENERAL COMPONENT HELPERS
    // =========================================================

    public static void styleLabel(JLabel label) {
        label.setFont(FONT_BODY);
        label.setForeground(TEXT);
    }

    public static void styleTitle(JLabel label) {
        label.setFont(FONT_H1);
        label.setForeground(TEXT);
    }

    public static void styleSubtitle(JLabel label) {
        label.setFont(FONT_BODY);
        label.setForeground(TEXT_SECONDARY);
    }

    public static void styleMutedLabel(JLabel label) {
        label.setFont(FONT_SMALL);
        label.setForeground(TEXT_MUTED);
    }

    public static void styleTextField(JTextField field) {
        field.setFont(FONT_BODY);
        field.setForeground(TEXT);
        field.setBackground(WHITE);
        field.setBorder(inputBorder());
        field.setPreferredSize(
                new Dimension(0, FIELD_HEIGHT)
        );
        field.setCaretColor(BLUE);
    }

    public static void stylePasswordField(
            JPasswordField field) {

        field.setFont(FONT_BODY);
        field.setForeground(TEXT);
        field.setBackground(WHITE);
        field.setBorder(inputBorder());
        field.setPreferredSize(
                new Dimension(0, FIELD_HEIGHT)
        );
        field.setCaretColor(BLUE);
    }

    public static void styleTextArea(JTextArea area) {
        area.setFont(FONT_BODY);
        area.setForeground(TEXT);
        area.setBackground(WHITE);
        area.setBorder(textAreaBorder());
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setCaretColor(BLUE);
    }

    public static void styleComboBox(
            JComboBox<?> comboBox) {

        comboBox.setFont(FONT_BODY);
        comboBox.setForeground(TEXT);
        comboBox.setBackground(WHITE);
        comboBox.setBorder(inputBorder());
        comboBox.setPreferredSize(
                new Dimension(0, FIELD_HEIGHT)
        );
    }

    // =========================================================
    // BUTTONS
    // =========================================================

    public static JButton primaryButton(String text) {

        JButton button = createButton(text);

        button.setBackground(BLUE);
        button.setForeground(WHITE);

        return button;
    }

    public static JButton secondaryButton(String text) {

        JButton button = createButton(text);

        button.setBackground(WHITE);
        button.setForeground(TEXT);
        button.setBorder(
                BorderFactory.createLineBorder(
                        BORDER, 1
                )
        );

        return button;
    }

    public static JButton dangerButton(String text) {

        JButton button = createButton(text);

        button.setBackground(DANGER);
        button.setForeground(WHITE);

        return button;
    }

    public static JButton successButton(String text) {

        JButton button = createButton(text);

        button.setBackground(SUCCESS);
        button.setForeground(WHITE);

        return button;
    }

    public static JButton warningButton(String text) {

        JButton button = createButton(text);

        button.setBackground(WARNING);
        button.setForeground(WHITE);

        return button;
    }

    public static JButton createButton(String text) {

        JButton button = new JButton(text);

        button.setFont(FONT_BUTTON);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setCursor(
                Cursor.getPredefinedCursor(
                        Cursor.HAND_CURSOR
                )
        );

        button.setPreferredSize(
                new Dimension(0, BUTTON_HEIGHT)
        );

        button.setMargin(
                new Insets(0, 18, 0, 18)
        );

        return button;
    }

    // =========================================================
    // PANELS
    // =========================================================

    public static JPanel createCard() {

        JPanel panel = new JPanel(new BorderLayout());

        panel.setBackground(SURFACE);
        panel.setBorder(cardBorder());

        return panel;
    }

    public static JPanel createCard(
            java.awt.LayoutManager layout) {

        JPanel panel = new JPanel(layout);

        panel.setBackground(SURFACE);
        panel.setBorder(cardBorder());

        return panel;
    }

    public static JPanel createSidebar() {

        JPanel sidebar = new JPanel(new BorderLayout());

        sidebar.setBackground(NAVY);
        sidebar.setPreferredSize(
                new Dimension(
                        SIDEBAR_WIDTH,
                        0
                )
        );

        return sidebar;
    }

    public static JPanel createPage() {

        JPanel panel = new JPanel(new BorderLayout());

        panel.setBackground(BACKGROUND);

        return panel;
    }

    // =========================================================
    // SIDEBAR BUTTONS
    // =========================================================

    public static JButton sidebarButton(String text) {

        JButton button = new JButton(text);

        button.setFont(FONT_BODY_BOLD);
        button.setForeground(
                new Color(226, 232, 240)
        );
        button.setBackground(NAVY);
        button.setHorizontalAlignment(
                JButton.LEFT
        );
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        button.setCursor(
                Cursor.getPredefinedCursor(
                        Cursor.HAND_CURSOR
                )
        );

        button.setBorder(
                BorderFactory.createEmptyBorder(
                        0, 22, 0, 15
                )
        );

        button.setPreferredSize(
                new Dimension(
                        SIDEBAR_WIDTH,
                        48
                )
        );

        return button;
    }

    public static void styleSidebarButton(
            JButton button) {

        button.setFont(FONT_BODY_BOLD);
        button.setForeground(
                new Color(226, 232, 240)
        );
        button.setBackground(NAVY);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setHorizontalAlignment(
                JButton.LEFT
        );
        button.setCursor(
                Cursor.getPredefinedCursor(
                        Cursor.HAND_CURSOR
                )
        );
    }

    // =========================================================
    // TABLES
    // =========================================================

    public static void styleTable(JTable table) {

        table.setFont(FONT_BODY);
        table.setForeground(TEXT);
        table.setBackground(WHITE);
        table.setGridColor(BORDER);
        table.setRowHeight(42);
        table.setShowVerticalLines(false);
        table.setShowHorizontalLines(true);
        table.setIntercellSpacing(
                new Dimension(0, 1)
        );
        table.setSelectionBackground(
                BLUE_LIGHT
        );
        table.setSelectionForeground(TEXT);

        JTableHeader header =
                table.getTableHeader();

        header.setFont(FONT_SMALL_BOLD);
        header.setForeground(TEXT_SECONDARY);
        header.setBackground(
                new Color(248, 250, 252)
        );
        header.setPreferredSize(
                new Dimension(0, 44)
        );
        header.setReorderingAllowed(false);
    }

    public static void styleScrollPane(
            JScrollPane scrollPane) {

        scrollPane.setBorder(
                BorderFactory.createLineBorder(
                        BORDER, 1
                )
        );

        scrollPane.getViewport()
                .setBackground(WHITE);

        scrollPane.getVerticalScrollBar()
                .setUnitIncrement(16);

        scrollPane.getVerticalScrollBar()
                .setUI(new ModernScrollBarUI());
    }

    // =========================================================
    // STAT CARDS
    // =========================================================

    public static JPanel createStatCard(
            String title,
            String value,
            Color accent) {

        JPanel card = new JPanel(
                new BorderLayout(0, 6)
        );

        card.setBackground(WHITE);

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER, 1
                        ),
                        BorderFactory.createEmptyBorder(
                                18, 20, 18, 20
                        )
                )
        );

        JLabel titleLabel =
                new JLabel(title);

        titleLabel.setFont(FONT_SMALL_BOLD);
        titleLabel.setForeground(
                TEXT_SECONDARY
        );

        JLabel valueLabel =
                new JLabel(value);

        valueLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        27
                )
        );

        valueLabel.setForeground(accent);

        card.add(
                titleLabel,
                BorderLayout.NORTH
        );

        card.add(
                valueLabel,
                BorderLayout.CENTER
        );

        return card;
    }

    // =========================================================
    // BADGES
    // =========================================================

    public static JLabel badge(
            String text,
            Color background,
            Color foreground) {

        JLabel label = new JLabel(text);

        label.setFont(FONT_SMALL_BOLD);
        label.setForeground(foreground);
        label.setBackground(background);
        label.setOpaque(true);

        label.setBorder(
                BorderFactory.createEmptyBorder(
                        6, 10, 6, 10
                )
        );

        return label;
    }

    public static JLabel successBadge(String text) {
        return badge(
                text,
                SUCCESS_LIGHT,
                SUCCESS
        );
    }

    public static JLabel warningBadge(String text) {
        return badge(
                text,
                WARNING_LIGHT,
                WARNING
        );
    }

    public static JLabel dangerBadge(String text) {
        return badge(
                text,
                DANGER_LIGHT,
                DANGER
        );
    }

    public static JLabel blueBadge(String text) {
        return badge(
                text,
                BLUE_LIGHT,
                BLUE_DARK
        );
    }

    // =========================================================
    // BRAND PANEL
    // =========================================================

    public static JPanel createBrandPanel() {

        return new GradientPanel(
                NAVY_DARK,
                NAVY
        );
    }

    // =========================================================
    // UTILITY PANELS
    // =========================================================

    public static JPanel horizontalPanel() {

        JPanel panel =
                new JPanel(new FlowLayout(
                        FlowLayout.LEFT,
                        10,
                        0
                ));

        panel.setOpaque(false);

        return panel;
    }

    public static JPanel verticalPanel() {

        JPanel panel =
                new JPanel();

        panel.setLayout(
                new javax.swing.BoxLayout(
                        panel,
                        javax.swing.BoxLayout.Y_AXIS
                )
        );

        panel.setOpaque(false);

        return panel;
    }

    // =========================================================
    // MODERN SCROLL BAR
    // =========================================================

    private static class ModernScrollBarUI
            extends BasicScrollBarUI {

        @Override
        protected void configureScrollBarColors() {

            thumbColor =
                    new Color(148, 163, 184);

            trackColor =
                    new Color(241, 245, 249);
        }

        @Override
        protected JButton createDecreaseButton(
                int orientation) {

            return invisibleButton();
        }

        @Override
        protected JButton createIncreaseButton(
                int orientation) {

            return invisibleButton();
        }

        private JButton invisibleButton() {

            JButton button = new JButton();

            button.setPreferredSize(
                    new Dimension(0, 0)
            );

            button.setMinimumSize(
                    new Dimension(0, 0)
            );

            button.setMaximumSize(
                    new Dimension(0, 0)
            );

            return button;
        }
    }

    // =========================================================
    // GRADIENT PANEL
    // =========================================================

    public static class GradientPanel
            extends JPanel {

        private final Color startColor;
        private final Color endColor;

        public GradientPanel(
                Color startColor,
                Color endColor) {

            this.startColor = startColor;
            this.endColor = endColor;

            setOpaque(false);
        }

        @Override
        protected void paintComponent(
                Graphics graphics) {

            Graphics2D g2 =
                    (Graphics2D) graphics.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            GradientPaint gradient =
                    new GradientPaint(
                            0,
                            0,
                            startColor,
                            getWidth(),
                            getHeight(),
                            endColor
                    );

            g2.setPaint(gradient);

            g2.fillRect(
                    0,
                    0,
                    getWidth(),
                    getHeight()
            );

            g2.dispose();

            super.paintComponent(graphics);
        }
    }
}