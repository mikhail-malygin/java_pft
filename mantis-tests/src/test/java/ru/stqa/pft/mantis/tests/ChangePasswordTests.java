package ru.stqa.pft.mantis.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.mantis.model.Users;
import ru.stqa.pft.mantis.model.UsersData;

public class ChangePasswordTests extends TestBase{

    @Test
    public void testChangePassword() {
                Users users = app.db().users();
        UsersData modifiedUser = users.iterator().next();
        for (UsersData user : users) {
            if (user.getEmail().equals("test.malygin@gmail.com")) {
                modifiedUser = user;
                break;
            }
        }
        if (! modifiedUser.getEmail().equals("test.malygin@gmail.com")) {
            System.out.println("Пользователь не найден. Выберете другого пользователя");
            return;
        }
        String id = String.valueOf(modifiedUser.getId());
        changePasswordByAdmin(id);
    }

    private void changePasswordByAdmin(String id) {
        app.session().start("administrator", "root");
        app.navigation().goToManageUsersPage();
        app.navigation().resetPassword(id);
    }
}
