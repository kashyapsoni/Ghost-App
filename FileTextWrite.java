import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class FileTextWrite implements FileTextWriter {

    @Override
    public void writeToFile(String string, String path) throws IOException, IllegalArgumentException {

        BufferedWriter bf = null;

        try {
            File file = new File(path);

            if (!file.exists()){
                file.createNewFile();
            }
            else

            bf = new BufferedWriter(new FileWriter(file, true));

            bf.newLine();

            bf.append(string);


        }catch (IOException e){
            e.printStackTrace();
        }
        finally
        {
            try {
                if (bf!=null){
                    bf.close();
                }
            }catch (Exception ex){
                System.out.println("Error in closing the BufferedWriter"+ ex);
            }
        }
    }

//    public static void main(String[] args) throws IOException{
//        FileTextWrite fileTextWrite = new FileTextWrite();
//        fileTextWrite.writeToFile("bob", "demo.txt");
//    }
}
