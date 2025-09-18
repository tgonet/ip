package tom;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.VBox;
import tom.customui.DialogBox;
import tom.exception.TomException;
import tom.ui.UI;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Tom tom;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/123.jpg"));
    private Image dukeImage = new Image(this.getClass().getResourceAsStream("/images/456.jpg"));
    private BackgroundImage backgroundImage = new BackgroundImage(
            new Image("/images/background.jpg"),
            BackgroundRepeat.NO_REPEAT,
            BackgroundRepeat.NO_REPEAT,
            BackgroundPosition.CENTER,
            new BackgroundSize(
                    100, 100, true, true, true, true) // adjust scaling
    );

    private UI ui;

    @FXML
    public void initialize() {
        ui = new UI();
        dialogContainer.setBackground(new Background(backgroundImage));
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        dialogContainer.getChildren().addAll(
                DialogBox.getDukeDialog(ui.printWelcome(), dukeImage));
    }

    /** Injects the Duke instance */
    public void setTom(Tom t) {
        tom = t;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing
     * Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response;
        try {
            response = tom.getResponse(input);
            dialogContainer.getChildren().addAll(
                    DialogBox.getUserDialog(input, userImage),
                    DialogBox.getDukeDialog(response, dukeImage));
        } catch (TomException e) {
            dialogContainer.getChildren().addAll(
                    DialogBox.getUserDialog(input, userImage),
                    DialogBox.getDukeDialog(e.getMessage(), dukeImage));
        } finally {
            userInput.clear();
        }
    }
}
