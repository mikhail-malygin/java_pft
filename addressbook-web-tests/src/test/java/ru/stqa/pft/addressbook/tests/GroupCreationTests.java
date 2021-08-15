package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.*;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.*;

public class GroupCreationTests extends TestBase {

  @DataProvider
  public Iterator<Object[]> validGroups() throws IOException {
    List<Object[]> list = new ArrayList<Object[]>();
    BufferedReader reader = new BufferedReader(new FileReader("src/test/resources/groups.csv"));
    String line = reader.readLine();
    while (!line.equals("")) {
      String[] split = line.split(";");
      list.add (new Object[] {new GroupData().withName(split[0]).withHeader(split[1]).withFooter(split[2])});
      line = reader.readLine();
    }
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

  @Test(enabled = false)
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
