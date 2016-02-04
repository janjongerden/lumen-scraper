package nl.jongerden.lumenscraper;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.util.Set;


/**
 * Created by jan on 13 January 2016.
 */
public class ScraperTest {

    private static Set<String> allMovieUrls;

    @BeforeClass
    public static void setup() throws IOException {
        String response = Scraper.getAllMovies();

        allMovieUrls = Extractor.getAllMovieUrls(response);
    }

    @Test
    public void testGetAllMovies() {
        Assert.assertTrue("There should be at least one movie ", allMovieUrls.size() > 0);
    }

    @Test
    public void testHtmlOfOneMovie() {
        String movieInfo = Scraper.getMovieInfo(allMovieUrls.iterator().next());
        Assert.assertTrue("The movie info should at least a div like this ", movieInfo.contains("<div class=\"pagewide carrousel timetable\">"));
    }
}
