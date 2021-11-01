package service;

import entity.FileVo;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @Author: July
 * @Date: 2021-10-30 11:35
 **/
public class FileService {
    private static FileService fileService = new FileService();

    private FileService() {
    }

    public static FileService getInstance() {
        return fileService;
    }

    public List<FileVo> listFile(String path) {
        File file = new File(path);
        List<FileVo> fileVos = new ArrayList<>();
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null && files.length != 0) {
                for (File f : files) {
                    FileVo fileVo = new FileVo(f.getName(), f.isDirectory());
                    fileVos.add(fileVo);
                }
            }
        }
        return fileVos;
    }

    public void copy(List<File> files, String dest) {
        File dir = new File(dest);
        try {
            for (File file : files) {
                if (file.isDirectory()) {
                    File newDIr = new File(dest + "\\" + file.getName());
                    FileUtils.copyDirectory(file, newDIr);
                } else {
                    FileUtils.copyToDirectory(file, dir);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void transfer(List<File> files, String dest) {
        File dir = new File(dest);
        try {
            for (File file : files) {
                FileUtils.moveToDirectory(file, dir, true);
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public void delete(List<File> files) {
        for (File file : files) {
            if (file.isDirectory()) {
                try {
                    FileUtils.deleteDirectory(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                file.delete();
            }
        }
    }


}
