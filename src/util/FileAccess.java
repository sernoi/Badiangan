package util;

import java.io.File;
public class FileAccess 
{
    public FileAccess()
    {
        try
        {
            if ((new File("F:\\sample.pdf")).exists()) 
            {
                Process p = Runtime
                   .getRuntime()
                   .exec("rundll32 url.dll,FileProtocolHandler F:\\sample.pdf");
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
