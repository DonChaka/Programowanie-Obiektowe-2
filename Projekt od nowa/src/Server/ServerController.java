package Server;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;

import java.util.List;

import static javafx.collections.FXCollections.observableArrayList;

public class ServerController {


    private String chosenDisc = "Mount 1";
    private String base = "D:\\CatchBox\\";

    public ServerController() {}
    //private void initlialize() {}

    @FXML
    private ListView<String> loggedUsers;

    @FXML ListView<String> filesView;

    @FXML
    private MenuButton discMenu;


    @FXML
    private void chooseDisc1()
    {
        chosenDisc = "Mount 1";
        discMenu.setText("Disc1");
        displayFiles();
    }

    @FXML
    private void chooseDisc2()
    {
        chosenDisc = "Mount 2";
        discMenu.setText("Disc2");
        displayFiles();
    }


    @FXML
    private void chooseDisc3()
    {
        chosenDisc = "Mount 3";
        discMenu.setText("Disc3");
        displayFiles();
    }


    @FXML
    private void chooseDisc4()
    {
        chosenDisc = "Mount 4";
        discMenu.setText("Disc4");
        displayFiles();
    }


    @FXML
    private void chooseDisc5()
    {
        chosenDisc = "Mount 5";
        discMenu.setText("Disc5");
        displayFiles();
    }

    @FXML
    private void displayFiles()
    {
        ReadOnlyObjectProperty<String> temp = loggedUsers.getSelectionModel().selectedItemProperty();
        String user = temp.getValue();
        if(user == null)
            return;

        ObservableList<String> items = observableArrayList();
        items.addAll(Utils.Tools.FileLister(base + chosenDisc + "\\" + user + "\\"));
        filesView.setItems(items);
        System.out.println(base + chosenDisc + "\\" + user + "\\");
    }

    @FXML
    void displayUsers(List<String> logged)
    {
        ObservableList<String> items = observableArrayList();
        items.addAll(logged);
        loggedUsers.setItems(items);
    }



}
