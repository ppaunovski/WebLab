package mk.ukim.finki.mk.lab.service.impl;

import mk.ukim.finki.mk.lab.model.Movie;
import mk.ukim.finki.mk.lab.model.Production;
import mk.ukim.finki.mk.lab.model.exceptions.InvalidIdException;
import mk.ukim.finki.mk.lab.model.exceptions.InvalidRatingInput;
import mk.ukim.finki.mk.lab.model.exceptions.MissingMovieDetails;
import mk.ukim.finki.mk.lab.model.exceptions.MovieNotFoundException;
import mk.ukim.finki.mk.lab.repository.MovieRepository;
import mk.ukim.finki.mk.lab.repository.ProductionRepository;
import mk.ukim.finki.mk.lab.service.MovieService;
import org.springframework.stereotype.Service;

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
        return movieRepository.findAllByTitleLike(text);
    }

    @Override
    public List<Movie> searchMoviesByRating(double rating) {
        return movieRepository.findAllByRating(rating);
    }

    @Override
    public List<Movie> searchMoviesByTextAndRating(String text, double rating) {
        return movieRepository.findAllByTitleContainingIgnoreCaseAndRating(text, rating);

    }

    @Override
    public void add(String movieTitle, String summary, Double rating, Long productionId) {

        if(movieTitle.isEmpty() || summary.isEmpty()){
            throw new MissingMovieDetails("Missing movie details");
        }
        Movie movie = new Movie(movieTitle, summary, rating, productionRepository.findById(productionId).orElseThrow(() -> new RuntimeException("Production not found")));
        movieRepository.save(movie);
    }

    @Override
    public Movie findById(Long movieId) throws InvalidIdException {
        return movieRepository.findById(movieId).orElseThrow(() -> new InvalidIdException("Invalid movie id"));
    }

    @Override
    public void editMovieById(Long movieId, String title, String summary, Double rating, Long productionId) {
        Optional<Production> production = productionRepository.findById(productionId);

        Production prod = production.orElseThrow(() -> new RuntimeException("Invalid production id"));
        Movie movie = movieRepository.findById(movieId).orElseThrow(MovieNotFoundException::new);

        movie.setTitle(title);
        movie.setSummary(summary);
        movie.setRating(rating);
        movie.setProduction(prod);

        this.movieRepository.save(movie);

    }

    @Override
    public void deleteById(Long id) {
        movieRepository.deleteById(id);
    }
}
