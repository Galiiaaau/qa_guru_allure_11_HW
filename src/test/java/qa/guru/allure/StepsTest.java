package qa.guru.allure;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static io.qameta.allure.Allure.step;
import static org.openqa.selenium.By.linkText;

public class StepsTest {

    private static final String REPOSITORY = "Galiiaaau/qa_guru_allure_11_HW";
    private static final String ISSUE = "Issue for HW 11. Allure reports";

    @DisplayName("Tests with just Selenide (with a Listener)")
    @Owner("Galiia U")
    @Severity(SeverityLevel.NORMAL)
    @Test
    public void testIssueSearch() {
        SelenideLogger.addListener("allure", new AllureSelenide());

        open("https://github.com");
        $(".header-search-input").click();
        $(".header-search-input").sendKeys(REPOSITORY);
        $(".header-search-input").submit();

        $(linkText(REPOSITORY)).click();
        $("#issues-tab").click();
        $(withText(ISSUE)).should(Condition.exist);
    }

    @DisplayName("Tests with Lambda")
    @Owner("Galiia U")
    @Severity(SeverityLevel.NORMAL)
    @Test
    public void TestLambdaSearch() {
        SelenideLogger.addListener("allure", new AllureSelenide());
        step("Open main page", context -> {
            open("https://github.com");
        });
        step("Look for repository" + REPOSITORY, () -> {
            $(".header-search-input").click();
            $(".header-search-input").setValue(REPOSITORY);
            $(".header-search-input").submit();
        });
        step("Click on repository link" + REPOSITORY, () -> {
            $(linkText(REPOSITORY)).click();
        });
        step("Open the 'Issue' Tab", () -> {
            $("#issues-tab").click();
        });
        step("Confirm that 'Issue' tab with a specified issue exists", () -> {
            $(withText(ISSUE)).should(Condition.exist);
        });
    }

    @DisplayName("Tests with @step annotations")
    @Owner("Galiia U")
    @Severity(SeverityLevel.NORMAL)
    @Test
    public void testAnnotatedStep() {
        SelenideLogger.addListener("allure", new AllureSelenide());
        WebSteps steps = new WebSteps();

        steps.openMainPage();
        steps.searchForRepository(REPOSITORY);
        steps.clickOnRepositoryLink(REPOSITORY);
        steps.openIssuesTab();
        steps.shouldSeeIssueWithNumber(ISSUE);
    }
}
