package nl.jongerden.lumenscraper;

/**
 * Information of one particular screening of one movie.
 * Created by jan on 12 January 2016.
 */
public class Screening {
    private String date;
    private String time;
    private String bookingUrl;

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public String getBookingUrl() {
        return bookingUrl;
    }

    public void setBookingUrl(String bookingUrl) {
        this.bookingUrl = bookingUrl;
    }
}
