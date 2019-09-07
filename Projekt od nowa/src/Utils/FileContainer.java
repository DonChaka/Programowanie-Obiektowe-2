package Utils;


import java.io.Serializable;

public class FileContainer implements Serializable {

    private String owner;
    private byte[] content;
    private String name;

    public FileContainer(String owner, String name, byte[] content)
    {
        this.owner = owner;
        this.content = content;
        this.name = name;
    }

    public String getOwner()
    { return owner; }

    public String getName()
    { return name; }

    public byte[] getContent()
    { return content; }
}
