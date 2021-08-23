package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.io.File;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.*;

public class ContactModificationTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        if (app.db().contacts().size() == 0) {
            app.goTo().gotoContactCreationPage();
            app.contact().create(new ContactData().withFirstName("Mikhail").withMiddleName("Sergeevich").
                    withLastName("Malygin").withAddress("Russia, Testing region, Agile city, Jira str, appart: 47, 9").
                    withHomeNumber("8(343)9").withMobileNumber("799999999999").withWorkNumber("123-34").
                    withEmail("test.malygin@gmail.com").withEmail3("tes3t@mail.ru").withGroup("test1"),
                    true, false);
            app.goTo().returnToHomePage();
        }
    }

    @Test
    public void testContactModification() {
        Contacts beforeContact= app.db().contacts();
        ContactData modifiedContact = beforeContact.iterator().next();
        ContactData contact = new ContactData().withId(modifiedContact.getId()).withFirstName("Misha").
                withMiddleName("Sergeevich").withLastName("Malygin").
                withAddress("Russia, Testing region, Agile city, Jira str, appart: 47, 9").
                withHomeNumber("8(3439)9").withMobileNumber("799999999998").withWorkNumber("123-45").
                withEmail("test.malygin1@gmail.com").withEmail2("").withEmail3("test4@mail.ru").withGroup(null).
                withPhoto(new File("src/test/resources/stru.png"));
        app.contact().modify(contact);
        app.goTo().returnToHomePage();
        assertThat(app.contact().count(), equalTo(beforeContact.size()));
        Contacts afterContact= app.db().contacts();
        assertThat(afterContact, equalTo(beforeContact.without(modifiedContact).withAdded(contact)));
    }
}
