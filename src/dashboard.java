import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class dashboard extends JFrame{
    private JPanel dasboardPanel;
    private JButton btnAddMovie;
    private JButton btnProfile;
    private JPasswordField pfAdminPassword;
    private JButton btnWatchMovie;
    private JButton btnLogOut;
    private String adminPassword="admin";
    public dashboard(){
        setTitle("Dashboard");
        setContentPane(dasboardPanel);
        setMinimumSize(new Dimension(450,450));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        btnProfile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                ProfileDetails profileDetails=new ProfileDetails();
            }
        });
        btnWatchMovie.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                moviesPanel moviesPanel=new moviesPanel();
            }
        });
        btnAddMovie.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verifyIfAdmin();
            }
        });
        btnLogOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                FirstWindow firstWindow=new FirstWindow();
            }
        });
    }

    private void verifyIfAdmin(){
        String password= String.valueOf(pfAdminPassword.getPassword());
        if (password.isEmpty()){
            JOptionPane.showMessageDialog(null,
                    "Enter the admin password",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!adminPassword.equals(password)){
            JOptionPane.showMessageDialog(null,
                    "That's not the Admin password",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        else{
            dispose();
            AddMovieAsAdmin addMovieAsAdmin=new AddMovieAsAdmin();
        }
    }
}
