package beneficiary.map;
import beneficiary.BenePanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JDialog;
import javax.swing.event.MouseInputListener;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.input.CenterMapListener;
import org.jxmapviewer.input.MapClickListener;
import org.jxmapviewer.input.PanKeyListener;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCenter;
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
    BenePanel bp;
    JXMapViewer mapViewer;
    public MapController(MapPanel mpp, BenePanel bp)
    {
        this.mpp = mpp;
        this.bp = bp;
        this.mpp.allListener(new Action(), new Mouse());
        
        initMarker();
    }
    public MapController(MapPanel mpp, BenePanel bp, double locLong, double locLat)
    {
        this.mpp = mpp;
        this.bp = bp;
        this.mpp.allListener(new Action(), new Mouse());
        
        setMarker(new GeoPosition(locLat,locLong));
    }
    void initMap()
    {
        GeoPosition badiangan = new GeoPosition(10.9938,122.5418);
        //TileFactoryInfo info = new OSMTileFactoryInfo("ZIP archive", "jar:file:F:/Theses/Badiangan/Project/Badiangan/tiles/tiles.zip!");
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
                if(mapViewer.getCenterPosition().getLatitude() >= 11.038957490552914 ||
                   mapViewer.getCenterPosition().getLatitude() <= 10.972735371100471 ||
                   mapViewer.getCenterPosition().getLongitude() >= 122.6109795349121 ||
                   mapViewer.getCenterPosition().getLongitude() <= 122.51605079345703)
                {
                    //JOptionPane.showMessageDialog(null,"Lapaw");
                }
             }
             
        });
    }
    void initMarker()
    {
        initMap();
        mapViewer.addMouseListener(new MapClickListener(mapViewer) {
            @Override
            public void mapClicked(GeoPosition gp) {
                Set<MyWaypoint> wp = new HashSet<MyWaypoint>(Arrays.asList(
                new MyWaypoint("B", Color.ORANGE, gp)));
                WaypointPainter<MyWaypoint> wpr = new WaypointPainter<MyWaypoint>();
                wpr.setWaypoints(wp);
                wpr.setRenderer(new FancyWaypointRenderer());
                mapViewer.setOverlayPainter(wpr);
                
                mpp.longLbl.setText("" + gp.getLongitude());
                mpp.latLbl.setText("" + gp.getLatitude());
            }
        });
        
        //JDialog mapDialog = new JDialog();
        mpp.mapDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        mpp.mapDialog.setModal(true);
        mpp.mapDialog.setPreferredSize(new Dimension(1000, 700));
        mpp.mapDialog.setSize(new Dimension(1000, 700));
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
        
        initMap();
        
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
        mpp.mapDialog.setPreferredSize(new Dimension(1000, 700));
        mpp.mapDialog.setSize(new Dimension(1000, 700));
        mpp.mapPanel.add(mapViewer);
        mpp.mapDialog.setLocationRelativeTo(null);
        mpp.mapDialog.setTitle("Map Dialog");
        mpp.mapDialog.pack();
        mpp.mapDialog.setVisible(true);
    }
    void saveLoc()
    {
        bp.longLatLbl.setText(mpp.longLbl.getText() + "," + mpp.latLbl.getText());
        bp.longLatLbl1.setText(mpp.longLbl.getText() + "," + mpp.latLbl.getText());
        mpp.mapDialog.dispose();
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
