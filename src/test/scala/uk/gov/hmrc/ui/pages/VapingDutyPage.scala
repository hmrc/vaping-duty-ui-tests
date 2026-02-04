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
import uk.gov.hmrc.ui.models.AuthUser
import uk.gov.hmrc.ui.pages.VapingDutyLocators.*

object VapingDutyPage extends BasePage {

  val base: String               = redirectUrl.stripSuffix("/")
  private val enrolmentFrontend  = enrolmentUrl.stripSuffix("/")
  private val businessTaxAccount = businessTaxAccountUrl.stripSuffix("/")

  val doYouHaveApprovalIdUrl: String       = s"$base/enrolment/do-you-have-an-approval-id"
  val youNeedAnApprovalIdUrl: String       = s"$base/enrolment/you-need-an-approval-id"
  val alreadyEnrolledUrl: String           = s"$base/enrolment/already-enrolled"
  val enrolmentSignInUrl: String           = s"$base/enrolment/sign-in"
  val enrolmentAccessUrl: String           =
    s"$enrolmentFrontend/HMRC-VPD-ORG/request-access-tax-scheme?continue=$businessTaxAccount"
  val businessAccountUrl: String           = s"business-account"
  val howDoYouWantToBeContactedUrl: String = s"$base/contact-preferences/how-do-you-want-to-be-contacted"
  val confirmYourPostalAddressUrl: String  = s"$base/contact-preferences/review-confirm-address"
  val postalAddressConfirmationUrl: String = s"$base/contact-preferences/postal-address-confirmation"

  def goToUrl(url: String): Unit = {
    get(url)
    fluentWait.until(ExpectedConditions.urlContains(url))
  }

  def signIntoAuth(user: AuthUser, redirectUrl: String = doYouHaveApprovalIdUrl): Unit = {
    get(loginUrl)
    sendKeys(redirectionUrlField, redirectUrl)
    selectByValue(affinityGroupSelect, user.affinityGroup)

    user.enrolment.foreach { e =>
      sendKeys(enrolmentKey, e.enrolmentKey)
      sendKeys(identifierName, e.identifierName)
      sendKeys(identifierValue, e.identifierValue)
    }

    click(submitButton)
  }

  def urlConfirmation(expectedUrl: String): Boolean =
    fluentWait.until { driver =>
      driver.getCurrentUrl.contains(expectedUrl)
    }

  def selectVapingDutyProductsIdRadio(hasVapingProductsId: Boolean): Unit =
    click(if (hasVapingProductsId) yesRadioButton else noRadioButton)
    click(continueButton)

  def clickContinueToBusinessTaxAccount(): Unit =
    click(continueToBTAButton)

  def selectContactPreference(contactPreference: String): Unit =
    click(if (contactPreference == "Post") postContactPreferenceRadioButton else emailContactPreferenceRadioButton)
    click(continueContactPreference)

  def clickConfirmAddress(): Unit =
    click(confirmAddressButton)
}
