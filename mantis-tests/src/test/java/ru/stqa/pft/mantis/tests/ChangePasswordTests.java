package ru.stqa.pft.mantis.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.stqa.pft.mantis.appmanager.HttpSession;
import ru.stqa.pft.mantis.model.MailMessage;
import ru.stqa.pft.mantis.model.Users;
import ru.stqa.pft.mantis.model.UsersData;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

import static org.testng.Assert.assertTrue;

public class ChangePasswordTests extends TestBase{


    @BeforeMethod
    public void startMailServer() {
        app.mail().start();
    }

    @Test
    public void testChangePassword() throws MessagingException, IOException {
        Users users = app.db().users();
        UsersData modifiedUser = users.iterator().next();
        for (UsersData user : users) {
            if (user.getEmail().equals("user1631250853094@localhost")) {
                modifiedUser = user;
                break;
            }
        }
        if (! modifiedUser.getEmail().equals("user1631250853094@localhost")) {
            System.out.println("Пользователь не найден. Выберете другого пользователя");
            return;
        }
        long now = System.currentTimeMillis();
        String id = String.valueOf(modifiedUser.getId());
        String userName = modifiedUser.getUserName();
        String newPassword = String.format("password%s", now);
        String email = modifiedUser.getEmail();
        changePasswordByAdmin(id);
        gettingLinkFromEmail(email, newPassword);
        HttpSession session = app.newSession();
        assertTrue(session.login(userName, newPassword));
        assertTrue(session.isLoggedInAs("Mikhail_Malygin"));
    }

    private void changePasswordByAdmin(String id) {
        app.session().start("administrator", "root");
        app.navigation().goToManageUsersPage();
        app.navigation().resetPassword(id);
    }

    private void gettingLinkFromEmail(String email, String password) throws MessagingException,
                                                                                         IOException {
        List<MailMessage> mailMessages = app.mail().waitForMail(1, 10000);
        String conformationLink = findConformationLink(mailMessages, email);
        app.registration().finish(conformationLink, password);
    }

    private String findConformationLink(List<MailMessage> mailMessages, String email) {
        MailMessage mailMessage = mailMessages.stream().filter((m) -> m.to.equals(email)).findFirst().get();
        VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();
        return regex.getText(mailMessage.text);
    }

    @AfterMethod(alwaysRun = true)
    public void stopMailServer() {
        app.mail().stop();
    }
}
