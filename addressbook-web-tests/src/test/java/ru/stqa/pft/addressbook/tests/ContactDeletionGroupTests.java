package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;


public class ContactDeletionGroupTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        if (app.db().groups().size() == 0) {
            app.goTo().groupPage();
            app.group().create(new GroupData().withName("test1").withHeader("test1").withFooter("test1"));
            app.goTo().returnToHomePage();
        }
    }

    @Test
    public void testContactDeletionGroup() {
        ContactData contactAddingGroup = app.db().contacts().iterator().next();
        if (contactAddingGroup.getGroups().size() == 0) {
            app.contact().addGroup(contactAddingGroup);
            app.contact().DeleteGroup(contactAddingGroup);
        } else {
            app.contact().DeleteGroup(contactAddingGroup);
        }
    }
}
