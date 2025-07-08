package ru.netology.delivery.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.delivery.data.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static ru.netology.delivery.data.DataGenerator.generateDate;

public class CardDeliveryTest {

    @BeforeEach
    void setUp(){
        Selenide.open("http://localhost:9999");
    }
// UserInfo
    @Test
    void shouldChangeMeet(){

        var validUser = DataGenerator.Registration.generateUser("ru");
        var daysForMeeting = 4;
        var firstMeet = DataGenerator.generateDate(daysForMeeting);
        var daysForNextMeeting = 7;
        var secondMeet = DataGenerator.generateDate(daysForNextMeeting);

        $("[data-test-id='city'] input").setValue(validUser.getCity());
        $("[data-test-id='date'] input").press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(firstMeet);
        $("[data-test-id='name'] input").setValue(validUser.getName());
        $("[data-test-id='phone'] input").setValue(validUser.getPhone());
        $("[data-test-id='agreement']").click();
        $(byText("Запланировать")).click();
        $("[data-test-id='success-notification'] .notification__content").should(Condition.visible,  Duration.ofSeconds(5)).should(Condition.exactText("Встреча успешно запланирована на " + firstMeet));
        $("[data-test-id='date'] input").press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.DELETE);
        $("[data-test-id='date'] input").setValue(secondMeet);
        $(byText("Запланировать")).click();
        $("[data-test-id='replan-notification'] .notification__content").should(Condition.visible,  Duration.ofSeconds(5)).should(Condition.text("У вас уже запланирована встреча на другую дату. Перепланировать?"));
        $(byText("Перепланировать")).click();
        $("[data-test-id='success-notification'] .notification__content").should(Condition.visible,  Duration.ofSeconds(5)).should(Condition.text("Встреча успешно запланирована на " + secondMeet));
    }
}
