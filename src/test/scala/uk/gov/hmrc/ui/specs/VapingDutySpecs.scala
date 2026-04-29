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

package uk.gov.hmrc.ui.specs

import uk.gov.hmrc.ui.models.{AuthUser, Enrolment}
import uk.gov.hmrc.ui.pages.VapingDutyPage
import uk.gov.hmrc.ui.specs.tags.{VapingDutyTaggedTest, ZapAccessibility}

class VapingDutySpecs extends BaseSpec {

  Feature("VapingDuty Tests") {

    Scenario("Vaping Duty Journey User Without Enrolment To Claim", VapingDutyTaggedTest, ZapAccessibility) {
      Given("User authenticates using Government Gateway")
      VapingDutyPage.signIntoAuth(AuthUser.organisation())

      When("User selects no on VPMA page")
      VapingDutyPage.selectVapingDutyProductsIdRadio(false)

      Then("User should be on apply for approval page")
      assert(
        VapingDutyPage.urlConfirmation(VapingDutyPage.youNeedAnApprovalIdUrl),
        "Expected to be on the apply for approval page"
      )
    }

    Scenario("Vaping Duty Journey User With Enrolment To Claim", VapingDutyTaggedTest, ZapAccessibility) {
      Given("User authenticates using Government Gateway")
      VapingDutyPage.signIntoAuth(AuthUser.organisation())

      When("User selects yes on VPMA page")
      VapingDutyPage.selectVapingDutyProductsIdRadio(true)

      Then("User should be on enrolment request access page")
      assert(
        VapingDutyPage.urlConfirmation(VapingDutyPage.enrolmentAccessUrl),
        "Expected to be on the request enrolment access page"
      )
    }

    Scenario("Vaping Duty Journey User With Enrolment Already Claimed", VapingDutyTaggedTest, ZapAccessibility) {
      Given("User authenticates using Government Gateway")
      VapingDutyPage.signIntoAuth(AuthUser.organisation(Some(Enrolment.Vpd)))

      Then("User should be on already enrolled page")
      assert(
        VapingDutyPage.urlConfirmation(VapingDutyPage.alreadyEnrolledUrl),
        "Expected to be on the already enrolled"
      )
      When("User clicks on continue to your business tax account button")
      VapingDutyPage.clickContinueToBusinessTaxAccount()

      Then("User should be on the BTA page")
      assert(
        VapingDutyPage.urlConfirmation(VapingDutyPage.businessAccountRoute),
        "Expected to be on the BTA page"
      )
    }

    Scenario("Vaping Duty Journey User With Agent account", VapingDutyTaggedTest, ZapAccessibility) {
      Given("User authenticates using Government Gateway")
      VapingDutyPage.signIntoAuth(AuthUser.agent())

      Then("User should be on the organisation sign in page")
      assert(
        VapingDutyPage.urlConfirmation(VapingDutyPage.administratorRequiredUrl),
        "Expected to be on the organisation sign in page"
      )
    }

    Scenario("Vaping Duty Journey User With Individual account", VapingDutyTaggedTest, ZapAccessibility) {
      Given("User authenticates using Government Gateway")
      VapingDutyPage.signIntoAuth(AuthUser.individual())

      Then("User should be on the organisation sign in page")
      assert(
        VapingDutyPage.urlConfirmation(VapingDutyPage.administratorRequiredUrl),
        "Expected to be on the organisation sign in page"
      )
    }

    Scenario(
      "Vaping Duty Journey User updates contact preference from email to post",
      VapingDutyTaggedTest,
      ZapAccessibility
    ) {
      Given("User authenticates using Government Gateway and redirect to tell us how we should contact you page ")
      VapingDutyPage.signIntoAuth(
        AuthUser.organisation(Some(Enrolment.contactPreferenceEmailToPost)),
        VapingDutyPage.howShouldWeContactedYouUrl
      )

      Then("User should be on the vaping duty tell us how we should contact you page")
      assert(
        VapingDutyPage.urlConfirmation(VapingDutyPage.howShouldWeContactedYouUrl),
        "Expected to be on the tell us how we should contact you page"
      )

      When("User clicks on the post radio button")
      VapingDutyPage.selectContactPreference("Post")

      Then("User should be on the confirm your postal address page")
      assert(
        VapingDutyPage.urlConfirmation(VapingDutyPage.checkYourPostalAddressUrl),
        "Expected to be on the confirm your postal address page"
      )

      When("User clicks on the confirm address button")
      VapingDutyPage.clickConfirmAddress()

      Then("User should be on the your contact preference has been updated page")
      assert(
        VapingDutyPage.urlConfirmation(VapingDutyPage.emailContactPreferenceConfirmationUrl),
        "Expected to be on the your contact preference has been updated page"
      )
    }

    Scenario(
      "Vaping Duty Journey User updates contact preference from post to post",
      VapingDutyTaggedTest,
      ZapAccessibility
    ) {
      Given("User authenticates using Government Gateway and redirect to tell us how we should contact you page ")
      VapingDutyPage.signIntoAuth(
        AuthUser.organisation(Some(Enrolment.contactPreferencePostToPost)),
        VapingDutyPage.howShouldWeContactedYouUrl
      )

      Then("User should be on the vaping duty tell us how we should contact you page")
      assert(
        VapingDutyPage.urlConfirmation(VapingDutyPage.howShouldWeContactedYouUrl),
        "Expected to be on the tell us how we should contact you page"
      )

      When("User clicks on the post radio button")
      VapingDutyPage.selectContactPreference("Post")

      Then("User should be on the confirm your postal address page")
      assert(
        VapingDutyPage.urlConfirmation(VapingDutyPage.changeYourPostalAddressUrl),
        "Expected to be on the confirm your postal address page"
      )
    }

    Scenario(
      "Vaping Duty Journey User updates contact preference to email",
      VapingDutyTaggedTest,
      ZapAccessibility
    ) {
      Given("User authenticates using Government Gateway and redirect to tell us how we should contact you page ")
      VapingDutyPage.signIntoAuth(
        AuthUser.organisation(Some(Enrolment.contactPreferenceEmailAlreadyVerified)),
        VapingDutyPage.howShouldWeContactedYouUrl
      )

      Then("User should be on the vaping duty tell us how we should contact you page")
      assert(
        VapingDutyPage.urlConfirmation(VapingDutyPage.howShouldWeContactedYouUrl),
        "Expected to be on the tell us how we should contact you page"
      )

      When("User clicks on the email radio button")
      VapingDutyPage.selectContactPreference("Email")

      Then("User should be on the what email address should we use to contact you page")
      assert(
        VapingDutyPage.urlConfirmation(VapingDutyPage.enterEmailAddressUrl),
        "Expected to be on the what email address should we use to contact you page"
      )

      When("User enter a valid email address and click continue")
      VapingDutyPage.submitEmailAddress(VapingDutyPage.emailAddressToVerify)

      Then("User should be on the enter code to confirm your email address page")
      assert(
        VapingDutyPage.urlConfirmation(VapingDutyPage.enterToConfirmCodeUrl),
        "Expected to be on the enter code to confirm your email address"
      )

      When("User gets and submit the confirmation code")
      VapingDutyPage.submitConfirmationCode(VapingDutyPage.emailAddressToVerify)

      Then("User should be on the your confirmation code has been received and approved")
      assert(
        VapingDutyPage.urlConfirmation(VapingDutyPage.confirmEmailAddressUrl),
        "Expected to be on the confirm your email address page"
      )

      When("User clicks save and continue on confirm your email address page")
      VapingDutyPage.confirmCodeHasBeenReceivedAndApproved()

      Then("User should be on the your contact preference has been updated page")
      assert(
        VapingDutyPage.urlConfirmation(VapingDutyPage.emailContactPreferenceConfirmationUrl),
        "Expected to be on the your contact preference has been updated page"
      )

      When("User redirected to how would you like to be contacted page")
      VapingDutyPage.goToUrl(VapingDutyPage.howShouldWeContactedYouUrl)

      Then("User should be on the vaping duty tell us how we should contact you page")
      assert(
        VapingDutyPage.urlConfirmation(VapingDutyPage.howShouldWeContactedYouUrl),
        "Expected to be on the tell us how we should contact you page"
      )

      When("User clicks on the email radio button")
      VapingDutyPage.selectContactPreference("Email")

      Then("User should be on the what email address should we use to contact you page")
      assert(
        VapingDutyPage.urlConfirmation(VapingDutyPage.enterEmailAddressUrl),
        "Expected to be on the what email address should we use to contact you page"
      )

      When("User enters a valid email address and click continue")
      VapingDutyPage.submitEmailAddress(VapingDutyPage.emailAddressToVerify)

      Then("User should be on the you have asked us to use the email address page")
      assert(
        VapingDutyPage.urlConfirmation(VapingDutyPage.confirmEmailAddressUrl),
        "Expected to be on the confirm your email address page"
      )

      When("User clicks save and continue on your confirmation code has been received and approved")
      VapingDutyPage.confirmCodeHasBeenReceivedAndApproved()

      Then("User should be on the your contact preference has been updated page")
      assert(
        VapingDutyPage.urlConfirmation(VapingDutyPage.emailContactPreferenceConfirmationUrl),
        "Expected to be on the your contact preference has been updated page"
      )
    }

    Scenario(
      "Vaping Duty Journey User attempts confirmation code 5 times",
      VapingDutyTaggedTest,
      ZapAccessibility
    ) {
      Given("User authenticates using Government Gateway and redirect to tell us how we should contact you page ")
      VapingDutyPage.signIntoAuth(
        AuthUser.organisation(Some(Enrolment.contactPreferenceEmail)),
        VapingDutyPage.howShouldWeContactedYouUrl
      )

      Then("User should be on the vaping duty tell us how we should contact you page")
      assert(
        VapingDutyPage.urlConfirmation(VapingDutyPage.howShouldWeContactedYouUrl),
        "Expected to be on the tell us how we should contact you page"
      )

      When("User clicks on the email radio button")
      VapingDutyPage.selectContactPreference("Email")

      Then("User should be on the what email address should we use to contact you page")
      assert(
        VapingDutyPage.urlConfirmation(VapingDutyPage.enterEmailAddressUrl),
        "Expected to be on the what email address should we use to contact you page"
      )

      When("User enters a valid email address and click continue")
      VapingDutyPage.submitEmailAddress(VapingDutyPage.emailAddressForWrongCode)

      Then("User should be on the enter code to confirm your email address page")
      assert(
        VapingDutyPage.urlConfirmation(VapingDutyPage.enterToConfirmCodeUrl),
        "Expected to be on the enter code to confirm your email address"
      )

      When("User gets and submit the wrong confirmation code 6 times")
      VapingDutyPage.submitIncorrectConfirmationCodeSixTimes(VapingDutyPage.wrongConfirmationCode)

      Then("User should be on the you have reached the maximum number of attempts to enter a confirmation code")
      assert(
        VapingDutyPage.urlConfirmation(VapingDutyPage.tooManyAttemptsUrl),
        "Expected to be on the you have reached the maximum number of attempts to enter a confirmation code"
      )

    }

    Scenario("Vaping Duty Journey user with no duty to declare", VapingDutyTaggedTest, ZapAccessibility) {
      Given("User authenticates using Government Gateway and user redirects to before you start page")
      VapingDutyPage.signIntoAuth(AuthUser.organisation(Some(Enrolment.Vpd)), VapingDutyPage.beforeYouStartPageUrl)

      Then("User should be on before you start page")
      assert(
        VapingDutyPage.urlConfirmation(VapingDutyPage.beforeYouStartPageUrl),
        "Expected to be on the before you start page"
      )

      When("User Clicks on continue on before you start page")
      VapingDutyPage.clickContinueOnBeforeYouStartPage()

      Then("User should be on task list page")
      assert(
        VapingDutyPage.urlConfirmation(VapingDutyPage.taskListUrl),
        "Expected to be on the task list page"
      )

      When("User clicks on Tell us if you need to declare duty")
      VapingDutyPage.clickLinkFromTaskList("declareDuty")

      Then("User should be on Do you need to declare vaping products for duty page")
      assert(
        VapingDutyPage.urlConfirmation(VapingDutyPage.declareDutyUrl),
        "Expected to be on the declare duty question page"
      )

      When("User selects no on do you need to declare vaping products for duty page")
      VapingDutyPage.selectDeclareVapingProductsForDutyRadio(false)

      Then("User should be on task list page")
      assert(
        VapingDutyPage.urlConfirmation(VapingDutyPage.taskListUrl),
        "Expected to be on the task list page"
      )
    }

    Scenario("Vaping Duty Journey submit return journey", VapingDutyTaggedTest, ZapAccessibility) {
      Given("User authenticates using Government Gateway and user redirects to before you start page")
      VapingDutyPage.signIntoAuth(
        AuthUser.organisation(Some(Enrolment.contactPreferenceEmailToPost)),
        VapingDutyPage.beforeYouStartPageUrl
      )

      Then("User should be on before you start page")
      assert(
        VapingDutyPage.urlConfirmation(VapingDutyPage.beforeYouStartPageUrl),
        "Expected to be on the before you start page"
      )

      When("User Clicks on continue on before you start page")
      VapingDutyPage.clickContinueOnBeforeYouStartPage()

      Then("User should be on task list page")
      assert(
        VapingDutyPage.urlConfirmation(VapingDutyPage.taskListUrl),
        "Expected to be on the task list page"
      )

      When("User clicks on Tell us if you need to declare duty")
      VapingDutyPage.clickLinkFromTaskList("declareDuty")

      Then("User should be on Do you need to declare vaping products for duty page")
      assert(
        VapingDutyPage.urlConfirmation(VapingDutyPage.declareDutyUrl),
        "Expected to be on the declare duty question page"
      )

      When("User selects yes on do you need to declare vaping products for duty page")
      VapingDutyPage.selectDeclareVapingProductsForDutyRadio(true)

      Then("User should be on the how much vaping products released do you need to declare?")
      assert(
        VapingDutyPage.urlConfirmation(VapingDutyPage.amountOfVapingProductsReleasedUrl),
        "Expected to be on the How much vaping products released do you need to declare?"
      )

      When("User enters an amount and click continue")
      VapingDutyPage.submitTotalMillilitresOfVapingLiquid("1000")

      Then("User should be on task list page")
      assert(
        VapingDutyPage.urlConfirmation(VapingDutyPage.taskListUrl),
        "Expected to be on the task list page"
      )
      When("User clicks on check your answers and submit return link")
      VapingDutyPage.clickLinkFromTaskList("checkYourAnswers")

      Then("User should be on the check your answers page")
      assert(
        VapingDutyPage.urlConfirmation(VapingDutyPage.checkYourAnswersUrl),
        "Expected to be on the check your answers page"
      )

      When("User clicks confirm and submit on the check your answers page")
      VapingDutyPage.clickConfirmAndSubmit()

      Then("User should be on the return submitted confirmation page")
      assert(
        VapingDutyPage.urlConfirmation(VapingDutyPage.returnSubmittedUrl),
        "Expected to be on the return submitted confirmation page"
      )
    }
  }
}
