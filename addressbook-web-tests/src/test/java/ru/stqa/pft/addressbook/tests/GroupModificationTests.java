package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupModificationTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        if (app.db().groups().size() == 0) {
            app.goTo().groupPage();
            app.group().create(new GroupData().withName("test1"));
        }
    }

    @Test
    public void testGroupModification() {
        Groups beforeGroup = app.db().groups();
        GroupData modifiedGroup = beforeGroup.iterator().next();
        GroupData groupData = new GroupData().
                withId(modifiedGroup.getId()).withName("test3").withHeader("test2").withFooter("test3");
        app.goTo().groupPage();
        app.group().modify(groupData);
        Assert.assertEquals(app.group().count(), beforeGroup.size());
        Groups afterGroup = app.db().groups();
        assertThat(afterGroup, equalTo(beforeGroup.without(modifiedGroup).withAdded(groupData)));
    }
}

