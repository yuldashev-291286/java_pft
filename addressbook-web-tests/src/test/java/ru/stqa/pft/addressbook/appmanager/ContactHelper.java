package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

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
    attach(By.name("photo"), contactData.getPhoto());
    type(By.name("address"), contactData.getAddress());
    type(By.name("home"), contactData.getTelephone());
    type(By.name("email"), contactData.getEmail());

    if (creation) {
      if (contactData.getGroups().size() > 0) {
        Assert.assertTrue(contactData.getGroups().size() == 1);
        new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroups().iterator().next().getName());
      }
    } else {
      Assert.assertFalse(isElementPresent(By.name("new_group")));
    }
  }

  public void fillContactFormWithoutGroup(ContactData contactData) {
    type(By.name("firstname"), contactData.getFirstname());
    type(By.name("lastname"), contactData.getLastname());
    attach(By.name("photo"), contactData.getPhoto());
    type(By.name("address"), contactData.getAddress());
    type(By.name("home"), contactData.getTelephone());
    type(By.name("email"), contactData.getEmail());
  }

    public void submitContactCreation() {
    click(By.name("submit"));
  }

  public void selectContactById(int id) {
    wd.findElement(By.cssSelector("input[value='" + id + "']")).click();
  }

  public void selectContact() {
    wd.findElement(By.name("selected[]")).click();
  }

  public void deleteSelectedContacts() {
    click(By.xpath("//input[@value='Delete']"));
  }

  public void assertConfirmation() {
    wd.switchTo().alert().accept();
  }

  public void editContact(int lastElementOfContacts) {
    click(By.xpath("(//img[@alt='Edit'])[" + lastElementOfContacts + "]"));
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

  public ContactData create(ContactData contactData, boolean creation) {
    initContactCreation();
    fillContactForm(contactData, creation);
    submitContactCreation();
    contactCache = null;
    returnToContactPage();
    return contactData;
  }

  public void createContactWithoutAGroup(ContactData contactData) {
    initContactCreation();
    fillContactFormWithoutGroup(contactData);
    submitContactCreation();
    returnToContactPage();
  }

  public void modify(int id, ContactData contact) {
    initContactModificationById(id);
    fillContactForm(contact, false);
    submitContactModification();
    contactCache = null;
    returnToContactPage();
  }

  public void delete(ContactData contact) {
    selectContactById(contact.getId());
    deleteSelectedContacts();
    assertConfirmation();
    contactCache = null;
    home();
  }

  public void removeContactFromGroup(int idContact, String nameGroup) {
    home();
    goToContactGroup(nameGroup);
    selectContactById(idContact);
    removeFromGroup();
  }

  private void removeFromGroup() {
    click(By.name("remove"));
  }

  private void goToContactGroup(String nameGroup) {
    new Select(wd.findElement(By.name("group"))).selectByVisibleText(nameGroup);
  }

  public int count() {
    return wd.findElements(By.name("selected[]")).size();
  }

  private Contacts contactCache = null;

  public Contacts all() {
    if (contactCache != null) {
      return new Contacts(contactCache);
    }
    contactCache = new Contacts();
    List<WebElement> elements = wd.findElements(By.name("entry"));
    for (WebElement element: elements) {
      List<WebElement> cells = element.findElements(By.tagName("td"));
      int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
      String lastName = cells.get(1).getText();
      String firstName = cells.get(2).getText();
      String address = cells.get(3).getText();
      String allEmails = cells.get(4).getText();
      String allPhones = cells.get(5).getText();
      contactCache.add(new ContactData().withId(id).withFirstname(firstName).withLastname(lastName)
              .withAllPhones(allPhones).withAddress(address).withAllEmails(allEmails));
    }
    return contactCache;
  }

  public ContactData infoFromEditForm(ContactData contact) {
    initContactModificationById(contact.getId());
    String firstname = wd.findElement(By.name("firstname")).getAttribute("value");
    String lastname = wd.findElement(By.name("lastname")).getAttribute("value");
    String home = wd.findElement(By.name("home")).getAttribute("value");
    String mobile = wd.findElement(By.name("mobile")).getAttribute("value");
    String work = wd.findElement(By.name("work")).getAttribute("value");
    String address = wd.findElement(By.name("address")).getAttribute("value");
    String email = wd.findElement(By.name("email")).getAttribute("value");
    String email2 = wd.findElement(By.name("email2")).getAttribute("value");
    String email3 = wd.findElement(By.name("email3")).getAttribute("value");
    wd.navigate().back();
    return new ContactData().withId(contact.getId()).withFirstname(firstname).withLastname(lastname)
            .withHomePhone(home).withMobilePhone(mobile).withWorkPhone(work)
            .withAddress(address).withEmail(email).withEmail2(email2).withEmail3(email3);
  }

  private void initContactModificationById(int id) {
    WebElement checkbox = wd.findElement(By.cssSelector(String.format("input[value='%s']", id)));
    WebElement row = checkbox.findElement(By.xpath("./../.."));
    List<WebElement> cells = row.findElements(By.tagName("td"));
    cells.get(7).findElement(By.tagName("a")).click();

    //wd.findElement(By.xpath(String.format("//input[value='%s']/../../td[8]/a", id))).click();
    //wd.findElement(By.xpath(String.format("//tr[.//input[value='%s']]/td[8]/a", id))).click();
    //wd.findElement(By.cssSelector(String.format("a[href='edit.php?id=%s']", id))).click();
  }

  public void toGroup(String nameGroup) {
    new Select(wd.findElement(By.name("to_group"))).selectByVisibleText(nameGroup);
  }

  public void addToGroup() {
    click(By.name("add"));
  }

  private void contactsNoneGroups() {
    new Select(wd.findElement(By.name("group"))).selectByVisibleText("[none]");
  }

  private void contactsWithGroups(String nameGroup) {
    new Select(wd.findElement(By.name("group"))).selectByVisibleText(nameGroup);
  }

  public boolean сontactsWithoutAGroups() {
    home();
    contactsNoneGroups();
    if (wd.findElement(By.id("search_count")).getText().equals("0")) {
      return true;
    } else {
      return false;
    }
  }

  public void addContactFromGroup(int idContact, String nameGroup) {
    home();
    selectContactById(idContact);
    toGroup(nameGroup);
    addToGroup();
  }

  public boolean contactsInGroups(String nameGroup) {
    home();
    contactsWithGroups(nameGroup);
    if (wd.findElement(By.id("search_count")).getText().equals("0")) {
      return true;
    } else {
      return false;
    }
  }

}
