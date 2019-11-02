package map.evacuate;

import java.util.ArrayList;
import javax.swing.JOptionPane;

public class EvacuationController 
{
    EvacuationPanel evacp;
    ArrayList<Integer> availableSites = new ArrayList<Integer>();
    ArrayList<Integer> affectedBene = new ArrayList<Integer>();
    public EvacuationController(ArrayList<Integer> affectedBene, ArrayList<Integer> availableSites)
    {
        this.affectedBene = affectedBene;
        this.availableSites = availableSites;
        System.out.println(affectedBene);
        System.out.println(availableSites);
        
        String str = "";
        for(int x = 0 ; x < affectedBene.size() ; x++)
        {
            System.out.println("BeneId: " + affectedBene.get(x));
            System.out.print("EvacId: ");
            System.out.println(EvacuationModel.getNearestSite(affectedBene.get(x), availableSites));
            str += "BeneId: " + affectedBene.get(x) + " to " + "EvacId: " +
                    EvacuationModel.getNearestSite(affectedBene.get(x), availableSites) + "\n";
        }
        JOptionPane.showMessageDialog(null, str);
    }
}
