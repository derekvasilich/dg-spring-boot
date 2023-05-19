package com.example.service;

import java.time.Instant;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.example.controllers.UserController;
import com.example.models.RefreshToken;
import com.example.models.User;
import com.example.security.service.RefreshTokenService;

@Service
public class EmailServiceImpl {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private static final String EMAIL_TEXT_TEMPLATE_NAME = "text/signup";
    private static final String EMAIL_HTML_TEMPLATE_NAME = "html/signup";

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
	private JavaMailSender emailSender;

    @Autowired
    private TemplateEngine htmlTemplateEngine;

    @Autowired
    private TemplateEngine textTemplateEngine;

    @Value("${app.mail.signup.from}")
    private String signupMailFrom;

    @Value("${app.mail.signup.subject}")
    private String signupMailSubject;

    public void sendSignupEmailForUser(User user) {
        RefreshToken refresh = refreshTokenService.createRefreshToken(user.id);
        String token = refresh.getToken();

        final Context ctx = new Context();
        ctx.setVariable("firstName", user.firstName);
        ctx.setVariable("token", token);
        ctx.setVariable("signupDate", Instant.now());
//        ctx.setVariable("imageResourceName", imageResourceName); // so that we can reference it from HTML
                
        try {
            final MimeMessage mimeMessage = emailSender.createMimeMessage();
            final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            message.setFrom(signupMailFrom);
            message.setTo(user.email);
            message.setSubject(signupMailSubject);

            String textHtml = this.htmlTemplateEngine.process(EMAIL_HTML_TEMPLATE_NAME, ctx);
            message.setText(textHtml, true);

            // Add the inline image, referenced from the HTML code as "cid:${imageResourceName}"
            // final InputStreamSource imageSource = new ByteArrayResource(imageBytes);
            // message.addInline(imageResourceName, imageSource, imageContentType);

            emailSender.send(mimeMessage); 
        } catch (MessagingException exception) {
            log.error(exception.getMessage(), exception);
            exception.printStackTrace();
        } catch (MailException exception) {
            log.error(exception.getMessage(), exception);
            exception.printStackTrace();
        }
    }

}
