package sample;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.*;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;

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
    private MenuItem mItemOpen;
    @FXML
    private MenuItem mItemSave;
    @FXML
    private MenuItem mItemSaveAs;
    @FXML
    private MenuItem mItemNew;
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
    @FXML
    private HBox hboxTriangle;
    @FXML
    private HBox hboxRoundRectangle;
    @FXML
    private HBox hboxPolygon;
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
    @FXML
    private Text txtCoordinates;
    @FXML
    private Text txtCanvasSize;
    //endregion

    @FXML
    private GridPane btnSizeGridBox;
    //endregion

    private int size;

    private Color primaryColor = Color.rgb(0, 0, 0);
    private Color secondaryColor = Color.rgb(255, 255, 255);

    private GraphicsContext gc;

    private Shapes2D shapeDrawer;

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
        shapeDrawer = new Shapes2D(gc);
        fileSaver = new FileSaver(drawingCanvas);

        hboxes = new HBox[]{hbox1, hbox2, hbox3, hbox4, hbox5, hbox6, hbox7, hbox8, hbox9, hbox10, hbox11, hbox12, hbox13, hbox14, hbox15, hbox16, hbox17, hbox18, hbox19, hbox20};
        tools = new HBox[]{hboxPencil, hboxDropper, hboxRubber, hboxText};
        shapes = new HBox[]{hboxLine, hboxRectangle, hboxCircle, hboxTriangle, hboxRoundRectangle, hboxPolygon};
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
        handleMenu();
        setOnShutDown();
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


    static boolean firstTimeSave = true;
    static boolean changesMade = false;
    static File f;
    private FileSaver fileSaver;
    static String saveFormatType;


    private void saveToFile(String type) {
        if (f != null) {
            fileSaver.saveToFile(type);
        }
    }


    private void saveToFile() {
        fileSaver.saveToFile();
    }


    /*
    Handles the buttons in the menu.
     */
    private void handleMenu() {
        //Handles the first button of the 'File' menu.
        mItemNew.setOnAction(event -> {
            if (changesMade) {
                changesWarning(false);
            }
            gc.clearRect(0, 0, drawingCanvas.getWidth(), drawingCanvas.getHeight());
            changesMade = false;
            firstTimeSave = true;
        });

        //Handles the second button of the 'File' menu.
        mItemOpen.setOnAction(event -> {
            FileChooser fc = new FileChooser();
            fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PNG", "*.png"),
                    new FileChooser.ExtensionFilter("JPG", "*.jpg", "*.jpeg", "*.jpe", "*.jfif"));
            File f = fc.showOpenDialog(null);

            if (f != null) {
                try {
                    BufferedImage bufferedImage = ImageIO.read(f);
                    Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                    gc.drawImage(image, 0, 0);
                    changesMade = true;
                } catch (IOException ex) {
                    System.err.println(ex.getMessage());
                }
            }
        });

        //Handles the third button of the 'File' menu.
        mItemSave.setOnAction(event -> {
            if (firstTimeSave) {
                saveToFile();
            } else {
                saveToFile(saveFormatType);
            }
        });
        mItemSave.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));

        //Handles the fourth button of the 'File' menu.
        mItemSaveAs.setOnAction(event -> {
            saveToFile();
        });
    }


    /*
    Shows a dialog if you try to close the application without saving.
     */
    private void changesWarning(boolean withExit) {
        if (changesMade) {
            Alert alert = new Alert(Alert.AlertType.WARNING);

            ButtonType btnSave = new ButtonType("Save");
            ButtonType btnDontSave = new ButtonType("Don't Save");
            ButtonType btnClose = new ButtonType("Close", ButtonBar.ButtonData.CANCEL_CLOSE);

            alert.setHeaderText("Unsaved changes.");

            alert.getButtonTypes().setAll(btnSave, btnDontSave, btnClose);

            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent()) {
                if (result.get() == btnSave) {
                    if (saveFormatType != null) {
                        saveToFile(saveFormatType);
                    } else {
                        saveToFile();
                    }
                    if (withExit) {
                        Platform.exit();
                    }
                } else if (result.get() == btnDontSave) {
                    alert.close();
                    if (withExit) {
                        Platform.exit();
                    }
                } else {
                    alert.close();
                }
            }
        } else if (withExit) {
            Platform.exit();
        }
    }


    private void setOnShutDown() {
        Stage primaryStage = Main.getPrimaryStage();

        primaryStage.setOnCloseRequest(event -> {
            event.consume();
            changesWarning(true);
        });

        Main.setPrimaryStage(primaryStage);
    }


    //on click coordinates
    private double x1;
    private double y1;

    //on release coordinates
    private double x2;
    private double y2;

    //x & y coordinates for polygon
    private double polyX;
    private double polyY;

    private boolean polygonIsFirst = true;


    /*
    Logic for when the mouse is pressed.
     */
    private void handleDrawingCanvas() {
        drawingCanvas.setOnMouseMoved(event -> {
            txtCoordinates.setText(String.format("%.2f, %.2fpx", event.getX(), event.getY()));
        });
        drawingCanvas.setOnMouseExited(event -> {
            txtCoordinates.setText("");
        });
        txtCanvasSize.setText(String.format("%.2f, %.2fpx", drawingCanvas.getWidth(), drawingCanvas.getHeight()));
        drawingCanvas.setOnMousePressed(event -> {
            setSize();
            gc.setLineWidth(size);
            if (event.getButton() == MouseButton.PRIMARY) {
                gc.setStroke(primaryColor);
            } else if (event.getButton() == MouseButton.SECONDARY) {
                gc.setStroke(secondaryColor);
            }
            x1 = event.getX();
            y1 = event.getY();
            if (shapePressed == hboxPolygon) {
                if (!polygonIsFirst) {
                    x1 = x2;
                    y1 = y2;
                } else {
                    polyX = x1;
                    polyY = y1;
                }
            }
            gc.beginPath();
            gc.moveTo(x1, y1);
            changesMade = true;
        });


        drawingCanvas.setOnMouseDragged(event -> {
            changesMade = true;
            if (!shapeIsPressed) {
                gc.lineTo(event.getX(), event.getY());
                gc.stroke();
            }
        });


        /*
        Handle shapes.
         */
        drawingCanvas.setOnMouseReleased(event -> {
            x2 = event.getX();
            y2 = event.getY();

            changesMade = true;

            if (x1 == x2 && y1 == y2)
                return;

            double width = x2 - x1;
            double height = y2 - y1;

            if (shapePressed == hboxLine) {
                shapeDrawer.drawLine(x2, y2);
            } else if (shapePressed == hboxRectangle) {
                shapeDrawer.drawRectangle(x1, y1, width, height);
            } else if (shapePressed == hboxCircle) {
                shapeDrawer.drawOval(x1, y1, width, height);
            } else if (shapePressed == hboxTriangle) {
                shapeDrawer.drawTriangle(x1, y1, x2, y2, width);
            } else if (shapePressed == hboxRoundRectangle) {
                shapeDrawer.drawRoundRectangle(x1, y1, width, height);
            } else if (shapePressed == hboxPolygon) {
                if (polygonIsFirst) {
                    polygonIsFirst = false;
                }
                if (x2 >= polyX - 10 && x2 <= polyX + 10 && y2 <= polyY + 10 && y2 >= polyY - 10) {
                    gc.lineTo(polyX, polyY);
                    gc.stroke();
                    polygonIsFirst = true;
                    return;
                }
                gc.lineTo(x2, y2);
                gc.stroke();
            }
        });
    }

    private boolean shapeIsPressed;

    /*
    Handle shapes.
     */
    private void handleShapes() {
        for (HBox shape : shapes) {
            shape.setOnMouseClicked(event -> {
                if (shapePressed == null) {
                    shapePressed = shape;
                    shapeIsPressed = true;
                    shapePressed.setStyle("-fx-border-color: rgb(97, 167, 237); -fx-background-color: rgba(97, 167, 237, 0.3)");
                } else if (shape != shapePressed) {
                    shapeIsPressed = true;
                    shapePressed.setStyle("");
                    shapePressed = shape;
                } else {
                    shapePressed.setStyle("");
                    shapePressed = null;
                    shapeIsPressed = false;
                }
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
                    shape.setStyle("-fx-border-color: rgb(97, 167, 237); -fx-background-color: rgba(97, 167, 237, 0.1)");
                }
            });
        }
        for (HBox dc : defColors) {
            dc.setOnMouseEntered(event -> {
                if (dc != defColorPressed) {
                    dc.setStyle("-fx-border-color: rgb(97, 167, 237); -fx-background-color: rgba(97, 167, 237, 0.1)");
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