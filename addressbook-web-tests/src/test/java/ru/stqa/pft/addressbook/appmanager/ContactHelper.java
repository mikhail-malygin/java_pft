package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.util.List;

public class ContactHelper extends HelperBase {

    public ContactHelper(WebDriver wd) {
        super(wd);
    }

    public void fillContactForm(ContactData contactData, boolean creation, boolean modification) {
        type(By.name("firstname"), contactData.getFirstName());
        type(By.name("middlename"), contactData.getMiddleName());
        type(By.name("lastname"), contactData.getLastName());
        type(By.xpath("/html/body/div/div[4]/form/textarea[1]"), contactData.getAddress());
        type(By.name("home"), contactData.getHomeNumber());
        type(By.name("mobile"), contactData.getMobileNumber());
        type(By.name("work"), contactData.getWorkNumber());
        attach(By.name("photo"), contactData.getPhoto());
        if (modification) {
            type(By.xpath("/html/body/div/div[4]/form[1]/input[15]"), contactData.getEmail());
            type(By.xpath("/html/body/div/div[4]/form[1]/input[16]"), contactData.getEmail2());
            type(By.xpath("/html/body/div/div[4]/form[1]/input[17]"), contactData.getEmail3());
        } else {
            type(By.xpath("/html/body/div/div[4]/form[1]/input[14]"), contactData.getEmail());
            type(By.xpath("/html/body/div/div[4]/form[1]/input[15]"), contactData.getEmail2());
            type(By.xpath("/html/body/div/div[4]/form[1]/input[16]"), contactData.getEmail3());
        }

        if (creation) {
            if (contactData.getGroups().size() > 0) {
                Assert.assertTrue(contactData.getGroups().size() == 1);
                new Select(wd.findElement(By.name("new_group"))).
                        selectByVisibleText(contactData.getGroups().iterator().next().getName());
            }
        } else {
            Assert.assertFalse(isElementPresent(By.name("new_group")));
        }
    }

    public void submitContactForm() {
        click(By.xpath("//div[@id='content']/form/input[21]"));
    }

    public void initContactModificationById(int id) {
        wd.findElement(By.cssSelector("a[href=\"edit.php?id=" + id + "\"]")).click();
    }

    public void submitContactModification() {
        click(By.cssSelector("input[value=Update]"));
    }

    public void deleteContact() {
        click(By.cssSelector("input[value=Delete]"));
    }

    private void addContactToGroup(ContactData contactData) {
        new Select(wd.findElement(By.name("to_group"))).
                selectByVisibleText(contactData.getGroups().iterator().next().getName());
        click(By.cssSelector("Add to"));
    }

    public void selectContactById(int id) {
        wd.findElement(By.cssSelector("#container #maintable input[value = '" + id + "']")).click();
    }

    public void submitContactDeletion() {
        wd.switchTo().alert().accept();
        wd.findElement(By.cssSelector("div.msgbox"));
    }

    public void returnToHomePage() {
        click(By.cssSelector("a[href=\"./\"]"));
    }

    public boolean isThereAContact() {
        return isElementPresent(By.cssSelector("#container #maintable input"));
    }

    public void create(ContactData contact, boolean creation, boolean modification) {
        fillContactForm(contact, creation, modification);
        submitContactForm();
        contactCache = null;
        returnToHomePage();
    }

    public void modify(ContactData contact) {
        initContactModificationById(contact.getId());
        fillContactForm(contact, false, true);
        submitContactModification();
        contactCache = null;
        returnToHomePage();
    }

    public void delete(ContactData contact) {
        selectContactById(contact.getId());
        deleteContact();
        contactCache = null;
        submitContactDeletion();
    }

    public void addGroup(ContactData contact) {
        selectContactById(contact.getId());
        addContactToGroup(contact);
    }

    public int count() {
        return wd.findElements(By.name("selected[]")).size();
    }

    private Contacts contactCache = null;

    public Contacts all() {
        if (contactCache != null) {
            return new Contacts(contactCache);
        }

        Contacts contactCache = new Contacts();
        List<WebElement> elements = wd.findElements(By.name("entry"));
        for (WebElement element : elements) {
            List<WebElement> cells = element.findElements(By.tagName("td"));
            String lastName = cells.get(1).getText();
            String firstName = cells.get(2).getText();
            String address = cells.get(3).getText();
            String allEmail = cells.get(4).getText();
            String allPhones = cells.get(5).getText();
            int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));
            contactCache.add(new ContactData().withId(id).withFirstName(firstName).withLastName(lastName).
                    withAddress(address).withAllEmail(allEmail).withAllPhones(allPhones));
        }
        return new Contacts(contactCache);
    }

    public ContactData infoFromEditForm(ContactData contact) {
        initContactModificationById(contact.getId());
        String firstName = wd.findElement(By.name("firstname")).getAttribute("value");
        String lastName = wd.findElement(By.name("lastname")).getAttribute("value");
        String middleName = wd.findElement(By.name("middlename")).getAttribute("value");
        String address = wd.findElement(By.xpath("/html/body/div/div[4]/form/textarea[1]")).getAttribute("value");
        String email = wd.findElement(By.xpath("/html/body/div/div[4]/form[1]/input[15]")).getAttribute("value");
        String email2 = wd.findElement(By.xpath("/html/body/div/div[4]/form[1]/input[16]")).getAttribute("value");
        String email3 = wd.findElement(By.xpath("/html/body/div/div[4]/form[1]/input[17]")).getAttribute("value");
        String homeNumber = wd.findElement(By.name("home")).getAttribute("value");
        String mobileNumber = wd.findElement(By.name("mobile")).getAttribute("value");
        String workNumber = wd.findElement(By.name("work")).getAttribute("value");
        wd.navigate().back();
        return new ContactData().withId(contact.getId()).withFirstName(firstName).withLastName(lastName).
                withMiddleName(middleName).withAddress(address).withEmail(email).withEmail2(email2).
                withEmail3(email3).withHomeNumber(homeNumber).withMobileNumber(mobileNumber).withWorkNumber(workNumber);
    }
}