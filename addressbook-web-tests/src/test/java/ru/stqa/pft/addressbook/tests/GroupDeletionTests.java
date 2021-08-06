package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.*;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Set;

public class GroupDeletionTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    app.goTo().groupPage();
    if (app.group().all().size() == 0) {
      app.group().create(new GroupData().withName("test1"));
    }
  }

  @Test
  public void testGroupDeletion() throws Exception {
    Set<GroupData> beforeGroup = app.group().all();
    GroupData deletedGroup = beforeGroup.iterator().next();
    app.group().delete(deletedGroup);
    Set<GroupData> afterGroup = app.group().all();
    Assert.assertEquals(afterGroup.size(), beforeGroup.size() - 1);

    beforeGroup.remove(deletedGroup);
    Assert.assertEquals(beforeGroup, afterGroup);
  }
}
