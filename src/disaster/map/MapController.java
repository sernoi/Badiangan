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
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.JDialog;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.input.MapClickListener;
import org.jxmapviewer.painter.CompoundPainter;
import org.jxmapviewer.painter.Painter;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.WaypointPainter;
import util.maputil.FancyWaypointRenderer;
import util.maputil.MapDimension;
import util.maputil.MapGenerate;
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
        
        JXMapViewer mapViewer = MapGenerate.generateMap();
        initMarker(mapViewer);
    }
    public MapController(MapPanel mpp, DisasterPanel bp, double locLong, double locLat)
    {
        disColor = new Color(255,0,0,100);
        this.mpp = mpp;
        this.bp = bp;
        this.mpp.allListener(new Action(), new Mouse());
        
        JXMapViewer mapViewer = MapGenerate.generateMap();
        setMarker(mapViewer, new GeoPosition(locLat,locLong));
    }
    void initMarker(JXMapViewer mapViewer)
    {   
        Painter<JXMapViewer> origOverLay = (Painter<JXMapViewer>) mapViewer.getOverlayPainter();
        List<Painter<JXMapViewer>> painters = new ArrayList<Painter<JXMapViewer>>();
        
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
                        g.fillOval(sx - (MapDimension.ovalW * val)/2, sy - (MapDimension.ovalH * val)/2, MapDimension.ovalW * val, MapDimension.ovalH * val);
                        mpp.radSpinner.setEnabled(true);
                    } 
                };
                painters.clear();
                painters.add(ovalOverlay);
                painters.add(origOverLay);
                CompoundPainter<JXMapViewer> painter = new CompoundPainter<JXMapViewer>(painters);
                mapViewer.setOverlayPainter(painter);
                
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
                        g.fillOval(sx - (MapDimension.ovalW * val)/2 , sy - (MapDimension.ovalH * val)/2, MapDimension.ovalW * val, MapDimension.ovalH * val);
                        mpp.radSpinner.setEnabled(true);
                    } 
                };
                painters.clear();
                painters.add(ovalOverlay);
                painters.add(origOverLay);
                CompoundPainter<JXMapViewer> painter = new CompoundPainter<JXMapViewer>(painters);
                mapViewer.setOverlayPainter(painter);
            }
        });
        
        //JDialog mapDialog = new JDialog();
        mpp.mapDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        mpp.mapDialog.setModal(true);
        mpp.mapDialog.setPreferredSize(new Dimension(MapDimension.W, MapDimension.H));
        mpp.mapDialog.setSize(new Dimension(MapDimension.W, MapDimension.H));
        mpp.mapPanel.add(mapViewer);
        mpp.mapDialog.setLocationRelativeTo(null);
        mpp.mapDialog.setTitle("Map Dialog");
        mpp.mapDialog.pack();
        mpp.mapDialog.setVisible(true);
    }
    
    void setMarker(JXMapViewer mapViewer, GeoPosition gp)
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
        mpp.mapDialog.setPreferredSize(new Dimension(MapDimension.W, MapDimension.H));
        mpp.mapDialog.setSize(new Dimension(MapDimension.W, MapDimension.H));
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
