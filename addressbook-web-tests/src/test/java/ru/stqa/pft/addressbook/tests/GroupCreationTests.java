package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.*;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.HashSet;
import java.util.List;

public class GroupCreationTests extends TestBase {

  @Test
  public void testGroupCreation() throws Exception {
    app.getNavigationHelper().gotoGrouppage();
    List<GroupData> beforeGroup = app.getGroupHelper().getGroupList();
    GroupData groupData = new GroupData("test1", null, null);
    app.getGroupHelper().createGroup(groupData);
    List<GroupData> afterGroup = app.getGroupHelper().getGroupList();
    Assert.assertEquals(afterGroup.size(), beforeGroup.size() + 1);

    int max = 0;
    for (GroupData g : afterGroup) {
      if (g.getId() > max) {
        max = g.getId();
      }
    }
    groupData.setId(max);
    beforeGroup.add(groupData);
    Assert.assertEquals(new HashSet<Object>(beforeGroup), new HashSet<Object>(afterGroup));
  }
}
