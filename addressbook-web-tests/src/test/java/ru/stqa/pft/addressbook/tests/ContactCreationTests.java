package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.*;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Set;

public class ContactCreationTests extends TestBase {

  @Test
  public void testContactCreation() throws Exception {
    Set<ContactData> beforeContact= app.contact().all();
    app.goTo().gotoContactCreationPage();
    ContactData contact = new ContactData().withFirstName("Mikhail").withMiddleName("Sergeevich").
            withLastName("Malygin").withNumber("799999999999").withEmail("test.malygin@gmail.com").withGroup("test1");
    app.contact().create(contact, true);
    Set<ContactData> afterContact = app.contact().all();
    Assert.assertEquals(afterContact.size(), beforeContact.size() + 1);

    contact.withId(afterContact.stream().mapToInt(ContactData::getId).max().getAsInt());
    beforeContact.add(contact);
    Assert.assertEquals(beforeContact, afterContact);
  }
}
