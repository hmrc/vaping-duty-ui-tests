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
import uk.gov.hmrc.ui.helper.{AuthLoginStubSessionClient, TestOnlyPasscodeClient}
import uk.gov.hmrc.ui.models.AuthUser
import uk.gov.hmrc.ui.pages.VapingDutyLocators.*

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object VapingDutyPage extends BasePage {

  // ---------- Base URLs ----------
  val vapingDutyBaseUrl: String          = redirectUrl.stripSuffix("/")
  val emailVerificationBaseUrl: String   = emailVerificationUrl.stripSuffix("/")
  private val enrolmentFrontend: String  = enrolmentUrl.stripSuffix("/")
  private val businessTaxAccount: String = businessTaxAccountUrl.stripSuffix("/")

  // ---------- Test data ----------
  val emailAddressToVerify: String = randomTestEmail()

  // ---------- Clients ----------
  private val passcodeClient    = new TestOnlyPasscodeClient()
  private val authSessionClient = new AuthLoginStubSessionClient()

  // ---------- Enrolment URLs ----------
  val doYouHaveApprovalIdUrl: String = s"$vapingDutyBaseUrl/enrolment/do-you-have-an-approval-id"
  val youNeedAnApprovalIdUrl: String = s"$vapingDutyBaseUrl/enrolment/you-need-an-approval-id"
  val alreadyEnrolledUrl: String     = s"$vapingDutyBaseUrl/enrolment/already-enrolled"
  val enrolmentSignInUrl: String     = s"$vapingDutyBaseUrl/enrolment/sign-in"
  val businessAccountUrl: String     = "business-account"
  val enrolmentAccessUrl: String     =
    s"$enrolmentFrontend/HMRC-VPD-ORG/request-access-tax-scheme?continue=$businessTaxAccount"

  // ---------- Contact preference URLs ----------
  private val contactPreferencesBase = s"$vapingDutyBaseUrl/contact-preferences"

  val howDoYouWantToBeContactedUrl: String =
    s"$contactPreferencesBase/how-do-you-want-to-be-contacted"

  val confirmYourPostalAddressUrl: String =
    s"$contactPreferencesBase/review-confirm-address"

  val enterEmailAddressUrl: String =
    s"$contactPreferencesBase/enter-email-address"

  val postalAddressConfirmationUrl: String =
    s"$contactPreferencesBase/postal-address-confirmation"

  val emailContactPreferenceConfirmationUrl: String =
    s"$contactPreferencesBase/email-confirmation"

  val enterToConfirmCodeUrl: String =
    s"$emailContactPreferenceConfirmationUrl&origin=Vaping+Products+Duty"

  def authStubSession(): uk.gov.hmrc.ui.helper.AuthStubSession =
    authSessionClient.getSession(Driver.instance)

  def latestEmailPasscode(email: String): String = {
    val authStub = authStubSession()
    passcodeClient.getLatestPasscode(
      baseUrl = emailVerificationBaseUrl,
      email = email,
      authorization = authStub.bearerToken,
      sessionId = authStub.sessionId
    )
  }

  def randomTestEmail(): String = {
    val formatter = DateTimeFormatter.ofPattern("ddMMmmss")
    val timestamp = LocalDateTime.now().format(formatter)
    s"autotest$timestamp@example.com"
  }

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

  def submitEmailAddress(emailAddress: String): Unit =
    sendKeys(emailContactField, emailAddress)
    click(continueContactPreference)

  def submitConfirmationCode(email: String): Unit = {
    val code = latestEmailPasscode(email)
    sendKeys(emailConfirmationCodeField, code)
    click(emailConfirmationCodeConfirmButton)
  }
}
