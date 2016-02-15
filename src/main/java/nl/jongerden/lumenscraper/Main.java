package nl.jongerden.lumenscraper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

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

        List<ScreeningWithMovieInfo> screenings = new ArrayList<>();
        for (Movie movie : movies) {
            for (Screening screening : movie.getScreenings()) {
                ScreeningWithMovieInfo s = new ScreeningWithMovieInfo();

                s.setDate(screening.getDate());
                s.setBookingUrl(screening.getBookingUrl());
                s.setTime(screening.getTime());

                s.setDescription(movie.getDescription());
                s.setShortDescription(movie.getShortDescription());
                s.setUrl(movie.getUrl());
                s.setTitle(movie.getTitle());

                screenings.add(s);
            }
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy");
        String date = sdf.format(new Date());

        writeJson(path + "movies.json", movies, mapper);
        writeJson(path + "movies-" + date + ".json", movies, mapper);

        writeJson(path + "screenings.json", screenings, mapper);
        writeJson(path + "screenings-" + date + ".json", screenings, mapper);
    }

    private static void writeJson(String fileLocation, Object data, ObjectMapper mapper) throws IOException {
        mapper.writeValue(new File(fileLocation), data);
        System.out.println("Json weggeschreven naar '" + fileLocation + "'");
    }
}
