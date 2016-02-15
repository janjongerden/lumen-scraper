package nl.jongerden.lumenscraper;

/**
 * Information of one particular screening of one movie.
 * Includes the Movie-info.
 */
public class ScreeningWithMovieInfo extends Screening {
    private String url;
    private String description;
    private String shortDescription;
    private String title;

    public ScreeningWithMovieInfo() {
        
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }
}
