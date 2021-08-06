package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.*;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.*;

public class GroupCreationTests extends TestBase {

  @Test
  public void testGroupCreation() throws Exception {
    app.goTo().groupPage();
    Groups beforeGroup = app.group().all();
    GroupData groupData = new GroupData().withName("test1");
    app.group().create(groupData);
    Groups afterGroup = app.group().all();
    assertThat(afterGroup.size(), equalTo(beforeGroup.size() + 1));
    assertThat(afterGroup, equalTo(
            beforeGroup.withAdded(groupData.withId(afterGroup.stream().mapToInt(GroupData::getId).max().getAsInt()))));
  }
}
