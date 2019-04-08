package test;

import org.junit.Test;
import pages.GoogleResultsPage;
import pages.GoogleSearchPage;
import pages.TinkoffMobTariffsPage;

public class WindowHandleTests extends BaseRunner {

    @Test
    public void switchWindows() {
        GoogleSearchPage googleSearchPage = app.google;
        googleSearchPage.open("https://www.google.ru/");
        googleSearchPage.performSearch("мобайл тинькофф", "//ul[contains(@role, 'listbox')]/*",
                "мобайл тинькофф тарифы");

        GoogleResultsPage googleResultsPage = app.googleResults;
        googleResultsPage.clickSearchResultsByLink("https://www.tinkoff.ru/mobile-operator/tariffs/");

        googleResultsPage.switchToWindow("Тарифы Тинькофф Мобайла");
        googleResultsPage.checkEquals("Тарифы Тинькофф Мобайла", googleResultsPage.getPageTitle());

        googleResultsPage.switchToMainTab();
        googleResultsPage.closeCurrentTab();

        googleResultsPage.switchToWindow("Тарифы Тинькофф Мобайла");
        googleResultsPage.checkEquals("https://www.tinkoff.ru/mobile-operator/tariffs/",
                googleResultsPage.getCurrentUrl());
    }

    @Test
    public void changeRegions() {
        TinkoffMobTariffsPage tinkoffMobTariffsPage = app.tinkoffMobTariffs;
        tinkoffMobTariffsPage.open("https://www.tinkoff.ru/mobile-operator/tariffs/");

        tinkoffMobTariffsPage.setMoscowRegion();
        tinkoffMobTariffsPage.checkField("Москва и Московская область",
                "//div[contains(@class, 'MvnoRegionConfirmation__title')]");

        tinkoffMobTariffsPage.refreshPage();
        tinkoffMobTariffsPage.checkField("Москва и Московская область",
                "//div[contains(@class, 'MvnoRegionConfirmation__title')]");

        String moscowDefault = tinkoffMobTariffsPage.getPriceText("//div[contains(@class, 'ui-form__field_title')]");
        tinkoffMobTariffsPage.pickMaxCoverage();
        String moscowMax = tinkoffMobTariffsPage.getPriceText("//div[contains(@class, 'ui-form__field_title')]");

        // Переключение на Краснодар
        tinkoffMobTariffsPage.switchRegion("г Краснодар");
        tinkoffMobTariffsPage.checkField("Краснодарский край",
                "//div[contains(@class, 'MvnoRegionConfirmation__title')]");

        String krasnodarDefault = tinkoffMobTariffsPage.getPriceText("//div[contains(@class, " +
                "'ui-form__field_title')]");
        tinkoffMobTariffsPage.pickMaxCoverage();
        String krasnodarMax = tinkoffMobTariffsPage.getPriceText("//div[contains(@class, 'ui-form__field_title')]");

        tinkoffMobTariffsPage.checkNotEquals(moscowDefault, krasnodarDefault);
        tinkoffMobTariffsPage.checkEquals(moscowMax, krasnodarMax);
    }

    @Test
    public void checkActiveButton() {
        TinkoffMobTariffsPage tinkoffMobTariffsPage = app.tinkoffMobTariffs;
        tinkoffMobTariffsPage.open("https://www.tinkoff.ru/mobile-operator/tariffs/");

        tinkoffMobTariffsPage.uncheckAll();
        tinkoffMobTariffsPage.checkField("Общая цена: 0 \u20BD",
                "//div[contains(@class, 'ui-form__field_title')]");

        tinkoffMobTariffsPage.checkButton("//div[contains(@class, 'LoaderRound__container')]");
    }
}