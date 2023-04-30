package org.kys.bnmo.components.home;
package com.example.contoh2;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.application.Platform;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    @FXML
    private AnchorPane Anchorpane1;

    @FXML
    private Label judul_logo;

    @FXML
    private Pane pane3;

    @FXML
    private Label textanggota;

    @FXML
    private Label welcomeback;

    @FXML
    private Label livetimeanddate;

    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd MMMM yyyy"); // Customize the date format
    private DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss"); // Customize the time format

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Update the time and date label every second
        Thread updateTimeThread = new Thread(() -> {
            while (true) {
                LocalDateTime now = LocalDateTime.now();
                String timeString = now.format(timeFormatter);
                String dateString = now.format(dateFormatter);
                String dateTimeString = String.format("%s %s", dateString, timeString);
                Platform.runLater(() -> livetimeanddate.setText(dateTimeString));
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        updateTimeThread.setDaemon(true);
        updateTimeThread.start();
    }
}

