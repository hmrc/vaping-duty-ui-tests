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
import uk.gov.hmrc.ui.specs.tags.{ContactPreference, VapingDutyTest, ZapAccessibility}

class ContactPreferenceSpecs extends BaseSpec {

  Feature("Vaping Duty Contact Preference Tests") {

    Scenario(
      "Vaping Duty Journey User updates contact preference from email to post",
      ContactPreference,
      VapingDutyTest,
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
      ContactPreference,
      VapingDutyTest,
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
      ContactPreference,
      VapingDutyTest,
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
      ContactPreference,
      VapingDutyTest,
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
  }
}
