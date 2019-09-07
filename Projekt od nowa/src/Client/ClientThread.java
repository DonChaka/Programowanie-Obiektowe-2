package Client;

import Utils.FileContainer;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

class ClientThread
{

    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    private ExecutorService threads;

    private String path;
    private String userName;

    private List<FileContainer> prioritizedFiles;

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

    ClientThread(String ip, int port, String path, String userName, ExecutorService threads) throws IOException
    {
        this.socket = new Socket(ip, port);
        this.in = new ObjectInputStream(socket.getInputStream());
        this.out = new ObjectOutputStream(socket.getOutputStream());
        out.writeObject(userName);
        this.path = path;
        this.userName = userName;
        this.threads = threads;



        prioritizedFiles = new ArrayList<>();

        ClientSynchronizer synchro = new ClientSynchronizer(in, out, path, threads, prioritizedFiles, userName);
        threads.submit(synchro::synchronizer);
    }
}

       /*try (Scanner fileInput = new Scanner(new File(path + "/" + name))) {
            System.out.println(name + " dla " + receiver);
            StringBuilder fileContent = new StringBuilder();

            while (fileInput.hasNextLine())
                fileContent.append(fileInput.nextLine());

            FileContainer sending = new FileContainer(receiver, name, fileContent.toString());
            prioritizedFiles.add(sending);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }*/
