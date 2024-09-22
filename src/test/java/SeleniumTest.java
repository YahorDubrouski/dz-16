import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;
import org.testng.Assert;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.JavascriptExecutor;

public class SeleniumTest extends SeleniumBaseTest {
    @Test
    public void testClickTheButtonsTab() {
        // Open a webpage
        driver.get("https://demoqa.com/elements");

        //Click the buttons tab
        WebElement buttonsBlock = driver.findElement(
                By.xpath("//*[@id=\"item-4\"]/*[contains(text(), \"Buttons\")]/..")
        );
        buttonsBlock.click();

        //Click the buttons tab
        WebElement clickButton = driver.findElement(By.xpath("//button[text()=\"Click Me\"]"));
        clickButton.click();

        WebElement finalMessage = driver.findElement(By.id("dynamicClickMessage"));
        System.out.println(finalMessage.getText());
        Assert.assertEquals(finalMessage.getText(), "You have done a dynamic click");
    }

    @Test
    public void testAddTableRow() throws InterruptedException {
        // Open a webpage
        driver.get("https://demoqa.com/webtables");

        createNewRow();
        checkThatRowWasCreated();
        editCreatedRow();
        driver.findElement(By.xpath("//*[@role=\"gridcell\" and text()=\"Bob Changed\"]"));
    }

    private void editCreatedRow() {
        WebElement editButton = driver.findElement(
                By.xpath("(//*[contains(@id, \"edit-record-\")])[last()]")
        );
        //Over scroll the advertisement block
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", editButton);
        editButton.click();
        WebElement firstName = driver.findElement(By.id("firstName"));
        firstName.clear();
        firstName.sendKeys("Bob Changed");
        WebElement buttonSubmit = driver.findElement(By.id("submit"));
        buttonSubmit.click();
    }

    private void createNewRow() throws NoSuchElementException {
        WebElement buttonAdd = driver.findElement(By.id("addNewRecordButton"));
        buttonAdd.click();
        fillInNewRowForm();
        WebElement buttonSubmit = driver.findElement(By.id("submit"));
        buttonSubmit.click();
    }

    private void checkThatRowWasCreated() {
        driver.findElement(
                By.xpath("//*[@role=\"gridcell\" and text()=\"Bob\"]")
        );
        driver.findElement(
                By.xpath("//*[@role=\"gridcell\" and text()=\"Smith\"]")
        );
        driver.findElement(
                By.xpath("//*[@role=\"gridcell\" and text()=\"bob_smith@gmail.com\"]")
        );
        driver.findElement(By.xpath("//*[@role=\"gridcell\" and text()=\"30\"]"));
        driver.findElement(By.xpath("//*[@role=\"gridcell\" and text()=\"1000000000\"]"));
        driver.findElement(By.xpath("//*[@role=\"gridcell\" and text()=\"Java\"]"));
    }

    private void fillInNewRowForm() {
        WebElement firstName = driver.findElement(By.id("firstName"));
        firstName.sendKeys("Bob");

        WebElement lastName = driver.findElement(By.id("lastName"));
        lastName.sendKeys("Smith");

        WebElement userEmail = driver.findElement(By.id("userEmail"));
        userEmail.sendKeys("bob_smith@gmail.com");

        WebElement age = driver.findElement(By.id("age"));
        age.sendKeys("30");

        WebElement salary = driver.findElement(By.id("salary"));
        salary.sendKeys("1000000000");

        WebElement department = driver.findElement(By.id("department"));
        department.sendKeys("Java");
    }
}
