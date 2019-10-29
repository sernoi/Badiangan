package login;

import main.MainFrame;
import util.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import static java.lang.Thread.State.NEW;
import static java.lang.Thread.State.TERMINATED;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class LoginController 
{
    String adminName;
    String adminPos;
    LoginFrame lf;
    int progVal = 0;
    boolean flag = true;
    MainFrame mf;
    
    public Worker worker = new Worker();
    
    public LoginController(LoginFrame lf)
    { 
        this.lf = lf;
        
        this.lf.loginBtn.addActionListener((ActionEvent e) -> {
            startLogin();
        });
        
        this.lf.pwPF.addKeyListener(new KeyAdapter() 
        {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER)
                {
                    startLogin();
                }
            }
        });
    }

    public void loginNow() 
    {
        lf.loginBtn.setEnabled(false);
        String[] name = new String[4];
        name = LoginModel.loginAdmin(lf.unTF.getText(), lf.pwPF.getText());
        if(name[0] != null && name[1] != null && name[2] != null && name[3] != null)
        {
            mf = new MainFrame();
            Timer t = new Timer(mf);
            t.setTime();
            mf.deptLbl.setText(name[1]);
            mf.fnameLbl.setText(name[2]);
            mf.adminIDTF.setText(name[3]);
            mf.setExtendedState(JFrame.MAXIMIZED_BOTH); 
            //setting up the user config based on the dept
            mf.setConfig();
            lf.setVisible(false);
            mf.setVisible(true);
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Wrong username or password!","Error",JOptionPane.ERROR_MESSAGE);
        }
        //flag = false;
        lf.loginBtn.setEnabled(true);
        //System.out.println("End"+worker.getState());
        worker.stop();
    }
    
    public void startLogin()
    {  
        //System.out.println("Start"+worker.getState());
        if(worker.getState() == NEW)
        {
            worker.start();
        }
        else if(worker.getState() == TERMINATED)
        {
            worker.run();
        }
        else
        {
            worker.run();
        }
    }
    
    class Worker extends Thread 
    {
        @Override
        public void run() 
        {
            loginNow();
        }
    }
}
