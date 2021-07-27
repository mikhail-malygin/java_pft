package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactHelper extends HelperBase {

    public ContactHelper(WebDriver wd) {
        super(wd);
    }

    public void fillContactForm(ContactData contactData, boolean creation) {
      type(By.name("firstname"),contactData.getFirstName());
      type(By.name("middlename"),contactData.getMiddleName());
      type(By.name("lastname"),contactData.getLastName());
      type(By.name("home"),contactData.getNumber());
      type(By.name("email"),contactData.getEmail());

      if (creation) {
          new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroup());
      } else {
          Assert.assertFalse(isElementPresent(By.name("new_group")));
      }
    }

    public void submitContactForm() {
      click(By.xpath("//div[@id='content']/form/input[21]"));
    }

    public void initContactModification() {
        click(By.cssSelector("#container #maintable .center a img[title=Edit]"));
    }

    public void submitContactModification() {
        click(By.cssSelector("input[value=Update]"));
    }

    public void deleteContact() {
        click(By.cssSelector("input[value=Delete]"));
    }

    public void selectContact() {
        click(By.cssSelector("#container #maintable input"));
    }

    public void submitContactDeletion() {
        wd.switchTo().alert().accept();
    }

    public boolean isThereAContact() {
        return isElementPresent(By.cssSelector("#container #maintable input"));
    }

    public void createContact(ContactData contact, boolean creation) {
        fillContactForm(contact, creation);
        submitContactForm();
    }
}
