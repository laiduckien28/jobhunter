package vn.devops.jobhunter.service;

import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.spring6.SpringTemplateEngine;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import vn.devops.jobhunter.domain.Job;
import vn.devops.jobhunter.domain.Skill;
import vn.devops.jobhunter.repository.JobRepository;

@Service
public class EmailService {
    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;
    private final JobRepository jobRepository;

    public EmailService(JavaMailSender javaMailSender,  SpringTemplateEngine templateEngine, JobRepository jobRepository) {
        this.javaMailSender = javaMailSender; this.templateEngine = templateEngine;  this.jobRepository = jobRepository;
    }
    public void  sendEmail() {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo("laiduckien28@gmail.com");
        simpleMailMessage.setFrom("Devops-Teams");
        simpleMailMessage.setText("Sliver");
        simpleMailMessage.setSubject("SliverOps");
        javaMailSender.send(simpleMailMessage);
    }
    public void sendEmailSync(String to, String subject, String content, boolean isMultipart, boolean isHtml) {
            // Prepare message using a Spring helper
            MimeMessage mimeMessage = this.javaMailSender.createMimeMessage();
            try {
                MimeMessageHelper message = new MimeMessageHelper(mimeMessage, isMultipart, StandardCharsets.UTF_8.name());
                message.setTo(to);
                message.setSubject(subject);
                message.setText(content, isHtml);
                this.javaMailSender.send(mimeMessage);
            } catch (MailException | MessagingException e) {
                System.out.println("ERROR SEND EMAIL: " + e);
            }
    }

    public void sendEmailFromTemplateSync(String to, List<Skill> listSkills, String subject, String templateName, List<Job> list) {
        org.thymeleaf.context.Context context = new org.thymeleaf.context.Context();

        // List<Job> jobs =  this.jobRepository.findAll();
        context.setVariable("jobs", list);  
        context.setVariable("listSkills", listSkills); 
        String content = this.templateEngine.process(templateName, context);
        this.sendEmailSync(to, subject, content, false, true);
}


    

    
}
