package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import net.engineeringdigest.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<User> all = userService.getAll();
        if (all != null && !all.isEmpty()) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        try {
           // user.setDate(LocalDateTime.now());
            userService.saveEntry(user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        }

    }

    @GetMapping("id/{myId}")
    public ResponseEntity<?> findUserById(@PathVariable ObjectId myId) {
        Optional<User> journalEntry = userService.findById(myId);
        if (journalEntry.isPresent()) {
            return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);

        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }

    }

    @PutMapping("id/{userName}")
    public ResponseEntity<?> updateUser(@PathVariable String userName, @RequestBody User user) {
        User userInDb = userService.findByUsername(userName);
        if (userInDb != null) {
            userInDb.setUserName(user.getUserName() != null && !user.getUserName().equalsIgnoreCase("") ? user.getUserName() : userInDb.getUserName());
            userInDb.setPassword(user.getPassword() != null && !user.getPassword().equalsIgnoreCase("") ? user.getPassword() : userInDb.getPassword());
            userService.saveEntry(userInDb);
            return new ResponseEntity<>(userInDb, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @DeleteMapping("id/{myId}")
    public ResponseEntity<?> deleteUserById(@PathVariable ObjectId myId) {
        userService.deleteById(myId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }
}
