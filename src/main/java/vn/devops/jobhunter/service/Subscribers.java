package vn.devops.jobhunter.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import vn.devops.jobhunter.domain.Skill;
import vn.devops.jobhunter.repository.SkillRepository;
import vn.devops.jobhunter.repository.SubscribersRepository;
import vn.devops.jobhunter.util.error.IdInvalidException;
@Service
public class Subscribers {
    private final SubscribersRepository subscribersRepository;
    private final SkillRepository skillRepository;
    public Subscribers(SubscribersRepository subscribersRepository,  SkillRepository skillRepository) {
        this.subscribersRepository = subscribersRepository; this.skillRepository = skillRepository;
    }

    public vn.devops.jobhunter.domain.Subscribers CreateNewSubscribers(vn.devops.jobhunter.domain.Subscribers subscribers) throws IdInvalidException {

        vn.devops.jobhunter.domain.Subscribers hasSubcribers = this.subscribersRepository.findByName(subscribers.getName());
        if( hasSubcribers != null) {

            throw new IdInvalidException("Subscribers Name đã tồn tại rồi!");
        }

        if( subscribers.getSkills() != null) { 
            List<Long> reqSkills = subscribers.getSkills().stream().map(x -> x.getId()).collect(Collectors.toList());
            List<Skill> dbSkills  = this.skillRepository.findByIdIn(reqSkills);


            subscribers.setSkills(dbSkills);

        }





        
        return this.subscribersRepository.save(subscribers);
        
    }


    public vn.devops.jobhunter.domain.Subscribers UpdateSubscribers(vn.devops.jobhunter.domain.Subscribers subscribers) throws IdInvalidException {


        Optional<vn.devops.jobhunter.domain.Subscribers> Current_Sub = this.subscribersRepository.findById(subscribers.getSubscribers_id());
        
        if( Current_Sub.isEmpty()) {
            throw new IdInvalidException("Subscribers không tồn tại");
        }

        else {
            vn.devops.jobhunter.domain.Subscribers currentSub = Current_Sub.get();

            currentSub.setSkills(subscribers.getSkills());
            currentSub.setEmail(subscribers.getEmail());
            currentSub.setName(subscribers.getName());
            return this.subscribersRepository.save(currentSub);
        }
        
        
    }

    public void DeleteSubscribers(Long id) {
        this.subscribersRepository.deleteById(id);
    }

    public List<vn.devops.jobhunter.domain.Subscribers> GetSubscribers()   { return this.subscribersRepository.findAll(); }


}
