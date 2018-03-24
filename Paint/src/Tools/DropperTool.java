package Tools;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import java.util.List;

public class DropperTool {
    private PixelReader pr;

    private int x, y;

    public DropperTool(List<Canvas> list, int x, int y){
        Canvas temp = CanvasCreator.createCanvas(list);
        pr = temp.snapshot(null, new WritableImage((int)temp.getWidth(), (int)temp.getHeight())).getPixelReader();
        this.x = x;
        this.y = y;
    }

    public Color getColor(){
        return pr.getColor(x, y);
    }
}
