package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase {

  @Test
  public void testContactCreation() throws Exception {
    app.getNavigationHelper().gotoContactPage();
    app.getContactHelper().fillContactForm(new ContactData("Ruslan", "Yuldashev", "Moscow", "8-495-716-45-78", "uldashev@inbox.ru", "test1"), true);
    app.getContactHelper().submitContactCreation();
    app.getContactHelper().returnToContactPage();
  }

}
