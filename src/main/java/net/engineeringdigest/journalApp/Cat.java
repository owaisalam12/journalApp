package net.engineeringdigest.journalApp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Cat {

    @Autowired
    private Dog dog;

    @GetMapping("test")
    public String name(){
        return dog.fun();
    }

}
