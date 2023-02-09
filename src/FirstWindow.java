import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FirstWindow extends JFrame{
    private JButton btnLogin;
    private JButton btnRegister;
    private JPanel FirstWindowPanel;

    public FirstWindow(){
        setTitle("First window");
        setContentPane(FirstWindowPanel);
        setMinimumSize(new Dimension(450,450));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                LoginForm loginForm=new LoginForm(null);
            }
        });
        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                RegistrationForm registrationForm=new RegistrationForm(null);
            }
        });
        setVisible(true);
    }

}
