package ru.stqa.pft.mantis.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class PasswordChangesInMantisBTTests extends TestBase{

  @BeforeMethod(alwaysRun = true)
  public void startMailServer() {
    app.mail().start();
  }

  @Test
  public void testPasswordChangesInMantisBT() {

  }

  @AfterMethod(alwaysRun = true)
  public void stopMailServer() {
    app.mail().stop();
  }

}
