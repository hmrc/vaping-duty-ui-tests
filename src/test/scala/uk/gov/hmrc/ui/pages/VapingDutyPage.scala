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

import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.support.ui.ExpectedConditions
import uk.gov.hmrc.selenium.webdriver.Driver
import uk.gov.hmrc.ui.helper.{AuthLoginStubSessionClient, TestOnlyPasscodeClient}
import uk.gov.hmrc.ui.models.AuthUser
import uk.gov.hmrc.ui.pages.VapingDutyLocators.*

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import scala.util.Random

object VapingDutyPage extends BasePage {

  // ---------- Base URLs ----------
  val vapingDutyBase: String                = redirectUrl.stripSuffix("/")
  private val authStubBase: String          = authLoginStubBaseUrl.stripSuffix("/")
  private val emailVerificationBase: String = emailVerificationUrl.stripSuffix("/")
  private val enrolmentFrontendBase: String = enrolmentUrl.stripSuffix("/")
  private val btaBase: String               = businessTaxAccountUrl.stripSuffix("/")

  // Auth-login-stub endpoints
  val ggSignInUrl: String = s"$authStubBase/gg-sign-in"
  val sessionUrl: String  = s"$authStubBase/session"

  // ---------- Test data ----------
  val emailAddressToVerify: String     = randomTestEmail()
  val emailAddressForWrongCode: String = randomTestEmail("IncorrectCode")
  val wrongConfirmationCode: String    = "DNCLRK"

  // ---------- Clients ----------
  private val passcodeClient    = new TestOnlyPasscodeClient()
  private val authSessionClient = new AuthLoginStubSessionClient()

  // ---------- Common route bases ----------
  private val enrolmentBase: String   = s"$vapingDutyBase/enrolment"
  private val contactPrefBase: String = s"$vapingDutyBase/contact-preferences"

  // ---------- Enrolment URLs ----------
  val doYouHaveApprovalIdUrl: String   = s"$enrolmentBase/do-you-have-an-approval-id"
  val youNeedAnApprovalIdUrl: String   = s"$enrolmentBase/you-need-an-approval-id"
  val alreadyEnrolledUrl: String       = s"$enrolmentBase/already-enrolled"
  val administratorRequiredUrl: String = s"$enrolmentBase/organisation-administrator-required"

  val enrolmentAccessUrl: String =
    s"$enrolmentFrontendBase/HMRC-VPD-ORG/request-access-tax-scheme?continue=$btaBase"

  val businessAccountRoute: String = "business-account"

  // ---------- Contact preference URLs ----------
  val howShouldWeContactedYouUrl: String = s"$contactPrefBase/how-should-we-contact-you"
  val checkYourPostalAddressUrl: String  = s"$contactPrefBase/check-your-postal-address"
  val changeYourPostalAddressUrl: String = s"$contactPrefBase/post-continue"
  val enterEmailAddressUrl: String       = s"$contactPrefBase/enter-email-address"

  val emailContactPreferenceConfirmationUrl: String = s"$contactPrefBase/contact-preference-updated"
  val enterToConfirmCodeUrl: String                 = s"$contactPrefBase/confirm-email-address&origin=Vaping+Products+Duty"
  val confirmEmailAddressUrl: String                = s"$contactPrefBase/confirm-email-address"
  val tooManyAttemptsUrl: String                    = s"$contactPrefBase/too-many-attempts"

  def authStubSession(): uk.gov.hmrc.ui.helper.AuthStubSession =
    authSessionClient.getSession(Driver.instance)

  def latestEmailPasscode(email: String): String = {
    val authStub = authStubSession()
    passcodeClient.getLatestPasscode(
      baseUrl = emailVerificationBase,
      email = email,
      authorization = authStub.bearerToken,
      sessionId = authStub.sessionId
    )
  }

  def randomTestEmail(email: String = "autotest"): String = {
    val formatter = DateTimeFormatter.ofPattern("ddMMmmss")
    val timestamp = LocalDateTime.now().format(formatter)
    s"$email$timestamp@example.com"
  }

  def randomConsonantCode(): String = {
    val consonants = "DNCLRK"
    (1 to 5)
      .map(_ => consonants(Random.nextInt(consonants.length)))
      .mkString
  }

  def goToUrl(url: String): Unit = {
    get(url)
    fluentWait.until(ExpectedConditions.urlContains(url))
  }

  def signIntoAuth(user: AuthUser, redirectUrl: String = doYouHaveApprovalIdUrl): Unit = {
    Driver.instance.manage().deleteAllCookies()
    get(ggSignInUrl)
    sendKeys(redirectionUrlField, redirectUrl)
    selectByValue(affinityGroupSelect, user.affinityGroup)

    user.enrolment.foreach { e =>
      sendKeys(enrolmentKey, e.enrolmentKey)
      sendKeys(identifierName, e.identifierName)
      sendKeys(identifierValue, e.identifierValue)
      sendKeys(credIdField, e.credId.getOrElse(""))
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
    waitForElementToBeVisible(emailContactField)
    sendKeys(emailContactField, emailAddress)

    waitForElementToBeVisible(continueContactPreference)
    click(continueContactPreference)

  def submitConfirmationCode(email: String): Unit = {
    val code = latestEmailPasscode(email)
    waitForElementToBeVisible(emailConfirmationCodeField)
    sendKeys(emailConfirmationCodeField, code)

    waitForElementToBeVisible(emailConfirmationCodeConfirmButton)
    click(emailConfirmationCodeConfirmButton)
  }

  private def timeOrigin(): Double =
    Driver.instance
      .asInstanceOf[JavascriptExecutor]
      .executeScript("return performance.timeOrigin;")
      .asInstanceOf[Number]
      .doubleValue()

  private def waitForReload(previousTimeOrigin: Double): Unit = {
    fluentWait.until(_ => timeOrigin() != previousTimeOrigin)

    fluentWait.until { _ =>
      Driver.instance
        .asInstanceOf[JavascriptExecutor]
        .executeScript("return document.readyState;")
        .toString == "complete"
    }
  }

  def submitIncorrectConfirmationCodeSixTimes(wrongCode: String): Unit =
    (1 to 6).foreach { _ =>
      fluentWait.until(ExpectedConditions.elementToBeClickable(emailConfirmationCodeField))

      val before = timeOrigin()

      sendKeys(emailConfirmationCodeField, wrongCode)
      click(emailConfirmationCodeConfirmButton)

      waitForReload(before)
    }
  def confirmCodeHasBeenReceivedAndApproved(): Unit                    =
    waitForElementToBeVisible(saveAndContinueButton)
    click(saveAndContinueButton)
}
