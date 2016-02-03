package nl.jongerden.lumenscraper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by jan on 13 January 2016.
 * Entry point for the scraping.
 */
public class Main {

    public static void main(String[] args) throws IOException {
        String path = "/tmp/";
        for (String arg : args) {
            if (arg.startsWith("path=")) {
                path = arg.substring(5);
                if (!path.endsWith("/")) {
                    path += "/";
                }
            }
        }
        System.out.println("scraping to destination dir '" + path + "'");
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

        writeJson(path + "movies.json", movies, mapper);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy");
    	String date = sdf.format(new Date());
        writeJson(path + "movies-" + date + ".json", movies, mapper);
    }

    private static void writeJson(String fileLocation, List<Movie> movies, ObjectMapper mapper) throws IOException {
        mapper.writeValue(new File(fileLocation), movies);
        System.out.println("Json weggeschreven naar '" + fileLocation + "'");
    }
}
