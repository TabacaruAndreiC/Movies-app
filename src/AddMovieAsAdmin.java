import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class AddMovieAsAdmin extends JFrame {

    private JPanel AddMovieAsAdminPanel;
    private JButton btnAddMovie;
    private JButton btnBackToDasboard;
    private JTextField tfNumberOfLikes;
    private JTextField tfReleaseDateMovie;
    private JTextField tfCategoryMovie;
    private JTextField tfNameMovie;

    public AddMovieAsAdmin(){
        setTitle("Add movie");
        setContentPane(AddMovieAsAdminPanel);
        setMinimumSize(new Dimension(450,450));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        btnBackToDasboard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dashboard dashboard=new dashboard();
                dispose();
            }
        });
        btnAddMovie.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addMovie();
            }
        });
    }
    private void addMovie(){
        String movieName=tfNameMovie.getText();
        String movieCategory= tfCategoryMovie.getText();
        String movieReleaseDate= tfReleaseDateMovie.getText();
        int likes= Integer.parseInt(tfNumberOfLikes.getText());

        if (movieName.isEmpty() || movieCategory.isEmpty() || movieReleaseDate.isEmpty() )
        {
            JOptionPane.showMessageDialog(this,
                    "Please enter all fields",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        movie= addMovieToDataBase(movieName, movieCategory,movieReleaseDate, likes);
        if (movie!=null)
        {
            dispose();
            dashboard dashboard=new dashboard();
        }
        else {
            JOptionPane.showMessageDialog(this,
                    "Failed to register new user",
                    "Try again",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    public Movie movie;
    private Movie addMovieToDataBase(String name, String category, String releaseDate, int likes){
        Movie movie=null;
        final String DB_URL = "jdbc:mysql://localhost/MyStore?serverTimezone=UTC";
        final String USERNAME = "root";
        final String PASSWORD = "";

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            Statement stmt = conn.createStatement();
            String sql= "INSERT INTO movies ( name, category, releaseDate, likes) VALUES (?,?,?,?)";
            PreparedStatement preparedStatement= conn.prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, category);
            preparedStatement.setString(3, releaseDate);
            preparedStatement.setInt(4, likes);


            int addedRows = preparedStatement.executeUpdate();
            if (addedRows > 0){
                movie= new Movie();
                movie.name=name;
                movie.category=category;
                movie.releaseDate=releaseDate;
                movie.likes=likes;
            }

            stmt.close();
            conn.close();
        }catch(Exception e){
            e.printStackTrace();
        }

        return movie;
    }

}
