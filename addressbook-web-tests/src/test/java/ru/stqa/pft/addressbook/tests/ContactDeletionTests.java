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
        if (app.db().contacts().size() == 0) {
            app.goTo().gotoContactCreationPage();
            app.contact().create(new ContactData().withFirstName("Mikhail").withMiddleName("Sergeevich").
                    withLastName("Malygin").withAddress("Russia, Testing region, Agile city, Jira str, appart: 47, 9").
                    withHomeNumber("8(343)9").withMobileNumber("799999999999").withWorkNumber("123-34").
                    withEmail("test.malygin@gmail.com").withEmail3("tes3t@mail.ru").withGroup("test1"),
                    true, false);
            app.goTo().returnToHomePage();
            verifyContactListInUI();
        }
    }

    @Test
    public void testContactDeletion() throws Exception {
        Contacts beforeContact= app.db().contacts();
        ContactData deletedContact = beforeContact.iterator().next();
        app.contact().delete(deletedContact);
        assertThat(app.contact().count(), equalTo(beforeContact.size() - 1));
        Contacts afterContact= app.db().contacts();
        assertThat(afterContact, equalTo(beforeContact.without(deletedContact)));
    }
}
