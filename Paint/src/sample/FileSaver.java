package sample;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.IOException;

public class FileSaver {
    private Canvas drawingCanvas;
    private double drawingCanvasWidth;
    private double drawingCanvasHeight;

    public FileSaver(Canvas drawingCanvas){
        setDrawingCanvas(drawingCanvas);
    }

    public void setDrawingCanvas(Canvas drawingCanvas) {
        this.drawingCanvas = drawingCanvas;
        drawingCanvasWidth = drawingCanvas.getWidth();
        drawingCanvasHeight = drawingCanvas.getHeight();
    }

    public void saveToFile(String type) {
        if (Controller.f != null) {
            try {
                WritableImage writableImage = new WritableImage((int) drawingCanvasWidth, (int) drawingCanvasHeight);
                drawingCanvas.snapshot(null, writableImage);
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
        if(Controller.f != null){
            Controller.saveFormatType = fc.getSelectedExtensionFilter().getDescription();
            saveToFile(Controller.saveFormatType);
        }
    }
}
