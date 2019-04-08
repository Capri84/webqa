package test;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pages.TinkoffVacanciesPage;

public class VacancyFormTests extends BaseRunner {

    private Logger logger = LoggerFactory.getLogger(VacancyFormTests.class);

    @Test
    public void VacancyFormTestOne() {
        TinkoffVacanciesPage tinkoffVacanciesPage = app.tinkoffVacancies;
        tinkoffVacanciesPage.open("https://www.tinkoff.ru/career/vacancies/");

        // Прокликивание полей
        tinkoffVacanciesPage.clickField(tinkoffVacanciesPage.nameField);
        logger.info("Кликнуто поле \"Фамилия и имя\"");
        tinkoffVacanciesPage.clickField(tinkoffVacanciesPage.birthdayField);
        logger.info("Кликнуто поле \"Дата рождения\"");
        tinkoffVacanciesPage.clickField(tinkoffVacanciesPage.cityField);
        logger.info("Кликнуто поле \"Город проживания\"");

        // Проверка сообщения об ошибке
        tinkoffVacanciesPage.checkField("Поле обязательное", "//div" +
                "[contains(@class, 'ui-form__row_date')]//div[contains(@class, 'ui-form-field-error-message')]");

        // Прокликивание полей
        tinkoffVacanciesPage.clickField(tinkoffVacanciesPage.emailField);
        logger.info("Кликнуто поле \"Электронная почта\"");
        tinkoffVacanciesPage.clickField(tinkoffVacanciesPage.phoneField);
        logger.info("Кликнуто поле \"Мобильный телефон\"");
        tinkoffVacanciesPage.clickField(tinkoffVacanciesPage.uploadField);
        logger.info("Кликнуто поле \"Перетащите или загрузите резюме/портфолио\"");
        tinkoffVacanciesPage.clickField(tinkoffVacanciesPage.socialField);
        logger.info("Кликнуто поле \"Ссылка на профиль в соцсетях\"");
        tinkoffVacanciesPage.clickField(tinkoffVacanciesPage.agreeCheckBox);
        logger.info("Кликнут чекбокс \"Я согласен с условиями...\"");
        tinkoffVacanciesPage.clickTabButton(tinkoffVacanciesPage.birthdayField);
        // Проверка сообщения об ошибке
        tinkoffVacanciesPage.checkField("Поле обязательное", "//div" +
                "[contains(@class, 'ui-form__row_checkbox')]//div[contains(@class, 'ui-form-field-error-message')]");
    }

    @Test
    public void VacancyFormTestTwo() {
        TinkoffVacanciesPage tinkoffVacanciesPage = app.tinkoffVacancies;
        tinkoffVacanciesPage.open("https://www.tinkoff.ru/career/vacancies/");
        // Тестирование поля "Фамилия и имя"
        tinkoffVacanciesPage.testFIO();
        // Тестирование поля "Дата рождения"
        tinkoffVacanciesPage.testDOB();
        // Тестирование поля "Электронная почта"
        tinkoffVacanciesPage.testEMail();
        // Тестирование поля "Мобильный телефон"
        tinkoffVacanciesPage.testMobPhone();
    }
}

