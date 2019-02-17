package app.ws.shared;

import app.ws.shared.dto.UserDto;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.*;

public class AmazonSES {

    // This address must be verified with Amazon SES.
    private final String FROM = "";

    // The subject line for email.
    private final String SUBJECT = "";

    // The HTML body for the email.
    private final String HTML_BODY = "<p>.../email-verification?token=$tokenValue</p>";

    // The email body for recipients with non-HTML email clients.
    private final String TEXT_BODY = ".../email-verification?token=$tokenValue";

    public void verifyEmail(UserDto userDto) {
        AmazonSimpleEmailService clienteSES = AmazonSimpleEmailServiceClientBuilder.standard()
                .withRegion(Regions.US_EAST_1).build();

        String htmlBodyWithToken = HTML_BODY.replace("$tokenValue", userDto.getEmailVerificationToken());
        String textBodyWithToken = TEXT_BODY.replace("$tokenValue", userDto.getEmailVerificationToken());

        SendEmailRequest emailRequest = new SendEmailRequest()
                .withDestination(new Destination().withToAddresses(userDto.getEmail()))
                .withMessage(new Message()
                        .withBody(new Body().withHtml(new Content().withCharset("UTF-8").withData(htmlBodyWithToken))
                                .withText(new Content().withCharset("UTF-8").withData(textBodyWithToken)))
                        .withSubject(new Content().withCharset("UTF-8").withData(SUBJECT)))
                .withSource(FROM);

        clienteSES.sendEmail(emailRequest);
        System.out.println("Email send!");

    }


}
