//TODO javadoc

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

class ServerThread {

    private static ServerThread server;

    private ServerSocket serverSocket;
    private ExecutorService threads;
    private List<String> loggedUsers;

    private final String[] PATHS ={
            "D:\\CatchBox\\Mount 1",
            "D:\\CatchBox\\Mount 2",
            "D:\\CatchBox\\Mount 3",
            "D:\\CatchBox\\Mount 4",
            "D:\\CatchBox\\Mount 5"
    };



    static ServerThread getServer()
    {
        if(server == null)
            server = new ServerThread();
        return server;
    }

    void init(int port, ExecutorService threads) throws IOException
    {
        server.serverSocket = new ServerSocket(port);
        loggedUsers = new ArrayList<>();

        this.threads = threads;
        threads.submit(this::connectionHandler);

    }

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
                loggedUsers.add(userName);
                //ServerMain.displayUsers(loggedUsers);
                for (String path : PATHS) {
                    File folder = new File(path + "\\" + userName);

                    if (!folder.exists() || !folder.isDirectory())
                        folder.mkdir();
                }

                ServerSynchronizer synchronizer = new ServerSynchronizer(in, out, PATHS, threads, userName, waiting, loggedUsers);
                threads.submit(synchronizer::synchronizer);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }
}
