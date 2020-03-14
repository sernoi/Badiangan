/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package settings;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.viewer.TileFactoryInfo;

/**
 *
 * @author SINFUEGO
 */
public class Settings 
{
    //user of the database
    public static String userName = "root";  
    //password
    public static String password = "1234";
    //url of db
    //public static String url = "jdbc:mysql://192.168.1.250/badiangan";
    public static String url = "jdbc:mysql://localhost/badiangan";
    
    //offline map
    public static TileFactoryInfo info = new OSMTileFactoryInfo("","jar:file:tiles/tiles.zip!");
    //online map
    //public static TileFactoryInfo info = new OSMTileFactoryInfo();
    
    //string url inputstreamo of report
    public static String in = "http://192.168.1.250:8080/badiangan/report.pdf";
}
