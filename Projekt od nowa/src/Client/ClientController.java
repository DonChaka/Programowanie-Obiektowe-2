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

public class ClientController
{

    public ClientController() {}

    private void initlialize() {}

    @FXML
    AnchorPane anchorPane;

    @FXML
    private Button button_send;

    @FXML
    private Label statusLabel;

    @FXML
    private Label label_userName;

    @FXML
    private Label label_path;

    @FXML
    private ListView<String> filesView;

    @FXML
    void setLoggedUser(String user) {
        label_userName.setText("Aktualnie zalogowany użytkownik: " + user);
    }

    @FXML
    void setFolderPath(String path) {
        label_path.setText("Aktualnie obserwowany folder: " + path);
    }

    @FXML
    void setStatusLabel(String text) {
        statusLabel.setText("Status: " + text);
    }

    @FXML
    void displayFiles(List<String> files) {
        ObservableList<String> items = observableArrayList();
        items.addAll(files);
        filesView.setItems(items);
    }


    @FXML
    private ListView<String> usersView;

    @FXML
    void displayUsers(List<String> users) {
        ObservableList<String> items = observableArrayList();
        items.addAll(users);
        usersView.setItems(items);
    }

    @FXML
    private void sendButtonAction() {
        ReadOnlyObjectProperty<String> temp = filesView.getSelectionModel().selectedItemProperty();
        String filename = temp.getValue();
        temp = usersView.getSelectionModel().selectedItemProperty();
        String owner = temp.getValue();

        System.out.println("Sending " + filename + " to " + owner);

        ClientMain.client.sendFile(filename, owner);

    }

}