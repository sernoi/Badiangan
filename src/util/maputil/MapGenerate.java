/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util.maputil;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.event.MouseInputListener;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.input.CenterMapListener;
import org.jxmapviewer.input.PanKeyListener;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCenter;
import org.jxmapviewer.painter.CompoundPainter;
import org.jxmapviewer.painter.Painter;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactory;
import org.jxmapviewer.viewer.TileFactoryInfo;

/**
 *
 * @author John Rey Alipe
 */
public class MapGenerate 
{
    public static JXMapViewer generateMap()
    {
        
        final JXMapViewer mapViewer = new JXMapViewer();
        GeoPosition badiangan = new GeoPosition(10.999783, 122.526848);
        //TileFactoryInfo info = new OSMTileFactoryInfo("ZIP archive", "jar:file:F:/Theses/Badiangan/Project/Badiangan/tiles/tiles.zip!");
        TileFactoryInfo info = new OSMTileFactoryInfo("ZIP archive", "jar:file:tiles/tiles.zip!");
        TileFactory tileFactory = new DefaultTileFactory(info);
        
        // Setup JXMapViewer
        mapViewer.setTileFactory(tileFactory);

        // Set the focus
        mapViewer.setZoom(6);
        mapViewer.setAddressLocation(badiangan);
        mapViewer.setCenterPosition(badiangan);

        //generate border
        GeoPosition b1 = new GeoPosition(11.031891, 122.456722);
        GeoPosition b2 = new GeoPosition(11.028535, 122.486424);
        GeoPosition b3 = new GeoPosition(11.025579, 122.519342);
        GeoPosition b4 = new GeoPosition(11.022772, 122.539654);
        GeoPosition b5 = new GeoPosition(11.021922, 122.551392);
        GeoPosition b6 = new GeoPosition(11.020419, 122.564916);
        GeoPosition b7 = new GeoPosition(11.020419, 122.568387);
        GeoPosition b8 = new GeoPosition(11.019067, 122.577420);
        GeoPosition b9 = new GeoPosition(11.019067, 122.581044);
        GeoPosition b10 = new GeoPosition(11.018365, 122.584055);
        GeoPosition b11 = new GeoPosition(11.015009, 122.591710);
        GeoPosition b12 = new GeoPosition(11.010099, 122.603908);
        GeoPosition b13 = new GeoPosition(10.983047, 122.586096);
        GeoPosition b14 = new GeoPosition(10.967966, 122.582881);
        GeoPosition b15 = new GeoPosition(10.973728, 122.575787);
        GeoPosition b16 = new GeoPosition(10.970722, 122.565631);
        GeoPosition b17 = new GeoPosition(10.977736, 122.564457);
        GeoPosition b18 = new GeoPosition(10.980291, 122.565274);
        GeoPosition b19 = new GeoPosition(10.983848, 122.558231);
        GeoPosition b20 = new GeoPosition(10.984850, 122.554658);
        GeoPosition b21 = new GeoPosition(10.969644, 122.548147);
        GeoPosition b22 = new GeoPosition(10.955965, 122.539777);
        GeoPosition b23 = new GeoPosition(10.959422, 122.536868);
        GeoPosition b24 = new GeoPosition(10.960926, 122.530386);
        GeoPosition b25 = new GeoPosition(10.963924, 122.530470);
        GeoPosition b26 = new GeoPosition(10.965527, 122.529144);
        GeoPosition b27 = new GeoPosition(10.969385, 122.521284);
        GeoPosition b28 = new GeoPosition(10.969936, 122.517916);
        GeoPosition b29 = new GeoPosition(10.969034, 122.516538);
        GeoPosition b30 = new GeoPosition(10.968684, 122.514088);
        GeoPosition b31 = new GeoPosition(10.967331, 122.510720);
        GeoPosition b32 = new GeoPosition(10.967181, 122.509138);
        GeoPosition b33 = new GeoPosition(10.967682, 122.507607);
        GeoPosition b34 = new GeoPosition(10.970337, 122.505820);
        GeoPosition b35 = new GeoPosition(10.973293, 122.502962);
        GeoPosition b36 = new GeoPosition(10.975498, 122.502044);
        GeoPosition b37 = new GeoPosition(10.975698, 122.504698);
        GeoPosition b38 = new GeoPosition(10.977852, 122.503320);
        GeoPosition b39 = new GeoPosition(10.978253, 122.500870);
        GeoPosition b40 = new GeoPosition(10.979355, 122.500053);
        GeoPosition b41 = new GeoPosition(10.981359, 122.497757);
        GeoPosition b42 = new GeoPosition(10.983714, 122.493623);
        GeoPosition b43 = new GeoPosition(10.987321, 122.491428);
        GeoPosition b44 = new GeoPosition(10.994335, 122.481273);
        GeoPosition b45 = new GeoPosition(11.006559, 122.478976);
        GeoPosition b46 = new GeoPosition(11.016729, 122.464023);
        GeoPosition b47 = new GeoPosition(11.031891, 122.456722);

        // Create a track from the geo-positions
        List<GeoPosition> track = Arrays.asList(
        b1, b2, b3, b4, b5, b6, b7, b8, b9, b10,
        b11, b12, b13, b14, b15, b16, b17, b18, b19, b20, 
        b21, b22, b23, b24, b25, b26, b27, b28, b29, b30,
        b31, b32, b33, b34, b35, b36, b37, b38, b39, b40,
        b41, b42, b43, b44, b45, b46, b47);
        RoutePainter routePainter = new RoutePainter(track);
        
        // Create a compound painter that uses both the route-painter and the waypoint-painter
        List<Painter<JXMapViewer>> painters = new ArrayList<Painter<JXMapViewer>>();
        painters.add(routePainter);

        CompoundPainter<JXMapViewer> painter = new CompoundPainter<JXMapViewer>(painters);
        mapViewer.setOverlayPainter(painter);

        // Add interactions
        MouseInputListener mia = new PanMouseInputListener(mapViewer);
        mapViewer.addMouseListener(mia);
        mapViewer.addMouseMotionListener(mia);
        mapViewer.addMouseListener(new CenterMapListener(mapViewer));
        mapViewer.addMouseWheelListener(new ZoomMouseWheelListenerCenter(mapViewer));
        mapViewer.addKeyListener(new PanKeyListener(mapViewer));

        mapViewer.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
               if(mapViewer.getZoom() > 6 )
               {
                   mapViewer.setZoom(6);
                   mapViewer.setAddressLocation(badiangan);
               }
            }
        });
        
        mapViewer.addMouseMotionListener(new MouseAdapter()
        {
            @Override
             public void mouseDragged(MouseEvent e)
             {
                //System.out.println(mapViewer.getCenterPosition().getLatitude());
                //high 11.038957490552914
                //low 10.972735371100471
                
                //System.out.println(mapViewer.getCenterPosition().getLongitude());
                //right 122.6109795349121
                //left 122.40
                if(mapViewer.getCenterPosition().getLatitude() >= 11.1030 ||
                   mapViewer.getCenterPosition().getLatitude() <= 10.8220 ||
                   mapViewer.getCenterPosition().getLongitude() >= 122.7024 ||
                   mapViewer.getCenterPosition().getLongitude() <= 122.3373)
                {
                    JOptionPane.showMessageDialog(null,"You are out of bounds...\n Returning to the center");
                    mapViewer.setZoom(6);
                    mapViewer.setAddressLocation(badiangan);
                }
             }
             
        });
        
        return mapViewer;
    }
}
