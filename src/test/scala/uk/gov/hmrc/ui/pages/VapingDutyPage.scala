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
import uk.gov.hmrc.ui.models.VpdUser
import uk.gov.hmrc.ui.pages.VapingDutyLocators.*

object VapingDutyPage extends BasePage {

  private val base = redirectUrl.stripSuffix("/")

  val approvalIdUrl: String      = s"$base/enrolment/approval-id"
  val noApprovalIdUrl: String    = s"$base/enrolment/no-approval-id"
  val alreadyEnrolledUrl: String = s"$base/enrolment/already-enrolled"
  val orgSignInUrl: String       = s"$base/enrolment/organisation-sign-in"
  val enrolmentAccessUrl: String = s"$base/enrolment/enrolment-access"

  def goToUrl(url: String): Unit = {
    get(url)
    fluentWait.until(ExpectedConditions.urlContains(url))
  }

  def signIntoAuth(user: VpdUser): Unit = {
    get(loginUrl)
    sendKeys(redirectionUrlField, approvalIdUrl)
    selectByValue(affinityGroupSelect, user.affinityGroup)
    user.enrolmentKey.foreach(sendKeys(enrolmentKey, _))
    user.identifierName.foreach(sendKeys(identifierName, _))
    user.identifierValue.foreach(sendKeys(identifierValue, _))
    click(SubmitButton)
  }

  def urlConfirmation(expectedUrl: String): Boolean =
    fluentWait.until { driver =>
      driver.getCurrentUrl.contains(expectedUrl)
    }

  def SelectVapingDutyProductsIdRadio(hasVapingProductsId: Boolean): Unit =
    click(if (hasVapingProductsId) yesRadioButton else noRadioButton)
    click(continueButton);
}
