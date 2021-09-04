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

public class ContactAddingGroupTests extends TestBase{

    @BeforeMethod
    public void ensurePreconditions() {
        if (app.db().contacts().size() == 0) {
            app.goTo().gotoContactCreationPage();
            File photo = new File("src/test/resources/stru.png");
            app.contact().create(new ContactData().withFirstName("Mikhail").withMiddleName("Sergeevich").
                            withLastName("Malygin").
                            withAddress("Russia, Testing region, Agile city, Jira str, appart: 47, 9").
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
    public void testContactAddingGroup() {
        Contacts contacts = app.db().contacts();
        ContactData contactBeforeAddingGroup = contacts.iterator().next();
        for (ContactData contact : contacts) {
            if (contact.getGroups().size() == 0) {
                contactBeforeAddingGroup = contact;
                break;
            }
        }
        if (contactBeforeAddingGroup.getGroups().size() != 0) {
            contactBeforeAddingGroup = new ContactData().withFirstName("Mikhail").withMiddleName("Sergeevich").
                    withLastName("Malygin").withAddress("Russia, Testing region, Agile city, Jira str, appart: 47, 9").
                    withHomeNumber("8(3439)9").withMobileNumber("799999999999").withWorkNumber("123-45").
                    withEmail("test.malygin1@gmail.com").withEmail2("").withEmail3("test4@mail.ru").
                    withPhoto(new File("src/test/resources/stru.png"));
            app.goTo().gotoContactCreationPage();
            app.contact().create(contactBeforeAddingGroup.withId(
                    contacts.stream().mapToInt(ContactData::getId).max().getAsInt() + 1),true, false);
        }
        Groups groupBeforeAddingGroupInContact = app.db().gettingModifiedContactData(contactBeforeAddingGroup.getId()).
                getGroups();
        GroupData groupForAddingContact = app.db().groups().iterator().next();
        String idGroupForAddingContact = String.valueOf(groupForAddingContact.getId());
        app.contact().addGroup(contactBeforeAddingGroup, idGroupForAddingContact);
        Groups groupAfterAddingGroupInContact = app.db().gettingModifiedContactData(contactBeforeAddingGroup.getId()).
                getGroups();
        assertThat(groupAfterAddingGroupInContact,
                equalTo(groupBeforeAddingGroupInContact.withAdded(groupForAddingContact)));
    }
}
