package Util;

import java.io.InputStream;
import java.net.URL;

public class Updater {
    private final static String versionURL = "http://dev.pascalgame.net/versionGame.html";
    public static String getLatestVersion() throws Exception {
        String data = getData(versionURL);
        return data.substring(data.indexOf("[version]")+9,data.indexOf("[/version]"));
    }
    public static String getString() throws Exception {
        String data = getData(versionURL);
        return data.substring(data.indexOf("[string]")+8,data.indexOf("[/string]"));
    }
    private static String getData(String address)throws Exception
    {
        URL url = new URL(address);
        
        InputStream html = null;

        html = url.openStream();
        
        int c = 3;
        StringBuffer buffer = new StringBuffer("");

        while(c != -1) {
            c = html.read();
            
        buffer.append((char)c);
        }
        return buffer.toString();
    }
}