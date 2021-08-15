package ru.stqa.pft.addressbook.tests;

import com.thoughtworks.xstream.XStream;
import org.testng.annotations.*;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;

import java.io.*;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.*;

public class GroupCreationTests extends TestBase {

  @DataProvider
  public Iterator<Object[]> validGroups() throws IOException {
    BufferedReader reader = new BufferedReader(new FileReader("src/test/resources/groups.xml"));
    StringBuilder xml = new StringBuilder();
    String line = reader.readLine();
    while (line != null) {
      xml.append(line);
      line = reader.readLine();
    }
    XStream xstream = new XStream();
    xstream.processAnnotations(GroupData.class);
    List<GroupData> groups = (List<GroupData>)xstream.fromXML(xml.toString());
    return groups.stream().map((g) -> new Object[] {g}).collect(Collectors.toList()).iterator();
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
