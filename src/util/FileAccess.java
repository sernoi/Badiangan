package util;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import settings.Settings;
public class FileAccess 
{
    public FileAccess()
    {
        try
        {
            InputStream in = new URL(Settings.in).openStream();
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
