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

import org.openqa.selenium.By
import uk.gov.hmrc.configuration.TestEnvironment

object VapingDutyLocators {

  // ---------- Environment URLs ----------
  val authLoginStubBaseUrl: String  = TestEnvironment.url("auth-login-stub")
  val redirectUrl: String           = TestEnvironment.url("vaping-duty-frontend")
  val enrolmentUrl: String          = TestEnvironment.url("enrolment-management-frontend")
  val businessTaxAccountUrl: String = TestEnvironment.url("business-tax-account")
  val emailVerificationUrl: String  = TestEnvironment.url("email-verification")

  // ---------- Auth Login Stub ----------
  val credIdField: By         = By.id("authorityId")
  val redirectionUrlField: By = By.id("redirectionUrl")
  val affinityGroupSelect: By = By.id("affinityGroupSelect")
  val enrolmentKey: By        = By.id("enrolment[0].name")
  val identifierName: By      = By.id("input-0-0-name")
  val identifierValue: By     = By.id("input-0-0-value")
  val submitButton: By        = By.cssSelector("#inputForm > input:nth-child(37)")
  val ggSignInButton: By      = By.id("vpd-gg-logon-1")

  // ---------- Common Buttons ----------
  val continueButton: By         = By.className("govuk-button")
  val saveAndContinueButton: By  = By.xpath("//button[contains(normalize-space(),'Save and continue')]")
  val confirmAndSubmitButton: By = By.xpath("//*[@type='submit']")
  val continueToBTAButton: By    = By.cssSelector("#main-content > div > div > a")
  val continueBeforeYouStart: By = By.cssSelector("#main-content > div > div > a")

  // ---------- Radio Buttons ----------
  val yesRadioButton: By = By.id("value")
  val noRadioButton: By  = By.cssSelector("#value-no")

  // ---------- View Returns Links ----------
  val firstOutstandingReturnLink: By =
    By.cssSelector("ul.govuk-task-list a.govuk-task-list__link[href*='before-you-start']")

  def completedReturnLinkByMonth(month: String): By =
    By.xpath(
      s"//ul[contains(@class,'govuk-task-list')]" +
        s"//a[contains(@class,'govuk-task-list__link') and normalize-space()='$month']"
    )

  val firstCompletedReturnLink: By =
    By.cssSelector("ul.govuk-task-list a.govuk-task-list__link[href*='view-your-returns/']")

  // ---------- Task List Links ----------
  val submitReturnLink: By         = By.id("submit-link")
  val viewReturnLink: By           = By.id("view-link")
  val declareDutyLink: By          = By.xpath("//a[normalize-space(text())='Tell us if you need to declare duty']")
  val checkYourAnswersLink: By     = By.xpath("//a[normalize-space(text())='Check your answers and submit return']")
  val dutySuspendedLink: By        =
    By.xpath("//a[contains(@href, '/vaping-duty/complete-return/duty-suspended/suspended-products')]")
  val spoiltAdjustmentsLink: By    = By.xpath("//a[contains(@href,'/complete-return/adjustment/declare-spoilt-products')]")
  val overUnderAdjustmentsLink: By =
    By.xpath("//a[contains(@href,'/complete-return/adjustment/declare-adjustments')]")

  // ---------- Declare Duty ----------
  val vapingLiquidField: By     = By.id("value")
  val changeDeclareDutyLink: By =
    By.cssSelector("a.govuk-link[href*='enter-amount-released']")

  // ---------- Duty Suspended ----------
  val vapingLiquidReceivedField: By = By.id("volumeReceived")
  val vapingLiquidMovedField: By    = By.id("volumeMoved")
  val changeDutySuspendedLink: By   =
    By.cssSelector("a.govuk-link[href*='/duty-suspended/enter-received-or-moved-amount']")

  // ---------- Spoilt Adjustments ----------
  val selectSpoiltPeriodLink: By = By.xpath("//a[contains(@href,'enter-spoilt-amount')]")
  val spoiltAmountField: By      = By.id("value")

  // ---------- Over / Under Adjustments ----------
  val firstAdjustmentPeriodLink: By =
    By.cssSelector("ul.govuk-task-list a.govuk-task-list__link[href*='enter-over-or-under-declaration-amount']")

  val changeAdjustmentVolumeLink: By =
    By.cssSelector("a.govuk-link[href*='enter-over-or-under-declaration-amount']")

  def adjustmentPeriodLinkByMonth(month: String): By =
    By.xpath(
      s"//ul[contains(@class,'govuk-task-list')]" +
        s"//a[contains(@class,'govuk-task-list__link') and normalize-space()='$month']"
    )

  val underDeclaredAdjustmentRadio: By = By.id("adjustmentType-2")
  val overDeclaredAdjustmentRadio: By  = By.id("adjustmentType")
  val underDeclaredVolumeField: By     = By.id("underDeclaredVolume")
  val overDeclaredVolumeField: By      = By.id("overDeclaredVolume")

  // ---------- Contact Preferences ----------
  val postContactPreferenceRadioButton: By  = By.cssSelector("#value_1")
  val emailContactPreferenceRadioButton: By = By.cssSelector("#value_0")
  val continueContactPreference: By         = By.xpath("//button[contains(normalize-space(),'Continue')]")
  val confirmAddressButton: By              = By.cssSelector("#main-content > div > div > form > button")
  val emailContactField: By                 = By.id("email")

  // ---------- Email Verification ----------
  val emailConfirmationCodeField: By         = By.cssSelector("#passcode")
  val emailConfirmationCodeConfirmButton: By = By.xpath("//button[contains(normalize-space(),'Confirm')]")

  // ---------- Return Declaration ----------
  val declarationFullNameField: By = By.id("fullName")
  val declarationCapacityField: By = By.id("capacityInWhichSigned")
  val declarationEmailField: By    = By.id("email")

}
