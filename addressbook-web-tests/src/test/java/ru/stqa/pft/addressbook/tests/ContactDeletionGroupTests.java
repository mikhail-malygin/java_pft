package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.io.File;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ContactDeletionGroupTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        if (app.db().groups().size() == 0) {
            app.goTo().groupPage();
            app.group().create(new GroupData().withName("test1").withHeader("test1").withFooter("test1"));
            app.goTo().returnToHomePage();
        }
        if (app.db().contacts().size() == 0) {
            Groups groups = app.db().groups();
            app.goTo().gotoContactCreationPage();
            app.contact().create(new ContactData().withFirstName("Mikhail").withMiddleName("Sergeevich").
                            withLastName("Malygin").
                            withAddress("Russia, Testing region, Agile city, Jira str, appart: 47, 9").
                            withHomeNumber("8(343)9").withMobileNumber("799999999999").withWorkNumber("123-34").
                            withEmail("test.malygin@gmail.com").withEmail3("tes3t@mail.ru").
                            InGroup(groups.iterator().next()).withPhoto(new File("src/test/resources/stru.png")),
                    true, false);
            app.goTo().returnToHomePage();
        }
    }

    @Test
    public void testContactDeletionGroup() {
        Contacts contacts = app.db().contacts();
        ContactData contactBeforeDeletionGroup = contacts.iterator().next();
        for (ContactData contact : contacts) {
            if (contact.getGroups().size() != 0) {
                contactBeforeDeletionGroup = contact;
                break;
            }
        }
        if (contactBeforeDeletionGroup.getGroups().size() == 0) {
            Groups groups = app.db().groups();
            contactBeforeDeletionGroup = new ContactData().withFirstName("Mikhail").withMiddleName("Sergeevich").
                    withLastName("Malygin").withAddress("Russia, Testing region, Agile city, Jira str, appart: 47, 9").
                    withHomeNumber("8(3439)9").withMobileNumber("799999999999").withWorkNumber("123-45").
                    withEmail("test.malygin1@gmail.com").withEmail2("").withEmail3("test4@mail.ru").
                    InGroup(groups.iterator().next()).withPhoto(new File("src/test/resources/stru.png"));
            app.goTo().gotoContactCreationPage();
            app.contact().create(contactBeforeDeletionGroup.withId(
                    contacts.stream().mapToInt(ContactData::getId).max().getAsInt() + 1),true, false);
        }
        Groups groupBeforeDeletionInContact = app.db().gettingModifiedContactData(contactBeforeDeletionGroup.getId()).
                getGroups();
        int idGroupForDeletionContact = app.group().getIdGroupForDeletionContact(groupBeforeDeletionInContact);
        GroupData groupForDeletionContact = app.db().gettingModifiedGroupData(idGroupForDeletionContact);
        app.contact().deleteGroup(contactBeforeDeletionGroup, idGroupForDeletionContact);
        Groups groupAfterDeletionGroupInContact = app.db().gettingModifiedContactData(
                contactBeforeDeletionGroup.getId()).getGroups();
        assertThat(groupAfterDeletionGroupInContact,
                equalTo(groupBeforeDeletionInContact.without(groupForDeletionContact)));
    }
}

