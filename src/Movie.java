public class Movie {
    public String name;
    public String category;
    public String releaseDate;
    public int likes;
    public Movie(){};
    public Movie(String name, String category, String releaseDate, int likes) {
        this.name = name;
        this.category = category;
        this.releaseDate = releaseDate;
        this.likes = likes;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public int getLikes() {
        return likes;
    }
}
