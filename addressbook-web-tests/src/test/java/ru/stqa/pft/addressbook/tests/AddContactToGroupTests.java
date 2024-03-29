package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class AddContactToGroupTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    app.goTo().groupPage();
    if (app.db().groups().size() == 0) {
      app.group().create(new GroupData().withName("test1"));
      app.goTo().home();
    } else {
      app.goTo().home();
    }
    if (app.db().contacts().size() == 0) {
      Groups groups = app.db().groups();
      app.contact().create(new ContactData()
              .withFirstname("Ivan").withLastname("Ivanov").withAddress("Moscow")
              .withTelephone("8-495-716-45-78").withEmail("Ivanov@mail.ru")
              .inGroup(groups.iterator().next()), true);
    }
    if (app.contact().сontactsWithoutAGroups()) {
      app.contact().createContactWithoutAGroup(new ContactData()
              .withFirstname("Ivan").withLastname("Ivanov").withAddress("Moscow")
              .withTelephone("8-495-716-45-78").withEmail("Ivanov@mail.ru"));
    }
  }

  @Test
  public void testAddContactToGroup() {
    Contacts beforeContacts = app.db().contacts();
    Groups groups = app.db().groups();
    int idContact = 0;
    String nameGroup = null;
    Groups beforeGroups = null;
    GroupData addGroup = null;
    int beforeNumberOfGroups = 0;
    for (ContactData contact : beforeContacts) {
      if (contact.getGroups().size() == 0) {
        idContact = contact.getId();
        beforeGroups = contact.getGroups();
        nameGroup = groups.iterator().next().getName();
        addGroup = groups.iterator().next();
        beforeNumberOfGroups = beforeGroups.size();
        break;
      }
    }
    app.contact().addContactFromGroup(idContact, nameGroup);

    Groups afterGroups = null;
    int afterNumberOfGroups = 0;
    Contacts afterContacts = app.db().contacts();
    for (ContactData contact : afterContacts) {
      if (contact.getId() == idContact) {
        afterGroups = contact.getGroups();
        afterNumberOfGroups = afterGroups.size();
      }
    }

    assertThat(afterNumberOfGroups, equalTo(beforeNumberOfGroups + 1));
    assertThat(afterGroups, equalTo(beforeGroups.withAdded(addGroup)));
  }

}
