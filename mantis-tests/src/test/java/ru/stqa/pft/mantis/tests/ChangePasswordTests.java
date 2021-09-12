package ru.stqa.pft.mantis.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.mantis.model.Users;

public class ChangePasswordTests extends TestBase{

    @Test
    public void testChangePassword() {
        app.session().start("administrator", "root");
        app.navigation().goToManageUsersPage();
        app.navigation().resetPassword();
    }
}
