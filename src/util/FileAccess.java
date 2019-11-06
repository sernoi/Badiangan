package util;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import settings.Settings;
public class FileAccess 
{
    public FileAccess()
    {
        try
        {
            URL website = new URL(Settings.in);
            ReadableByteChannel rbc = Channels.newChannel(website.openStream());
            FileOutputStream fos = new FileOutputStream("report.pdf");
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            
//            InputStream in = new URL(Settings.in).openStream();
//            Files.copy(in, Paths.get("D:\\thesis\\filedl\\report.pdf"), StandardCopyOption.REPLACE_EXISTING);

            if ((new File("report.pdf")).exists()) 
            {
                Process p = Runtime
                   .getRuntime()
                   .exec("rundll32 url.dll,FileProtocolHandler report.pdf");
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
