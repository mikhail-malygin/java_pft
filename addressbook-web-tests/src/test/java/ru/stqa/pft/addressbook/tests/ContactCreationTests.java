package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.*;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.io.File;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

  @Test
  public void testContactCreation() throws Exception {
    Contacts beforeContact = app.contact().all();
    app.goTo().gotoContactCreationPage();
    File photo = new File("src/test/resources/stru.png");
    ContactData contact = new ContactData().withFirstName("Mikhail").withMiddleName("Sergeevich").
            withLastName("Malygin").withAddress("Russia, Testing region, Agile city, Jira str, appart: 47, 9").
            withHomeNumber("8(343)9").withMobileNumber("799999999999").withWorkNumber("123-34").
            withEmail("test.malygin@gmail.com").withEmail3("tes3t@mail.ru").withGroup("test1").withPhoto(photo);
    app.contact().create(contact, true, false);
    assertThat(app.contact().count(), equalTo(beforeContact.size() + 1));
    Contacts afterContact = app.contact().all();
    assertThat(afterContact, equalTo(beforeContact.withAdded(
            contact.withId(afterContact.stream().mapToInt(ContactData::getId).max().getAsInt()))));
  }

  @Test(enabled = false)
  public void testBadContactCreation() throws Exception {
    Contacts beforeContact = app.contact().all();
    app.goTo().gotoContactCreationPage();
    ContactData contact = new ContactData().withFirstName("Mikhail'").withMiddleName("Sergeevich").
            withLastName("Malygin").withAddress("Russia, Testing region, Agile city, Jira str, appart: 47, 9").
            withHomeNumber("8(343)9").withMobileNumber("799999999999").withWorkNumber("123-34").
            withEmail("test.malygin@gmail.com").withEmail3("tes3t@mail.ru").withGroup("test1");
    app.contact().create(contact, true, false);
    assertThat(app.contact().count(), equalTo(beforeContact.size()));
    Contacts afterContact = app.contact().all();
    assertThat(afterContact, equalTo(beforeContact));
  }
}

