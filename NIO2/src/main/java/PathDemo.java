import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Here we will demonstrate the functionality described in the Path interface & Paths factory
 */
public class PathDemo {

    public static Path[] createPaths() throws URISyntaxException{
        Path path1 = Paths.get("/Users/ramandeepbedi/Documents/Java/OCP8"); //Single-arg Path.get() - Uses absolute path to a directory
        Path path2 = Paths.get("NIO2","src","main","java", "PathDemo.java"); //Varargs -- Relative path to a file
        Path path3 = null;
        //path3 = Paths.get(new URI("http://www.google.com")); //Creating a network path using URI object.

        Path[] paths = new Path[3];
        paths[0] = path1;
        paths[1] = path2;
        //paths[2] = path3;
        return paths;
    }

    public static void main(String[] args) throws URISyntaxException{

        Path[] paths = createPaths();
        for(Path path : paths){
            System.out.println(path);
        }

        //Demonstrating toString(), getNameCount() & getName()
        int nameCount = paths[0].getNameCount();
        System.out.format("Path: %s has a name count of: %d. The 3rd name is: %s\n",
                paths[0].toString(), nameCount, paths[0].getName(2)); //name is 0 indexed - hence index 2 is the 3rd name

        //Demonstrating getFileName(), getParent(), getRoot()
        System.out.println(paths[0].getFileName()); //Prints directory name (last element in Path).
        System.out.println(paths[1].getFileName()); //Prints file name: PathDemo.java
        System.out.println(paths[0].getParent());
        System.out.println(paths[1].getParent()); //Prints the path without the current element, the actual parent directory - one level up.
        System.out.println(paths[0].getRoot());
        System.out.println(paths[1].getRoot()); //Prints null as path is relative and does not contain root.

    }
}
