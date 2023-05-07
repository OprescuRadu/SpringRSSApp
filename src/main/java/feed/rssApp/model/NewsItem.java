package feed.rssApp.model;

import jakarta.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name="news_items")
public class NewsItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String link;
    private String description;
    private Date publicationDate;

    public NewsItem(){
    }

    public NewsItem(String title, String link, String description, Date publicationDate) {
        this.title = title;
        this.link = link;
        this.description = description;
        this.publicationDate = publicationDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NewsItem newsItem = (NewsItem) o;
        return Objects.equals(link, newsItem.link);
    }

    @Override
    public int hashCode() {
        return link != null ? link.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "title='" + title + '\'' +
                ", link='" + link + '\'' +
                ", description='" + description + '\'' +
                ", publicationDate=" + publicationDate + '\n';
    }
}
