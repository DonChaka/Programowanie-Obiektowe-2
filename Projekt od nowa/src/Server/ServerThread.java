package Server;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * Class waiting for new clients and creating new synchronizer threads for them
 *
 * @author Szymon Kuzik
 * @version 1.0
 */
class ServerThread {

    /**
     * Instance of server
     */
    private static ServerThread server;

    /**
     * ServerSocket of server
     */
    private ServerSocket serverSocket;

    /**
     * Thread pool
     */
    private ExecutorService threads;

    /**
     * List of available users
     */
    private List<String> availableUsers;

    /**
     * Paths to server discs
     */
    private final String[] PATHS ={
            "D:\\CatchBox\\Mount 1",
            "D:\\CatchBox\\Mount 2",
            "D:\\CatchBox\\Mount 3",
            "D:\\CatchBox\\Mount 4",
            "D:\\CatchBox\\Mount 5"
    };


    /**
     * Function creates new instance of server if there is none
     * @return active ServerThread instance
     */
    static ServerThread getServer()
    {
        if(server == null)
            server = new ServerThread();
        return server;
    }

    /**
     * Function initialize ServerSocket and starts thread connectionHandler
     * @param port Port on which server should start
     * @param threads Thread pool of the server
     * @throws IOException couldn't create ServerSocket
     */
    void init(int port, ExecutorService threads) throws IOException
    {
        server.serverSocket = new ServerSocket(port);
        availableUsers = new ArrayList<>();

        this.threads = threads;
        threads.submit(this::connectionHandler);

    }

    /**
     * Method waiting for new clients and starting new synchronizer threads for them
     */
    private void connectionHandler()
    {
        while (true) {
            try
            {
                System.out.println("Waiting");
                Socket waiting = serverSocket.accept();
                ObjectOutputStream out = new ObjectOutputStream(waiting.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(waiting.getInputStream());

                String userName = (String)in.readObject();
                System.out.println("Connetion with " + userName);
                availableUsers.add(userName);
                for (String path : PATHS) {
                    File folder = new File(path + "\\" + userName);

                    if (!folder.exists() || !folder.isDirectory())
                        folder.mkdir();
                }

                ServerSynchronizer synchronizer = new ServerSynchronizer(in, out, PATHS, threads, userName, waiting, availableUsers);
                threads.submit(synchronizer::synchronizer);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
