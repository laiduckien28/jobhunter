package vn.devops.jobhunter.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.devops.jobhunter.service.Subscribers;
import vn.devops.jobhunter.util.error.IdInvalidException;
@RestController
@RequestMapping("/api/v1/subscribers") 

public class SubscribersController {
    private final Subscribers subscribers_servies;
    public SubscribersController( Subscribers subscribers_servies) {
        this.subscribers_servies = subscribers_servies;
    }
    



    @PostMapping("/")
    public vn.devops.jobhunter.domain.Subscribers CreateNewSubscribersController(@RequestBody vn.devops.jobhunter.domain.Subscribers subscribers) throws IdInvalidException {
        return this.subscribers_servies.CreateNewSubscribers(subscribers);

    }
    @PutMapping("/")
    public vn.devops.jobhunter.domain.Subscribers UpdateSubscribersController(@RequestBody vn.devops.jobhunter.domain.Subscribers subscribers) throws IdInvalidException {
        return this.subscribers_servies.UpdateSubscribers(subscribers);

    } 

    @DeleteMapping("/{id}")
    public void DeleteSubscribersController(@PathVariable("id") Long id) { this.subscribers_servies.DeleteSubscribers(id);}
     
    @GetMapping("/")
    public List<vn.devops.jobhunter.domain.Subscribers> GetAllController() { 
        return this.subscribers_servies.GetSubscribers();
    }
}
