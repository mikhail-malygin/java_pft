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
        app.goTo().groupPage();
        if (app.group().all().size() == 0) {
            app.group().create(new GroupData().withName("test1"));
        }
    }

    @Test
    public void testGroupModification() {
        Groups beforeGroup = app.group().all();
        GroupData modifiedGroup = beforeGroup.iterator().next();
        GroupData groupData = new GroupData().
                withId(modifiedGroup.getId()).withName("test3").withHeader("test2").withFooter("test3");
        app.group().modify(groupData);
        Groups afterGroup = app.group().all();
        Assert.assertEquals(afterGroup.size(), beforeGroup.size());
        assertThat(afterGroup, equalTo(beforeGroup.without(modifiedGroup).withAdded(groupData)));
    }
}

