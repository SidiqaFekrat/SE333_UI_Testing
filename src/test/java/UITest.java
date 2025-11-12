
import com.microsoft.playwright.*;
import com.microsoft.playwright.options.*;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;

import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.*;

public class UITest {

    private Playwright playwright;
    private Browser browser;
    private BrowserContext context;
    private Page page;

    @BeforeEach
    public void setUp() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                .setHeadless(false));
        context = browser.newContext();
        context = browser.newContext(new Browser.NewContextOptions()
                .setRecordVideoDir(Paths.get("videos/"))
                .setRecordVideoSize(1280, 720));
        page = context.newPage();
    }

    @AfterEach
    public void tearDown() {
        if (context != null) {
            context.close();
        }
        if (browser != null) {
            browser.close();
        }
        if (playwright != null) {
            playwright.close();
        }
    }

    @Test
    public void testBookstoreSearch() {
        page.navigate("https://depaul.bncollege.com/");
        page.getByPlaceholder("Enter your search details (").click();
        page.getByPlaceholder("Enter your search details (").fill("earbuds");
        page.getByPlaceholder("Enter your search details (").press("Enter");
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("brand")).click();
        page.locator("#facet-brand").getByRole(AriaRole.LIST).locator("label").filter(new Locator.FilterOptions().setHasText("brand JBL")).click();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Color")).click();
        page.locator("label").filter(new Locator.FilterOptions().setHasText("Color Black")).click();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Price")).click();
        page.locator("label").filter(new Locator.FilterOptions().setHasText("Price Over $50")).click();
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("JBL Quantum True Wireless")).click();
        page.waitForTimeout(2000);
        page.getByLabel("Add to cart").click();
        page.waitForTimeout(2000);
        assertThat(page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("JBL Quantum True Wireless"))).isVisible();
        //assertThat(page.getByText("SKU")).isVisible();
        assertThat(page.getByText("$164.98")).isVisible();
        assertThat(page.getByText("Adaptive noise cancelling allows awareness of environment when gaming on the go. Light weight, durable, water resist. USB-C dongle for low latency connection < than 30ms")).isVisible();
        //assertThat(page.getByText("1 items")).isVisible();

    }

    @Test
    public void testShoppingCartPage() {
        page.navigate("https://depaul.bncollege.com/");
        page.getByPlaceholder("Enter your search details (").click();
        page.getByPlaceholder("Enter your search details (").fill("earbuds");
        page.getByPlaceholder("Enter your search details (").press("Enter");
        page.waitForTimeout(2000);
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("brand")).click();
        page.locator("#facet-brand").getByRole(AriaRole.LIST).locator("label").filter(new Locator.FilterOptions().setHasText("brand JBL")).click();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Color")).click();
        page.locator("label").filter(new Locator.FilterOptions().setHasText("Color Black")).click();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Price")).click();
        page.locator("label").filter(new Locator.FilterOptions().setHasText("Price Over $50")).click();
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("JBL Quantum True Wireless")).click();
        page.getByLabel("Add to cart").click();
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Cart 1 items")).click();
        page.waitForTimeout(2000);
        assertThat(page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Your Shopping Cart"))).isVisible();
        assertThat(page.getByText("JBL Quantum True Wireless Noise Cancelling Gaming Earbuds- Black")).isVisible();
        assertThat(page.getByLabel("Quantity, edit and press")).isVisible();
        String cartPrice = page.getByText("$164.98").first().locator("..").innerText();
        org.assertj.core.api.Assertions.assertThat(cartPrice).contains("$164.98");

        page.getByText("FAST In-Store PickupDePaul").click();
        page.waitForTimeout(1000);

        assertThat(page.locator("text='$164.98'").first()).isVisible();
        assertThat(page.locator("text='$3.00'")).isVisible();
        assertThat(page.locator("text='TBD'")).isVisible();
        assertThat(page.locator("text='$167.98'")).isVisible();

        page.getByLabel("Enter Promo Code").click();
        page.getByLabel("Enter Promo Code").fill("TEST");
        page.getByLabel("Apply").click();
        page.waitForTimeout(2000);

        assertThat(page.getByText("The coupon code entered is not valid")).isVisible();

        page.getByLabel("Proceed To Checkout").first().click();
        page.waitForTimeout(2000);
    }

    @Test
    public void testCreateAccountPage() {
        page.navigate("https://depaul.bncollege.com/");
        page.getByPlaceholder("Enter your search details (").click();
        page.getByPlaceholder("Enter your search details (").fill("earbuds");
        page.getByPlaceholder("Enter your search details (").press("Enter");
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("brand")).click();
        page.locator("#facet-brand").getByRole(AriaRole.LIST).locator("label").filter(new Locator.FilterOptions().setHasText("brand JBL")).click();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Color")).click();
        page.locator("label").filter(new Locator.FilterOptions().setHasText("Color Black")).click();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Price")).click();
        page.locator("label").filter(new Locator.FilterOptions().setHasText("Price Over $50")).click();
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("JBL Quantum True Wireless")).click();
        page.getByLabel("Add to cart").click();
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Cart 1 items")).click();
        page.waitForTimeout(2000);
        page.getByText("FAST In-Store PickupDePaul").click();
        page.getByLabel("Enter Promo Code").click();
        page.getByLabel("Enter Promo Code").fill("TEST");
        page.getByLabel("Proceed To Checkout").first().click();
        page.waitForTimeout(2000);
        assertThat(page.getByLabel("Create Account").first()).isVisible();
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Proceed As Guest")).click();

    }

    @Test
    public void testContactInformationPage() {
        page.navigate("https://depaul.bncollege.com/");
        page.getByPlaceholder("Enter your search details (").click();
        page.getByPlaceholder("Enter your search details (").fill("earbuds");
        page.getByPlaceholder("Enter your search details (").press("Enter");
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("brand")).click();
        page.locator("#facet-brand").getByRole(AriaRole.LIST).locator("label").filter(new Locator.FilterOptions().setHasText("brand JBL")).click();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Color")).click();
        page.locator("label").filter(new Locator.FilterOptions().setHasText("Color Black")).click();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Price")).click();
        page.locator("label").filter(new Locator.FilterOptions().setHasText("Price Over $50")).click();
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("JBL Quantum True Wireless")).click();
        page.getByLabel("Add to cart").click();
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Cart 1 items")).click();
        page.waitForTimeout(2000);
        page.getByText("FAST In-Store PickupDePaul").click();
        assertThat(page.locator("text='$164.98'").first()).isVisible();
        assertThat(page.locator("text='$3.00'")).isVisible();
        assertThat(page.locator("text='TBD'")).isVisible();
        assertThat(page.locator("text='$167.98'")).isVisible();

        page.getByLabel("Enter Promo Code").click();
        page.getByLabel("Enter Promo Code").fill("TEST");
        page.getByLabel("Proceed To Checkout").first().click();
        page.waitForTimeout(1000);
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Proceed As Guest")).click();
        page.waitForTimeout(1000);
        page.getByPlaceholder("Please enter your first name").fill("Sidiqa");
        page.getByPlaceholder("Please enter your last name").fill("Fekrat");
        page.getByPlaceholder("Please enter a valid email").fill("Sfekrat@depaul.edu");
        page.getByPlaceholder("Please enter a valid phone").fill("3127317195");
        assertThat(page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Contact Information"))).isVisible();
        String orderSummary = page.getByText("Order Summary").nth(1).locator("..").innerText();
        org.assertj.core.api.Assertions.assertThat(orderSummary).contains("$164.98", "$3.00", "TBD");
        assertThat(page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Continue"))).isVisible();

        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Continue")).click();
    }

    @Test
    public void testPickupInformation() {
        page.navigate("https://depaul.bncollege.com/");
        page.getByPlaceholder("Enter your search details (").click();
        page.getByPlaceholder("Enter your search details (").fill("earbuds");
        page.getByPlaceholder("Enter your search details (").press("Enter");
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("brand")).click();
        page.locator("#facet-brand").getByRole(AriaRole.LIST).locator("label").filter(new Locator.FilterOptions().setHasText("brand JBL")).click();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Color")).click();
        page.locator("label").filter(new Locator.FilterOptions().setHasText("Color Black")).click();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Price")).click();
        page.locator("label").filter(new Locator.FilterOptions().setHasText("Price Over $50")).click();
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("JBL Quantum True Wireless")).click();
        page.getByLabel("Add to cart").click();
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Cart 1 items")).click();
        page.waitForTimeout(2000);
        page.getByText("FAST In-Store PickupDePaul").click();
        page.getByLabel("Enter Promo Code").click();
        page.getByLabel("Enter Promo Code").fill("TEST");
        page.getByLabel("Proceed To Checkout").first().click();
        page.waitForTimeout(1000);
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Proceed As Guest")).click();
        page.waitForTimeout(1000);
        page.getByPlaceholder("Please enter your first name").fill("Sidiqa");
        page.getByPlaceholder("Please enter your last name").fill("Fekrat");
        page.getByPlaceholder("Please enter a valid email").fill("Sfekrat@depaul.edu");
        page.getByPlaceholder("Please enter a valid phone").fill("3127317195");
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Continue")).click();
        page.waitForTimeout(3000);
        page.getByText("I'll pick them up").click();
        page.waitForTimeout(2000);
        String fullName = page.getByText("Full Name").first().locator("..").innerText();
        String email = page.getByText("Email Address").first().locator("..").innerText();
        String phone = page.getByText("Phone Number").first().locator("..").innerText();
        String pickupLocation = page.getByText("Pickup Location").locator("..").innerText();

        org.assertj.core.api.Assertions.assertThat(fullName).contains("Sidiqa Fekrat");
        org.assertj.core.api.Assertions.assertThat(email).contains("Sfekrat@depaul.edu");
        org.assertj.core.api.Assertions.assertThat(phone).contains("+13127317195");
        org.assertj.core.api.Assertions.assertThat(pickupLocation).contains("DePaul University Loop Campus & SAIC");
        assertThat(page.getByRole(AriaRole.RADIO, new Page.GetByRoleOptions().setName("I'll pick them up"))).isChecked();

        String orderSummary = page.getByText("Order Summary").nth(1).locator("..").innerText();
        org.assertj.core.api.Assertions.assertThat(orderSummary).contains("$164.98", "$3.00", "TBD");

    }

    @Test
    public void testPaymentInformation() {
        page.navigate("https://depaul.bncollege.com/");
        page.getByPlaceholder("Enter your search details (").click();
        page.getByPlaceholder("Enter your search details (").fill("earbuds");
        page.getByPlaceholder("Enter your search details (").press("Enter");
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("brand")).click();
        page.locator("#facet-brand").getByRole(AriaRole.LIST).locator("label").filter(new Locator.FilterOptions().setHasText("brand JBL")).click();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Color")).click();
        page.locator("label").filter(new Locator.FilterOptions().setHasText("Color Black")).click();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Price")).click();
        page.locator("label").filter(new Locator.FilterOptions().setHasText("Price Over $50")).click();
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("JBL Quantum True Wireless")).click();
        page.getByLabel("Add to cart").click();
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Cart 1 items")).click();
        page.waitForTimeout(2000);
        page.getByText("FAST In-Store PickupDePaul").click();
        page.getByLabel("Enter Promo Code").click();
        page.getByLabel("Enter Promo Code").fill("TEST");
        page.getByLabel("Proceed To Checkout").first().click();
        page.waitForTimeout(1000);
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Proceed As Guest")).click();
        page.waitForTimeout(1000);
        page.getByPlaceholder("Please enter your first name").fill("Sidiqa");
        page.getByPlaceholder("Please enter your last name").fill("Fekrat");
        page.getByPlaceholder("Please enter a valid email").fill("Sfekrat@depaul.edu");
        page.getByPlaceholder("Please enter a valid phone").fill("3127317195");
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Continue")).click();
        page.waitForTimeout(2000);
        page.getByText("I'll pick them up").click();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Continue")).click();
        page.waitForTimeout(2000);
        String orderSummary = page.getByText("Order Summary").nth(1).locator("..").innerText();
        org.assertj.core.api.Assertions.assertThat(orderSummary).contains("$164.98", "$3.00", "$17.22","$185.20");

    }

    @Test
    public void testRemoveFromCart() {
        page.navigate("https://depaul.bncollege.com/");
        page.getByPlaceholder("Enter your search details (").click();
        page.getByPlaceholder("Enter your search details (").fill("earbuds");
        page.getByPlaceholder("Enter your search details (").press("Enter");
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("brand")).click();
        page.locator("#facet-brand").getByRole(AriaRole.LIST).locator("label").filter(new Locator.FilterOptions().setHasText("brand JBL")).click();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Color")).click();
        page.locator("label").filter(new Locator.FilterOptions().setHasText("Color Black")).click();
        page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Price")).click();
        page.locator("label").filter(new Locator.FilterOptions().setHasText("Price Over $50")).click();
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("JBL Quantum True Wireless")).click();
        page.getByLabel("Add to cart").click();
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Cart 1 items")).click();
        page.waitForTimeout(2000);
        assertThat(page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Your Shopping Cart"))).isVisible();

        page.locator("button[aria-label*='Remove']").first().click();
        page.waitForTimeout(2000);
        assertThat(page.getByText("Your cart is empty")).isVisible();

    }

}