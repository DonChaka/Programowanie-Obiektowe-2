package Server;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;

import java.util.List;

import static javafx.collections.FXCollections.observableArrayList;


/**
 * JavaFX controller for server interface
 *
 * @author Szymon Kuzik
 * @version 1.0
 */
public class ServerController {

    /**
     * Chosen disc to display files default value: Mount 1
     */
    private String chosenDisc = "Mount 1";

    /**
     * Base for paths to displaying users files
     */
    private String base = "D:\\CatchBox\\";

    /**
     * Main constructor
     */
    public ServerController() {}

    /**
     * Lisr of users that connected to server at least once
     */
    @FXML
    private ListView<String> loggedUsers;

    /**
     * List of files of chosen user
     */
    @FXML ListView<String> filesView;

    /**
     * Menu to switch between server discs
     */
    @FXML
    private MenuButton discMenu;


    /**
     * Method to change disc from which files of user are displayed
     */
    @FXML
    private void chooseDisc1()
    {
        chosenDisc = "Mount 1";
        discMenu.setText("Disc1");
        displayFiles();
    }

    /**
     * Method to change disc from which files of user are displayed
     */
    @FXML
    private void chooseDisc2()
    {
        chosenDisc = "Mount 2";
        discMenu.setText("Disc2");
        displayFiles();
    }

    /**
     * Method to change disc from which files of user are displayed
     */
    @FXML
    private void chooseDisc3()
    {
        chosenDisc = "Mount 3";
        discMenu.setText("Disc3");
        displayFiles();
    }

    /**
     * Method to change disc from which files of user are displayed
     */
    @FXML
    private void chooseDisc4()
    {
        chosenDisc = "Mount 4";
        discMenu.setText("Disc4");
        displayFiles();
    }

    /**
     * Method to change disc from which files of user are displayed
     */
    @FXML
    private void chooseDisc5()
    {
        chosenDisc = "Mount 5";
        discMenu.setText("Disc5");
        displayFiles();
    }

    /**
     * Method displaying files based on selected user and chosen disc
     */
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

    /**
     * Method displaying list of users connected to the server at least once
     * @param logged List of users
     */
    @FXML
    void displayUsers(List<String> logged)
    {
        ObservableList<String> items = observableArrayList();
        items.addAll(logged);
        loggedUsers.setItems(items);
    }



}
