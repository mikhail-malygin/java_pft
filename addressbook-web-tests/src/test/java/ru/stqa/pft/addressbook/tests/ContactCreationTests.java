package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.*;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.HashSet;
import java.util.List;

public class ContactCreationTests extends TestBase {

  @Test
  public void testContactCreation() throws Exception {
    List<ContactData> beforeContact= app.getContactHelper().getContactList();
    app.getNavigationHelper().gotoContactCreationPage();
    ContactData contact = new ContactData("Mikhail", "Sergeevich", "Malygin",
            "799999999999", "test.malygin@gmail.com", "test1");
    app.getContactHelper().createContact(contact, true);
    app.getNavigationHelper().returnToHomePage();
    List<ContactData> afterContact = app.getContactHelper().getContactList();
    Assert.assertEquals(afterContact.size(), beforeContact.size() + 1);

    int max = 0;
    for (ContactData c : afterContact) {
      if (c.getId() > max) {
        max = c.getId();
      }
    }
    contact.setId(max);
    beforeContact.add(contact);
    Assert.assertEquals(new HashSet<>(beforeContact), new HashSet<>(afterContact));
  }
}
