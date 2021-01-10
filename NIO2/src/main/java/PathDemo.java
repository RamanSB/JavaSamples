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

        //Demonstrating isAbsolute() & toAbsolutePath();
        System.out.println(paths[0].isAbsolute());
        System.out.println(paths[1].toAbsolutePath());

        //Demonstrating subpath
        System.out.println(paths[1].subpath(1, 3));

        //Demonstrating relativize() - this method requires arguments to be of the same path type (both absolute or both relative)
        Path pathRelative1 = Paths.get("example-directory/vehicles/cars/electric/bmw/i8");
        Path pathRelative2 = Paths.get("cars/electric/mercedes/c-class");
        //from invoking path how do we get to the arg path.x
        System.out.println(pathRelative1.relativize(pathRelative2)); // ../../../../../../cars/electric/mercedes/c-class
        //pathRelative1.relativize(paths[0]); //throws exception because both paths are not the same type, one is abs the other is rel

        //Demonstrating resolve() method path1.resolve(path2)
        System.out.println(pathRelative1.resolve(pathRelative2)); //pathRelative2 is appended to pathRelative1 - The path
        //the method is invoked on is the basis of the new path and the argument is appended to the base path.

        //normalize is like resolve but interprets the file path symbols "." and ".."

        //toRealPath()
    }
}
