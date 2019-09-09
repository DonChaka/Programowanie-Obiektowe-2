package Client;

import Utils.FileContainer;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;


/**
 * Class connecting with server and starting synchronization thread
 * @author Szymon Kuzik
 * @version 1.0
 */
class ClientThread
{
    /**
     * Path to users folder
     */
    private String path;

    /**
     * List of files to be sent to other users
     */
    private List<FileContainer> prioritizedFiles;

    /**
     * Method packing file into FileContainer and adding
     * it to prioritized list of files to send to server
     * @param name name of the file to be sent
     * @param receiver reciver of the file
     */
    void sendFile(String name, String receiver)
    {
        if(name == null || receiver.equals(""))
            return;

        try {
            File file = new File(path + "\\" + name);
            byte[] content = new byte[(int) file.length()];

            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream bis = new BufferedInputStream(fis);
            bis.read(content, 0, content.length);
            FileContainer sending = new FileContainer(receiver, name, content);

            prioritizedFiles.add(sending);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    /**
     *
     * @param ip ip of the server
     * @param port port on which server is running
     * @param path path to users folder
     * @param userName client username
     * @param threads thread pool
     * @throws IOException
     */
    ClientThread(String ip, int port, String path, String userName, ExecutorService threads) throws IOException
    {
        Socket socket = new Socket(ip, port);
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        out.writeObject(userName);
        this.path = path;

        prioritizedFiles = new ArrayList<>();

        ClientSynchronizer synchro = new ClientSynchronizer(in, out, path, threads, prioritizedFiles, userName);
        threads.submit(synchro::synchronizer);
    }
}
