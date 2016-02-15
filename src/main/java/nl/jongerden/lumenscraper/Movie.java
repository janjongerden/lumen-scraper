package nl.jongerden.lumenscraper;

import java.util.List;

public class Movie {
    private String title;
    private String url;
    private String description;
    private List<Screening> screenings;
    private String shortDescription;


    public Movie() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Movie)) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (getTitle() == null) {
            return ((Movie) obj).getTitle() == null;
        }
        return getTitle().equals(((Movie) obj).getTitle());
    }

    @Override
    public int hashCode() {
        if (getTitle() == null) {
            return 0;
        }
        return getTitle().hashCode();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Screening> getScreenings() {
        return screenings;
    }

    public void setScreenings(List<Screening> screenings) {
        this.screenings = screenings;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }
}
