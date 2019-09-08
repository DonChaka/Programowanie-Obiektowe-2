package Utils;

import java.io.Serializable;


/**
 *  This class is "frame" for files to be transfered
 */
public class FileContainer implements Serializable {

    private String owner;
    private byte[] content;
    private String name;

    /**
     * @param owner owner of the file
     * @param name name of the file
     * @param content content of the fi.e
     */
    public FileContainer(String owner, String name, byte[] content)
    {
        this.owner = owner;
        this.content = content;
        this.name = name;
    }

    /**

     * @return owner of the file
     */
    public String getOwner()
    { return owner; }

    /**
     * @return name of the file
     */
    public String getName()
    { return name; }

    /**
     * @return content of the file
     */
    public byte[] getContent()
    { return content; }
}
