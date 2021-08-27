package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.stream.Collectors;

public class ContactAddingGroupTests extends TestBase{

    @BeforeMethod
    public void ensurePreconditions() {
        if (app.db().groups().size() == 0) {
            app.goTo().groupPage();
            app.group().create(new GroupData().withName("test1").withHeader("test1").withFooter("test1"));
            app.goTo().returnToHomePage();
        }
    }

    @Test
    public void testContactAddingGroup() {
        ContactData contactBeforeAddingGroup = app.db().contacts().iterator().next();
        System.out.println("Check before: " +  contactBeforeAddingGroup.getGroups());
        if (contactBeforeAddingGroup.getGroups().size() == 0) {
            app.contact().addGroup(contactBeforeAddingGroup);
        } else {
            app.contact().DeleteGroup(contactBeforeAddingGroup);
            app.contact().addGroup(contactBeforeAddingGroup);
        }
        System.out.println("Check before: " +  contactBeforeAddingGroup.getGroups());
    }
}
