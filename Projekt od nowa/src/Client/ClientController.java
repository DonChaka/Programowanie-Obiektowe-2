package Client;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

import java.util.List;

import static javafx.collections.FXCollections.observableArrayList;


/**
 * Client FX interface controller
 * @author Szymon Kuzik
 * @version 1.0
 */
public class ClientController
{

    public ClientController() {}


    /**
     * Main AnchorPane at JavaFX Cilent Interface
     */
    @FXML
    AnchorPane anchorPane;

    /**
     * Button to send files
     */
    @FXML
    private Button button_send;

    /**
     * Label with status of synchronization
     */
    @FXML
    private Label statusLabel;

    /**
     * Label with username of client
     */
    @FXML
    private Label label_userName;

    /**
     * Label with path to local folder
     */
    @FXML
    private Label label_path;

    /**
     * List of files in local folder
     */
    @FXML
    private ListView<String> filesView;

    /**
     * List of available users connected to server
     */
    @FXML
    private ListView<String> usersView;

    /**
     * Method to set logged username to label_username
     * @param user username of client
     */
    @FXML
    void setLoggedUser(String user) {
        label_userName.setText("Aktualnie zalogowany użytkownik: " + user);
    }

    /**
     * Method to set path to label_path
     * @param path path to local folder
     */
    @FXML
    void setFolderPath(String path) {
        label_path.setText("Aktualnie obserwowany folder: " + path);
    }

    /**
     * Method setting proper status on statusLabel
     * @param text status to be displayed
     */
    @FXML
    void setStatusLabel(String text) {
        statusLabel.setText("Status: " + text);
    }

    /**
     * Method displaying files in local folder on filesView
     * @param files list of files in folder
     */
    @FXML
    void displayFiles(List<String> files) {
        ObservableList<String> items = observableArrayList();
        items.addAll(files);
        filesView.setItems(items);
    }

    /**
     * Method displaying available users on usersView
     * @param users List of available users
     */
    @FXML
    void displayUsers(List<String> users) {
        ObservableList<String> items = observableArrayList();
        items.addAll(users);
        usersView.setItems(items);
    }

    /**
     * Method getting selected file from filesView and user from usersView and passing them to add to files to send
     */
    @FXML
    private void sendButtonAction() {
        ReadOnlyObjectProperty<String> temp = filesView.getSelectionModel().selectedItemProperty();
        String filename = temp.getValue();
        temp = usersView.getSelectionModel().selectedItemProperty();
        String owner = temp.getValue();

        System.out.println("Wysylanie " + filename + " do " + owner);

        ClientMain.client.sendFile(filename, owner);

    }
}