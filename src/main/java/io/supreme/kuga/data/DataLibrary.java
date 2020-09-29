package io.supreme.kuga.data;

import java.io.File;

public class DataLibrary {

    public static File TEMPLATE_SERVER = new File("./template");
    public static File ACTIVE_SERVERS = new File("./servers");

    public void initialize() {
        if (!DataLibrary.TEMPLATE_SERVER.exists()) {
            DataLibrary.TEMPLATE_SERVER.mkdir();
        }
        if (!DataLibrary.ACTIVE_SERVERS.exists()) {
            DataLibrary.ACTIVE_SERVERS.mkdir();
        }
    }
}
