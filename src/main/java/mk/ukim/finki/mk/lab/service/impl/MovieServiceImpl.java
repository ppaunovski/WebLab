package mk.ukim.finki.mk.lab.service.impl;

import mk.ukim.finki.mk.lab.bootstrap.DataHolder;
import mk.ukim.finki.mk.lab.model.Movie;
import mk.ukim.finki.mk.lab.model.Production;
import mk.ukim.finki.mk.lab.model.exceptions.InvalidIdException;
import mk.ukim.finki.mk.lab.model.exceptions.InvalidRatingInput;
import mk.ukim.finki.mk.lab.model.exceptions.MissingMovieDetails;
import mk.ukim.finki.mk.lab.repository.MovieRepository;
import mk.ukim.finki.mk.lab.repository.ProductionRepository;
import mk.ukim.finki.mk.lab.service.MovieService;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.util.List;
import java.util.Optional;

@Service
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;
    private final ProductionRepository productionRepository;

    public MovieServiceImpl(MovieRepository movieRepository, ProductionRepository productionRepository){
        this.movieRepository = movieRepository;
        this.productionRepository = productionRepository;
    }
    @Override
    public List<Movie> listAll() {
        return movieRepository.findAll();
    }

    @Override
    public List<Movie> searchMoviesByText(String text) {
        return movieRepository.searchMovies(text);
    }

    @Override
    public List<Movie> searchMoviesByRating(double rating) {
        return movieRepository.searchMoviesByRating(rating);
    }

    @Override
    public List<Movie> searchMoviesByTextAndRating(String text, double rating) {
        return movieRepository.searchMoviesByTextAndRating(text, rating);
    }

    @Override
    public void add(String movieTitle, String summary, String rating, String productionId) {
        Double dRating;
        try{
            dRating = Double.parseDouble(rating);
        }
        catch (Exception e){
            throw new InvalidRatingInput("Invalid rating input");
        }
        if(movieTitle.isEmpty() || summary.isEmpty()){
            throw new MissingMovieDetails("Missing movie details");
        }
        Movie movie = new Movie(movieTitle, summary, dRating, productionRepository.findById(Integer.parseInt(productionId)).orElseThrow(() -> new RuntimeException("Production not found")));
        movieRepository.add(movie);
    }

    @Override
    public Movie findById(Long movieId) throws InvalidIdException {
        return movieRepository.findById(movieId).orElseThrow(() -> new InvalidIdException("Invalid movie id"));
    }

    @Override
    public void editMovieById(Long movieId, String title, String summary, String rating, String productionId) {
        Optional<Production> production = productionRepository.findById(Integer.parseInt(productionId));
        Double dRating;
        try{
            dRating = Double.parseDouble(rating);
        }
        catch (Exception e){
            throw new RuntimeException("Invalid rating");
        }
        Production prod = production.orElseThrow(() -> new RuntimeException("Invalid production id"));
        movieRepository.findById(movieId).ifPresent(
                m -> {
                    m.setTitle(title);
                    m.setSummary(summary);
                    m.setRating(dRating);
                    m.setProduction(prod);
                }
        );
    }

    @Override
    public void deleteById(Long id) {
        Movie movie = movieRepository.findById(id).orElseThrow(() -> new RuntimeException("Invalid movie id"));
        movieRepository.findAll().remove(movie);
    }
}
