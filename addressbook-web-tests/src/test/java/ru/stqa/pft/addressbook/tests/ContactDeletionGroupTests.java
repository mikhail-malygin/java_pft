package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.io.File;

import static org.testng.Assert.assertNotEquals;

public class ContactDeletionGroupTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        if (app.db().contacts().size() == 0) {
            app.goTo().gotoContactCreationPage();
            File photo = new File("src/test/resources/stru.png");
            app.contact().create(new ContactData().withFirstName("Mikhail").withMiddleName("Sergeevich").
                            withLastName("Malygin").withAddress("Russia, Testing region, Agile city, Jira str, appart: 47, 9").
                            withHomeNumber("8(343)9").withMobileNumber("799999999999").withWorkNumber("123-34").
                            withEmail("test.malygin@gmail.com").withEmail3("tes3t@mail.ru").withPhoto(photo),
                    true, false);
            app.goTo().returnToHomePage();
        }
        if (app.db().groups().size() == 0) {
            app.goTo().groupPage();
            app.group().create(new GroupData().withName("test1").withHeader("test1").withFooter("test1"));
            app.goTo().returnToHomePage();
        }
    }

    @Test
    public void testContactDeletionGroup() {
        ContactData contactPreparingToTest = app.db().contacts().iterator().next();
        if (contactPreparingToTest.getGroups().size() == 0) {
            app.contact().addGroup(contactPreparingToTest);
        }
        ContactData contactBeforeDeletionGroup = app.db().gettingModifiedContactData(contactPreparingToTest.getId());
        Groups groupBeforeDeletionGroupInContact = app.db().gettingModifiedContactData(
                contactBeforeDeletionGroup.getId()).getGroups();
        app.contact().deleteGroup(contactBeforeDeletionGroup);
        Groups groupAfterDeletionGroupInContact = app.db().gettingModifiedContactData(contactBeforeDeletionGroup.getId()).
                getGroups();
        assertNotEquals(groupAfterDeletionGroupInContact, groupBeforeDeletionGroupInContact);
    }
}

