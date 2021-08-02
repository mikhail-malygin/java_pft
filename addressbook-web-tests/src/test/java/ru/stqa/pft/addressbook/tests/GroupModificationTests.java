package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

public class GroupModificationTests extends TestBase{

    @Test
    public void testGroupModification() {
        app.getNavigationHelper().gotoGrouppage();
        if (! app.getGroupHelper().isThereAGroup()) {
            app.getGroupHelper().createGroup(new GroupData("test1", null, null));
        }
        List<GroupData> beforeGroup = app.getGroupHelper().getGroupList();
        app.getGroupHelper().selectGroup(beforeGroup.size() - 1);
        app.getGroupHelper().initGroupModification();
        GroupData groupData = new GroupData(beforeGroup.get(beforeGroup.size() - 1).getId(), "test2",
                                     "test2", "test3");
        app.getGroupHelper().fillGroupForm(groupData);
        app.getGroupHelper().submitGroupModification();
        app.getGroupHelper().returnToGroupPage();
        List<GroupData> afterGroup = app.getGroupHelper().getGroupList();
        Assert.assertEquals(afterGroup.size(), beforeGroup.size());

        beforeGroup.remove(beforeGroup.size() - 1);
        beforeGroup.add(groupData);
        Comparator<? super GroupData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
        beforeGroup.sort(byId);
        afterGroup.sort(byId);
        Assert.assertEquals(beforeGroup, afterGroup);
    }
}
