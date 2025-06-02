package vn.devops.jobhunter.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.devops.jobhunter.domain.Job;
import vn.devops.jobhunter.domain.Skill;
import vn.devops.jobhunter.domain.Subscribers;
import vn.devops.jobhunter.repository.JobRepository;
import vn.devops.jobhunter.repository.SubscribersRepository;
import vn.devops.jobhunter.service.EmailService;
@RestController

@RequestMapping("/api/v1/email")
public class EmailController {
    private final SubscribersRepository subscribersRepository;
    private final JobRepository jobRepository;
    private final EmailService emailService;

    public EmailController(
            SubscribersRepository subscribersRepository,
            JobRepository jobRepository,
            EmailService emailService) {
        this.subscribersRepository = subscribersRepository;
        this.jobRepository = jobRepository;
        this.emailService = emailService;
    }

     @Scheduled(fixedDelay = 60000)
     @Transactional
    @PostMapping("/send")


     public void sendSubscribersEmailJobs() {
        List<Subscribers> listSubs = this.subscribersRepository.findAll();
        if (listSubs != null && listSubs.size() > 0) {
            for (Subscribers sub : listSubs) {
                List<Skill> listSkills = sub.getSkills();
                if (listSkills != null && listSkills.size() > 0) {
                    // List<Long> listIdSkills = listSkills.stream().
                    // List<Job> listJobs = this.jobRepository.findBySkillsEmail(listSkills);

                    List<Long> listSkillId = listSkills.stream().map(b -> b.getId()).collect(Collectors.toList());
                    ArrayList<Job> listJobs = new ArrayList<>();
                    for( Long id : listSkillId) {
                       Optional<Job> listJobsSearch = this.jobRepository.findById(id);
                       listJobs.add(listJobsSearch.get());
                    }
                    

                    if (listJobs != null && listJobs.size() > 0) {

                        // List<ResEmailJob> arr = listJobs.stream().map(
                        // job -> this.convertJobToSendEmail(job)).collect(Collectors.toList());

                        this.emailService.sendEmailFromTemplateSync(
                                sub.getEmail(),  listSkills,
                                "Cơ hội việc làm hot đang chờ đón bạn, khám phá ngay",
                                "job",
                                listJobs);
                    }
                }
            }
        }
    }



}
