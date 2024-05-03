package org.example;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PhoneSearch phoneSearch = new PhoneSearch();
            phoneSearch.setVisible(true);
        });
    }
}