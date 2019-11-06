package util;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
public class FileAccess 
{
    public FileAccess()
    {
        try
        {
            InputStream in = new URL("http://192.168.1.250:8080/badiangan/report.pdf").openStream();
            Files.copy(in, Paths.get("D:\\thesis\\filedl\\report.pdf"), StandardCopyOption.REPLACE_EXISTING);

            if ((new File("D:\\thesis\\filedl\\report.pdf")).exists()) 
            {
                Process p = Runtime
                   .getRuntime()
                   .exec("rundll32 url.dll,FileProtocolHandler D:\\thesis\\filedl\\report.pdf");
                p.waitFor();
            } else 
            {
                System.out.println("File does not exist");
            }

        System.out.println("Done");

        } catch (Exception ex) {
              ex.printStackTrace();
        }
    }
    public static void main(String[] args)
    {
        new FileAccess();
    }
}
