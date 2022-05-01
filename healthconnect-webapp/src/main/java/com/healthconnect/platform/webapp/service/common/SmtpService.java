package com.healthconnect.platform.webapp.service.common;

import com.healthconnect.platform.common.CryptoUtility;
import com.healthconnect.platform.enums.EmailContentKey;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.EnumMap;

@Component
public class SmtpService {

    private static final Logger logger = LoggerFactory.getLogger(SmtpService.class);

    @Autowired
    private JavaMailSender mailSender;

    public boolean sendEmail(EmailContentMap<EmailContentKey,String> emailContent) {

        String emailSendTo = emailContent.get(EmailContentKey.TO);

        if (emailSendTo != null && !emailSendTo.trim().equalsIgnoreCase("null")) {
            logger.debug("Sending email to " + emailSendTo);
            try {
                String emailText = CryptoUtility.fromBase64_unsecure(emailContent.get(EmailContentKey.CONTENT));

                MimeMessage mimeMessage = mailSender.createMimeMessage();
                MimeMessageHelper mimeHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

                mimeHelper.setFrom(emailContent.get(EmailContentKey.FROM));
                mimeHelper.setTo(StringUtils.split(emailSendTo, ";"));

				/*
				 * if (emailContent.get(EmailContentKey.CC) != null) {
				 * mimeHelper.setCc(emailContent.get(EmailContentKey.CC)); }
				 */
                mimeHelper.setText(emailText, true);
                mimeHelper.setSubject(emailContent.get(EmailContentKey.SUBJECT));

                mailSender.send(mimeMessage);
                return true;
            } catch (MessagingException | MailException ex) {
                logger.error("email sent failed!", ex);
                return false;
            }
        } else {
            logger.error("No receiver's email found!, ignoring this email");
            // email send to is null, ignore it
            return true;
        }
    }

    public static class EmailContentMap<K extends Enum<K>, Object> extends EnumMap<K, Object> {

        private final Class keyType;

        public EmailContentMap(Class keyType) {
            super(keyType);
            this.keyType = keyType;
        }

        /**
         * String representation of this map as K1=V1,K2=V2...
         * <p>
         *
         * @return
         */
        @Override
        public String toString() {
            return super.toString()
                    .replace("{", "")
                    .replace("}", "");
        }

        /**
         * Fill the map by deserializing mapString of format K1=V1,K2=V2 ....
         * <p>
         *
         * @param mapString
         */
        public void fillAllByString(String mapString) {
            String[] kvPairs = mapString.split(",");

            for (String kvPair : kvPairs) {
                String[] values = kvPair.split("=");
                K keyObject = (K) Enum.valueOf(keyType, values[0].trim());
                this.put(keyObject, (Object) values[1]);
            }
        }
    }
}
