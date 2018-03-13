package sample;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Shapes2D {
    private Canvas c;
    private GraphicsContext gc;

    public void setCanvas(Canvas c, Color color){
        this.c = c;
        gc = c.getGraphicsContext2D();
        gc.setStroke(color);
    }

    public void drawLine(double x2, double y2) {
        gc.lineTo(x2, y2);
        gc.stroke();
    }

    public void drawOval(double x1, double y1, double width, double height) {
        if (width < 0) {
            width = -width;
            x1 = x1 - width;
        }
        if (height < 0) {
            height = -height;
            y1 = y1 - height;
        }
        gc.strokeOval(x1, y1, width, height);
    }

    public void drawRectangle(double x1, double y1, double width, double height) {
        if (width < 0) {
            width = -width;
            x1 = x1 - width;
        }
        if (height < 0) {
            height = -height;
            y1 = y1 - height;
        }
        gc.strokeRect(x1, y1, width, height);
    }

    public void drawTriangle(double x1, double y1, double x2, double y2, double width) {
        gc.beginPath();

        gc.moveTo(x1 + (width / 2), y1);
        gc.lineTo(x2, y2);

        gc.moveTo(x1 + (width / 2), y1);
        gc.lineTo(x1, y2);

        gc.moveTo(x1, y2);
        gc.lineTo(x2, y2);

        gc.stroke();
    }

    public void drawRoundRectangle(double x1, double y1, double width, double height) {
        if (width < 0) {
            width = -width;
            x1 = x1 - width;
        }
        if (height < 0) {
            height = -height;
            y1 = y1 - height;
        }
        gc.strokeRoundRect(x1, y1, width, height, 30, 30);
    }
}
