package page.classes;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utilities.WaitUtil;

public class HomepageFactory {

	@FindBy(xpath = ".//*[@id='hs_menu_wrapper_module_143922105801416']/ul/li[3]/ul/li[1]/a")
	WebElement aboutNomisChildMenu;

	@FindBy(xpath = ".//*[@id='hs_menu_wrapper_module_143922105801416']/ul/li[3]/a")
	WebElement aboutNomisMainMenu;

	@FindBy(id = "hs_cos_wrapper_module_14732906624785128")
	WebElement aboutWhoWeAre;

	@FindBy(xpath = ".//*[@ src=\"https://www.google.com/uds/css/v2/search_box_icon.png\"]")
	WebElement customSearchButton;

	@FindBy(id = "gsc-i-id1")
	WebElement customSearchInput;

	@FindBy(id = "gsc-i-id2")
	WebElement customSearchResult;

	WebDriver driver;

	@FindBy(xpath = ".//*[@id='fancybox-overlay']")
	WebElement fancyBoxClose;

	@FindBy(css = "img.hs-image-widget")
	WebElement homeLink;

	@FindBy(id = "hs_cos_wrapper_module_14273976952109892")
	WebElement requestDemoForm;

	@FindBy(xpath = ".//div[text()=\"REQUEST A DEMO\"]")
	WebElement requestDemoLink;

	@FindBy(xpath = ".//*[@id='hs_cos_wrapper_module_1443414471302953']/a/div")
	WebElement watchVideo;

	@FindBy(xpath = ".//*[@class=\"gs-bidi-start-align gs-snippet\"]/b")
	List<WebElement> webResult;
	// .//*[@class="gsc-webResult gsc-result"]

	public HomepageFactory(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	public void clickAboutMenu() {
		Actions action = new Actions(this.driver);
		action.moveToElement(this.aboutNomisMainMenu).moveToElement(this.aboutNomisChildMenu).click().build().perform();
	}

	public void clickHome() {

		this.homeLink.click();

		WaitUtil.getWhenVisible(this.driver, By.cssSelector("img.hs-image-widget"), 6);

	}

	public void closeVideo() {

		this.fancyBoxClose.click();

	}

	public String getVideoSrc() {
		WebElement video = this.driver.findElement(By.xpath(".//*[@id=\"wistia_simple_video_42\"]"));

		String videoSrc = video.getAttribute("src");

		return videoSrc;

	}

	public Double getVideoTime() throws InterruptedException {

		WebElement time_positioner = this.driver.findElement(By.xpath(".//*[@id=\"time_positioner_107\"]/div"));
		Thread.sleep(2000);
		Actions action = new Actions(this.driver);
		action.moveToElement(time_positioner).build().perform();

		String currentTime = time_positioner.getText().replace(":", ".");

		Double time = Double.parseDouble(currentTime);

		return time;

	}

	public int getWebResultSize() {

		int size = this.webResult.size();
		return size;
	}

	public String getWebResultText() {

		String text = this.webResult.get(0).getText();
		return text;
	}

	public void playVideo() throws InterruptedException {
		this.watchVideo.click();

		Thread.sleep(2000);
	}

	public void requestDemo() {

		WaitUtil.getWhenClickable(this.driver, By.xpath(".//div[text()=\"REQUEST A DEMO\"]"), 10);

		this.requestDemoLink.click();

	}

	public void searchItem(String item) {

		this.customSearchInput.sendKeys(item);
		this.customSearchButton.click();

	}

}
