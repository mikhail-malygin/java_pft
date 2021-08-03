package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactDeletionTests extends TestBase {

    @Test
    public void testContactDeletion() throws Exception {
        if (! app.getContactHelper().isThereAContact()) {
            app.getNavigationHelper().gotoContactCreationPage();
            app.getContactHelper().createContact(new ContactData("Mikhail", "Sergeevich", "Malygin",
                    "799999999999", "test.malygin@gmail.com", "test1"), true);
            app.getNavigationHelper().returnToHomePage();
        }
        int beforeContactGroup = app.getContactHelper().getContactCount();
        app.getContactHelper().selectContact(beforeContactGroup - 1);
        app.getContactHelper().deleteContact();
        app.getContactHelper().submitContactDeletion();
        app.getNavigationHelper().returnToHomePage();
        int afterContactGroup = app.getContactHelper().getContactCount();
        Assert.assertEquals(afterContactGroup, beforeContactGroup - 1);
    }
}
