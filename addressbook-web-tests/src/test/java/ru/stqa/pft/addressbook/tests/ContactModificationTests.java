package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactModificationTests extends TestBase {

    @Test
    public void testContactModification() {
        if (! app.getContactHelper().isThereAContact()) {
            app.getNavigationHelper().gotoContactCreationPage();
            app.getContactHelper().createContact(new ContactData("Mikhail", "Sergeevich", "Malygin",
                    "799999999999", "test.malygin@gmail.com", "test1"), true);
            app.getNavigationHelper().returnToHomePage();
        }
        int beforeContactGroup = app.getContactHelper().getContactCount();
        app.getContactHelper().initContactModification();
        app.getContactHelper().fillContactForm(new ContactData("Misha", "Sergeevich", "Malygin",
                "799999999998", "test.malygin@gmail.com", null), false);
        app.getContactHelper().submitContactModification();
        app.getNavigationHelper().returnToHomePage();
        int afterContactGroup = app.getContactHelper().getContactCount();
        Assert.assertEquals(afterContactGroup, beforeContactGroup);
    }
}
