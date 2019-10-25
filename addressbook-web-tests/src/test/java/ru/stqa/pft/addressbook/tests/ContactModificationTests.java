package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactModificationTests extends TestBase {

  @Test
  public void testContactModification() throws Exception {
    if (! app.getContactHelper().isThereAContact()) {
      app.getNavigationHelper().gotoContactPage();
      app.getContactHelper().createContact(new ContactData("Ruslan", "Yuldashev", "Moscow", "8-495-716-45-78", "uldashev@inbox.ru", "test1"), true);
    }
    app.getContactHelper().selectContact();
    app.getContactHelper().editContact();
    app.getContactHelper().
            fillContactForm(new ContactData("ruslan_1", "yuldashev_1", "moscow_1", "8-495-111-22-33", "yuldashev@prime.ru", "test1"), false);
    app.getContactHelper().submitContactModification();
    app.getContactHelper().returnToContactPage();
  }

}
