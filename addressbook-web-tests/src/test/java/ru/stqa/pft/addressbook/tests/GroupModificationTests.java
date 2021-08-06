package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.List;

public class GroupModificationTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        app.goTo().groupPage();
        if (app.group().list().size() == 0) {
            app.group().create(new GroupData("test1", null, null));
        }
    }

    @Test
    public void testGroupModification() {
        List<GroupData> beforeGroup = app.group().list();
        int index = beforeGroup.size() - 1;
        GroupData groupData = new GroupData(beforeGroup.get(index).getId(), "test3", "test2", "test3");
        app.group().modify(index, groupData);
        List<GroupData> afterGroup = app.group().list();
        Assert.assertEquals(afterGroup.size(), beforeGroup.size());

        beforeGroup.remove(index);
        beforeGroup.add(groupData);
        Comparator<? super GroupData> byId = Comparator.comparingInt(GroupData::getId);
        beforeGroup.sort(byId);
        afterGroup.sort(byId);
        Assert.assertEquals(beforeGroup, afterGroup);
    }
}

