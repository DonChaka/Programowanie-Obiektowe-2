package Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Tools {

    public static List<String> FileLister(String path)
    {
        File folder = new File(path);
        List<String> filesList = new ArrayList<>();

        if (!folder.exists()) {
            filesList.add("Folder not found");
        }
        else
        {
            Collections.addAll(filesList, folder.list());
        }

        return filesList;
    }
}
