package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.*;
import ru.stqa.pft.addressbook.model.GroupData;

public class GroupCreationTests extends TestBase {

  @Test
  public void testGroupCreation() throws Exception {
    app.getNavigationHelper().gotoGrouppage();
    int beforeGroupCount = app.getGroupHelper().getGroupCount();
    app.getGroupHelper().createGroup(new GroupData("test1", null, null));
    int afterGroupCount = app.getGroupHelper().getGroupCount();
    Assert.assertEquals(afterGroupCount, beforeGroupCount + 1);
  }
}
