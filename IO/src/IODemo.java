import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Abstract classes: Reader, Writer, InputStream, OutputStream
 * Low-Level Stream IO Classes: FileInputStream (reads file as Bytes), FileOutputStream (writes to file in bytes), FileReader (read file data as characters), FileWriter (writes to files with chars)
 * High-Level Stream IO Classes: BufferedWriter, BufferedReader, ObjectInputStream (Deserialize a Java Object or primitive from an existing InputStream), ObjectOutputStream (Serializes a java object or data type) - InputStreamReader, OutputStreamWriter
 *
 * PrintStream (Writes formatted representation of Java object to a **Binary** stream
 * PrintWriter (Writes formatted representation of Java objects to a **text-based** stream
 *
 *
 * Operates using groups of data (bytes or text): BufferedWriter, BufferedReader
 *
 */
public class IODemo {

    public static void main(String[] args) throws IOException {

        //Reading a file using absolute path or relative path
        FileReader fileReaderAbs = new FileReader("/Users/ramandeepbedi/Documents/Java/OCP8/IO/resources/example_text_file.txt"); //Absolute path
        FileReader fileReaderRel = new FileReader("IO/resources/example_text_file.txt"); //Relative path
        int data = fileReaderAbs.read();
        while(data!=-1){
            System.out.print((char) data); //cast int byte value to char
            data=fileReaderAbs.read();
        }
        fileReaderAbs.close(); //Closing fileReader resource to prevent resource leak


        //Demonstrating how to use a BufferedReader
        BufferedReader br = new BufferedReader(fileReaderRel); //BufferedReader - reads char data as outlined above but from an existing reader (in this case FileReader)
        long result = br.skip(3); //Moves the marker/cursor to the 4th char (skips 3 chars: 'a','n',' ')
        System.out.println("\nNumber of characters skipped: " + result);
        data = br.read();
        while(data != -1){
            if(result==10){
                System.out.println("\nMarking the BufferedReader stream after reading 'e' in example");
                br.mark(1000); //Marks the (end of) 10th character (' ' after example)
            }
            System.out.print((char) data);
            data = br.read();
            result++;
            if(result==30){
                System.out.println("\nResetting BufferedReader stream");
                br.reset(); //Moves the cursor/marker to the end of the 10th character (next char to be read will be a 't' from 'test file' )
            }
        }
        fileReaderRel.close();
        br.close();


        
    }
}
