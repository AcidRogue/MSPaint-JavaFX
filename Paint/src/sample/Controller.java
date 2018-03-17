package sample;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.*;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
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
    private AnchorPane anchorPane;
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
    private MenuItem mItemPreferences;
    @FXML
    private MenuItem mItemExit;
    @FXML
    private MenuItem mItemRevert;
    @FXML
    private MenuItem mItemRedo;
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
    @FXML
    private GridPane btnSizeGridBox;
    //endregion

    private double size = 1.2;

    private Color primaryColor = Color.rgb(0, 0, 0);
    private Color secondaryColor = Color.rgb(255, 255, 255);

    private GraphicsContext gc;

    private Shapes2D shapeDrawer = new Shapes2D();

    private HBox[] tools;
    private HBox[] shapes;
    private HBox[] defColors;
    private HBox[] hboxes;

    private HBox defColorPressed;
    private HBox toolPressed;

    private List<Canvas> list;
    private int counter = -1;

    /*
    Main method that is called when the program is run. Used for initializing methods and variables.
     */
    @FXML
    void initialize() {
        gc = drawingCanvas.getGraphicsContext2D();

        shapeDrawer.setCanvas(drawingCanvas, Color.BLACK);

        drawingCanvasWidth = (int) drawingCanvas.getWidth();
        drawingCanvasHeight = (int) drawingCanvas.getHeight();

        list = new ArrayList<>();

        hboxes = new HBox[]{hbox1, hbox2, hbox3, hbox4, hbox5, hbox6, hbox7, hbox8, hbox9, hbox10, hbox11, hbox12, hbox13, hbox14, hbox15, hbox16, hbox17, hbox18, hbox19, hbox20};
        tools = new HBox[]{hboxPencil, hboxDropper, hboxRubber, hboxText, hboxBrush};
        shapes = new HBox[]{hboxLine, hboxRectangle, hboxCircle, hboxTriangle, hboxRoundRectangle, hboxPolygon};
        defColors = new HBox[]{hboxDefColor1, hboxDefColor2};

        hboxDefColor1.setStyle("-fx-border-color: rgb(97, 167, 237); -fx-background-color: rgb(97, 167, 237, 0.3)");
        defColorPressed = (HBox) defColor1.getParent();

        toolPressed = hboxBrush;
        hboxBrush.setStyle("-fx-border-color: rgb(97, 167, 237); -fx-background-color: rgba(97, 167, 237, 0.3)");

        btnSize.getItems().addAll("1px", "3px", "5px", "8px");
        btnSize.setValue("1px");

        handleSize();
        handleColors();
        handleOnMouseOver();
        handleOnMouseExitedColors();
        handleDrawingCanvas();
        handleDefColors();
        handleToolsAndShapes();
        handleMenu();
        setOnShutDown();
        undo();
        redo();
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

    static int drawingCanvasWidth;
    static int drawingCanvasHeight;

    private void saveToFile(String type) {
        if (f != null) {
            fileSaver = new FileSaver(list);
            fileSaver.saveToFile(type);
        }
    }


    private void saveToFile() {
        fileSaver = new FileSaver(list);
        fileSaver.saveToFile();
    }

    /*
    Handles the buttons in the menu.
     */
    private void handleMenu() {
        //Handles the 'New' button of the 'File' menu.
        mItemNew.setOnAction(event -> {
            if (changesMade) {
                changesWarning(false);
            }
            gc.clearRect(0, 0, drawingCanvasWidth, drawingCanvasHeight);
            list = new ArrayList<>();
            changesMade = false;
            firstTimeSave = true;
        });

        //Handles the 'Open' button of the 'File' menu.
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

                }
            }
        });

        //Handles the 'Save' button of the 'File' menu.
        mItemSave.setOnAction(event -> {
            if (firstTimeSave) {
                saveToFile();
            } else {
                saveToFile(saveFormatType);
            }
        });

        mItemSave.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));

        //Handles the 'Save As' button of the 'File' menu.
        mItemSaveAs.setOnAction(event -> {
            saveToFile();
        });

        mItemRevert.setAccelerator(new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN));

        mItemRedo.setAccelerator(new KeyCodeCombination(KeyCode.R, KeyCombination.CONTROL_DOWN));

        //Handles the 'Preferences' button of the 'File' menu.
        mItemPreferences.setOnAction(event -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Preferences/preferences_gui.fxml"));
                Parent root1 = (Parent) fxmlLoader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root1));
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        mItemExit.setOnAction(event -> {
            changesWarning(true);
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

    private String hexToRgb(Color c) {
        return String.format("rgb(%d, %d, %d)",
                Integer.valueOf(c.toString().substring(2, 4), 16),
                Integer.valueOf(c.toString().substring(4, 6), 16),
                Integer.valueOf(c.toString().substring(6, 8), 16));
    }

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
            Canvas c = new Canvas(drawingCanvasWidth, drawingCanvasHeight);
            c.setOnMousePressed(drawingCanvas.getOnMousePressed());
            c.setOnMouseDragged(drawingCanvas.getOnMouseDragged());
            c.setOnMouseReleased(drawingCanvas.getOnMouseReleased());

            try {
                if (list.contains(list.get(++counter))) {
                    for (int i = list.size() - 1; i >= counter; i--) {
                        list.remove(i);
                    }
                }
            } catch (IndexOutOfBoundsException e) {

            }

            list.add(c);
            anchorPane.getChildren().add(c);
            gc = c.getGraphicsContext2D();

            gc.setLineWidth(size);

            x1 = event.getX();
            y1 = event.getY();


            if (event.getButton() == MouseButton.PRIMARY) {
                gc.setStroke(primaryColor);
                shapeDrawer.setCanvas(c, primaryColor);
            } else if (event.getButton() == MouseButton.SECONDARY) {
                gc.setStroke(secondaryColor);
                shapeDrawer.setCanvas(c, secondaryColor);
            }

            if (toolIsPressed) {
                if (toolPressed == hboxRubber) {
                    gc.setStroke(Color.WHITE);
                } else if (toolPressed == hboxPencil) {
                    gc.setLineWidth(0.3 * size);
                } else if (toolPressed == hboxDropper) {
                    fileSaver = new FileSaver(list);
                    Canvas temp = fileSaver.createCanvas(list);
                    PixelReader pr = temp.snapshot(null, new WritableImage(drawingCanvasWidth, drawingCanvasHeight)).getPixelReader();
                    Color tempColor = pr.getColor((int) event.getX(), (int) event.getY());
                    shapeDrawer.setCanvas(c, tempColor);
                    defColor1.setStyle("-fx-border-color: gray; -fx-background-color: " + hexToRgb(tempColor));
                    primaryColor = tempColor;
                    gc.setStroke(primaryColor);
                } else if (toolPressed == hboxPolygon) {
                    if (!polygonIsFirst) {
                        x1 = x2;
                        y1 = y2;
                    } else {
                        polyX = x1;
                        polyY = y1;
                    }
                }
            }

            gc.beginPath();
            gc.moveTo(x1, y1);
            changesMade = true;
        });


        drawingCanvas.setOnMouseDragged(event -> {
            changesMade = true;
            if (!toolIsPressed || toolPressed == hboxRubber || toolPressed == hboxPencil || toolPressed == hboxBrush) {
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

            if (x1 == x2 && y1 == y2 && toolIsPressed)
                return;

            changesMade = true;

            double width = x2 - x1;
            double height = y2 - y1;

            if (toolPressed == hboxLine) {
                shapeDrawer.drawLine(x2, y2);
            } else if (toolPressed == hboxRectangle) {
                shapeDrawer.drawRectangle(x1, y1, width, height);
            } else if (toolPressed == hboxCircle) {
                shapeDrawer.drawOval(x1, y1, width, height);
            } else if (toolPressed == hboxTriangle) {
                shapeDrawer.drawTriangle(x1, y1, x2, y2, width);
            } else if (toolPressed == hboxRoundRectangle) {
                shapeDrawer.drawRoundRectangle(x1, y1, width, height);
            } else if (toolPressed == hboxPolygon) {
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

    void redo() {
        mItemRedo.setOnAction(e -> {
            if (counter < list.size() - 1) {
                anchorPane.getChildren().add(list.get(++counter));
            }
        });
    }

    void undo() {
        mItemRevert.setOnAction(e -> {
            if (counter >= 0) {
                anchorPane.getChildren().remove(list.get(counter--));
            }
        });
    }

    private boolean toolIsPressed;

    private void toolsForEach(HBox[] tools) {
        for (HBox tool : tools) {
            tool.setOnMouseClicked(event -> {
                if (toolPressed == null) {
                    toolPressed = tool;
                    toolIsPressed = true;
                    toolPressed.setStyle("-fx-border-color: rgb(97, 167, 237); -fx-background-color: rgba(97, 167, 237, 0.3)");
                } else if (tool != toolPressed) {
                    toolIsPressed = true;
                    toolPressed.setStyle("");
                    toolPressed = tool;
                }
            });
        }
    }


    /*
    Handle shapes.
     */
    private void handleToolsAndShapes() {
        toolsForEach(shapes);
        toolsForEach(tools);
    }


    /*
    Takes the size from the size choice box and sets it.
     */
    private void handleSize() {
        btnSize.setOnAction(event -> {
            size = Integer.parseInt(Character.toString(btnSize.getValue().toString().charAt(0)));
        });
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
    private void handleOnMouseOver() {
        for (HBox hBox : hboxes) {
            hBox.setOnMouseEntered(event -> {
                hBox.setStyle(hBox.getStyle().replace("-fx-border-color: gray;", "-fx-border-color: rgb(97, 167, 237);"));
            });
        }
        for (HBox tool : tools) {
            tool.setOnMouseEntered(event -> {
                if (tool != toolPressed) {
                    tool.setStyle("-fx-border-color: rgb(97, 167, 237);");
                }
            });
        }
        for (HBox shape : shapes) {
            shape.setOnMouseEntered(event -> {
                if (shape != toolPressed) {
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
                if (tool != toolPressed) {
                    tool.setStyle("");
                }
            });
        }
        for (HBox shape : shapes) {
            shape.setOnMouseExited(event -> {
                if (shape != toolPressed) {
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