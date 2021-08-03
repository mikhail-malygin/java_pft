package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.*;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase {

  @Test
  public void testContactCreation() throws Exception {
    int beforeContactGroup = app.getContactHelper().getContactCount();
    app.getNavigationHelper().gotoContactCreationPage();
    app.getContactHelper().createContact(new ContactData("Mikhail", "Sergeevich", "Malygin",
            "799999999999", "test.malygin@gmail.com", "test1"), true);
    app.getNavigationHelper().returnToHomePage();
    int afterContactGroup = app.getContactHelper().getContactCount();
    Assert.assertEquals(afterContactGroup, beforeContactGroup + 1);
  }
}
