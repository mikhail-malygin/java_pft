package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.*;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;

public class ContactCreationTests extends TestBase {

  @Test
  public void testContactCreation() throws Exception {
    List<ContactData> beforeContact= app.contact().list();
    app.goTo().gotoContactCreationPage();
    ContactData contact = new ContactData().withFirstName("Mikhail").withMiddleName("Sergeevich").
            withLastName("Malygin").withNumber("799999999999").withEmail("test.malygin@gmail.com").withGroup("test1");
    app.contact().create(contact, true);
    List<ContactData> afterContact = app.contact().list();
    Assert.assertEquals(afterContact.size(), beforeContact.size() + 1);

    contact.withId(afterContact.stream().max(Comparator.comparingInt(ContactData::getId)).get().getId());
    beforeContact.add(contact);
    Comparator<? super ContactData> byId = Comparator.comparingInt(ContactData::getId);
    beforeContact.sort(byId);
    afterContact.sort(byId);
    Assert.assertEquals(beforeContact, afterContact);
  }
}
