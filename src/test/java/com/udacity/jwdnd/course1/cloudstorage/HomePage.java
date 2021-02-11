package com.udacity.jwdnd.course1.cloudstorage;

import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage extends AbstractPage {
    private WebDriver webDriver;

    public HomePage(WebDriver webDriver) {
        super(webDriver);
    }

    @FindBy(css = "#nav-files-tab")
    private WebElement filesTabLink;

    @FindBy(css = "#nav-notes-tab")
    private WebElement notesTabLink;

    @FindBy(css = "#nav-credentials-tab")
    private WebElement credentialsTabLink;

    // NOTES
    @FindBy(css = "#new-note-button")
    private WebElement newNoteButton;

    @FindBy(css = "#note-title")
    private WebElement noteTitleField;

    @FindBy(css = "#note-description")
    private WebElement noteDescriptionField;

    @FindBy(css = "#noteSubmit")
    private WebElement noteSubmitButton;

    @FindBy(css = "#delete-note-link")
    private List<WebElement> deleteNoteButtons;

    @FindBy(css = ".note-title")
    private List<WebElement> noteTitles;

    @FindBy(css = ".note-description")
    private List<WebElement> noteDescriptions;

    public void createNewNote(String noteTitle, String noteDescription) {
        clickOnButton(notesTabLink);
        clickOnButton(newNoteButton);
        setValue(noteTitleField, noteTitle);
        setValue(noteDescriptionField, noteDescription);
        clickOnButton(noteSubmitButton);
    }

    public void deleteNote(Integer position) {
        clickOnButton(notesTabLink);
        clickOnButton(deleteNoteButtons.get(position));
    }

    public List<String> getNoteTitles() {
        List<String> resultList = new ArrayList<String>();
        for (WebElement ele : noteTitles) {
            resultList.add(ele.getAttribute("innerHTML"));
        }
        return resultList;
    }

    public List<String> getNoteDescriptions() {
        List<String> resultList = new ArrayList<String>();
        for (WebElement ele : noteDescriptions) {
            resultList.add(ele.getAttribute("innerHTML"));
        }
        return resultList;
    }

    // CREDENTIALS
    @FindBy(css = "#new-credential-button")
    private WebElement newCredentialButton;

    @FindBy(css = "#credential-url")
    private WebElement credentialUrlField;

    @FindBy(css = "#credential-username")
    private WebElement credentialUsernameField;

    @FindBy(css = "#credential-password")
    private WebElement credentialPasswordField;

    @FindBy(css = "#credentialSubmit")
    private WebElement credentialSubmitButton;

    @FindBy(css = "#delete-credential-link")
    private List<WebElement> deleteCredentialButtons;

    @FindBy(css = ".credential-url")
    private List<WebElement> credentialUrls;

    @FindBy(css = ".credential-username")
    private List<WebElement> credentialUsernames;

    @FindBy(css = ".credential-key")
    private List<WebElement> credentialKeys;

    public void createNewCredential(String url, String username, String password) {
        clickOnButton(credentialsTabLink);
        clickOnButton(newCredentialButton);
        setValue(credentialUrlField, url);
        setValue(credentialUsernameField, username);
        setValue(credentialPasswordField, password);
        clickOnButton(credentialSubmitButton);
    }

    public void deleteCredential(Integer position) {
        clickOnButton(notesTabLink);
        clickOnButton(deleteCredentialButtons.get(position));
    }

    public List<String> getCredentialUrls() {
        List<String> resultList = new ArrayList<String>();
        for (WebElement ele : credentialUrls) {
            resultList.add(ele.getAttribute("innerHTML"));
        }
        return resultList;
    }

    public List<String> getCredentialUsernames() {
        List<String> resultList = new ArrayList<String>();
        for (WebElement ele : credentialUsernames) {
            resultList.add(ele.getAttribute("innerHTML"));
        }
        return resultList;
    }

    public List<String> getCredentialKeys() {
        List<String> resultList = new ArrayList<String>();
        for (WebElement ele : credentialKeys) {
            resultList.add(ele.getAttribute("innerHTML"));
        }
        return resultList;
    }
}
