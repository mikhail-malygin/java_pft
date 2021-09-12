package ru.stqa.pft.mantis.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class NavigationHelper extends HelperBase{

    private WebDriver wd;

    public NavigationHelper(ApplicationManager app) {
        super(app);
        wd = app.getDriver();
    }

    public void goToManageUsersPage() {
        if (isElementPresent(By.cssSelector("a[href=\"/mantisbt-1.2.20/manage_user_page.php\"]"))) {
            click(By.cssSelector("a[href=\"/mantisbt-1.2.20/manage_user_page.php\"]"));
        } else {
            click(By.cssSelector("a[href=\"/mantisbt-1.2.20/manage_overview_page.php\"]"));
            click(By.cssSelector("a[href=\"/mantisbt-1.2.20/manage_user_page.php\"]"));
        }
    }

    public void resetPassword() {
        click(By.cssSelector("a[href=\"manage_user_edit_page.php?user_id=7\"]"));
        click(By.cssSelector("input[value=\"Reset Password\"]"));
    }


}
