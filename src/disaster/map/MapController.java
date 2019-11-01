package disaster.map;
import disaster.DisasterPanel;
import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JDialog;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.input.MapClickListener;
import org.jxmapviewer.painter.CompoundPainter;
import org.jxmapviewer.painter.Painter;
import org.jxmapviewer.viewer.GeoPosition;
import util.maputil.MapDimension;
import util.maputil.MapGenerate;

public class MapController
{
    Color disColor = new Color(255,0,0,100);
    DisasterPanel dp;
    int index = 0;
    JXMapViewer mapViewer;
    MapPanel mpp;
    Painter<JXMapViewer> origOverLay;
    List<Painter<JXMapViewer>> painters;
    
    double sx = 0;
    double sy = 0;
    double lt = 0, lg = 0, rad = 0;
    
    //innermost -------------------------------------------- outermost
    int z1 = 32; int z2 = 16; int z3 = 8; int z4 = 4; int z5 = 2; int z6 = 1;
    int zoomMult = 1;
    int zoomValue = 7;
    
    public MapController(MapPanel mpp, DisasterPanel dp)
    {
        this.mpp = mpp;
        this.dp = dp;
        this.mpp.allListener(new Action());
        
        mapViewer = MapGenerate.generateMap();
        origOverLay = (Painter<JXMapViewer>) mapViewer.getOverlayPainter();
        painters = new ArrayList<Painter<JXMapViewer>>();
        initMarker();
    }
    public MapController(MapPanel mpp, DisasterPanel dp, double lt, double lg, double rad)
    {
        this.mpp = mpp;
        this.dp = dp;
        this.lt = lt;
        this.lg = lg;
        this.rad = rad;

        mapViewer = MapGenerate.generateMap();
        origOverLay = (Painter<JXMapViewer>) mapViewer.getOverlayPainter();
        painters = new ArrayList<Painter<JXMapViewer>>();
        setMarker();
    }
    void showOval()
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
                Double.parseDouble(mpp.latLbl.getText()),
                Double.parseDouble(mpp.longLbl.getText())));
        double val = Double.parseDouble(mpp.radLbl.getText());
        Painter<JXMapViewer> ovalOverlay = new Painter<JXMapViewer>()
        {
            public void paint(Graphics2D g, JXMapViewer map, int w, int h)
            {
                myShape(g,
                        pt.getX() - (MapDimension.ovalW * val)/2 * zoomMult,
                        pt.getY() - (MapDimension.ovalH * val)/2 * zoomMult,
                        (MapDimension.ovalW * val) * zoomMult,
                        (MapDimension.ovalH * val) * zoomMult);
            }
        };
        painters.clear();
        painters.add(ovalOverlay);
        painters.add(origOverLay);
        CompoundPainter<JXMapViewer> painter = new CompoundPainter<JXMapViewer>(painters);
        mapViewer.setOverlayPainter(painter);
    }
    void initMarker()
    {   
        //Mouse clicked
        mapViewer.addMouseListener(new MapClickListener(mapViewer) {
            @Override
            public void mapClicked(GeoPosition gp) {
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
                
                Point2D worldPos = mapViewer.getTileFactory().geoToPixel(gp, mapViewer.getZoom());
                Rectangle rect = mapViewer.getViewportBounds();
                sx =  worldPos.getX() - rect.x;
                sy = worldPos.getY() - rect.y;

                Painter<JXMapViewer> ovalOverlay = new Painter<JXMapViewer>() 
                { 
                    public void paint(Graphics2D g, JXMapViewer map, int w, int h) 
                    { 
                        double val = Double.parseDouble(mpp.radSpin.getValue().toString());
                        myShape(g,
                                sx - (MapDimension.ovalW * val)/2 * zoomMult,
                                sy - (MapDimension.ovalH * val)/2 * zoomMult,
                                (MapDimension.ovalW * val) * zoomMult,
                                (MapDimension.ovalH * val) * zoomMult);
                        
                        //mpp.radSpin.setEnabled(true);
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
        
        //Spinner Listener
        mpp.radSpin.addChangeListener(new ChangeListener() 
        {
            @Override
            public void stateChanged(ChangeEvent e) 
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
                        Double.parseDouble(mpp.latLbl.getText()),
                        Double.parseDouble(mpp.longLbl.getText())));
                double val = Double.parseDouble(mpp.radSpin.getValue().toString());
                Painter<JXMapViewer> ovalOverlay = new Painter<JXMapViewer>()
                {
                    public void paint(Graphics2D g, JXMapViewer map, int w, int h)
                    {
                        myShape(g,
                                pt.getX() - (MapDimension.ovalW * val)/2 * zoomMult,
                                pt.getY() - (MapDimension.ovalH * val)/2 * zoomMult,
                                (MapDimension.ovalW * val) * zoomMult,
                                (MapDimension.ovalH * val) * zoomMult);
                    }
                };
                painters.clear();
                painters.add(ovalOverlay);
                painters.add(origOverLay);
                CompoundPainter<JXMapViewer> painter = new CompoundPainter<JXMapViewer>(painters);
                mapViewer.setOverlayPainter(painter);
                mpp.radLbl.setText(""+Double.parseDouble(mpp.radSpin.getValue().toString()));
            }
        });
        
        //Mouse Dragged
        mapViewer.addMouseMotionListener(new MouseAdapter()
        {
            @Override
            public void mouseDragged(MouseEvent e)
            {
                showOval();
             }
        });
        
        mapViewer.addMouseWheelListener(new MouseWheelListener()
        {
            @Override
             public void mouseWheelMoved(MouseWheelEvent e)
             {
                showOval();
             }
        });
        
        //saveLoc Btn Pressed
        mpp.saveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showOval();
                double lat = Double.parseDouble(mpp.latLbl.getText());
                double lng = Double.parseDouble(mpp.longLbl.getText());
                Point2D pt = mapViewer.convertGeoPositionToPoint(new GeoPosition(lat,lng));
                GeoPosition gp = mapViewer.convertPointToGeoPosition(pt);
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
    void saveLoc()
    {
        dp.latSpin.setValue(Double.parseDouble(mpp.latLbl.getText()));
        dp.longSpin.setValue(Double.parseDouble(mpp.longLbl.getText()));
        dp.radSpin.setValue(Double.parseDouble(mpp.radLbl.getText()));
        
        dp.latSpin1.setValue(Double.parseDouble(mpp.latLbl.getText()));
        dp.longSpin1.setValue(Double.parseDouble(mpp.longLbl.getText()));
        dp.radSpin1.setValue(Double.parseDouble(mpp.radLbl.getText()));
        mpp.mapDialog.dispose();
    }
    void setMarker()
    {
        //Mouse Dragged
        mapViewer.addMouseMotionListener(new MouseAdapter()
        {
            @Override
            public void mouseDragged(MouseEvent e)
            {
                showOval();
            }
        });
        
        mapViewer.addMouseWheelListener(new MouseWheelListener()
        {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e)
            {
                showOval();
            }
        });
        
        //JDialog mapDialog = new JDialog();
        mpp.radSpin.setVisible(false);
        mpp.jLabel2.setVisible(false);
        mpp.saveBtn.setVisible(false);
        this.mpp.latLbl.setText("" + this.lt);
        this.mpp.longLbl.setText("" + this.lg);
        this.mpp.radLbl.setText("" + this.rad);
        mpp.mapDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        mpp.mapDialog.setModal(true);
        mpp.mapDialog.setPreferredSize(new Dimension(MapDimension.W, MapDimension.H));
        mpp.mapDialog.setSize(new Dimension(MapDimension.W, MapDimension.H));
        mpp.mapPanel.add(mapViewer);
        mpp.mapDialog.setLocationRelativeTo(null);
        mpp.mapDialog.setTitle("Map Dialog");
        mpp.mapDialog.pack();
        showOval();
        mpp.mapDialog.setVisible(true);         
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
}
