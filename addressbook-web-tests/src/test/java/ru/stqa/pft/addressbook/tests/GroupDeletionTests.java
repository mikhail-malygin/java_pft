package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.*;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.List;

public class GroupDeletionTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    app.goTo().groupPage();
    if (app.group().list().size() == 0) {
      app.group().create(new GroupData().withName("test1"));
    }
  }

  @Test
  public void testGroupDeletion() throws Exception {
    List<GroupData> beforeGroup = app.group().list();
    int index = beforeGroup.size() - 1;
    app.group().delete(index);
    List<GroupData> afterGroup = app.group().list();
    Assert.assertEquals(afterGroup.size(), beforeGroup.size() - 1);

    beforeGroup.remove(index);
    Assert.assertEquals(beforeGroup, afterGroup);
  }
}
