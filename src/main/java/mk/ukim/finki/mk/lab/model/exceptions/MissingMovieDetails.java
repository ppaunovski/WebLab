package mk.ukim.finki.mk.lab.model.exceptions;

public class MissingMovieDetails extends RuntimeException{
    public MissingMovieDetails(String message) {
        super(message);
    }
}
