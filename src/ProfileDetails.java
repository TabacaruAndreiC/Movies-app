import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ProfileDetails extends JFrame{
    private JPanel ProfileDetails;
    private JLabel userName;
    private JButton backToDasboard;
    private JTable table1;

    public ProfileDetails(){
        setTitle("Profile details");
        setContentPane(ProfileDetails);
        setMinimumSize(new Dimension(450, 450));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);


        backToDasboard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dashboard dashboard=new dashboard();
                dispose();
            }
        });
    }
    public User user;
}
