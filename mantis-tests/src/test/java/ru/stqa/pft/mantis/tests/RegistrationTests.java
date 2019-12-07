package ru.stqa.pft.mantis.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.stqa.pft.mantis.model.MailMessage;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.testng.Assert.assertTrue;

public class RegistrationTests extends TestBase {

  @BeforeMethod(enabled = false)
  public void testBeforeRegistration() throws IOException {
    app.ftp().upload(new File("src/test/resources/config_inc.php"), "config_inc.php", "config_inc.php.bak");
  }

  @BeforeMethod(alwaysRun = true)
  public void startMailServer() {
    app.mail().start();
  }

  @Test
  public void testRegistration() throws IOException {
    long now = System.currentTimeMillis();
    String user = String.format("user%s", now);
    String password = "password";
    String email = String.format("user%s@localhost.localdomain", now);
    app.registration().start(user, email);
    List<MailMessage> mailMessages = app.mail().waitForMail(2, 10000);
    String confirmationLink = findConfirmationLink(mailMessages, email);
    app.registration().finish(confirmationLink, password);
    assertTrue(app.newSession().login(user, password));
  }

  private String findConfirmationLink(List<MailMessage> mailMessages, String email) {
    MailMessage mailMessage = mailMessages.stream().filter((m) -> m.to.equals(email)).findFirst().get();
    VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
    return regex.getText(mailMessage.text);
  }

  @AfterMethod(alwaysRun = true)
  public void stopMailServer() {
    app.mail().stop();
  }

  @AfterMethod(enabled = false)
  public void testAfterRegistration() throws IOException {
    app.ftp().restore("config_inc.php.bak", "config_inc.php");
  }

}
