package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactPhoneTests extends TestBase{

    @BeforeMethod
    public void ensurePreconditions() {
        if (app.contact().all().size() == 0) {
            app.goTo().gotoContactCreationPage();
            app.contact().create(new ContactData().withFirstName("Mikhail").withMiddleName("Sergeevich").
                    withLastName("Malygin").withHomeNumber("8(343)9").withMobileNumber("799999999999").
                    withWorkNumber("123-34").withEmail("test.malygin@gmail.com").withGroup("test1"),true);
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
