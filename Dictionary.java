import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Dictionary extends AbstractDictionary {

    private static File file;

    public Dictionary(String path, FileTextReader<Set<String>> dictionaryFileReader) throws IOException {
        super(path, dictionaryFileReader);
    }

    @Override
    public void setDictionaryFilePath(String path) throws IllegalArgumentException {
       

       path = System.getProperty("user.dir")+File.separator+path;

       file = new File(path);

       if (!file.exists()) {
            throw new IllegalArgumentException();
        }

       //System.out.println(path);
    }

    @Override
    public String getDictionaryFilePath() {
        if (file.exists()) {
            return String.valueOf(file);
        }
        return " error in file path ";
    }

    @Override
    public int countWordsThatStartWith(String prefix, int size, boolean ignoreCase) throws IllegalArgumentException {

        int count = 0;
        List<String> list = new ArrayList<>();

        if (ignoreCase == true) {
            try (Stream<String> stream = Files.lines(Paths.get(String.valueOf(file)))) {
                list = stream
                        .map(String -> String.toLowerCase())
                        .filter(line -> line.startsWith(prefix))
                        .filter(line -> line.length() == size)
                        .collect(Collectors.toList());
//                    for (String str : list) {
//                        count++;
//                    }
                    return list.size();

            } catch (IOException e) {
                e.printStackTrace();
            } 
            
        }else {
            try (Stream<String> stream = Files.lines(Paths.get(String.valueOf(file)))) {
                list = stream
                        .filter(line -> line.startsWith(prefix))
                        .filter(line -> line.length() == size)
                        .collect(Collectors.toList());
//                for (String str : list) {
//                    count++;
//                }
                return list.size();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
		return list.size();
    }

    @Override
    public boolean containsWordsThatStartWith(String prefix, int size, boolean ignoreCase) throws IllegalArgumentException {
        List<String> list = new ArrayList<>();

        if (ignoreCase == true) {
            try (Stream<String> stream = Files.lines(Paths.get(String.valueOf(file)))) {
                list = stream
                        .map(String -> String.toLowerCase())
                        .filter(line -> line.startsWith(prefix))
                        .filter(line -> line.length() == size)
                        .collect(Collectors.toList());
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                for (String i : list) {
                    if ((i.length() == size) && (i.startsWith(prefix))) {
                        return true;
                    }
                }
            }
        } else {
            try (Stream<String> stream = Files.lines(Paths.get(String.valueOf(file)))) {
                list = stream
                        .filter(line -> line.startsWith(prefix))
                        .filter(line -> line.length() == size)
                        .collect(Collectors.toList());
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                for (String i : list) {
                    if ((i.length() == size) && (i.startsWith(prefix))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public Set<String> getWordsThatStartWith(String prefix, int size, boolean ignoreCase) throws IllegalArgumentException {

        Set<String> list = new LinkedHashSet<>();

        if (ignoreCase) {
            try (Stream<String> stream = Files.lines(Paths.get(String.valueOf(file)))) {
                list = stream
                        .map(String::toLowerCase)
                        .filter(line -> line.startsWith(prefix))
                        .filter(line -> line.length() >= size)
                        .collect(Collectors.toSet());
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                for (String i : list) {
                    if ((i.length() >= size) && (i.startsWith(prefix))) {
                        return list;
                    }
                }
            }
        } else {
            try (Stream<String> stream = Files.lines(Paths.get(String.valueOf(file)))) {
                list = stream
                        .filter(line -> line.startsWith(prefix))
                        .filter(line -> line.length() >= size)
                        .collect(Collectors.toSet());
            } catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                for (String i : list) {
                    if ((i.length() >= size) && (i.startsWith(prefix))) {
                        return list;
                    }
                }
            }
        }
        return null;
    }
}
