package sample;

import javafx.scene.canvas.GraphicsContext;

public class Shapes2D {
    private GraphicsContext gc;

    public Shapes2D(GraphicsContext gc){
        this.gc = gc;
    }

    public void drawLine(double x2, double y2){
        gc.lineTo(x2,y2);
        gc.stroke();
    }

    public void drawOval(double x1, double y1, double width, double height){
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

    public void drawRectangle(double x1, double y1, double width, double height){
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

    public void drawTriangle(double x1, double y1, double x2, double y2, double width){
        gc.beginPath();

        gc.moveTo(x1 + (width / 2), y1);
        gc.lineTo(x2, y2);

        gc.moveTo(x1 + (width / 2), y1);
        gc.lineTo(x1, y2);

        gc.moveTo(x1, y2);
        gc.lineTo(x2, y2);

        gc.stroke();
    }
}
