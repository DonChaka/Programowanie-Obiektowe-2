package Server;

import Utils.FileContainer;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

class ServerSynchronizer
{
    /**
     * ObjectOutputStream for server
     */
    private ObjectOutputStream out;

    /**
     * ObjectInputStream for server
     */
    private ObjectInputStream in;

    /**
     * Thread pool
     */
    private ExecutorService threads;

    /**
     * Paths to server discs
     */
    private String[] PATHS;

    /**
     * Username of client this synchronizer was created for
     */
    private String userName;

    /**
     * Socket connecting server with client
     */
    private Socket socket;

    /**
     * List of available users
     */
    private List<String> availableUsers;

    /**
     * @param in input stream for server
     * @param out output stream for server
     * @param PATHS server folders
     * @param threads thread pool
     * @param userName username of client this synchronizer is run for
     * @param socket Socket connecting client and server
     * @param availableUsers list of available users
     */
    ServerSynchronizer(ObjectInputStream in, ObjectOutputStream out, String[] PATHS, ExecutorService threads, String userName, Socket socket, List<String> availableUsers)
    {
        this.in = in;
        this.out = out;
        this.PATHS = PATHS;
        this.threads = threads;
        this.userName = userName;
        this.socket = socket;
        this.availableUsers = availableUsers;
    }


    /**
     * This function with ClientSynchronizer keeps both local and server folders up to date to each other.
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
        for(;;)
        {
            try
            {
                System.out.println("Liset sent to " + userName + ": " + ServerMain.availableUsers);
                out.writeObject(ServerMain.availableUsers);

                List<String> clientFiles = (List<String>) in.readObject(); // odebranie akyualnej listy plikow klienta

                List<String> serverFiles = Utils.Tools.FileLister(PATHS[0] + "\\" + userName); // wczytanie plikow na serwerze

                List<String> missingOnServer = clientFiles.stream() // ogarnicie czego brakuje na serwerze
                        .filter(i -> !serverFiles.contains(i))
                        .collect (Collectors.toList());

                List<String> missingOnClient = serverFiles.stream() // ogarnicie czego brakuje u klienta
                        .filter(i -> !clientFiles.contains(i))
                        .collect (Collectors.toList());

                out.writeObject(missingOnServer);

                FileContainer recivedFile = (FileContainer) in.readObject();

                if(recivedFile != null)
                {
                    for(int i = 0; i < PATHS.length; i++) // zapis dla wlasciwego odbiorcy
                    {
                        int finalI = i;
                        threads.submit( () ->
                        {

                            try(FileOutputStream fos = new FileOutputStream(PATHS[finalI] + "\\" + recivedFile.getOwner() + "\\" + recivedFile.getName()))
                            {
                                fos.write(recivedFile.getContent());

                                //faktyczny zapis pliku
                            } catch (IOException e) {
                                e.printStackTrace();
                            }


                        });
                    }
                }

                if(missingOnClient.isEmpty())
                {
                    out.writeObject(null);
                }
                else
                {
                    try
                    {
                        File file = new File(PATHS[0] + "\\" + userName + "\\" + missingOnClient.get(0));
                        byte[] content = new byte[(int)file.length()];
                        FileInputStream fis = new FileInputStream(file);
                        BufferedInputStream bis = new BufferedInputStream(fis);
                        bis.read(content, 0, content.length);

                        FileContainer sending = new FileContainer(userName, missingOnClient.get(0), content);

                        out.writeObject(sending);
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }



            }
            catch(SocketException e)
            {
                try
                {
                    socket.close();
                }
                catch(Exception e1)
                {
                    e1.printStackTrace();
                }
                System.out.println("Connection dropped with: " + userName);
                availableUsers.remove(userName);
                System.out.println(availableUsers);
                //ServerMain.displayUsers(availableUsers);

                return;
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }


    }

}