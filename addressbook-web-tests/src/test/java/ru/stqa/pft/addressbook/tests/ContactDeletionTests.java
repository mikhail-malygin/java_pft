package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactDeletionTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        if (app.contact().all().size() == 0) {
            app.goTo().gotoContactCreationPage();
            app.contact().create(new ContactData().withFirstName("Mikhail").withMiddleName("Sergeevich").
                    withLastName("Malygin").withHomeNumber("8(343)9").withMobileNumber("799999999999").
                    withWorkNumber("123-34").withEmail("test.malygin@gmail.com").withGroup("test1"),true);
            app.goTo().returnToHomePage();
        }
    }

    @Test
    public void testContactDeletion() throws Exception {
        Contacts beforeContact= app.contact().all();
        ContactData deletedContact = beforeContact.iterator().next();
        app.contact().delete(deletedContact);
        assertThat(app.contact().count(), equalTo(beforeContact.size() - 1));
        Contacts afterContact= app.contact().all();
        assertThat(afterContact, equalTo(beforeContact.without(deletedContact)));
    }
}
