package gestione.utenti.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService{

	@Autowired
	private JavaMailSender javaMailSender;

	public void sendMail(String mailAddressee, String mailObject, String mailMessage) throws MessagingException {
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
		mimeMessageHelper.setSubject(mailObject);
		mimeMessageHelper.setTo(mailAddressee);
		mimeMessageHelper.setText(mailMessage, true);
		javaMailSender.send(mimeMessage);
	}	
	
}
