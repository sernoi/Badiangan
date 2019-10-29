package map;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.painter.CompoundPainter;
import org.jxmapviewer.painter.Painter;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.WaypointPainter;
import util.maputil.FancyWaypointRenderer1;
import util.maputil.FancyWaypointRenderer2;
import util.maputil.MapGenerate;
import util.maputil.MyWaypoint;

public class MapController
{
    MapPanel mpp;
    JXMapViewer mapViewer;
    
    List<Painter<JXMapViewer>> painters;
    CompoundPainter<JXMapViewer> painter;
    public MapController(MapPanel mpp) 
    {
        this.mpp = mpp;
        //invokes the generateMap method from the MapGenerate Class
        mapViewer = MapGenerate.generateMap();
        mpp.add(mapViewer);

        Painter<JXMapViewer> origOverLay = (Painter<JXMapViewer>) mapViewer.getOverlayPainter();
        painters = new ArrayList<Painter<JXMapViewer>>();

        // Create a compound painter that uses both the route-painter and the waypoint-painter
        painters.clear();
        painters.add(origOverLay);

        painter = new CompoundPainter<JXMapViewer>(painters);
        mapViewer.setOverlayPainter(painter);
        mapViewer.repaint();
        
        Object beneObj = putAllBeneInMap();
        Object evacObj = putAllEvacInMap();
        
        mpp.beneCHB.addActionListener((ActionEvent e) -> 
        {
            if(mpp.beneCHB.isSelected())
            {
                painters.add((WaypointPainter<MyWaypoint>) beneObj);
                painter = new CompoundPainter<JXMapViewer>(painters);
                mapViewer.setOverlayPainter(painter);
                mapViewer.repaint();
            }
            else
            {
                painters.remove(beneObj);
                painter = new CompoundPainter<JXMapViewer>(painters);
                mapViewer.setOverlayPainter(painter);
                mapViewer.repaint();
            }
        });   
        
        mpp.evacCHB.addActionListener((ActionEvent e) -> 
        {
            if(mpp.evacCHB.isSelected())
            {
                painters.add((WaypointPainter<MyWaypoint>) evacObj);
                painter = new CompoundPainter<JXMapViewer>(painters);
                mapViewer.setOverlayPainter(painter);
                mapViewer.repaint();
            }
            else
            {
                painters.remove(evacObj);
                painter = new CompoundPainter<JXMapViewer>(painters);
                mapViewer.setOverlayPainter(painter);
                mapViewer.repaint();
            }
        }); 
    }
    
    WaypointPainter<MyWaypoint> putAllBeneInMap()
    {
        ArrayList<Integer> beneList = MapModel.getAllBene();
        ArrayList<String> geoList = MapModel.getAllBeneGeo();
        Set<MyWaypoint> waypoints = new HashSet<MyWaypoint>(Arrays.asList());
        
        for(int x = 0 ; x < beneList.size() ; x++)
        {
            String latLong = geoList.get(x);
            double latDbl = Double.parseDouble(latLong.substring(0,latLong.indexOf(",")));
            double longDbl = Double.parseDouble(latLong.substring(latLong.indexOf(",") + 1));
            waypoints.add(new MyWaypoint("B" + beneList.get(x), Color.YELLOW, 
                    new GeoPosition(latDbl, longDbl)));
        }
        //TODO bug when scrolling
        // Create a waypoint painter that takes all the waypoints
        WaypointPainter<MyWaypoint> waypointPainter = new WaypointPainter<MyWaypoint>();
        waypointPainter.setWaypoints(waypoints);
        waypointPainter.setRenderer(new FancyWaypointRenderer1());
        
        return waypointPainter;
    }
    
    WaypointPainter<MyWaypoint> putAllEvacInMap()
    {
        ArrayList<Integer> evacList = MapModel.getAllEvac();
        ArrayList<String> geoList = MapModel.getAllEvacGeo();
        Set<MyWaypoint> waypoints = new HashSet<MyWaypoint>(Arrays.asList());
        
        for(int x = 0 ; x < evacList.size() ; x++)
        {
            String latLong = geoList.get(x);
            double latDbl = Double.parseDouble(latLong.substring(0,latLong.indexOf(",")));
            double longDbl = Double.parseDouble(latLong.substring(latLong.indexOf(",") + 1));
            waypoints.add(new MyWaypoint("E" + evacList.get(x), Color.YELLOW, 
                    new GeoPosition(latDbl, longDbl)));
        }
        //TODO bug when scrolling
        // Create a waypoint painter that takes all the waypoints
        WaypointPainter<MyWaypoint> waypointPainter = new WaypointPainter<MyWaypoint>();
        waypointPainter.setWaypoints(waypoints);
        waypointPainter.setRenderer(new FancyWaypointRenderer2());
        return waypointPainter;
    }
}
