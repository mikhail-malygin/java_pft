package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;

public class ContactModificationTests extends TestBase {

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
    public void testContactModification() {
        List<ContactData> beforeContact= app.getContactHelper().getContactList();
        int index = beforeContact.size() - 1;
        ContactData contact = new ContactData(beforeContact.get(index).getId(),"Misha","Sergeevich",
                "Malygin","799999999998","test.malygin@gmail.com", null);
        app.getContactHelper().modifiedContact(index, contact);
        app.goTo().returnToHomePage();
        List<ContactData> afterContact= app.getContactHelper().getContactList();
        Assert.assertEquals(afterContact.size(), beforeContact.size());

        beforeContact.remove(index);
        beforeContact.add(contact);
        Comparator<? super ContactData> byId = Comparator.comparingInt(ContactData::getId);
        beforeContact.sort(byId);
        afterContact.sort(byId);
        Assert.assertEquals(beforeContact, afterContact);
    }
}
