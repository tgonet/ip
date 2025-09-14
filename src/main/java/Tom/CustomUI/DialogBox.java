package tom.customui;

import java.io.IOException;
import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import tom.MainWindow;

/**
 * Represents a custom dialog box used in the application's chat-style interface.
 * <p>
 * Each {@code DialogBox} contains a text label and an image, which can be flipped to
 * differentiate between user messages and application responses.
 * </p>
 */

public class DialogBox extends HBox {
    @FXML
    private Label dialog;
    @FXML
    private ImageView displayPicture;

    /**
     * Creates a new {@code DialogBox} with the specified text and display image.
     * <p>
     * Loads its layout from {@code DialogBox.fxml} and sets the text and image accordingly.
     * </p>
     *
     * @param text The text message to display inside the dialog box.
     * @param img  The image to display alongside the text.
     */

    private DialogBox(String text, Image img) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        dialog.setText(text);
        displayPicture.setImage(img);
    }

    /**
     * Flips the dialog box such that the ImageView is on the left and text on the right.
     */
    private void flip() {
        ObservableList<Node> tmp = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(tmp);
        getChildren().setAll(tmp);
        setAlignment(Pos.TOP_LEFT);
    }

    /**
     * Creates a dialog box representing the user's message.
     *
     * @param text The text of the user's message.
     * @param img  The user's display image.
     * @return A {@code DialogBox} instance for the user message.
     */

    public static DialogBox getUserDialog(String text, Image img) {
        return new DialogBox(text, img);
    }

    /**
     * Creates a dialog box representing the application's (Duke's) response.
     * <p>
     * The dialog box is flipped so that the image appears on the left.
     * </p>
     *
     * @param text The text of the application's response.
     * @param img  The application's display image.
     * @return A {@code DialogBox} instance for the application response.
     */

    public static DialogBox getDukeDialog(String text, Image img) {
        var db = new DialogBox(text, img);
        db.flip();
        return db;
    }
}
