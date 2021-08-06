package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.*;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.List;

public class GroupCreationTests extends TestBase {

  @Test
  public void testGroupCreation() throws Exception {
    app.goTo().groupPage();
    List<GroupData> beforeGroup = app.group().list();
    GroupData groupData = new GroupData("test2", null, null);
    app.group().create(groupData);
    List<GroupData> afterGroup = app.group().list();
    Assert.assertEquals(afterGroup.size(), beforeGroup.size() + 1);

    beforeGroup.add(groupData);
    Comparator<? super GroupData> byId = Comparator.comparingInt(GroupData::getId);
    beforeGroup.sort(byId);
    afterGroup.sort(byId);
    Assert.assertEquals(beforeGroup, afterGroup);
  }
}
