import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.*;

public class moviesPanel extends JFrame {
    private JTextField tfSearchMovie;
    private JPanel moviesPanel;
    private JButton btnLike;
    private JButton backToDasboard;
    private JLabel tfMovieName;
    private JLabel tfMovieCategory;
    private JLabel tfMovieReleaseDate;
    private JLabel tfMovieLikes;
    private JButton btnSearch;
    private JTable table2;
    private JTable jtMovies;

    List<Movie> movies = new ArrayList<>();

    public moviesPanel() {
        setTitle("Movies Panel");
        setContentPane(moviesPanel);
        setMinimumSize(new Dimension(800, 900));
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        //addMoviesFromFile("D:\\MIP\\ProiectMIP\\src\\movies.txt");
        //writeMoviesToFile("D:\\\\MIP\\\\ProiectMIP\\\\src\\\\output.txt");
        showFeed();

        backToDasboard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dashboard dashboard = new dashboard();
                dispose();
            }
        });
        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = tfSearchMovie.getText();
                movie = displayMovieDetails(name);
                if (movie != null) {
                    tfMovieName.setText(name);
                    tfMovieCategory.setText(movie.category);
                    tfMovieReleaseDate.setText(movie.releaseDate);
                    tfMovieLikes.setText(String.valueOf(movie.likes));
                } else {
                    JOptionPane.showMessageDialog(null,
                            "Cannot find the movie",
                            "Try again",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        btnLike.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    public Movie movie;

    private Movie displayMovieDetails(String name) {
        Movie movie = null;

        final String DB_URL = "jdbc:mysql://localhost/MyStore?serverTimezone=UTC";
        final String USERNAME = "root";
        final String PASSWORD = "";

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);

            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM movies WHERE name=? ";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, name);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                movie = new Movie();
                movie.name = resultSet.getString("name");
                movie.category = resultSet.getString("category");
                movie.releaseDate = resultSet.getString("releaseDate");
                movie.likes = resultSet.getInt("likes");
            }

            stmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return movie;
    }

    private void showFeed() {
        final String DB_URL = "jdbc:mysql://localhost/MyStore?serverTimezone=UTC";
        final String USERNAME = "root";
        final String PASSWORD = "";

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            Statement stmt = conn.createStatement();

            String sql = "SELECT * FROM movies ORDER BY CAST(likes AS UNSIGNED) DESC";
            ResultSet resultSet = stmt.executeQuery(sql);
            ResultSetMetaData rsmd = resultSet.getMetaData();
            DefaultTableModel model = (DefaultTableModel) jtMovies.getModel();

            int cols = rsmd.getColumnCount();
            String[] colName = new String[4];
            colName[0] = rsmd.getColumnName(1);
            colName[1] = rsmd.getColumnName(2);
            colName[2] = rsmd.getColumnName(3);
            colName[3] = rsmd.getColumnName(4);

            model.setColumnIdentifiers(colName);
            String movieName, gender, releaseDate;
            int likes;

            while ((resultSet.next())) {
                movieName = resultSet.getString(1);
                gender = resultSet.getString(2);
                releaseDate = resultSet.getString(3);
                likes = resultSet.getInt(4);

                String[] row = {movieName, gender, releaseDate, String.valueOf(likes)};
                model.addRow(row);
            }

            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void addMoviesFromFile(String filePath) {
        final String DB_URL = "jdbc:mysql://localhost/MyStore?serverTimezone=UTC";
        final String USERNAME = "root";
        final String PASSWORD = "";

        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            Statement stmt = conn.createStatement();

            String line;
            while ((line = br.readLine()) != null) {
                String[] movieData = line.split(",");
                String name = movieData[0];
                String category = movieData[1];
                String releaseDate = movieData[2];
                int likes = Integer.parseInt(movieData[3]);

                String sql = "INSERT INTO movies (name, category, releaseDate, likes) VALUES (?, ?, ?, ?)";
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, category);
                preparedStatement.setString(3, releaseDate);
                preparedStatement.setInt(4, likes);
                preparedStatement.executeUpdate();
            }

            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void writeMoviesToFile(String filePath) {
        final String DB_URL = "jdbc:mysql://localhost/MyStore?serverTimezone=UTC";
        final String USERNAME = "root";
        final String PASSWORD = "";

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
            Statement stmt = conn.createStatement();

            String sql = "SELECT * FROM movies";
            ResultSet resultSet = stmt.executeQuery(sql);

            FileWriter fileWriter = new FileWriter(filePath);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String category = resultSet.getString("category");
                String releaseDate = resultSet.getString("releaseDate");
                int likes = resultSet.getInt("likes");

                bufferedWriter.write(name + "," + category + "," + releaseDate + "," + likes);
                bufferedWriter.newLine();
            }

            bufferedWriter.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}



