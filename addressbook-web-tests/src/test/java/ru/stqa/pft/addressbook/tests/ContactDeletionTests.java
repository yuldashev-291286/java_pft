package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactDeletionTests extends TestBase {

  @Test
  public void testContactDeletion() throws Exception {
    if (! app.getContactHelper().isThereAContact()) {
      app.getNavigationHelper().gotoContactPage();
      app.getContactHelper().createContact(new ContactData("Ruslan", "Yuldashev", "Moscow", "8-495-716-45-78", "uldashev@inbox.ru", "test1"), true);
    }
    app.getContactHelper().selectContact();
    app.getContactHelper().deleteSelectedContacts();
    app.getContactHelper().assertConfirmation();
  }

}
