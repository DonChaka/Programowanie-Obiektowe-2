//TODO javadoc

package Client;

import Utils.Tools;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientMain extends Application {

    private String userName;
    private String path;
    private ExecutorService threads;

    static ClientController controller;
    static ClientThread client;

    static public void setStatusLabel(String text)
    {
        controller.setStatusLabel(text);
    }

    @Override
    public void stop()
    {
        threads.shutdown();
        threads.shutdownNow();
        System.out.println("Closing app");
        System.exit(0);
    }


    @Override
    public void init()
    {
        Parameters parameters = getParameters();
        List<String> params = parameters.getUnnamed();
        userName = params.get(0);
        path = params.get(1);
        threads = Executors.newFixedThreadPool(5);
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(getClass().getResource("ClientView.fxml").openStream());
        controller = loader.getController();

        controller.displayFiles(Tools.FileLister(path));

        controller.setLoggedUser(userName);
        controller.setFolderPath(path);

        primaryStage.setTitle("CatchBox");
        Scene scene = new Scene(root, 480, 400);
        primaryStage.setScene(scene);
        scene.getStylesheets().add(ClientMain.class.getResource("Victoria.css").toExternalForm());
        primaryStage.setResizable(false);
        primaryStage.show();

        client = new ClientThread("127.0.0.1", 2137, path, userName, threads);
        threads.submit(this::directoryWatcher);
    }

    /**
    * Function oversees users folder and puts its content onto the application window.
    * If anything changes, function gets folder content again and updates list view
    */

    private void directoryWatcher()
    {
        Path folder = Paths.get(path);
        try
        {

            WatchService watcher = folder.getFileSystem().newWatchService();
            WatchKey watchKey = folder.register(watcher,
                    StandardWatchEventKinds.ENTRY_CREATE,
                    StandardWatchEventKinds.ENTRY_DELETE,
                    StandardWatchEventKinds.ENTRY_MODIFY);

            while (true) {
                Thread.sleep(25); // lekki spowalniacz oraz opcja na zamkniecie watku przez interruptedException
                List<WatchEvent<?>> events = watchKey.pollEvents();
                for (WatchEvent event : events) {
                    if (event.kind() == StandardWatchEventKinds.ENTRY_CREATE || event.kind() == StandardWatchEventKinds.ENTRY_DELETE) {
                        List<String> fileList = Tools.FileLister(path);
                        Platform.runLater(() -> controller.displayFiles(fileList));
                        break;
                    }
                }
            }
        }
        catch(InterruptedException e)
        {
            return;
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
