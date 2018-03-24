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

import java.awt.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BucketTool {
    private int canvasWidth;
    private int canvasHeight;

    private PixelReader pr;
    private PixelWriter pw;

    private Color wantedColor;
    private Color currentColor;

    private boolean[][] checked;

    private int x, y;


    public BucketTool(List<Canvas> list, int x, int y, Color wantedColor) {
        Canvas canvas = CanvasCreator.createCanvas(list);
        this.canvasHeight = (int) canvas.getHeight();
        this.canvasWidth = (int) canvas.getWidth();

        WritableImage writableImage = new WritableImage(canvasWidth, canvasHeight);
        SnapshotParameters spa = new SnapshotParameters();
        spa.setTransform(Transform.scale(1, 1));
        pw = canvas.snapshot(spa, writableImage).getPixelWriter();
        pr = canvas.snapshot(spa, writableImage).getPixelReader();

        this.x = x;
        this.y = y;
        this.currentColor = pr.getColor(x, y);
        this.wantedColor = wantedColor;
        checked = new boolean[canvasHeight][canvasWidth];
        Controller.gc.drawImage(writableImage, 0, 0);
    }

    public void paint() {
        flood(x, y);
    }

    private void flood(int x, int y) {
        Queue<Point> queue = new LinkedList<>();
        queue.add(new Point(x, y));

        while (!queue.isEmpty()) {
            Point p = queue.remove();

            if (checker(p.x, p.y)) {
                queue.add(new Point(p.x, p.y - 1));
                queue.add(new Point(p.x, p.y + 1));
                queue.add(new Point(p.x - 1, p.y));
                queue.add(new Point(p.x + 1, p.y));
            }
        }
    }

    private boolean checker(int x, int y) {
        if (x < 0)
            return false;
        if (y < 0)
            return false;
        if (x >= canvasWidth)
            return false;
        if (y >= canvasHeight)
            return false;

        if (checked[y][x])
            return false;

        if (!pr.getColor(x, y).equals(currentColor))
            return false;

        pw.setColor(x, y, wantedColor);
        checked[y][x] = true;
        return true;
    }
}
