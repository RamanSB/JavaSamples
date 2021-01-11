import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Abstract classes: Reader, Writer, InputStream, OutputStream
 * Low-Level Stream IO Classes: (All classes beginning with File) FileInputStream (reads file as Bytes), FileOutputStream (writes to file in bytes), FileReader (read file data as characters), FileWriter (writes to files with chars)
 * High-Level Stream IO Classes: BufferedWriter, BufferedReader, ObjectInputStream (Deserialize a Java Object or primitive from an existing InputStream), ObjectOutputStream (Serializes a java object or data type) - InputStreamReader, OutputStreamWriter
 *
 * PrintStream (Writes formatted representation of Java object to a **Binary** stream
 * PrintWriter (Writes formatted representation of Java objects to a **text-based** stream
 *
 *
 * Operates using groups of data (bytes or text): BufferedWriter, BufferedReader
 *
 */

//ToDo: Create separate methods to demonstrate each individual concept.
//ToDo: What is Filter(Input/Output)Stream purpose.
public class IODemo {


    public static void readingFromAFile() throws IOException{
        //Reading a file using absolute path or relative path
        FileReader fileReaderAbs = new FileReader("/Users/ramandeepbedi/Documents/Java/OCP8/IO/resources/example_text_file.txt"); //Absolute path
        FileReader fileReaderRel = new FileReader("IO/resources/example_text_file.txt"); //Relative path
        int data = fileReaderAbs.read();
        while(data!=-1){
            System.out.print((char) data); //cast int byte value to char
            data=fileReaderAbs.read();
        }
        fileReaderAbs.close(); //Closing fileReader resource to prevent resource leak

    }

    public static void aFewBufferedReaderMethods() throws IOException{
        //Demonstrating how to use a BufferedReader
        FileReader fileReaderRel = new FileReader("IO/resources/example_text_file.txt"); //Relative path
        BufferedReader br = new BufferedReader(fileReaderRel); //BufferedReader - reads char data as outlined above but from an existing reader (in this case FileReader)
        long result = br.skip(3); //Moves the marker/cursor to the 4th char (skips 3 chars: 'a','n',' ')
        System.out.println("\nNumber of characters skipped: " + result);
        int data = br.read();
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

    public static void writeToFileViaByteAndCharArray() throws IOException{
        byte[] byteArrayToWrite = {12,127, 0, 24, 83, 24, 82, 29, 83, 12,15,127,15,125,93,1,92,1,0,5,31,51}; //random-bytes
        FileOutputStream fos = new FileOutputStream(new File("IO/resources/byte-array-write.txt"));
        fos.write(byteArrayToWrite);
        fos.close();

        char[] charArrayToWrite = {'u','s','i','n','g',' ','c','h','a','r','a','r','r','a','y'};
        FileWriter fileWriter = new FileWriter(new File("IO/resources/char-array-write.txt"));
        fileWriter.write(charArrayToWrite);
        fileWriter.close();

    }

    /**
     Illustrating how to serialize and deserialize an object using ObjectOutputStream & ObjectInputStream respectively.
     Remember: File*Input*Stream can be used to 'read' a file, File*Output*Stream can be used to 'write' to a file.
     By the same token Object*Output*Stream writes the JavaObject to disk (Serializing), Object*Input*Stream reads
     the data from disk in to a Java Object (Deserialization).
     */
    public static void demoSerialization() throws IOException{
        BufferedReader bufferedReader = new BufferedReader(new FileReader(new File("IO/resources/phone_data.txt"))); //Used to read data from file
        List<CustomObjectPhone> phones = new ArrayList<>();
        String textData;
        while((textData = bufferedReader.readLine()) != null){
            String[] phoneAttributes = textData.split(","); //Parsing data from file
            String brand = phoneAttributes[0];
            String model = phoneAttributes[1];
            float height = Float.parseFloat(phoneAttributes[2]);
            float width = Float.parseFloat(phoneAttributes[3]);
            phones.add(new CustomObjectPhone(brand, model, height, width)); //Instantiating phone objects to add to list, these will be serialized.
        }
        bufferedReader.close();

        for(CustomObjectPhone phone : phones){
            System.out.println(phone); //Data from text file has been written to an in-memory object (CustomObjectPhone)
        }

        //Serializing
        ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(new File("IO/resources/phones.data"))));
        for(CustomObjectPhone phone : phones){
            oos.writeObject(phone); //Serializing
        }
        oos.close();
        phones.clear();

        //Deserializing (excepting to not obtain any values for the float instance variables) as they are marked as transient
        try(ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(new File("IO/resources/phones.data"))))){
            Object obj;
            while((obj = ois.readObject()) != null){
                if(obj instanceof CustomObjectPhone){
                    phones.add((CustomObjectPhone) obj);
                }
            }
        }
        catch(ClassNotFoundException | EOFException ignored){ }
        finally{
            System.out.println(phones);
        }

        //ToDo: Demo PrintWriter & PrintStream classes. (System.out.print == printWriter.write( w/String.valueOf method)
    }
    public static void main(String[] args){
        try {
            readingFromAFile();
            aFewBufferedReaderMethods();
            writeToFileViaByteAndCharArray();
            demoSerialization();
        } catch(IOException ex){
            ex.printStackTrace();
        }
    }
}

class CustomObjectPhone implements Serializable { //Required in order to be serialized otherwise NotSerializableException is thrown when attempted to serialize.

    private static final long serialVersionUID = 1L;
    private String brand;
    private String model;
    transient float height; //These will not be stored/saved when serialized (that is what transient implies)
    transient float width;

    CustomObjectPhone(String brand, String model, float height, float width){
        this.brand = brand;
        this.model = model;
        this.height = height;
        this.width = width;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public float getHeight() {
        return height;
    }

    public float getWidth() {
        return width;
    }

    public String toString(){
        return String.format("Phone: %s %s | Dimensions: %f x %f", getBrand(), getModel(), getWidth(), getHeight());
    }

}
