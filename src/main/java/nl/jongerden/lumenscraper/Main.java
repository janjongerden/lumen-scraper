package nl.jongerden.lumenscraper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by jan on 13 January 2016.
 */
public class Main {

    public static void main(String[] args) throws IOException {
        // do post to get all current movies
        String allMovies = Scraper.getAllMovies();

        // extract the urls
        Set<String> allUrls = Extractor.getAllMovieUrls(allMovies);

        List<Movie> movies = new ArrayList<>();
        for (String url : allUrls) {
            String movieInfo = Scraper.getMovieInfo(url);
            Movie movie = Extractor.getMovie(movieInfo, url);

            // only add movies with screenings
            if (movie.getScreenings() != null && !movie.getScreenings().isEmpty()) {
                movies.add(movie);
            }
        }



        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        String fileLocation = "/tmp/movies.json";
        mapper.writeValue(new File(fileLocation), movies);
        System.out.println("Json weggeschreven naar '" + fileLocation + "'");
    }
}
