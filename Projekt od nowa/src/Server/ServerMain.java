//TODO javadoc

package Server;

import Utils.Tools;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.*;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerMain extends Application
{

    private final String[] PATHS ={
            "D:\\CatchBox\\Mount 1",
            "D:\\CatchBox\\Mount 2",
            "D:\\CatchBox\\Mount 3",
            "D:\\CatchBox\\Mount 4",
            "D:\\CatchBox\\Mount 5"
    };

    private static ServerThread server = ServerThread.getServer();

    private static ServerController controller;
    private ExecutorService threads;

    static List<String> availableUsers;

    private String file2style(File style)
    {
        try
        {
            return style.toURI().toURL().toString();
        }
        catch (MalformedURLException e)
        {
            return null;
        }
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
        public void start(Stage primaryStage) throws Exception
        {
            threads = Executors.newFixedThreadPool(20);

            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(getClass().getResource("ServerView.fxml").openStream());
            controller = loader.getController();

            primaryStage.setTitle("CatchBox Server");
            primaryStage.setResizable(false);
            Scene scene = new Scene(root, 450, 400);
            primaryStage.setScene(scene);
            String styl = file2style(new File("D:\\GitHub\\Programowanie-Obiektowe-2\\Projekt od nowa\\src\\Server\\Victoria.css"));
            if(styl != null)
            scene.getStylesheets().add(styl);
            primaryStage.show();
            availableUsers = Tools.FileLister(PATHS[0]);
            controller.displayUsers(availableUsers);

            threads.submit(this::directoryWatcher);
            server.init(2137, threads);
        }

    private void directoryWatcher()
    {
        Path folder = Paths.get(PATHS[0]);
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
                        availableUsers = Tools.FileLister(PATHS[0]);
                        Platform.runLater(() -> controller.displayUsers(availableUsers)
                        );
                        break;
                    }
                }
            }
        }
        catch(InterruptedException e)
        {}
        catch (IOException e) {
            e.printStackTrace();
        }
    }



        /*public static void displayUsers(List users)
        {
            controller.displayLoggedUsers(users);
        }
         */


}




