package edu.ucalgary.ensf480.group18.user.repository;

import edu.ucalgary.ensf480.group18.user.model.Cookie;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CookieRepo extends JpaRepository<Cookie, Integer> {
    Cookie findByToken(String token);
    void deleteByToken(String token);
}
