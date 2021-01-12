import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

import static org.junit.jupiter.api.Assertions.*;

class FilesDemoTest {

    Path resourcesPath;
    private static final String TEST_FILE_1 = "test-file-1.txt";

    @BeforeEach
    public void setup() throws IOException {
        Path modulePath = Paths.get(".").toRealPath(LinkOption.NOFOLLOW_LINKS);
        resourcesPath = modulePath.resolve(Paths.get("src", "main", "resources"));
        Files.createFile(resourcesPath.resolve(TEST_FILE_1));
        if (!Files.exists(Paths.get("./src/main/resources/testParentDir"))) {
            createTestDirectories();
        }
    }


    @AfterEach
    public void tearDown() throws IOException {
        Files.delete(resourcesPath.resolve(TEST_FILE_1));
    }

    @Test
    public void testIfFileExists() {
        assertTrue(Files.exists(resourcesPath.resolve(TEST_FILE_1)));
    }

    @Test
    public void testIfSameFile() throws IOException {
        Path testFile1Path = Paths.get("src/main/resources/", TEST_FILE_1);
        assertTrue(Files.isSameFile(testFile1Path, resourcesPath.resolve(TEST_FILE_1)));
        //Also can check if directories are the same (checks current working directory)
        assertTrue(Files.isSameFile(Paths.get(System.getProperty("user.dir")), Paths.get(".")));
    }

    public void createTestDirectories() throws IOException {
        Files.createDirectories(resourcesPath.resolve(Paths.get("testParentDir", "testChildDir", "testGrandChildDir")));
    }

    @Test
    public void testCopyFile() throws IOException {
        Files.copy(resourcesPath.resolve(Paths.get(TEST_FILE_1)),
                resourcesPath.resolve(Paths.get("testParentDir", "testChildDir", TEST_FILE_1.replace(".txt", "") + "-child-copy.txt")),
                StandardCopyOption.REPLACE_EXISTING);
        assertTrue(Files.exists(Paths.get("./src/main/resources/testParentDir/testChildDir/test-file-1-child-copy.txt")));

    }

    @Test
    public void testMoveFile() throws IOException {
        if (!Files.exists(Paths.get("./src/main/resources/testParentDir/testChild/testGrandChild"))) {
            Files.move(Paths.get("src/main/resources/testParentDir", "testChildDir"), Paths.get("src/main/resources/testParentDir/testChild"));
            Files.move(Paths.get("src/main/resources/testParentDir", "testChild", "testGrandChildDir"), Paths.get("src/main/resources/testParentDir/testChild/testGrandChild"));
            assertTrue(Files.exists(Paths.get("./src/main/resources/testParentDir/testChild/testGrandChild")));
        }
    }

    @Test
    public void testDeleteFile() {
        assertThrows(DirectoryNotEmptyException.class, () -> {
            Files.delete(resourcesPath.resolve("testParentDir"));
        });
    }

    @Test
    public void testDeleteFileIfExistsWhereFileDoesNotExist() throws IOException {
        boolean result = Files.deleteIfExists(resourcesPath.resolve(Paths.get("a-file-that-does-not-exist.txt")));
        assertFalse(result);
    }

    @Test
    public void writeAndReadFromTestFile() {
        if (Files.exists(resourcesPath.resolve(TEST_FILE_1))) {
            try (BufferedWriter bufferedWriter = Files.newBufferedWriter(resourcesPath.resolve(TEST_FILE_1), StandardCharsets.UTF_8)) {
                bufferedWriter.write("hello \n stay at home & isolate.");
                bufferedWriter.close();
                BufferedReader bufferedReader = Files.newBufferedReader(resourcesPath.resolve(TEST_FILE_1), StandardCharsets.UTF_8);
                String currentLine = null;
                while((currentLine = bufferedReader.readLine()) != null){
                    System.out.println(currentLine);
                    assertFalse(currentLine.isEmpty());
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}