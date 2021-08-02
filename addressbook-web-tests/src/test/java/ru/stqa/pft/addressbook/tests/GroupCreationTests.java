package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.*;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

public class GroupCreationTests extends TestBase {

  @Test
  public void testGroupCreation() throws Exception {
    app.getNavigationHelper().gotoGrouppage();
    List<GroupData> beforeGroup = app.getGroupHelper().getGroupList();
    GroupData groupData = new GroupData("test2", null, null);
    app.getGroupHelper().createGroup(groupData);
    List<GroupData> afterGroup = app.getGroupHelper().getGroupList();
    Assert.assertEquals(afterGroup.size(), beforeGroup.size() + 1);

    beforeGroup.add(groupData);
    Comparator<? super GroupData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
    beforeGroup.sort(byId);
    afterGroup.sort(byId);
    Assert.assertEquals(beforeGroup, afterGroup);
  }
}
