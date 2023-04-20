package org.kys.bnmo;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class Demo {
    public static void main(String[] args) {
        // Set the Mac Dark theme
        FlatMacDarkLaf.setup();

        // Create a new frame
        JFrame frame = new JFrame("Test");

        // Set the layout of the frame
        frame.setLayout(new BorderLayout());

        // Add a label to the frame
        JLabel label = new JLabel("Hello, World!");
        JPanel panel = new JPanel();
        panel.add(label);
        frame.add(panel, BorderLayout.CENTER);

        // Add a button to the frame
        JButton button = new JButton("Click me!");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Button clicked!");
            }
        });
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(button);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Add a dropdown menu to the frame
        String[] options = {"Option 1", "Option 2", "Option 3"};
        JComboBox<String> dropdown = new JComboBox<>(options);
        dropdown.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Selected option: " + dropdown.getSelectedItem());
            }
        });
        JPanel dropdownPanel = new JPanel();
        dropdownPanel.add(dropdown);
        frame.add(dropdownPanel, BorderLayout.NORTH);

        // Set the size of the frame
        frame.setSize(400, 300);

        // Set the default close operation
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Show the frame
        frame.setVisible(true);
    }
}
