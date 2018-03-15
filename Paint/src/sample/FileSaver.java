package sample;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileSaver {
    private List<Canvas> list;
    private int canvasWidth;
    private int canvasHeigth;

    public FileSaver(List<Canvas> list) {
        this.list = new ArrayList<>(list);
        canvasWidth = (int)list.get(list.size() - 1).getWidth();
        canvasHeigth = (int)list.get(list.size() - 1).getHeight();
    }

    public Canvas createCanvas(List<Canvas> list) {
        Canvas canvas = new Canvas(canvasWidth, canvasHeigth);
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.TRANSPARENT);
        for (int i = 0; i < list.size(); i++) {
            WritableImage image = list.get(i).snapshot(params, null);
            canvas.getGraphicsContext2D().drawImage(image, 0, 0);
        }
        return canvas;
    }

    public void saveToFile(String type) {
        if (Controller.f != null) {
            try {
                Canvas canvas = createCanvas(this.list);
                WritableImage writableImage = new WritableImage(canvasWidth, canvasHeigth);
                canvas.snapshot(null, writableImage);
                RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                ImageIO.write(renderedImage, type, Controller.f);
                Controller.firstTimeSave = false;
                Controller.changesMade = false;
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
        }
    }

    public void saveToFile() {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG", "*.png"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg", "*.jpeg", "*.jpe", "*.jfif"));
        Controller.f = fc.showSaveDialog(null);
        if (Controller.f != null) {
            Controller.saveFormatType = fc.getSelectedExtensionFilter().getDescription();
            saveToFile(Controller.saveFormatType);
        }
    }
}
