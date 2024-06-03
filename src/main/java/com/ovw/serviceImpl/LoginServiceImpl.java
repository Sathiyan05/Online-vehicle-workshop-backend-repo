package com.ovw.serviceImpl;

import java.util.HashMap;
import java.util.Map;

import org.jvnet.hk2.annotations.Service;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.ovw.bean.Login;
import com.ovw.bean.ServiceCenter;
import com.ovw.repository.LoginRepo;
import com.ovw.repository.ServiceCenterRepo;
import com.ovw.service.LoginService;

@Service
@Component
public class LoginServiceImpl implements LoginService {

	@Autowired
	private LoginRepo loginRepository;
	
	@Autowired
	private ServiceCenterRepo serviceRepo;
	
	@Autowired
	private JavaMailSender mailsender;

	@Override
    public ResponseEntity<?> login(String email, String password) {
		
        Login login = loginRepository.findByEmail(email);
        
        System.out.println(login);
        
        if (login == null || !password.equals(login.getPassword())) {
            return ResponseEntity.badRequest().body("Invalid email or password");
        }
        
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("sessionId", login.getSessionId());
        responseData.put("role", login.getRole());
        
        SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("no.reply.autorepair@gmail.com");
		message.setTo(login.getEmail());
		message.setText("\nGreeting of the day...!!!\n"
				+ "You're logged in to the Online Vehicle Workshop\n\n\n"
				+ "\n\n"
				+ "Regards\n Auto Repair");
		message.setSubject("Login Alert...!!!");
		mailsender.send(message);
        
        return ResponseEntity.ok(responseData);
    }
}
