package org.kys.bnmo.helpers.views;

import javafx.embed.swing.SwingFXUtils;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.LosslessFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.printing.PDFPageable;
import org.apache.pdfbox.printing.PDFPrintable;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.print.PrinterException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class DocumentPrinter {

    private Stage stage;

    public DocumentPrinter(Stage stage) {
        this.stage = stage;
    }

    public void printElement(Pane root, String defaultFilename) {
        ScrollPane sp = (ScrollPane) root.getChildren().get(0);
        VBox pages = (VBox) sp.getContent();
        Scene scene = new Scene(pages);
        List<File> files = new ArrayList<>();
        int fileIdx = 0;
        for (var node : pages.getChildren()) {
            WritableImage nodeshot = node.snapshot(new SnapshotParameters(), null);
            File file = new File("Page-" + fileIdx + ".png");
            files.add(file);
            fileIdx++;

            try {
                ImageIO.write(SwingFXUtils.fromFXImage(nodeshot, null), "png", file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Create a new PDF document
        PDDocument document = new PDDocument();

        // Iterate through the list of image files
        for (File imageFile : files) {
            try {
                // Load the image file into a PDImageXObject
                PDImageXObject image = PDImageXObject.createFromFile(imageFile.getAbsolutePath(), document);

                // Create a new page in the PDF document
                PDPage page = new PDPage(new PDRectangle(image.getWidth(), image.getHeight()));

                // Add the image to the new page
                PDPageContentStream contentStream = new PDPageContentStream(document, page);
                contentStream.drawImage(image, 0, 0);
                contentStream.close();

                // Add the new page to the document
                document.addPage(page);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Save the PDF document to a file
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save File");
        fileChooser.setInitialFileName(defaultFilename);
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF files (*.pdf)", "*.pdf"));
        File pdfFile = fileChooser.showSaveDialog(stage);
        try {
            document.save(pdfFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Close the PDF document
        try {
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (var file : files) {
            file.delete();
        }
    }
}
