package AI13.SpringBoot.repositories;

import AI13.SpringBoot.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}