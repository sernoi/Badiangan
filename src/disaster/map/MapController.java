package disaster.map;
import disaster.DisasterPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.MouseInputListener;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.input.CenterMapListener;
import org.jxmapviewer.input.MapClickListener;
import org.jxmapviewer.input.PanKeyListener;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCenter;
import org.jxmapviewer.painter.Painter;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactory;
import org.jxmapviewer.viewer.TileFactoryInfo;
import org.jxmapviewer.viewer.WaypointPainter;
import util.maputil.FancyWaypointRenderer;
import util.maputil.MyWaypoint;

public class MapController
{
    MapPanel mpp;
    DisasterPanel bp;
    JXMapViewer mapViewer;
    
    int sx = 0;
    int sy = 0;
    Color disColor;
    int ovalW = 10;
    int ovalH = 10;
    
    public MapController(MapPanel mpp, DisasterPanel bp)
    {
        disColor = new Color(255,0,0,100);
        this.mpp = mpp;
        this.bp = bp;
        this.mpp.allListener(new Action(), new Mouse());
        
        initMap();
        initMarker();
    }
    public MapController(MapPanel mpp, DisasterPanel bp, double locLong, double locLat)
    {
        disColor = new Color(255,0,0,100);
        this.mpp = mpp;
        this.bp = bp;
        this.mpp.allListener(new Action(), new Mouse());
        
        initMap();
        setMarker(new GeoPosition(locLat,locLong));
    }
    void initMap()
    {
        GeoPosition badiangan = new GeoPosition(10.9938,122.5418);
        TileFactoryInfo info = new OSMTileFactoryInfo("ZIP archive", "jar:file:tiles/tiles.zip!");
        TileFactory tileFactory = new DefaultTileFactory(info);
        
        // Setup JXMapViewer
        mapViewer = new JXMapViewer();
        mapViewer.setTileFactory(tileFactory);

        // Set the focus
        mapViewer.setZoom(6);
        mapViewer.setAddressLocation(badiangan);
        mapViewer.setCenterPosition(badiangan);
        mapViewer.setRestrictOutsidePanning(true);

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
                System.out.println(mapViewer.getZoom());
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
                //left 122.51605079345703
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
        //TODO circle overlay 
    }


    void initMarker()
    {   
        mapViewer.addMouseListener(new MapClickListener(mapViewer) {
            @Override
            public void mapClicked(GeoPosition gp) {
                
                Point2D worldPos = mapViewer.getTileFactory().geoToPixel(gp, mapViewer.getZoom());
                Rectangle rect = mapViewer.getViewportBounds();
                sx = (int) worldPos.getX() - rect.x;
                sy = (int) worldPos.getY() - rect.y;

                Painter<JXMapViewer> ovalOverlay = new Painter<JXMapViewer>() 
                { 
                    public void paint(Graphics2D g, JXMapViewer map, int w, int h) 
                    { 
                        int val = Integer.parseInt(mpp.radSpinner.getValue().toString());
                        g.setPaint(disColor);
                        g.fillOval(sx - (ovalW * val)/2, sy - (ovalH * val)/2, ovalW * val, ovalH * val);
                        mpp.radSpinner.setEnabled(true);
                    } 
                };
                mapViewer.setOverlayPainter(ovalOverlay);
                mpp.latLbl.setText("" + gp.getLatitude());
                mpp.longLbl.setText("" + gp.getLongitude());
            }
        });
        
        
        mpp.radSpinner.addChangeListener(new ChangeListener() 
        {
            @Override
            public void stateChanged(ChangeEvent e) 
            {
                int val = Integer.parseInt(mpp.radSpinner.getValue().toString());
                Painter<JXMapViewer> ovalOverlay = new Painter<JXMapViewer>() 
                { 
                    public void paint(Graphics2D g, JXMapViewer map, int w, int h) 
                    { 
                        g.setPaint(disColor);
                        g.fillOval(sx - (ovalW * val)/2 , sy - (ovalH * val)/2, ovalW * val, ovalH * val);
                        mpp.radSpinner.setEnabled(true);
                    } 
                };
                mapViewer.setOverlayPainter(ovalOverlay);
            }
        });
        
        //JDialog mapDialog = new JDialog();
        mpp.mapDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        mpp.mapDialog.setModal(true);
        mpp.mapDialog.setPreferredSize(new Dimension(1200, 700));
        mpp.mapDialog.setSize(new Dimension(1200, 700));
        mpp.mapPanel.add(mapViewer);
        mpp.mapDialog.setLocationRelativeTo(null);
        mpp.mapDialog.setTitle("Map Dialog");
        mpp.mapDialog.pack();
        mpp.mapDialog.setVisible(true);
    }
    
    void setMarker(GeoPosition gp)
    {
        mpp.saveBtn.setVisible(false);
        mpp.longLbl.setText("" + gp.getLongitude());
        mpp.latLbl.setText("" + gp.getLatitude());
        
        Set<MyWaypoint> wp = new HashSet<MyWaypoint>(Arrays.asList(
        new MyWaypoint("B", Color.ORANGE, gp)));
        WaypointPainter<MyWaypoint> wpr = new WaypointPainter<MyWaypoint>();
        wpr.setWaypoints(wp);
        wpr.setRenderer(new FancyWaypointRenderer());
        mapViewer.setOverlayPainter(wpr);
        
        mapViewer.recenterToAddressLocation();
        
        //JDialog mapDialog = new JDialog();
        mpp.mapDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        mpp.mapDialog.setModal(true);
        mpp.mapDialog.setPreferredSize(new Dimension(1200, 700));
        mpp.mapDialog.setSize(new Dimension(1200, 700));
        mpp.mapPanel.add(mapViewer);
        mpp.mapDialog.setLocationRelativeTo(null);
        mpp.mapDialog.setTitle("Map Dialog");
        mpp.mapDialog.pack();
        mpp.mapDialog.setVisible(true);
    }
    void saveLoc()
    {
//        bp.longLatLbl.setText(mpp.longLbl.getText() + "," + mpp.latLbl.getText());
//        bp.longLatLbl1.setText(mpp.longLbl.getText() + "," + mpp.latLbl.getText());
//        mpp.mapDialog.dispose();
    }
    class Action implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) 
        {
            if(e.getSource() == mpp.saveBtn)
            {
                saveLoc();
            }
        }
    }
    
    class Mouse extends MouseAdapter
    {
        @Override
        public void mouseReleased(MouseEvent e) 
        {      
           
        }
    }
    
}
