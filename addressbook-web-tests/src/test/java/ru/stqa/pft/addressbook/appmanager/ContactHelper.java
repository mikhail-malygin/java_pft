package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactHelper extends HelperBase {

    public ContactHelper(WebDriver wd) {
        super(wd);
    }

    public void fillContactForm(ContactData contactData) {
      type(By.name("firstname"),contactData.getFirstName());
      type(By.name("middlename"),contactData.getMiddleName());
      type(By.name("lastname"),contactData.getLastName());
      type(By.name("home"),contactData.getNumber());
      type(By.name("email"),contactData.getEmail());
    }

    public void submitContactForm() {
      click(By.xpath("//div[@id='content']/form/input[21]"));
    }

    public void returnToHomePage() {
        click(By.linkText("home page"));
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
}