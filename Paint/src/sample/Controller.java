package sample;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.*;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.ImageView;
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
    private ImageView pencil;
    @FXML
    private ImageView text;
    @FXML
    private ImageView rubber;
    @FXML
    private ImageView dropper;
    @FXML
    private ImageView brush;
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
    @FXML
    private HBox hboxLine;
    @FXML
    private GridPane btnSizeGridBox;
    //endregion

    private int size;
    private Color primaryColor = Color.rgb(0, 0, 0);
    private Color secondaryColor = Color.rgb(255, 255, 255);
    private GraphicsContext gc;
    private HBox[] tools;
    private HBox[] hboxes;
    private Pane pressed;
    private boolean def1ColorPressed;
    private boolean def2ColorPressed;

    /*
    Main method that is called when the program is run. Used for initializing methods and variables.
     */
    @FXML
    void initialize() {
        gc = drawingCanvas.getGraphicsContext2D();

        hboxes = new HBox[]{hbox1, hbox2, hbox3, hbox4, hbox5, hbox6, hbox7, hbox8, hbox9, hbox10, hbox11, hbox12, hbox13, hbox14, hbox15, hbox16, hbox17, hbox18, hbox19, hbox20};
        tools = new HBox[]{hboxPencil, hboxDropper, hboxRubber, hboxText};

        def1ColorPressed = true;
        hboxDefColor1.setStyle("-fx-border-color: rgb(97, 167, 237); -fx-background-color: rgb(97, 167, 237, 0.3)");
        pressed = defColor1;
        def2ColorPressed = false;

        btnSize.getItems().addAll("1px", "3px", "5px", "8px");
        btnSize.setValue("1px");

        handleColors();
        handleOnMouseOverColors();
        handleOnMouseExitedColors();
        drawingCanvasSetOnPressed();
        drawingCanvasSetOnDragged();
        defColorsOnPressed();
    }


    /*
    Changes the currently picked color.
     */
    private void changeDefColor(String colorStr, int r, int g, int b) {
        if(pressed == defColor1){
            primaryColor = Color.rgb(r, g, b);
            defColor1.setStyle("-fx-border-color: gray;-fx-background-color: " + colorStr);
        }
        else{
            secondaryColor = Color.rgb(r, g, b);
            defColor2.setStyle("-fx-border-color: gray;-fx-background-color: " + colorStr);
        }
    }


    /*
    Logic for when the mouse is pressed.
     */
    private void drawingCanvasSetOnPressed() {
        drawingCanvas.setOnMousePressed(event -> {
            setSize();
            gc.setLineWidth(size);
            if (event.getButton() == MouseButton.PRIMARY) {
                gc.setStroke(primaryColor);
            } else if (event.getButton() == MouseButton.SECONDARY) {
                gc.setStroke(secondaryColor);
            }
            gc.beginPath();
            gc.lineTo(event.getX(), event.getY());
            gc.stroke();
        });
    }


    /*
    Logic for when the mouse is dragged.
     */
    private void drawingCanvasSetOnDragged() {
        drawingCanvas.setOnMouseDragged(event -> {
            gc.lineTo(event.getX(), event.getY());
            gc.stroke();
        });
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
                    if(event.getButton() == MouseButton.PRIMARY)
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
        hboxDefColor1.setOnMouseEntered(event -> {
            hboxDefColor1.setStyle("-fx-border-color: rgb(97, 167, 237); -fx-background-color: rgb(97, 167, 237, 0.3)");
        });
        hboxDefColor2.setOnMouseEntered(event -> {
            hboxDefColor2.setStyle("-fx-border-color: rgb(97, 167, 237); -fx-background-color: rgb(97, 167, 237, 0.3)");
        });
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
        hboxDefColor1.setOnMouseExited(event -> {
            if(!def1ColorPressed){
                hboxDefColor1.setStyle("");
            }
        });
        hboxDefColor2.setOnMouseExited(event -> {
            if(!def2ColorPressed){
                hboxDefColor2.setStyle("");
            }
        });
        btnSize.setOnMouseExited(event -> {
            btnSizeGridBox.setStyle("");
        });
    }


    /*
    Handles the currently pressed def color.
     */
    @SuppressWarnings("Duplicates")
    private void defColorsOnPressed(){
        defColor1.setOnMousePressed(event -> {
            def1ColorPressed = true;
            pressed = defColor1;
            if(def2ColorPressed){
                def2ColorPressed = false;
                hboxDefColor2.setStyle("");
            }
        });
        defColor2.setOnMousePressed(event -> {
            def2ColorPressed = true;
            pressed = defColor2;
            if(def1ColorPressed){
                def1ColorPressed = false;
                hboxDefColor1.setStyle("");
            }
        });
    }
}

