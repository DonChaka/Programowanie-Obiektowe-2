package Utils;

import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.List;

public class ToolsTest
{

    @org.junit.Test
    public void fileListerTestNullPath()
    {
        List<String> test = Tools.FileLister(null);

        Assertions.assertNull(test);
    }

    @org.junit.Test
    public void fileListerTestNotExistingPath()
    {
        List<String> test = Tools.FileLister("Not Existing Folder");
        List<String> expected = new ArrayList<>();
        expected.add("Folder not found");

        Assertions.assertEquals(expected, test);
    }

    @org.junit.Test
    public void fileListerTestCorrectFolder()
    {
        List<String> test = Tools.FileLister("D:\\Tests\\FileListerTest");

        List<String> expected = new ArrayList<>();
        expected.add("File1.txt");
        expected.add("File2.txt");
        expected.add("File3.txt");
        expected.add("File4.txt");
        Assertions.assertEquals(expected, test);
    }
}