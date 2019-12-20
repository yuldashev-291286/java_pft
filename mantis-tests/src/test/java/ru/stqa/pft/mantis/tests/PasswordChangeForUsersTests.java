package ru.stqa.pft.mantis.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.stqa.pft.mantis.appmanager.HttpSession;
import ru.stqa.pft.mantis.model.MailMessage;
import ru.stqa.pft.mantis.model.UserData;

import java.io.IOException;
import java.util.List;

import static org.testng.Assert.assertTrue;

public class PasswordChangeForUsersTests extends TestBase {

  @BeforeMethod(alwaysRun = true)
  public void startMailServer() {
    app.mail().start();
  }

  @Test
  public void testPasswordChangeForUsers() throws IOException {

    app.session().login("administrator", "root");

    //String user = app.manageUsers().addNewAccount();
    String user = null;
    String email = null;
    List<UserData> usersList = app.db().usersList();
    for (UserData userData: usersList) {
      if (! userData.getUsername().equals("administrator")) {
        user = userData.getUsername();
        email = userData.getEmail();
        break;
      } else {
        continue;
      }
    }
    app.manageUsers().changePasswordToUser(user);

    List<MailMessage> mailMessages = app.mail().waitForMail(2, 10000);
    String confirmationLink = findConfirmationLink(mailMessages, email);

    String newPassword = "password";
    app.getDriver(confirmationLink);
    app.manageUsers().enterNewPassword(newPassword, newPassword);

    HttpSession session = app.newSession();
    assertTrue(session.login(user, newPassword));
    assertTrue(session.isLoggedInAs(user));
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

}
