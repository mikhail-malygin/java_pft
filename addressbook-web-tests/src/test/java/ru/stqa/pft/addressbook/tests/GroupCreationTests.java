package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.*;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Set;

public class GroupCreationTests extends TestBase {

  @Test
  public void testGroupCreation() throws Exception {
    app.goTo().groupPage();
    Set<GroupData> beforeGroup = app.group().all();
    GroupData groupData = new GroupData().withName("test2");
    app.group().create(groupData);
    Set<GroupData> afterGroup = app.group().all();
    Assert.assertEquals(afterGroup.size(), beforeGroup.size() + 1);

    groupData.withId(afterGroup.stream().mapToInt(GroupData::getId).max().getAsInt());
    beforeGroup.add(groupData);
    Assert.assertEquals(beforeGroup, afterGroup);
  }
}
