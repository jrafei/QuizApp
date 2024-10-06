
package AI13.SpringBoot.controller;

import AI13.SpringBoot.models.beans.User;
import AI13.SpringBoot.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class userController {
    private final UserService userService; //inversion de control

    @PostMapping(value = "/create" )
    public User create(@RequestBody User u){
        return userService.createUser(u);
    }

    @GetMapping("/read")
    public List<User> read(){
        return userService.getAllUsers();
    }

    @PutMapping("/update/{id}")
    public User update(@PathVariable int id,  @RequestBody User u){
        return userService.updateUser(id, u);
    }

    @DeleteMapping("/delete")
    public String delete(@PathVariable int id){
        return userService.deleteUser(id);

    }


}