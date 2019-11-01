package map;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.JTextField;
import main.MainFrame;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.painter.CompoundPainter;
import org.jxmapviewer.painter.Painter;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.WaypointPainter;
import util.BeneCBBHandler;
import util.DisasterCBBHandler;
import util.maputil.FancyWaypointRenderer;
import util.maputil.FancyWaypointRenderer1;
import util.maputil.FancyWaypointRenderer2;
import util.maputil.MapDimension;
import util.maputil.MapGenerate;
import util.maputil.MyWaypoint;

public class MapController
{
    MapPanel mpp;
    JXMapViewer mapViewer;
    
    Color disColor = new Color(255,0,0,100);
    int index = 0;
    Painter<JXMapViewer> origOverLay;
    List<Painter<JXMapViewer>> painters;
    CompoundPainter<JXMapViewer> painter;
    
    double sx = 0;
    double sy = 0;
    double lt = 0, lg = 0, rad = 0;
    
    //innermost -------------------------------------------- outermost
    int z1 = 32; int z2 = 16; int z3 = 8; int z4 = 4; int z5 = 2; int z6 = 1;
    int zoomMult = 1;
    int zoomValue = 7;
    
    public MapController(MapPanel mpp, MainFrame mf) 
    {
        this.mpp = mpp;
        //invokes the generateMap method from the MapGenerate Class
        mapViewer = MapGenerate.generateMap();
        mpp.add(mapViewer);

        origOverLay = (Painter<JXMapViewer>) mapViewer.getOverlayPainter();
        painters = new ArrayList<Painter<JXMapViewer>>();

        // Create a compound painter that uses both the route-painter and the waypoint-painter
        painters.clear();
        painters.add(origOverLay);

        painter = new CompoundPainter<JXMapViewer>(painters);
        mapViewer.setOverlayPainter(painter);
        mapViewer.repaint();
        
        Object beneObj = putAllBeneInMap();
        Object evacObj = putAllEvacInMap();
        Object hazardObj = putAllHazardInMap();
        
        painters.add((WaypointPainter<MyWaypoint>) beneObj);
        painter = new CompoundPainter<JXMapViewer>(painters);
        mapViewer.setOverlayPainter(painter);
        mapViewer.repaint();
        
        painters.add((WaypointPainter<MyWaypoint>) evacObj);
        painter = new CompoundPainter<JXMapViewer>(painters);
        mapViewer.setOverlayPainter(painter);
        mapViewer.repaint();
        
        painters.add((WaypointPainter<MyWaypoint>) hazardObj);
        painter = new CompoundPainter<JXMapViewer>(painters);
        mapViewer.setOverlayPainter(painter);
        mapViewer.repaint();

        origOverLay = (Painter<JXMapViewer>) mapViewer.getOverlayPainter();
        
        this.mpp.allListener(new Action(), new DisasterCBBHandler(mpp.disasterCBB));
        
        CardLayout cl = (CardLayout) (mf.mainPanel.getLayout());
        mf.mainPanel.add(this.mpp,"MapPanel");
        cl.show(mf.mainPanel, "MapPanel");  
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
            waypoints.add(new MyWaypoint("B" + beneList.get(x), Color.WHITE, 
                    new GeoPosition(latDbl, longDbl)));
        }
        // Create a waypoint painter that takes all the waypoints
        WaypointPainter<MyWaypoint> waypointPainter = new WaypointPainter<MyWaypoint>();
        waypointPainter.setWaypoints(waypoints);
        waypointPainter.setRenderer(new FancyWaypointRenderer());
        
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
            waypoints.add(new MyWaypoint("E" + evacList.get(x), Color.GREEN, 
                    new GeoPosition(latDbl, longDbl)));
        }
        // Create a waypoint painter that takes all the waypoints
        WaypointPainter<MyWaypoint> waypointPainter = new WaypointPainter<MyWaypoint>();
        waypointPainter.setWaypoints(waypoints);
        waypointPainter.setRenderer(new FancyWaypointRenderer1());
        return waypointPainter;
    }
    WaypointPainter<MyWaypoint> putAllHazardInMap()
    {
        ArrayList<Integer> hazardList = MapModel.getAllHazard();
        ArrayList<String> geoList = MapModel.getAllHazardGeo();
        Set<MyWaypoint> waypoints = new HashSet<MyWaypoint>(Arrays.asList());
        
        for(int x = 0 ; x < hazardList.size() ; x++)
        {
            String latLong = geoList.get(x);
            double latDbl = Double.parseDouble(latLong.substring(0,latLong.indexOf(",")));
            double longDbl = Double.parseDouble(latLong.substring(latLong.indexOf(",") + 1));
            waypoints.add(new MyWaypoint("H" + hazardList.get(x), Color.YELLOW, 
                    new GeoPosition(latDbl, longDbl)));
        }
        // Create a waypoint painter that takes all the waypoints
        WaypointPainter<MyWaypoint> waypointPainter = new WaypointPainter<MyWaypoint>();
        waypointPainter.setWaypoints(waypoints);
        waypointPainter.setRenderer(new FancyWaypointRenderer2());
        return waypointPainter;
    }
    
    //TODO add scrolling and panning
    
    void showDisaster(double lt, double lg, double rad)
    {
        int currentZoom = mapViewer.getZoom();
        switch(currentZoom)
        {
            case 1:
                zoomMult = z1;
                break;
            case 2:
                zoomMult = z2;
                break;
            case 3:
                zoomMult = z3;
                break;
            case 4:
                zoomMult = z4;
                break;
            case 5:
                zoomMult = z5;
                break;
            case 6:
                zoomMult = z6;
                break;
        }
        Point2D pt = mapViewer.convertGeoPositionToPoint(new GeoPosition(
                lt,
                lg));
        double val = rad;
        Painter<JXMapViewer> ovalOverlay = new Painter<JXMapViewer>()
        {
            public void paint(Graphics2D g, JXMapViewer map, int w, int h)
            {
                myShape(g,
                        pt.getX() - (MapDimension.ovalW * val)/2 * zoomMult,
                        pt.getY() - (MapDimension.ovalH * val)/2 * zoomMult,
                        (MapDimension.ovalW * val) * zoomMult,
                        (MapDimension.ovalH * val) * zoomMult);
                //g.drawString(str, h, h);
            }
        };
        painters.clear();
        painters.add(ovalOverlay);
        painters.add(origOverLay);
        CompoundPainter<JXMapViewer> painter = new CompoundPainter<JXMapViewer>(painters);
        mapViewer.setOverlayPainter(painter);
    }
    void myShape(Graphics2D g, double xCoord, double yCoord, double w, double h)
    {
        /* Enable anti-aliasing and pure stroke */
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        
        /* Construct a shape and draw it */
        Ellipse2D.Double shape = new Ellipse2D.Double(xCoord, yCoord, w, h);
        g.setPaint(disColor);
        g.fill(shape);
    }
    
    class Action implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            if(e.getSource() == mpp.viewBtn)
            {
                JTextField text = (JTextField) mpp.disasterCBB.getEditor().getEditorComponent();
                String str = text.getText();
                ArrayList<String> list = (MapModel.getDisaster(Integer.parseInt(str.substring(str.indexOf(":") + 1,str.indexOf(")")))));
                mpp.idLbl.setText(list.get(0));
                mpp.typeLbl.setText(list.get(1));
                mpp.nameLbl.setText(list.get(2));
                mpp.dateLbl.setText(list.get(3));
                mpp.ltLbl.setText(list.get(4));
                mpp.lgLbl.setText(list.get(5));
                mpp.radLbl.setText(list.get(6));
                mpp.remarksTA.setText(list.get(7));
                showDisaster(Double.parseDouble(list.get(4)), 
                        Double.parseDouble(list.get(5)),
                        Double.parseDouble(list.get(6)));
                
                mapViewer.addMouseMotionListener(new MouseAdapter()
                {
                    @Override
                    public void mouseDragged(MouseEvent e)
                    {
                        showDisaster(Double.parseDouble(list.get(4)), 
                        Double.parseDouble(list.get(5)),
                        Double.parseDouble(list.get(6)));
                     }
                });

                mapViewer.addMouseWheelListener(new MouseWheelListener()
                {
                    @Override
                     public void mouseWheelMoved(MouseWheelEvent e)
                     {
                        showDisaster(Double.parseDouble(list.get(4)), 
                        Double.parseDouble(list.get(5)),
                        Double.parseDouble(list.get(6)));
                     }
                });
            }
        }
    }
}
