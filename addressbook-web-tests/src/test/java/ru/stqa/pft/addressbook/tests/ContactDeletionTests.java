package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.List;

public class ContactDeletionTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        if (! app.getContactHelper().isThereAContact()) {
            app.goTo().gotoContactCreationPage();
            app.getContactHelper().createContact(new ContactData("Mikhail", "Sergeevich",
                            "Malygin", "799999999999","test.malygin@gmail.com", "test1"),
                            true);
            app.goTo().returnToHomePage();
        }
    }

    @Test
    public void testContactDeletion() throws Exception {
        List<ContactData> beforeContact= app.getContactHelper().getContactList();
        int index = beforeContact.size() - 1;
        app.getContactHelper().selectContact(index);
        app.getContactHelper().deleteContact();
        app.getContactHelper().submitContactDeletion();
        app.goTo().returnToHomePage();
        List<ContactData> afterContact= app.getContactHelper().getContactList();
        Assert.assertEquals(afterContact.size(), beforeContact.size() - 1);

        beforeContact.remove(index);
        Assert.assertEquals(beforeContact, afterContact);

    }
}
