package org.example;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class PhoneSearch extends JFrame {

    private DefaultTableModel tableModel;
    private JTextField searchField;

    public PhoneSearch() {
        setTitle("Быстрый поиск телефона по фамилии");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        tableModel = new DefaultTableModel(new Object[]{"Имя", "Фамилия", "Номер телефона"}, 0);
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        searchField = new JTextField(20);
        searchField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                search();
            }
        });

        JPanel searchPanel = new JPanel();
        searchPanel.add(new JLabel("Фамилия:"));
        searchPanel.add(searchField);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(searchPanel, BorderLayout.NORTH);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
    }

    private void search() {
        tableModel.setRowCount(0); // Очищаем таблицу перед новым поиском

        String searchText = searchField.getText().toLowerCase();

        try {
            File xmlFile = new File("phonebook.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("entry");
            for (int temp = 0; temp < nodeList.getLength(); temp++) {
                Element entry = (Element) nodeList.item(temp);
                String firstname = entry.getElementsByTagName("firstname").item(0).getTextContent();
                String surname = entry.getElementsByTagName("surname").item(0).getTextContent();
                String number = entry.getElementsByTagName("number").item(0).getTextContent();

                if (surname.toLowerCase().contains(searchText)) {
                    tableModel.addRow(new Object[]{firstname, surname, number});
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}