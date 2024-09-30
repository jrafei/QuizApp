package AI13.SpringBoot.controllers;
package AI13.SpringBoot.models;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    public UserController() {}

    @GetMapping("/user")
    public User getUser(@RequestParam(value = "id") Integer id) {
        return new User(counter.incrementAndGet(),
                String.format(template, id));
    }

    @PostMapping(value = "/users")
    public User createUser(@RequestBody User newUser) {
        return userRepository.save(newUser);
    }
}