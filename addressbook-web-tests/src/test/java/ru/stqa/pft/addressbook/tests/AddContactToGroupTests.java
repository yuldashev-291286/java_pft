package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

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
    if (app.contact().checkContactsWithoutGroups()) {
      app.contact().createContactWithoutGroup(new ContactData()
              .withFirstname("Ivan").withLastname("Ivanov").withAddress("Moscow")
              .withTelephone("8-495-716-45-78").withEmail("Ivanov@mail.ru"));
    }
  }

  @Test
  public void testAddContactToGroup() {
    int countBefore = app.db().countAddressInGroups();
    app.contact().contactAddToGroup();
    int countAfter = app.db().countAddressInGroups();
    assertThat(countAfter, equalTo(countBefore + 1));
  }

}
