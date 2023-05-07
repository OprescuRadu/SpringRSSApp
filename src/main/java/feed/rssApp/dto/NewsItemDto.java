package feed.rssApp.dto;

import java.util.Date;

public class NewsItemDto {

    private String title;
    private String link;
    private String description;
    private Date publicationDate;

    public NewsItemDto() {
    }

    public NewsItemDto(String title, String link, String description, Date publicationDate) {
        this.title = title;
        this.link = link;
        this.description = description;
        this.publicationDate = publicationDate;
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
}
