package ru.netology.delivery.test;

import java.time.Duration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import ru.netology.delivery.data.DataGenerator;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
 class CardDeliveryTest {
     @BeforeEach
     void setup(){
         open("http://localhost:9999");
     }
     @Test
     @DisplayName("Should successful plan meeting")
     void shouldSuccessfulPlanMeeting(){
         var validUser = DataGenerator. Registration.generateUser ("ru");
         var daysToAddForFirstMeeting = 4;
         var firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
         var daysToAddForSecondMeeting = 7;
         var secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);
         $("[data-test-id=city] input"). setValue(validUser. getCity());
         $("[data-test-id=date] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys. BACK_SPACE);
         $("[data-test-id=date] input").setValue(firstMeetingDate);
         $("[data-test-id=name] input").setValue(validUser.getName());
         $("[data-test-id=phone] input").setValue(validUser.getPhone());
         $( "[data-test-id=agreement]").click();
         $(byText("Запланировать")).click();
         $(byText("Успешно!")).shouldBe(visible, Duration.ofSeconds (15));
         $("[data-test-id='success-notification'] .notification__content")
                 .shouldHave(exactText("Встреча успешно заплонирована на " + firstMeetingDate))
                 .shouldBe(visible);
         $("[data-test-id=date) input"). sendKeys(Keys.chord(Keys.SHIFT, Keys. HOME), Keys.BACK_SPACE);
         $("[data-test-id=date] input").setValue (secondMeetingDate);
         $(byText("Запланировать")) .click();
         $("[data-test-id=data] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
         $("[data-test-id=date] input").setValue (secondMeetingDate);
         $(byText( "Запланировать")) .click();
         $("[data-test-id='replan-notification'] .notification__content")
                 .shouldHave(text("У вас уже заплонирована встреча на другую дату. Перепланировать?"))
                 .shouldBe(visible);
         $("[data-test-id='replan-notification'] button").click();
         $("[data-test-id='success-notification'] .notification__content")
                 .shouldHave(exactText("Встреча успешно заплонирована на " + secondMeetingDate))
                 .shouldBe(visible);

     }
}