package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.*;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class RemoveContactFromGroupTests extends TestBase {

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
    Groups groups = app.db().groups();
    String nameGroup = null;
    for (GroupData group: groups) {
      nameGroup = groups.iterator().next().getName();
      if (app.contact().contactsInGroups(nameGroup)) {
        app.contact().create(new ContactData()
                .withFirstname("Ivan").withLastname("Ivanov").withAddress("Moscow")
                .withTelephone("8-495-716-45-78").withEmail("Ivanov@mail.ru")
                .inGroup(groups.iterator().next()), true);
      }
    }
  }

  @Test
  public void testRemoveContactFromGroup() {
    Contacts beforeContacts = app.db().contacts();
    int idContact = 0;
    String nameGroup = null;
    Groups beforeGroups = null;
    GroupData removeGroup = null;
    int beforeNumberOfGroups = 0;
    for (ContactData contact : beforeContacts) {
      if (contact.getGroups().size() != 0) {
        idContact = contact.getId();
        beforeGroups = contact.getGroups();
        nameGroup = beforeGroups.iterator().next().getName();
        removeGroup = beforeGroups.iterator().next();
        beforeNumberOfGroups = beforeGroups.size();
        break;
      }
    }
    app.contact().removeContactFromGroup(idContact, nameGroup);

    Groups afterGroups = null;
    int afterNumberOfGroups = 0;
    Contacts afterContacts = app.db().contacts();
    for (ContactData contact : afterContacts) {
      if (contact.getId() == idContact) {
        afterGroups = contact.getGroups();
        afterNumberOfGroups = afterGroups.size();
      }
    }

    assertThat(afterNumberOfGroups, equalTo(beforeNumberOfGroups - 1));
    assertThat(afterGroups, equalTo(beforeGroups.withOut(removeGroup)));
  }

}
