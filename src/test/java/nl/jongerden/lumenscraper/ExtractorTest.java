package nl.jongerden.lumenscraper;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by jan on 11 January 2016.
 * Test extraction from a fixed prefetched html file.
 */
public class ExtractorTest {

    private Set<String> getAllMovieUrls() {

        return Extractor.getAllMovieUrls(getFile("/alle_films.html"));
    }

    private Movie getLobster() {
        String file = getFile("/lobster.html");

        String url = "https://www.filmhuis-lumen.nl/film/the-lobster";
        return Extractor.getMovie(file, url);
    }

    private Movie getPawn() {
        String file = getFile("/pawn-sacrifice.html");

        String url = "https://www.filmhuis-lumen.nl/film/pawn-sacrifice";
        return Extractor.getMovie(file, url);
    }

    private String getFile(String fileName) {
//        try {
//            Scanner s = new Scanner(getClass().getResource(fileName).getFile());
        Path path = Paths.get(getClass().getResource(fileName).getFile());

        String stringFromFile = null;
        try {
            stringFromFile = new String(
                    java.nio.file.Files.readAllBytes(path));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stringFromFile;
    }

    @Test
    public void checkNumberOfMovies() {

        Assert.assertEquals("The number of movies extracted from the test file should be correct", 22, getAllMovieUrls().size());
    }

    @Test
    public void checkUrlInAllMovies() {

        Assert.assertTrue("One movie with url for Youth should be found",
                getAllMovieUrls().contains("https://www.filmhuis-lumen.nl/film/youth-la-giovinezza/"));
    }

    @Test
    public void checkTitle() {
        Assert.assertEquals("Title should match", "The Lobster", getLobster().getTitle());
    }

    @Test
    public void checkUrl() {
        Assert.assertEquals("Url should match", "https://www.filmhuis-lumen.nl/film/the-lobster", getLobster().getUrl());
    }

    @Test
    public void checkDescription() {
        Assert.assertTrue("Description should be okay", getLobster().getDescription().contains("een licht absurdistische film met een ernstige ondertoon en een melancholisch randje"));
    }

    @Test
    public void checkSingleScreening() {
        List<Screening> screenings = getLobster().getScreenings();
        Assert.assertEquals("Lobster should have one screening", 1, screenings.size());

        Screening screening = screenings.get(0);
        Assert.assertEquals("Screening is on 12 jan", "di 12 jan", screening.getDate());

        Assert.assertEquals("Screening is at 22:00", "22:00", screening.getTime());

        Assert.assertEquals("Check the booking url",
                "https://www.filmhuis-lumen.nl/bestel-kaarten/the-lobster/1716", screening.getBookingUrl());

    }

    @Test
    public void checkTwoScreeningsSameDay() {
        List<Screening> screenings = getPawn().getScreenings();

        // the pawn movie has two screenings on vr 15 jan
        List<String> times = screenings.stream()
                .filter(s -> s.getDate().equalsIgnoreCase("vr 15 jan"))
                .map(Screening::getTime)
                .collect(Collectors.toList());

        Assert.assertEquals("Are there two screenings?", 2, times.size());

        Assert.assertEquals("First screening at 14:30", "14:30", times.get(0));

        Assert.assertEquals("Second screening at 21:15", "21:15", times.get(1));
    }

}
