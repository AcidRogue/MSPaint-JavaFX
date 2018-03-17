package Preferences;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import sample.FileSaver;

public class PreferencesController {
    @FXML
    private ResourceBundle resources;
    @FXML
    private URL location;
    @FXML
    private Text txaLastSaved;
    @FXML
    private Text txaSizeOnDisc;
    @FXML
    private Text txaResolution;
    @FXML
    private RadioButton rBtnInches;
    @FXML
    private ToggleGroup group;
    @FXML
    private RadioButton rBtnCm;
    @FXML
    private RadioButton rBtnPixels;
    @FXML
    private TextField txfWidth;
    @FXML
    private TextField txfHeight;
    @FXML
    private Button btnDefault;
    @FXML
    private Button btnOK;
    @FXML
    private Button btnCancel;
    @FXML
    private AnchorPane root;

    private boolean changesMade;

    @FXML
    void initialize() {
        check();
    }

    private void handleBtnOk(){
    }

    private void check(){
        String lastSaved = FileSaver.dateSaved;
        if(!lastSaved.equals("")){
            txaLastSaved.setText(lastSaved);
        }
    }

    private void handleRadioButtons(){
        group.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            changesMade = true;
        });
    }
}
