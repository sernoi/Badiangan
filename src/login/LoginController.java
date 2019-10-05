package login;

import main.MainFrame;
import util.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class LoginController 
{
    String adminName;
    String adminPos;
    LoginFrame lf;
    
    public LoginController(LoginFrame lf)
    { 
        this.lf = lf;
        //this.lf.loginListener(new LoginClass());
        
        this.lf.loginBtn.addActionListener((ActionEvent e) -> {
            loginNow();
        });
        
        this.lf.pwPF.addKeyListener(new KeyListener() 
        {
        public void actionPerformed(KeyEvent evt) 
            {
                System.out.println("Handled by anonymous class listener");
            }

            @Override
            public void keyTyped(KeyEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER)
                {
                    loginNow();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        
    }

    public void loginNow() 
    {
        String[] name = new String[4];
        name = LoginModel.loginAdmin(lf.unTF.getText(), lf.pwPF.getText());
        if(name[0] != null && name[1] != null && name[2] != null && name[3] != null)
        {
            MainFrame mf = new MainFrame();
            Timer t = new Timer(mf);
            t.setTime();
            mf.fnameLbl.setText(name[2]);
            mf.adminIDTF.setText(name[3]);
            mf.setExtendedState(JFrame.MAXIMIZED_BOTH); 
            mf.setVisible(true);
            lf.setVisible(false);
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Wrong username or password!","Error",JOptionPane.ERROR_MESSAGE);
        }
    }
}
