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
        if (app.contact().list().size() == 0) {
            app.goTo().gotoContactCreationPage();
            app.contact().create(new ContactData().withFirstName("Mikhail").withMiddleName("Sergeevich").
                    withLastName("Malygin").withNumber("799999999999").withEmail("test.malygin@gmail.com").
                    withGroup("test1"), true);
            app.goTo().returnToHomePage();
        }
    }

    @Test
    public void testContactModification() {
        List<ContactData> beforeContact= app.contact().list();
        int index = beforeContact.size() - 1;
        ContactData contact = new ContactData().withId(beforeContact.get(index).getId()).withFirstName("Misha").
                withMiddleName("Sergeevich").withLastName("Malygin").withNumber("799999999998").
                withEmail("test.malygin@gmail.com").withGroup(null);
        app.contact().modify(index, contact);
        app.goTo().returnToHomePage();
        List<ContactData> afterContact= app.contact().list();
        Assert.assertEquals(afterContact.size(), beforeContact.size());

        beforeContact.remove(index);
        beforeContact.add(contact);
        Comparator<? super ContactData> byId = Comparator.comparingInt(ContactData::getId);
        beforeContact.sort(byId);
        afterContact.sort(byId);
        Assert.assertEquals(beforeContact, afterContact);
    }
}
