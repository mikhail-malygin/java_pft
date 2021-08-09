package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Set;

public class ContactDeletionTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        if (app.contact().all().size() == 0) {
            app.goTo().gotoContactCreationPage();
            app.contact().create(new ContactData().withFirstName("Mikhail").withMiddleName("Sergeevich").
                    withLastName("Malygin").withNumber("799999999999").withEmail("test.malygin@gmail.com").
                    withGroup("test1"),true);
            app.goTo().returnToHomePage();
        }
    }

    @Test
    public void testContactDeletion() throws Exception {
        Set<ContactData> beforeContact= app.contact().all();
        ContactData deletedContact = beforeContact.iterator().next();
        app.contact().delete(deletedContact);
        Set<ContactData> afterContact= app.contact().all();
        Assert.assertEquals(afterContact.size(), beforeContact.size() - 1);

        beforeContact.remove(deletedContact);
        Assert.assertEquals(beforeContact, afterContact);

    }
}
