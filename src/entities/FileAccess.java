package entities;

import java.util.List;
import java.util.UUID;

public interface FileAccess {

    public void writeToFile();

    public List<String> findInFile(UUID id);

}
