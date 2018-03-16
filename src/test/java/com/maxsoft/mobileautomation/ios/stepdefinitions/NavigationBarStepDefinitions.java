package com.maxsoft.mobileautomation.ios.stepdefinitions;

import com.maxsoft.mobileautomation.ios.pages.NavigationBar;
import com.maxsoft.mobileautomation.ios.util.DriverSetup;
import com.thoughtworks.gauge.Step;
import org.openqa.selenium.support.PageFactory;
import java.io.IOException;


/**
 * Created by Osanda on 4/29/2017.
 */


public class NavigationBarStepDefinitions {

    private NavigationBar navigationBar = PageFactory.initElements(DriverSetup.iosDriver, NavigationBar.class);

    @Step("On Expert Decks page")
    public void navigateToExpertDeckPage() throws IOException {
        navigationBar.navigateToExpertDeckPage();
    }

    @Step("On My Decks page")
    public void navigateToMyDeckPage() throws IOException {
        navigationBar.navigateToMyDeckPage();
    }

    @Step("On Archived page")
    public void navigateToArchivedPage() throws IOException {
        navigationBar.navigateToArchivedPage();
    }

    @Step("On Settings page")
    public void navigateToSettingsPage() throws IOException {
        navigationBar.navigateToSettingsPage();
    }


}
