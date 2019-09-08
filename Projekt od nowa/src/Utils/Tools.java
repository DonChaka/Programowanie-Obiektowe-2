package Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Tools
{

    /**
     *
     * @param path path to folder list files from
     * @return list of files in path
     */
    public static List<String> FileLister(String path)
    {
        if(path == null)
            return null;

        File folder = new File(path);
        List<String> filesList = new ArrayList<>();

        if (!folder.exists())
        {
            filesList.add("Folder not found");
        }
        else
        {
            Collections.addAll(filesList, folder.list());
        }

        return filesList;
    }
}
