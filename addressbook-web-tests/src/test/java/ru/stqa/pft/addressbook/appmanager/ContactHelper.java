package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.ArrayList;
import java.util.List;

public class ContactHelper extends HelperBase {

  public ContactHelper(WebDriver wd) {
    super(wd);
  }

  public void returnToContactPage() {
    click(By.linkText("home page"));
  }

  public void fillContactForm(ContactData contactData, boolean creation) {
    type(By.name("firstname"), contactData.getFirstname());
    type(By.name("lastname"), contactData.getLastname());
    type(By.name("address"), contactData.getAddress());
    type(By.name("home"), contactData.getTelephone());
    type(By.name("email"), contactData.getEmail());

    if (creation) {
      new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroup());
    } else {
      Assert.assertFalse(isElementPresent(By.name("new_group")));
    }
  }

  public void submitContactCreation() {
    click(By.name("submit"));
  }

  public void selectContact(int index) {
    wd.findElements(By.name("selected[]")).get(index).click();
  }

  public void deleteSelectedContacts() {
    click(By.xpath("//input[@value='Delete']"));
  }

  public void assertConfirmation() {
    wd.switchTo().alert().accept();
  }

  public void editContact() {
    click(By.xpath("//img[@alt='Edit']"));
  }

  public void submitContactModification() {
    click(By.xpath("//input[@name='update']"));
  }

  public boolean isThereAContact() {
    return isElementPresent(By.name("selected[]"));
  }

  public void initContactCreation() {
    if (! isElementPresent(By.id("maintable"))) {
      return;
    }
    click(By.linkText("add new"));
  }

  public void home() {
    click(By.linkText("home"));
  }

  public void create(ContactData contactData, boolean creation) {
    initContactCreation();
    fillContactForm(contactData, creation);
    submitContactCreation();
    returnToContactPage();
  }

  public void modify(int index, ContactData contact) {
    selectContact(index);
    editContact();
    fillContactForm(contact, false);
    submitContactModification();
    returnToContactPage();
  }

  public void delete(int index) {
    selectContact(index);
    deleteSelectedContacts();
    assertConfirmation();
    home();
  }

  public int contactCount() {
    return wd.findElements(By.name("selected[]")).size();
  }

  public List<ContactData> list() {
    List<ContactData> contacts = new ArrayList<ContactData>();
    List<WebElement> elements = wd.findElements(By.name("entry"));
    for (WebElement element: elements) {
      String[] strings = element.getText().split(" ");
      String lastName = strings[0];
      String firstName = strings[1];
      int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
      ContactData contact = new ContactData().withId(id).withFirstname(firstName).withLastname(lastName);
      contacts.add(contact);
    }
    return contacts;
  }

}
