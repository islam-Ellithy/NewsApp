package lamaatech.com.newsapp;

import java.io.Serializable;


public class News implements Serializable {
    private String title;
    private String author;
    private String url;
    private String date;
    private String section;

    public News(String title, String author, String url, String date, String section) {
        this.title = title;
        this.author = author;
        this.url = url;
        setDate(date);
        this.section = section;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getUrl() {
        return url;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        date = date.substring(0, date.indexOf("T"));
        this.date = date;
    }

    public String getSection() {
        return section;
    }
}
