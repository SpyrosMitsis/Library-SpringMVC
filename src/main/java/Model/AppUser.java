package Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.HashSet;
import java.util.Set;

@Entity
public class AppUser {
    @Id
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    @Enumerated(jakarta.persistence.EnumType.STRING)
    private Role role;
    @OneToMany(mappedBy = "user")
    private Set<Rating> ratings = new HashSet<>();

}
