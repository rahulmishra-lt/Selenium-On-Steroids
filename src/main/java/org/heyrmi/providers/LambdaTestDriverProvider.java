package org.heyrmi.providers;

import java.net.URL;
import java.util.HashMap;

import javax.annotation.Nonnull;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.codeborne.selenide.WebDriverProvider;

import lombok.SneakyThrows;

public class LambdaTestDriverProvider implements WebDriverProvider {

  private static final String LAMBDATEST_USERNAME = System.getenv("LAMBDATEST_USERNAME");
  private static final String LAMBDATEST_ACCESS_KEY = System.getenv("LAMBDATEST_ACCESS_KEY");
  private static final String LAMBDATEST_TUNNEL_NAME = System.getenv("LAMBDATEST_TUNNEL_NAME");

  @SneakyThrows
  @Nonnull
  @Override
  public WebDriver createDriver(@Nonnull Capabilities capabilities) {
    ChromeOptions browserOptions = new ChromeOptions();
    browserOptions.setPlatformName("Windows 10");
    browserOptions.setBrowserVersion("latest");
    HashMap<String, Object> ltOptions = new HashMap<String, Object>();
    ltOptions.put("username", LAMBDATEST_USERNAME);
    ltOptions.put("accessKey", LAMBDATEST_ACCESS_KEY);
    ltOptions.put("visual", true);
    ltOptions.put("video", true);
    ltOptions.put("resolution", "1920x1080");
    ltOptions.put("network", true);
    ltOptions.put("build", "Github-Action-Tunnel-Build");
    ltOptions.put("project", "Github-Action-Tunnel-Project");
    ltOptions.put("name", "Github-Action-Tunnel-Test");
    ltOptions.put("tunnel", true);
    ltOptions.put("tunnelName", LAMBDATEST_TUNNEL_NAME);
    ltOptions.put("selenium_version", "latest");
    ltOptions.put("w3c", true);
    browserOptions.setCapability("LT:Options", ltOptions);

    return new RemoteWebDriver(
        new URL("https://" + LAMBDATEST_USERNAME + ":" + LAMBDATEST_ACCESS_KEY
            + "@hub.lambdatest.com/wd/hub"),
        browserOptions);
  }
}