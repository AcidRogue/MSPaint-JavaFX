package sample;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.*;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class Controller {

    //region FXML variables
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private VBox vBox;
    @FXML
    private Canvas drawingCanvas;
    @FXML
    private ChoiceBox btnBrushType;
    @FXML
    private GridPane gridColors;
    @FXML
    private ChoiceBox btnSize;
    @FXML
    private HBox hboxDefColor1;
    @FXML
    private HBox hboxDefColor2;
    @FXML
    private HBox hboxPencil;
    @FXML
    private Pane defColor1;
    @FXML
    private Pane defColor2;
    @FXML
    private HBox hboxRubber;
    @FXML
    private HBox hboxDropper;
    @FXML
    private HBox hboxText;
    @FXML
    private HBox hboxBrush;
    //region shapes
    @FXML
    private HBox hboxLine;
    @FXML
    private HBox hboxRectangle;
    @FXML
    private HBox hboxCircle;
    //endregion
    //region colors
    @FXML
    private HBox hbox1;
    @FXML
    private HBox hbox2;
    @FXML
    private HBox hbox3;
    @FXML
    private HBox hbox4;
    @FXML
    private HBox hbox5;
    @FXML
    private HBox hbox6;
    @FXML
    private HBox hbox7;
    @FXML
    private HBox hbox8;
    @FXML
    private HBox hbox9;
    @FXML
    private HBox hbox10;
    @FXML
    private HBox hbox11;
    @FXML
    private HBox hbox12;
    @FXML
    private HBox hbox13;
    @FXML
    private HBox hbox14;
    @FXML
    private HBox hbox15;
    @FXML
    private HBox hbox16;
    @FXML
    private HBox hbox17;
    @FXML
    private HBox hbox18;
    @FXML
    private HBox hbox19;
    @FXML
    private HBox hbox20;
    //endregion

    @FXML
    private GridPane btnSizeGridBox;
    //endregion

    private int size;

    private Color primaryColor = Color.rgb(0, 0, 0);
    private Color secondaryColor = Color.rgb(255, 255, 255);

    private GraphicsContext gc;

    private HBox[] tools;
    private HBox[] shapes;
    private HBox[] defColors;
    private HBox[] hboxes;

    private HBox defColorPressed;

    private HBox shapePressed;

    /*
    Main method that is called when the program is run. Used for initializing methods and variables.
     */
    @FXML
    void initialize() {
        gc = drawingCanvas.getGraphicsContext2D();

        hboxes = new HBox[]{hbox1, hbox2, hbox3, hbox4, hbox5, hbox6, hbox7, hbox8, hbox9, hbox10, hbox11, hbox12, hbox13, hbox14, hbox15, hbox16, hbox17, hbox18, hbox19, hbox20};
        tools = new HBox[]{hboxPencil, hboxDropper, hboxRubber, hboxText};
        shapes = new HBox[]{hboxLine, hboxRectangle, hboxCircle};

        defColors = new HBox[]{hboxDefColor1, hboxDefColor2};

        hboxDefColor1.setStyle("-fx-border-color: rgb(97, 167, 237); -fx-background-color: rgb(97, 167, 237, 0.3)");
        defColorPressed = (HBox) defColor1.getParent();

        btnSize.getItems().addAll("1px", "3px", "5px", "8px");
        btnSize.setValue("1px");

        handleColors();
        handleOnMouseOverColors();
        handleOnMouseExitedColors();
        handleDrawingCanvas();
        handleDefColors();
        handleShapes();
    }


    /*
    Changes the currently picked color.
     */
    private void changeDefColor(String colorStr, int r, int g, int b) {
        if (defColorPressed.getChildren().get(0) == defColor1) {
            primaryColor = Color.rgb(r, g, b);
            defColor1.setStyle("-fx-border-color: gray;-fx-background-color: " + colorStr);
        } else {
            secondaryColor = Color.rgb(r, g, b);
            defColor2.setStyle("-fx-border-color: gray;-fx-background-color: " + colorStr);
        }
    }

    //on click coordinates
    private double xCorA;
    private double yCorA;

    //on release coordinates
    private double xCorB;
    private double yCorB;

    /*
    Logic for when the mouse is pressed.
     */
    private void handleDrawingCanvas() {
        drawingCanvas.setOnMousePressed(event -> {
            setSize();
            gc.setLineWidth(size);
            if (event.getButton() == MouseButton.PRIMARY) {
                gc.setStroke(primaryColor);
            } else if (event.getButton() == MouseButton.SECONDARY) {
                gc.setStroke(secondaryColor);
            }
            gc.beginPath();
            xCorA = event.getX();
            yCorA = event.getY();
            gc.lineTo(event.getX(), event.getY());
        });


        drawingCanvas.setOnMouseDragged(event -> {
            if (shapePressed != hboxLine && shapePressed != hboxRectangle && shapePressed != hboxCircle) {
                gc.lineTo(event.getX(), event.getY());
                gc.stroke();
            }
        });


        /*
        Handle shapes.
         */
        drawingCanvas.setOnMouseReleased(event -> {
            xCorB = event.getX();
            yCorB = event.getY();

            double width = xCorB - xCorA;
            double height = yCorB - yCorA;

            if (width < 0) {
                width = -width;
                xCorA = xCorA - width;
            }
            if (height < 0) {
                height = -height;
                yCorA = yCorA - height;
            }
            
            if (shapePressed == hboxLine) {
                gc.lineTo(event.getX(), event.getY());
                gc.stroke();
            } else if (shapePressed == hboxRectangle) {
                gc.strokeRect(xCorA, yCorA, width, height);
            } else if (shapePressed == hboxCircle) {
                gc.strokeOval(xCorA, yCorA, width, height);
            }
        });
    }

    /*
    Handle shapes.
     */
    private void handleShapes() {
        for (HBox shape : shapes) {
            shape.setOnMouseClicked(event -> {
                if (shapePressed != null) {
                    shapePressed.setStyle("");
                }
                shapePressed = shape;
                shapePressed.setStyle("-fx-border-color: rgb(97, 167, 237); -fx-background-color: rgb(97, 167, 237, 0.3)");
            });
        }
    }


    /*
    Takes the size from the size choice box and sets it.
     */
    private void setSize() {
        size = Integer.parseInt(Character.toString(btnSize.getValue().toString().charAt(0)));
    }


    /*
    Handler for the color panes.
     */
    private void handleColors() {
        for (HBox hBox : hboxes) {
            Pane pane = (Pane) hBox.getChildren().get(0);
            pane.setOnMouseClicked(event -> {
                Pattern p = Pattern.compile("rgb\\((\\d+),(\\d+),(\\d+)\\)");
                Matcher m = p.matcher(pane.getStyle());

                if (m.find()) {
                    String colorStr = m.group(0);
                    int r = Integer.parseInt(m.group(1));
                    int g = Integer.parseInt(m.group(2));
                    int b = Integer.parseInt(m.group(3));
                    if (event.getButton() == MouseButton.PRIMARY)
                        changeDefColor(colorStr, r, g, b);
                }
            });
        }
    }


    /*
    Changes the color of objects' background and border colors when mouse is above them.
     */
    private void handleOnMouseOverColors() {
        for (HBox hBox : hboxes) {
            hBox.setOnMouseEntered(event -> {
                hBox.setStyle(hBox.getStyle().replace("-fx-border-color: gray;", "-fx-border-color: rgb(97, 167, 237);"));
            });
        }
        for (HBox tool : tools) {
            tool.setOnMouseEntered(event -> {
                tool.setStyle("-fx-border-color: rgb(97, 167, 237);");
            });
        }
        for (HBox shape : shapes) {
            shape.setOnMouseEntered(event -> {
                if (shape != shapePressed) {
                    shape.setStyle("-fx-border-color: rgb(97, 167, 237); -fx-background-color: rgb(97, 167, 237, 0.1)");
                }
            });
        }
        for (HBox dc : defColors) {
            dc.setOnMouseEntered(event -> {
                if (dc != defColorPressed) {
                    dc.setStyle("-fx-border-color: rgb(97, 167, 237); -fx-background-color: rgb(97, 167, 237, 0.1)");
                }
            });
        }
        btnSize.setOnMouseEntered(event -> {
            btnSizeGridBox.setStyle("-fx-border-color: rgb(97, 167, 237)");
        });
    }


    /*
    Sets the color of the objects' background and border colors to default.
     */
    private void handleOnMouseExitedColors() {
        for (HBox hBox : hboxes) {
            hBox.setOnMouseExited(event -> {
                hBox.setStyle(hBox.getStyle().replace("-fx-border-color: rgb(97, 167, 237);", "-fx-border-color: gray;"));
            });
        }
        for (HBox tool : tools) {
            tool.setOnMouseExited(event -> {
                tool.setStyle("");
            });
        }
        for (HBox shape : shapes) {
            shape.setOnMouseExited(event -> {
                if (shape != shapePressed) {
                    shape.setStyle("");
                }
            });
        }
        for (HBox dc : defColors) {
            dc.setOnMouseExited(event -> {
                if (dc != defColorPressed) {
                    dc.setStyle("");
                }
            });
        }
        btnSize.setOnMouseExited(event -> {
            btnSizeGridBox.setStyle("");
        });
    }


    /*
    Handles the currently pressed def color.
     */
    private void handleDefColors() {
        for (HBox dc : defColors) {
            dc.setOnMousePressed(event -> {
                if (dc != defColorPressed) {
                    defColorPressed.setStyle("");
                    dc.setStyle("-fx-border-color: rgb(97, 167, 237); -fx-background-color: rgb(97, 167, 237, 0.3)");
                    defColorPressed = dc;
                }
            });
        }
    }
}


