package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.*;
import ru.stqa.pft.addressbook.model.GroupData;

public class GroupDeletionTests extends TestBase {

  @Test
  public void testGroupDeletion() throws Exception {
    app.getNavigationHelper().gotoGrouppage();
    if (! app.getGroupHelper().isThereAGroup()) {
      app.getGroupHelper().createGroup(new GroupData("test1", null, null));
    }
    int beforeGroupCount = app.getGroupHelper().getGroupCount();
    app.getGroupHelper().selectGroup(beforeGroupCount - 1);
    app.getGroupHelper().deleteSelectedGroups();
    app.getGroupHelper().returnToGroupPage();
    int afterGroupCount = app.getGroupHelper().getGroupCount();
    Assert.assertEquals(afterGroupCount, beforeGroupCount - 1);
  }
}
