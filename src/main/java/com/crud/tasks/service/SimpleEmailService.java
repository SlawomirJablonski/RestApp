package com.crud.tasks.service;

import com.crud.tasks.domain.Mail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
public class SimpleEmailService {

    private final static Logger LOGGER = LoggerFactory.getLogger(SimpleMailMessage.class);

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private MailCreatorService mailCreatorService;

    public void send(final Mail mail){

        LOGGER.info("Starting email preparation...");

        try{
            //SimpleMailMessage mailMassage = createMailMassage(mail);
            javaMailSender.send(createMimeMessage(mail));
            javaMailSender.send(createScheduledMailMessage(mail));

            LOGGER.info("Email has been sent");

        }catch(MailException e){
            LOGGER.error("Failed to process email sending: ",e.getMessage(), e);
        }

    }

    private MimeMessagePreparator createMimeMessage(final Mail mail){
        return mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(mail.getMailTo());
            messageHelper.setSubject(mail.getSubject());
            messageHelper.setText(mailCreatorService.buildTrelloCardEmail(mail.getMessage()), true);
        };
    }

    private MimeMessagePreparator createScheduledMailMessage(final Mail mail) {
        return mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("sjmailuser@gmail.com");
            messageHelper.setTo(mail.getMailTo());
            messageHelper.setSubject(mail.getSubject());
            messageHelper.setText(mailCreatorService.buildScheduledEmail(mail.getMessage()), true);
        };
    }

    private SimpleMailMessage createMailMassage(final Mail mail){
        SimpleMailMessage mailMassage = new SimpleMailMessage();
        mailMassage.setTo(mail.getMailTo());
        mailMassage.setSubject(mail.getSubject());
        mailMassage.setText(mailCreatorService.buildTrelloCardEmail(mail.getMessage()));
        return mailMassage;
    }

    /*private SimpleMailMessage createMailMassage(final Mail mail){
        SimpleMailMessage mailMassage = new SimpleMailMessage();
        mailMassage.setTo(mail.getMailTo());
        mailMassage.setSubject(mail.getSubject());
        mailMassage.setText(mail.getMessage());
        if(mail.getToCc()!=null &&  mail.getToCc()!=""){
            mailMassage.setCc(mail.getToCc());
        }
        return mailMassage;
    }*/
}
