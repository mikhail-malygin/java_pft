package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.*;

public class ContactModificationTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        if (app.contact().all().size() == 0) {
            app.goTo().gotoContactCreationPage();
            app.contact().create(new ContactData().withFirstName("Mikhail").withMiddleName("Sergeevich").
                    withLastName("Malygin").withNumber("799999999999").withEmail("test.malygin@gmail.com").
                    withGroup("test1"), true);
            app.goTo().returnToHomePage();
        }
    }

    @Test
    public void testContactModification() {
        Contacts beforeContact= app.contact().all();
        ContactData modifiedContact = beforeContact.iterator().next();
        ContactData contact = new ContactData().withId(modifiedContact.getId()).withFirstName("Misha").
                withMiddleName("Sergeevich").withLastName("Malygin").withNumber("799999999998").
                withEmail("test.malygin@gmail.com").withGroup(null);
        app.contact().modify(contact);
        app.goTo().returnToHomePage();
        assertThat(app.contact().count(), equalTo(beforeContact.size()));
        Contacts afterContact= app.contact().all();
        assertThat(afterContact, equalTo(beforeContact.without(modifiedContact).withAdded(contact)));
    }
}
