package io.nathan.kuga.manager;

import java.io.File;

public class LibraryManager {

    public static File TEMPLATE_SERVER = new File("./template");
    public static File ACTIVE_SERVERS = new File("./servers");

    public void initialize() {
        if (!LibraryManager.TEMPLATE_SERVER.exists()) {
            LibraryManager.TEMPLATE_SERVER.mkdir();
        }
        if (!LibraryManager.ACTIVE_SERVERS.exists()) {
            LibraryManager.ACTIVE_SERVERS.mkdir();
        }
    }
}
