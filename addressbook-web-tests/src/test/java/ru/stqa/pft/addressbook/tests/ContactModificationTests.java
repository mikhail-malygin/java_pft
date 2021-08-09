package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Set;

public class ContactModificationTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        if (app.contact().all().size() == 0) {
            app.goTo().gotoContactCreationPage();
            app.contact().create(new ContactData().withFirstName("Mikhail").withMiddleName("Sergeevich").
                    withLastName("Malygin").withNumber("799999999999").withEmail("test.malygin@gmail.com").
                    withGroup("test1"), true);
            app.goTo().returnToHomePage();
        }
    }

    @Test
    public void testContactModification() {
        Set<ContactData> beforeContact= app.contact().all();
        ContactData modifiedContact = beforeContact.iterator().next();
        ContactData contact = new ContactData().withId(modifiedContact.getId()).withFirstName("Misha").
                withMiddleName("Sergeevich").withLastName("Malygin").withNumber("799999999998").
                withEmail("test.malygin@gmail.com").withGroup(null);
        app.contact().modify(contact);
        app.goTo().returnToHomePage();
        Set<ContactData> afterContact= app.contact().all();
        Assert.assertEquals(afterContact.size(), beforeContact.size());

        beforeContact.remove(modifiedContact);
        beforeContact.add(contact);
        Assert.assertEquals(beforeContact, afterContact);
    }
}
