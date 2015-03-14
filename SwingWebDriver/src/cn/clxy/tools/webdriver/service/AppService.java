package cn.clxy.tools.webdriver.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import cn.clxy.tools.webdriver.model.Account;

public class AppService {

    private static final String fileName = "accounts.data";

    public List<Account> getAccounts() {

        List<Account> accounts = null;

        // load accounts
        ObjectInputStream decoder = null;
        try {
            decoder = new ObjectInputStream(new BufferedInputStream(new FileInputStream(fileName)));
            accounts = (List<Account>) decoder.readObject();
        } catch (Exception e) {
            accounts = new ArrayList<Account>();
        } finally {
            if (decoder != null) {
                try {
                    decoder.close();
                } catch (IOException e) {
                }
            }
        }

        return accounts;
    }

    public void saveAccount(List<Account> accounts) {

        ObjectOutputStream encoder = null;
        try {
            encoder = new ObjectOutputStream(new BufferedOutputStream(
                    new FileOutputStream(fileName)));
            encoder.writeObject(accounts);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (encoder != null) {
                try {
                    encoder.close();
                } catch (IOException e) {
                }
            }
        }
    }
}
