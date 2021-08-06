package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.List;

public class ContactDeletionTests extends TestBase {

    @Test
    public void testContactDeletion() throws Exception {
        if (! app.getContactHelper().isThereAContact()) {
            app.goTo().gotoContactCreationPage();
            app.getContactHelper().createContact(new ContactData("Mikhail", "Sergeevich",
                                                                 "Malygin",  "799999999999",
                                                                 "test.malygin@gmail.com", "test1"),
                                                                 true);
            app.goTo().returnToHomePage();
        }
        List<ContactData> beforeContact= app.getContactHelper().getContactList();
        app.getContactHelper().selectContact(beforeContact.size() - 1);
        app.getContactHelper().deleteContact();
        app.getContactHelper().submitContactDeletion();
        app.goTo().returnToHomePage();
        List<ContactData> afterContact= app.getContactHelper().getContactList();
        Assert.assertEquals(afterContact.size(), beforeContact.size() - 1);

        beforeContact.remove(beforeContact.size() - 1);
        Assert.assertEquals(beforeContact, afterContact);

    }
}
