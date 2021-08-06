package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;

public class ContactModificationTests extends TestBase {

    @Test
    public void testContactModification() {
        if (! app.getContactHelper().isThereAContact()) {
            app.goTo().gotoContactCreationPage();
            app.getContactHelper().createContact(new ContactData("Mikhail", "Sergeevich",
                                                                 "Malygin", "799999999999",
                                                                 "test.malygin@gmail.com", "test1"),
                                                                 true);
            app.goTo().returnToHomePage();
        }
        List<ContactData> beforeContact= app.getContactHelper().getContactList();
        app.getContactHelper().initContactModification(beforeContact.size() - 1);
        ContactData contact = new ContactData(beforeContact.get(beforeContact.size() - 1).getId(),"Misha",
                                             "Sergeevich", "Malygin","799999999998",
                                             "test.malygin@gmail.com", null);
        app.getContactHelper().fillContactForm(contact, false);
        app.getContactHelper().submitContactModification();
        app.goTo().returnToHomePage();
        List<ContactData> afterContact= app.getContactHelper().getContactList();
        Assert.assertEquals(afterContact.size(), beforeContact.size());

        beforeContact.remove(beforeContact.size() - 1);
        beforeContact.add(contact);
        Comparator<? super ContactData> byId = Comparator.comparingInt(ContactData::getId);
        beforeContact.sort(byId);
        afterContact.sort(byId);
        Assert.assertEquals(beforeContact, afterContact);
    }
}
