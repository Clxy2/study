package cn.clxy.tools.webdriver.service;

import java.util.List;

import cn.clxy.tools.webdriver.model.Account;
import cn.clxy.tools.webdriver.model.ClxyFile;

public interface WebDriverService {

    List<ClxyFile> getRoot(Account account);

    void downloadFolder();

    void downloadFolder(boolean nest);

    void downloadFile();

    void uploadFile();

    void compare();

    void renameFile();

    void renameFolder();

    void deleteFile();

    void deleteFolder();

    void createFolder();

    void addFile();
}
