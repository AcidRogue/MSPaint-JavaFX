package Tools;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.transform.Transform;
import sample.Controller;
import sample.FileSaver;

import java.util.List;

public class BucketTool {
    private Canvas canvas;
    private WritableImage writableImage;

    private PixelReader pr;
    private PixelWriter pw;

    private Color wantedColor;
    private Color currentColor;

    private int x,y;


    public BucketTool(List<Canvas> list, int x, int y, Color wantedColor){
        this.canvas = FileSaver.createCanvas(list);

        this.writableImage = new WritableImage((int)canvas.getWidth(), (int)canvas.getHeight());
        SnapshotParameters spa = new SnapshotParameters();
        spa.setTransform(Transform.scale(1, 1));
        pw = canvas.snapshot(spa, writableImage).getPixelWriter();
        pr = canvas.snapshot(spa, writableImage).getPixelReader();
        this.x = x;
        this.y = y;
        this.currentColor = pr.getColor(x, y);
        this.wantedColor = wantedColor;
    }

    public void paint(){
        flood(x, y);
    }

    private void flood(int x, int y){
        if(x <= 1)
            return;
        if(y <= 1)
            return;
        if(x >= canvas.getWidth() - 1)
            return;
        if(y >= canvas.getHeight() - 1)
            return;

        if(!pr.getColor(x, y).equals(currentColor)){
            return;
        }
        pw.setColor(x, y, wantedColor);
        Controller.gc.drawImage(writableImage, 0, 0);

        flood(x + 1, y);
        flood(x - 1, y);
        flood(x, y + 1);
        flood(x, y + 1);
    }


}
