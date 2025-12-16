/*
 * Copyright 2025 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.ui.pages

import org.openqa.selenium.support.ui.ExpectedConditions
import uk.gov.hmrc.selenium.webdriver.Driver
import uk.gov.hmrc.ui.pages.VapingDutyLocators.*

object VapingDutyPage extends BasePage {

  def goToUrl(url: String): Unit = {
    get(url)
    fluentWait.until(ExpectedConditions.urlContains(url))
  }

  def signIntoAuth(enromentName: String, affinityGroup: String, redirectUrl: String): Unit = {
    get(loginUrl)

    sendKeys(redirectionUrlField, redirectUrl)
    selectByValue(affinityGroupSelect, affinityGroup)
    sendKeys(enrolmentKey, "HMRC-VPD-ORG")
    sendKeys(identifierName, enromentName)
    click(SubmitButton)
  }

  def confirmation(url: String): Boolean = {
    fluentWait.until(ExpectedConditions.urlContains(url))
    val currentUrl = Driver.instance.getCurrentUrl
    currentUrl != null && currentUrl.contains(url)
  }

  def SelectVapingDutyProductsIdRadio(hasVapingProductsId: Boolean): Unit =
    click(if (hasVapingProductsId) yesRadioButton else noRadioButton)
    click(continueButton);
}
