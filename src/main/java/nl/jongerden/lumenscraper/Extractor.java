package nl.jongerden.lumenscraper;

import nl.jongerden.lumenscraper.util.DateUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Extract information from html from the Lumen site.
 * Created by jan on 9-1-16.
 */
public class Extractor {

    public static Set<String> getAllMovieUrls(String html) {
        Set<String> urls = new HashSet<>();
        Document doc = Jsoup.parse(html, "UTF-8");

        Elements events = doc.getElementsByClass("event-text");
        for (Element event : events) {
            Elements titleElements = event.getElementsByClass("wp_theatre_event_title");
            if (!titleElements.isEmpty()) {
                Elements a = titleElements.get(0).getElementsByTag("a");
                // url can contain line breaks
                String url = a.attr("href").replace("\n", "");

                urls.add(url);
            }

        }

        return urls;
    }

    private static Document getDocument(File file) {
        Document doc = null;
        try {
            doc = Jsoup.parse(file, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return doc;
    }

    public static Movie getMovie(String html, String url) {
        Document doc = Jsoup.parse(html, "UTF-8");

        Movie movie = new Movie();

        movie.setUrl(url);

        try {
            String title = doc.getElementsByClass("theatre-post-title").html();
            movie.setTitle(title);

            String description = doc.getElementsByClass("feature").html();
            movie.setDescription(description);

            String shortDescription = doc.getElementsByClass("theatre-post-short-text").get(0).text();
            movie.setShortDescription(shortDescription);

            Element timeTable = doc.getElementsByClass("wpt_production_timetable").get(0);

            List<Screening> screenings = new ArrayList<>();

            Elements rows = timeTable.getElementsByTag("tr");
            Elements dates = rows.get(0).getElementsByTag("th");

            for (int row = 1; row < rows.size(); row++) {
                for (int i = 0; i < dates.size() - 1; i++) {
                    Element dateElement = dates.get(i + 1);
                    String date = DateUtil.convertLumenDate(dateElement.html(), LocalDate.now());

                    getTimeFromRow(rows.get(row).getElementsByTag("td"), i, date, screenings);

                }
            }
            movie.setScreenings(screenings);
        } catch (Exception e) {
            System.err.println("Unfortunately something went wrong for url: " + url + " message = " + e.getMessage());
        }

        return movie;
    }

    private static void getTimeFromRow(Elements times, int i, String date, List<Screening> screenings) {

        Elements timeElements = times.get(i).getElementsByTag("a");
        for (Element time : timeElements) {
            Screening screening = new Screening();

            screening.setDate(date);
            screening.setTime(time.html().replace(".", ":"));
            screening.setBookingUrl(time.attr("href"));

            screenings.add(screening);
        }
    }
}
