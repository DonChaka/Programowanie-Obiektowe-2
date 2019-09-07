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
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private ExecutorService threads;
    private String[] PATHS;
    private String userName;
    private Socket socket;
    private List<String> loggedUsers;

    ServerSynchronizer(ObjectInputStream in, ObjectOutputStream out, String[] PATHS, ExecutorService threads, String userName, Socket socket, List<String> loggedUsers)
    {
        this.in = in;
        this.out = out;
        this.PATHS = PATHS;
        this.threads = threads;
        this.userName = userName;
        this.socket = socket;
        this.loggedUsers = loggedUsers;
    }

    void synchronize()
    {
        while(true)
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
                loggedUsers.remove(userName);
                System.out.println(loggedUsers);
                //ServerMain.displayUsers(loggedUsers);

                return;
            }
            catch (IOException | ClassNotFoundException e)
            {
                e.printStackTrace();
            }
        }


    }

}

 /*
                            try(FileWriter fileOutput = new FileWriter(PATHS[finalI] + "\\" + recivedFile.getOwner() + "\\" + recivedFile.getName()))
                            {
                                fileOutput.write(recivedFile.getContent());
                            }
                            catch (IOException e)
                            {
                                e.printStackTrace();
                            }*/
/*
try (Scanner fileInput = new Scanner(new File(PATHS[0] + "\\" + userName + "\\" + missingOnClient.get(0)))) {
        StringBuilder fileContent = new StringBuilder();

        while (fileInput.hasNextLine())
        fileContent.append(fileInput.nextLine());

        FileContainer sending = new FileContainer(userName, missingOnClient.get(0), fileContent.toString());
        out.writeObject(sending);
        }
        catch (IOException e)
        {
        e.printStackTrace();
        }
*/