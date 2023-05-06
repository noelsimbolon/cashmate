package org.kys.bnmo.helpers;

import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DocumentPrinter {

    private Stage stage;

    public DocumentPrinter(Stage stage) {
        this.stage = stage;
    }

    public void printElement(Pane root) {
        PrinterJob printerJob = PrinterJob.createPrinterJob();

        for (Node pageNode : root.getChildren()) {
            VBox container = new VBox();
            printerJob.printPage(container);
        }

        printerJob.endJob();
    }
}