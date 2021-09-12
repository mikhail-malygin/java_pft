package ru.stqa.pft.mantis.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.mantis.model.Users;

public class ChangePasswordTests extends TestBase{

    @Test
    public void testChangePassword() {
        Users users = app.db().users();
        System.out.println("users: " + users);
        app.session().start("administrator", "root");
        app.navigation().goToManageUsersPage();
        app.navigation().resetPassword();
    }
}
