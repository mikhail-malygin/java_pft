package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.io.File;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactPhoneTests extends TestBase{

    @BeforeMethod
    public void ensurePreconditions() {
        if (app.db().contacts().size() == 0) {
            app.goTo().gotoContactCreationPage();
            File photo = new File("src/test/resources/stru.png");
            app.contact().create(new ContactData().withFirstName("Mikhail").withMiddleName("Sergeevich").
                            withLastName("Malygin").withAddress("Russia, Testing region, Agile city, Jira str, appart: 47, 9").
                            withHomeNumber("8(343)9").withMobileNumber("799999999999").withWorkNumber("123-34").
                            withEmail("test.malygin@gmail.com").withEmail3("tes3t@mail.ru").withPhoto(photo),
                    true, false);
            app.goTo().returnToHomePage();
        }
    }

    @Test
    public void testContactPhones() {
        ContactData contact = app.contact().all().iterator().next();
        ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);

        assertThat(contact.getAllPhones(), equalTo(mergePhones(contactInfoFromEditForm)));
    }

    private String mergePhones(ContactData contact) {
        return Arrays.asList(contact.getHomeNumber(), contact.getMobileNumber(), contact.getWorkNumber()).
                stream().filter((s) -> !s.equals("")).map(ContactPhoneTests::cleaned).
                collect(Collectors.joining("\n"));
    }

    public static String cleaned (String phone) {
        return phone.replaceAll("\\s", "").replaceAll("[-()]", "");
    }
}
