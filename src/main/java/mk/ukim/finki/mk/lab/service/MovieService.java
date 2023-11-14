package mk.ukim.finki.mk.lab.service;

import mk.ukim.finki.mk.lab.model.Movie;
import mk.ukim.finki.mk.lab.model.exceptions.InvalidIdException;

import java.util.List;

public interface MovieService {
    List<Movie> listAll();
    List<Movie> searchMoviesByText(String text);
    List<Movie> searchMoviesByRating(double rating);
    List<Movie> searchMoviesByTextAndRating(String text, double rating);

    void add(String movieTitle, String summary, String rating, String productionId);

    Movie findById(Long movieId) throws InvalidIdException;

    void editMovieById(Long movieId, String title, String summary, String rating, String productionId);

    void deleteById(Long id);
}
