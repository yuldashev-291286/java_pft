package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.By;

public class ManageUsersHelper extends HelperBase {

  public ManageUsersHelper(ApplicationManager app) {
    super(app);
  }

  public void manage() {
    click(By.linkText("Manage"));
  }

  public void manageUsers() {
    click(By.linkText("Manage Users"));
  }

  public void selectUser(String linkText) {
    click(By.linkText(linkText));
  }

  public void resetPassword() {
    click(By.xpath("//input[@value='Reset Password']"));
  }

  public void createNewAccount() {
    click(By.xpath("//input[@value='Create New Account']"));
  }

  public void changePasswordToUser(String selectUser) {
    manage();
    manageUsers();
    selectUser(selectUser);
    resetPassword();
  }

  public void enterNewPassword(String password, String confirmPassword) {
    type(By.name("password"), password);
    type(By.name("password_confirm"), confirmPassword);
    click(By.xpath("//input[@value='Update User']"));
  }

  public String addNewAccount() {
    long now = System.currentTimeMillis();
    String username = String.format("user%s", now);
    String email = String.format("user%s@localhost", now);
    manage();
    manageUsers();
    createNewAccount();
    createUser(username, email);
    return username;
  }

  public void createUser(String username, String email) {
    type(By.name("username"), username);
    type(By.name("email"), email);
    click(By.xpath("//input[@value='Create User']"));
  }

}
