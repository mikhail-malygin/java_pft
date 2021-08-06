package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.List;

public class ContactDeletionTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        if (app.contact().list().size() == 0) {
            app.goTo().gotoContactCreationPage();
            app.contact().create(new ContactData().withFirstName("Mikhail").withMiddleName("Sergeevich").
                    withLastName("Malygin").withNumber("799999999999").withEmail("test.malygin@gmail.com").
                    withGroup("test1"),true);
            app.goTo().returnToHomePage();
        }
    }

    @Test
    public void testContactDeletion() throws Exception {
        List<ContactData> beforeContact= app.contact().list();
        int index = beforeContact.size() - 1;
        app.contact().delete(index);
        List<ContactData> afterContact= app.contact().list();
        Assert.assertEquals(afterContact.size(), beforeContact.size() - 1);

        beforeContact.remove(index);
        Assert.assertEquals(beforeContact, afterContact);

    }
}
