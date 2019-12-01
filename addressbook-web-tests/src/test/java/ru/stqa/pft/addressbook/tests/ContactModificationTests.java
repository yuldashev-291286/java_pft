package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactModificationTests extends TestBase {

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
  }

  @Test
  public void testContactModification() throws Exception {
    Groups groups = app.db().groups();
    Contacts before = app.db().contacts();
    ContactData modifiedContact = before.iterator().next();
    ContactData contact = new ContactData().withId(modifiedContact.getId()).withFirstname("Ivan").withLastname("Ivanov")
            .withAddress("Moscow").withTelephone("8-495-716-45-78").withEmail("Ivanov@mail.ru")
            .inGroup(groups.iterator().next());
    app.contact().modify(modifiedContact.getId(), contact);
    assertThat(app.contact().count(), equalTo(before.size()));
    Contacts after = app.db().contacts();
    assertThat(after, equalTo(before.without(modifiedContact).withAdded(contact)));
  }

}
