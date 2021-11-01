package entity;

/**
 * @Description:
 * @Author: July
 * @Date: 2021-10-30 11:38
 **/
public class FileVo {
   private String name;
   private boolean directory;

    public FileVo(String name, boolean directory) {
        this.name = name;
        this.directory = directory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDirectory() {
        return directory;
    }

    public void setDirectory(boolean directory) {
        this.directory = directory;
    }
}
