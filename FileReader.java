import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileReader implements FileTextReader {
    @Override
    public String readText(String path) throws IOException {
        String lines = null;

        try (Stream<String> stringStream = Files.lines(Paths.get(path))) {

            lines = stringStream
                    .filter(line -> line.length() == 1)
                    .collect(Collectors.joining());

            return lines;
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public Collection<String> getAllLines(String path) throws IOException {

        Collection<String> sets = new HashSet<>();

        BufferedReader bf = null;
        
        try {
        	File file = new File(path);
        	bf = new BufferedReader(new java.io.FileReader(file));
        	 String line = bf.readLine();
             while (line != null){
                 sets.add(line);
                 line = bf.readLine();
             }

             	bf.close();
             	return sets;

        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public String getLastLine(String path) throws IOException {

        String currentLine;
        String lastLine = null;
        BufferedReader br = null;
        
        try
        {
        	File file = new File(path);
            br= new BufferedReader(new java.io.FileReader(file));

            while((currentLine = br.readLine()) != null){
                lastLine = currentLine;
            }

            br.close();
            return lastLine;

        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }

//    public static void main(String[] args) throws IOException {
//
//        FileReader fileReader = new FileReader();
//        System.out.println(fileReader.readText("demo.txt"));
//        System.out.println(fileReader.getAllLines("demo.txt"));
//        System.out.println(fileReader.getLastLine("demo.txt"));
//
//    }
}
