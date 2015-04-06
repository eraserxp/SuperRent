/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.File;
import java.io.IOException;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

/**
 *
 * this class is used to convert a javafx node into a image
 */
public class PngConverter {
    
    private Stage primaryStage;
    
    public PngConverter(Stage stage) {
        primaryStage = stage;
    }

    public void saveAsPng(Node node) {
        WritableImage image = node.snapshot(new SnapshotParameters(), null);

        // TODO: probably use a file chooser here
//        File file = new File("chart.png");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialFileName("report.png");

        //Set extension filter
//        FileChooser.ExtensionFilter extFilter
//                = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
//        fileChooser.getExtensionFilters().add(extFilter);

        //Show save file dialog
        File file = fileChooser.showSaveDialog(primaryStage);

//        if (file != null) {
//            SaveFile(textArea.getText(), file);
//        }

        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
        } catch (IOException e) {
            // TODO: handle exception here
        }
    }

}
