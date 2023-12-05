package mk.ukim.finki.mk.lab.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Table(name = "movie_shop_users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    @Convert(converter = UserFullnameConverter.class)
    private UserFullname fullname;
    private String password;
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate dateOfBirth;
    @OneToMany
    private List<ShoppingCart> carts;


    public User(String username, String password, String name, String surname, LocalDate localDate) {
        this.username = username;
        this.password = password;
        this.fullname = new UserFullname(name, surname);
        this.dateOfBirth = localDate;
    }

    public User() {

    }
}
