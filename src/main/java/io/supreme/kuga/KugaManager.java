package io.supreme.kuga;

import io.supreme.kuga.data.DataLibrary;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class KugaManager {

    public void startServer(int maxPlayers, String serverName) throws IOException {

        File newServerFile = new File(DataLibrary.ACTIVE_SERVERS, serverName);
        FileUtils.deleteDirectory(newServerFile);
        FileUtils.copyDirectory(DataLibrary.TEMPLATE_SERVER, newServerFile);

    }

    private Process createSpigotProcess() {
        final List<String> args = new ArrayList<String>();
        args.add("java");
        args.add("-Xms1G");
        args.add("-Xmx2G");
        args.add("-XX:-OmitStackTraceInFastThrow");
        args.add("-DIReallyKnowWhatIAmDoingISwear=true");
        args.add("-jar");
        args.add("spigot.jar");
        final ProcessBuilder setup = new ProcessBuilder(args);
        setup.directory(DataLibrary.ACTIVE_SERVERS);
        setup.redirectErrorStream(true);
        try {
            return setup.start();
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

}
