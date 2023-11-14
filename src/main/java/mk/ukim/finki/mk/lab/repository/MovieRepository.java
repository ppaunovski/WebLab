package mk.ukim.finki.mk.lab.repository;

import mk.ukim.finki.mk.lab.bootstrap.DataHolder;
import mk.ukim.finki.mk.lab.model.Movie;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class MovieRepository {

    public List<Movie> findAll(){
        return DataHolder.movies;
    }

    public List<Movie> searchMovies(String text){
        return DataHolder.movies.stream()
                .filter(m -> m.getTitle().contains(text) ||
                        m.getSummary().contains(text))
                .collect(Collectors.toList());
    }

    public List<Movie> searchMoviesByRating(double rating){
        return DataHolder.movies.stream()
                .filter(m -> m.getRating() >= rating)
                .collect(Collectors.toList());
    }

    public List<Movie> searchMoviesByTextAndRating(String text, double rating){
        return DataHolder.movies.stream()
                .filter(m -> m.getRating() >= rating &&
                        (m.getTitle().contains(text) || m.getSummary().contains(text)))
                .collect(Collectors.toList());
    }

    public Movie add(Movie movie) {
        DataHolder.movies.add(movie);
        return movie;
    }

    public Optional<Movie> findById(Long movieId) {
        return DataHolder.movies.stream().filter(m -> m.getId().equals(movieId)).findFirst();
    }
}
