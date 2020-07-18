import java.io.File;

public class FileMonitor extends AbstractFileMonitor {

    public FileMonitor(String path) throws Exception {
        super(path);
    }

    private static File file;
    @Override
    public void setFilePath(String path) {
        file = new File(path);
    }

    @Override
    public String getFilePath() throws IllegalStateException {
        if (file.exists()){
            return String.valueOf(file);
        }
        else {
        return String.valueOf(new IllegalStateException());
        }
    }
}
