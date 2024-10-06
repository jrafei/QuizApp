package AI13.SpringBoot.repository;

import AI13.SpringBoot.models.beans.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
