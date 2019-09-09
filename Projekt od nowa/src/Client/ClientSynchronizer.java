package Client;

import Utils.FileContainer;
import javafx.application.Platform;

import java.io.*;
import java.net.SocketException;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;

/**
 * Class synchronizing files with server
 * @author Szymon Kuzik
 * @version 1.0
 */
class ClientSynchronizer
{
    /**
     * ObjectOutputStream of client
     */
    private ObjectOutputStream out;

    /**
     * ObjectInputStream of client
     */
    private ObjectInputStream in;

    /**
     * Thread pool
     */
    private ExecutorService threads;

    /**
     * Path to local folder
     */
    private String path;

    /**
     * List of files to be sent to other users
     */
    private List<FileContainer> prioritizedFiles;

    /**
     * Username of client
     */
    private String userName;

    /**
     * Slower to simulate network lags
     */
    private final int bumper = 2000;

    /**
     *
     * @param in input stream of client
     * @param out output stream of client
     * @param path observed path
     * @param threads thread pool
     * @param prioritizedFiles list of files to be sent to other users
     * @param username username of client
     */
    ClientSynchronizer(ObjectInputStream in, ObjectOutputStream out, String path, ExecutorService threads, List<FileContainer> prioritizedFiles, String username)
    {
        this.in = in;
        this.out = out;
        this.path = path;
        this.threads = threads;
        this.prioritizedFiles = prioritizedFiles;
        this.userName = username;
    }


    /**
     * This function with ServerSynchronizer keeps both local and server folders up to date to each other.
     * Data is exchanged in such order:
     *  - Server sends list of available users
     *  - Client sends list of their files
     *  - Server sends list of files missing on server
     *  - Client sends one file missing on server or sends file to another user
     *  - Server saves file to proper user folders
     *  - Server sends one file missing on client
     */
    void synchronizer()
    {
        for (;;)
        {
            try
            {

                Random rand = new Random();

                Platform.runLater(() -> ClientMain.setStatusLabel("Waiting"));
                Thread.sleep(25); // lekki spowalniacz oraz opcja na zamkniecie watku przez interruptedException

                List<String> loggedUsers = (List<String>) in.readObject();
                loggedUsers.remove(userName);
                ClientMain.controller.displayUsers(loggedUsers);

                //Start wysylania jednego(1) pliku na serwer;
                List<String> files = Utils.Tools.FileLister(path);
                out.writeObject(files);

                List<String> files2Send = (List<String>) in.readObject();

                if (!prioritizedFiles.isEmpty())
                {
                    Platform.runLater(() -> ClientMain.setStatusLabel("Sending " + prioritizedFiles.get(0).getName() + " to " + prioritizedFiles.get(0).getOwner()));

                    int k = rand.nextInt(bumper) + bumper;
                    for(int i = 0 ; i < k ; i++)
                        for(int j = 0; j < k; j++)
                            Math.pow(Math.log10(Math.log10(Math.log10(i))), j);

                    out.writeObject(prioritizedFiles.get(0));
                    prioritizedFiles.remove(0);
                }
                else if (!files2Send.isEmpty())
                {
                    try
                    {

                        Platform.runLater(() -> ClientMain.setStatusLabel("Saving file on server"));

                        int k = rand.nextInt(bumper) + bumper;
                        for(int i = 0 ; i < k ; i++)
                            for(int j = 0; j < k; j++)
                                Math.pow(Math.log10(Math.log10(Math.log10(i))), j);

                        File file = new File (path + "\\" + files2Send.get(0));
                        byte[] content = new byte[(int)file.length()];
                        FileInputStream fis = new FileInputStream(file);
                        BufferedInputStream bis = new BufferedInputStream(fis);
                        bis.read(content, 0, content.length);

                        FileContainer sending = new FileContainer(userName, files2Send.get(0), content);

                        out.writeObject(sending);
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
                else
                {
                    out.writeObject(null);
                }
                // koniec wysylania pliku;

                //Odbieranie i zapis pliku;
                FileContainer recived = (FileContainer) in.readObject();
                if(recived == null)
                    continue;

                Platform.runLater(() -> ClientMain.setStatusLabel("Receiving file from server"));

                int k = rand.nextInt(bumper) + bumper;
                for(int i = 0 ; i < k ; i++)
                    for(int j = 0; j < k; j++)
                        Math.pow(Math.log10(Math.log10(Math.log10(i))), j);

                if(recived.getOwner().equals(userName))
                {
                    threads.submit( () ->
                    {

                        try(FileOutputStream fos = new FileOutputStream(path + "\\" + recived.getName()))
                        {
                            fos.write(recived.getContent());

                            //faktyczny zapis pliku
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                }
                //wyslane i odebrane czyli od poczatku

            }
            catch (InterruptedException | SocketException e)
            {
                return;
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}