package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.*;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.*;

public class GroupCreationTests extends TestBase {

  @DataProvider
  public Iterator<Object[]> validGroups() {
    List<Object[]> list = new ArrayList<Object[]>();
    list.add(new Object[] {new GroupData().withName("test 1").withHeader("header 1").withFooter("footer 1")});
    list.add(new Object[] {new GroupData().withName("test 2").withHeader("header 2").withFooter("footer 2")});
    list.add(new Object[] {new GroupData().withName("test 3").withHeader("header 3").withFooter("footer 3")});
    return list.iterator();
  }

  @Test (dataProvider = "validGroups")
  public void testGroupCreation(GroupData groupData) throws Exception {
    app.goTo().groupPage();
    Groups beforeGroup = app.group().all();
    app.group().create(groupData);
    assertThat(app.group().count(), equalTo(beforeGroup.size() + 1));
    Groups afterGroup = app.group().all();
    assertThat(afterGroup, equalTo(
            beforeGroup.withAdded(groupData.withId(afterGroup.stream().mapToInt(GroupData::getId).max().getAsInt()))));
  }

  @Test
  public void testBadGroupCreation() throws Exception {
    app.goTo().groupPage();
    Groups beforeGroup = app.group().all();
    GroupData groupData = new GroupData().withName("test4'");
    app.group().create(groupData);
    assertThat(app.group().count(), equalTo(beforeGroup.size()));
    Groups afterGroup = app.group().all();
    assertThat(afterGroup, equalTo(beforeGroup));
  }
}
