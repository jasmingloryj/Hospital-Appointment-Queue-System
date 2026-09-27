package com.hospital.ui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JPasswordField;

/**
 * Centralized visual theme for the Hospital Appointment System.
 *
 * All UI screens can use this class so that the
 * application has a consistent professional appearance.
 */
public final class AppTheme {

    /*
     * Prevent creating objects of this utility class.
     */
    private AppTheme() {
    }

    // Main application colors
    public static final Color PRIMARY =
            new Color(25, 118, 210);

    public static final Color PRIMARY_DARK =
            new Color(13, 71, 161);

    public static final Color BACKGROUND =
            new Color(245, 247, 250);

    public static final Color SURFACE =
            Color.WHITE;

    public static final Color TEXT_PRIMARY =
            new Color(33, 37, 41);

    public static final Color TEXT_SECONDARY =
            new Color(108, 117, 125);

    public static final Color BORDER =
            new Color(220, 224, 230);

    public static final Color SUCCESS =
            new Color(40, 167, 69);

    public static final Color WARNING =
            new Color(255, 193, 7);

    public static final Color DANGER =
            new Color(220, 53, 69);

    // Common fonts
    public static final Font TITLE_FONT =
            new Font("Segoe UI", Font.BOLD, 28);

    public static final Font HEADING_FONT =
            new Font("Segoe UI", Font.BOLD, 20);

    public static final Font SUBHEADING_FONT =
            new Font("Segoe UI", Font.BOLD, 15);

    public static final Font BODY_FONT =
            new Font("Segoe UI", Font.PLAIN, 14);

    public static final Font SMALL_FONT =
            new Font("Segoe UI", Font.PLAIN, 12);

    /**
     * Applies the standard styling to a button.
     */
    public static void styleButton(JButton button) {

        button.setFont(SUBHEADING_FONT);

        button.setForeground(Color.WHITE);

        button.setBackground(PRIMARY);

        button.setFocusPainted(false);

        button.setBorder(
                BorderFactory.createEmptyBorder(
                        10, 18, 10, 18
                )
        );

        button.setCursor(
                new java.awt.Cursor(
                        java.awt.Cursor.HAND_CURSOR
                )
        );
    }

    /**
     * Applies the standard styling to a text field.
     */
    public static void styleTextField(JTextField field) {

        field.setFont(BODY_FONT);

        field.setForeground(TEXT_PRIMARY);

        field.setBackground(SURFACE);

        field.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER
                        ),
                        BorderFactory.createEmptyBorder(
                                8, 10, 8, 10
                        )
                )
        );
    }

    /**
     * Applies the standard styling to a password field.
     */
    public static void stylePasswordField(
            JPasswordField field) {

        field.setFont(BODY_FONT);

        field.setForeground(TEXT_PRIMARY);

        field.setBackground(SURFACE);

        field.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER
                        ),
                        BorderFactory.createEmptyBorder(
                                8, 10, 8, 10
                        )
                )
        );
    }
}
