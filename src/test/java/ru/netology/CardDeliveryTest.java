package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {

    public String calculateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @Test
    void test() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
        String planningDate = calculateDate(10);
        $x("//input[@placeholder='Город']").setValue("Ростов-на-дону");
        $x("//input [@placeholder='Дата встречи']").doubleClick();
        $x("//input [@placeholder='Дата встречи']").sendKeys(Keys.DELETE);
        $x("//input[@placeholder = 'Дата встречи']").setValue(planningDate);
        $x("//input[@name = 'name']").setValue("Иванов Иван");
        $x("//input [@name ='phone']").setValue("+79990993212");
        $("[data-test-id='agreement']").click();
        $("[type='button'] .button__text").click();
        $x("//div[@class='notification notification_visible notification_has-closer notification_stick-to_right notification_theme_alfa-on-white']").should(visible, Duration.ofSeconds(20));
        $(".notification__content").shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate), Duration.ofSeconds(20)).shouldBe(Condition.visible);
    }
}
