package ru.stqa.pft.addressbook.tests;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;
import org.testng.annotations.*;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

  @DataProvider
  public Iterator<Object[]> validContactsFromXml() throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader("src/test/resources/contacts.xml"))) {
      StringBuilder xml = new StringBuilder();
      String line = reader.readLine();
      while (line != null) {
        xml.append(line);
        line = reader.readLine();
      }
      XStream xstream = new XStream();
      xstream.processAnnotations(ContactData.class);
      List<ContactData> contacts = (List<ContactData>)xstream.fromXML(xml.toString());
      return contacts.stream().map((c) -> new Object[] {c}).collect(Collectors.toList()).iterator();
    }
  }

  @DataProvider
  public Iterator<Object[]> validContactsFromJson() throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader("src/test/resources/contacts.json"))) {
      StringBuilder json = new StringBuilder();
      String line = reader.readLine();
      while (line != null) {
        json.append(line);
        line = reader.readLine();
      }
      Gson gson = new Gson();
      // List<ContactData>.class
      List<ContactData> contacts = gson.fromJson(String.valueOf(json), new TypeToken<List<ContactData>>(){}.getType());
      return contacts.stream().map((c) -> new Object[] {c}).collect(Collectors.toList()).iterator();
    }
  }

  @BeforeMethod
  public void ensurePreconditions(GroupData groupData) {
    if (app.db().groups().size() == 0) {
      app.goTo().groupPage();
      app.group().create(groupData);
      app.goTo().returnToHomePage();
    }
  }

  @Test (dataProvider = "validContactsFromJson")
  public void testContactCreation(ContactData contactData) throws Exception {
    Groups groups = app.db().groups();
    File photo = new File("src/test/resources/stru.png");
    Contacts beforeContact = app.db().contacts();
    app.goTo().gotoContactCreationPage();
    app.contact().create(contactData.withPhoto(photo).InGroup(groups.iterator().next()), true, false);
    assertThat(app.contact().count(), equalTo(beforeContact.size() + 1));
    Contacts afterContact = app.db().contacts();
    assertThat(afterContact, equalTo(beforeContact.withAdded(
            contactData.withId(afterContact.stream().mapToInt(ContactData::getId).max().getAsInt()))));
    verifyContactListInUI();
  }

  @Test(enabled = false)
  public void testBadContactCreation() throws Exception {
    Contacts beforeContact = app.contact().all();
    app.goTo().gotoContactCreationPage();
    ContactData contact = new ContactData().withFirstName("Mikhail'").withMiddleName("Sergeevich").
            withLastName("Malygin").withAddress("Russia, Testing region, Agile city, Jira str, appart: 47, 9").
            withHomeNumber("8(343)9").withMobileNumber("799999999999").withWorkNumber("123-34").
            withEmail("test.malygin@gmail.com").withEmail3("tes3t@mail.ru").
            withPhoto(new File("src/test/resources/stru.png")).
            withEmail("test.malygin@gmail.com").withEmail3("tes3t@mail.ru");//.withGroup("test1");

    app.contact().create(contact, true, false);
    assertThat(app.contact().count(), equalTo(beforeContact.size()));
    Contacts afterContact = app.contact().all();
    assertThat(afterContact, equalTo(beforeContact));
  }
}

