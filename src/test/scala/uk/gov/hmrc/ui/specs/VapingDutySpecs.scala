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
      Given("I authenticate using Government Gateway")
      VapingDutyPage.signIntoAuth(AuthUser.organisation())

      When("User selects no on VPMA page")
      VapingDutyPage.selectVapingDutyProductsIdRadio(false)

      Then("I should be on apply for approval page")
      assert(
        VapingDutyPage.urlConfirmation(VapingDutyPage.youNeedAnApprovalIdUrl),
        "Expected to be on the apply for approval page"
      )
    }

    Scenario(
      "Vaping Duty Journey User already enrolled accesses index page",
      VapingDutyTaggedTest,
      ZapAccessibility
    ) {
      Given("I authenticate using Government Gateway")
      VapingDutyPage.signIntoAuth(AuthUser.organisation(Some(Enrolment.Vpd)), VapingDutyPage.vapingDutyBase)

      Then("I should be on the vaping duty index page")
      assert(
        VapingDutyPage.urlConfirmation(VapingDutyPage.vapingDutyBase),
        "Expected to be on the vaping duty index page"
      )
    }

    Scenario("Vaping Duty Journey User With Enrolment To Claim", VapingDutyTaggedTest, ZapAccessibility) {
      Given("I authenticate using Government Gateway")
      VapingDutyPage.signIntoAuth(AuthUser.organisation())

      When("User selects yes on VPMA page")
      VapingDutyPage.selectVapingDutyProductsIdRadio(true)

      Then("I should be on enrolment request access page")
      assert(
        VapingDutyPage.urlConfirmation(VapingDutyPage.enrolmentAccessUrl),
        "Expected to be on the request enrolment access page"
      )
    }

    Scenario("Vaping Duty Journey User With Enrolment Already Claimed", VapingDutyTaggedTest, ZapAccessibility) {
      Given("I authenticate using Government Gateway")
      VapingDutyPage.signIntoAuth(AuthUser.organisation(Some(Enrolment.Vpd)))

      Then("I should be on already enrolled page")
      assert(
        VapingDutyPage.urlConfirmation(VapingDutyPage.alreadyEnrolledUrl),
        "Expected to be on the already enrolled"
      )
      When("I click on continue to your business tax account button")
      VapingDutyPage.clickContinueToBusinessTaxAccount()

      Then("I should be on the BTA page")
      assert(
        VapingDutyPage.urlConfirmation(VapingDutyPage.businessAccountRoute),
        "Expected to be on the BTA page"
      )
    }

    Scenario("Vaping Duty Journey User With Agent account", VapingDutyTaggedTest, ZapAccessibility) {
      Given("I authenticate using Government Gateway")
      VapingDutyPage.signIntoAuth(AuthUser.agent())

      Then("I should be on the organisation sign in page")
      assert(
        VapingDutyPage.urlConfirmation(VapingDutyPage.administratorRequiredUrl),
        "Expected to be on the organisation sign in page"
      )
    }

    Scenario("Vaping Duty Journey User With Individual account", VapingDutyTaggedTest, ZapAccessibility) {
      Given("I authenticate using Government Gateway")
      VapingDutyPage.signIntoAuth(AuthUser.individual())

      Then("I should be on the organisation sign in page")
      assert(
        VapingDutyPage.urlConfirmation(VapingDutyPage.administratorRequiredUrl),
        "Expected to be on the organisation sign in page"
      )
    }

    Scenario(
      "Vaping Duty Journey User updates contact preference to post",
      VapingDutyTaggedTest,
      ZapAccessibility
    ) {
      Given("I authenticate using Government Gateway and redirect to tell us how we should contact you page ")
      VapingDutyPage.signIntoAuth(
        AuthUser.organisation(Some(Enrolment.contactPreferencePost)),
        VapingDutyPage.howDoYouWantToBeContactedUrl
      )

      Then("I should be on the vaping duty tell us how we should contact you page")
      assert(
        VapingDutyPage.urlConfirmation(VapingDutyPage.howDoYouWantToBeContactedUrl),
        "Expected to be on the tell us how we should contact you page"
      )

      When("I click on the post radio button")
      VapingDutyPage.selectContactPreference("Post")

      Then("I should be on the confirm your postal address page")
      assert(
        VapingDutyPage.urlConfirmation(VapingDutyPage.confirmYourPostalAddressUrl),
        "Expected to be on the confirm your postal address page"
      )

      When("I click on the confirm address button")
      VapingDutyPage.clickConfirmAddress()

      Then("I should be on the your contact preference has been updated page")
      assert(
        VapingDutyPage.urlConfirmation(VapingDutyPage.postalAddressConfirmationUrl),
        "Expected to be on the your contact preference has been updated page"
      )
    }

    Scenario(
      "Vaping Duty Journey User updates contact preference to email",
      VapingDutyTaggedTest,
      ZapAccessibility
    ) {
      Given("I authenticate using Government Gateway and redirect to tell us how we should contact you page ")
      VapingDutyPage.signIntoAuth(
        AuthUser.organisation(Some(Enrolment.contactPreferenceEmailAlreadyVerified)),
        VapingDutyPage.howDoYouWantToBeContactedUrl
      )

      Then("I should be on the vaping duty tell us how we should contact you page")
      assert(
        VapingDutyPage.urlConfirmation(VapingDutyPage.howDoYouWantToBeContactedUrl),
        "Expected to be on the tell us how we should contact you page"
      )

      When("I click on the email radio button")
      VapingDutyPage.selectContactPreference("Email")

      Then("I should be on the what email address should we use to contact you page")
      assert(
        VapingDutyPage.urlConfirmation(VapingDutyPage.enterEmailAddressUrl),
        "Expected to be on the what email address should we use to contact you page"
      )

      When("I enter a valid email address and click continue")
      VapingDutyPage.submitEmailAddress(VapingDutyPage.emailAddressToVerify)

      Then("I should be on the enter code to confirm your email address page")
      assert(
        VapingDutyPage.urlConfirmation(VapingDutyPage.enterToConfirmCodeUrl),
        "Expected to be on the enter code to confirm your email address"
      )

      When("I get and submit the confirmation code")
      VapingDutyPage.submitConfirmationCode(VapingDutyPage.emailAddressToVerify)

      Then("I should be on the your confirmation code has been received and approved")
      assert(
        VapingDutyPage.urlConfirmation(VapingDutyPage.submitEmailUrl),
        "Expected to be on the your confirmation code has been received and approved"
      )

      When("I click submit on your confirmation code has been received and approved")
      VapingDutyPage.confirmCodeHasBeenReceivedAndApproved()

      Then("I should be on the your contact preference has been updated page")
      assert(
        VapingDutyPage.urlConfirmation(VapingDutyPage.emailContactPreferenceConfirmationUrl),
        "Expected to be on the your contact preference has been updated page"
      )

      When("I redirected to how would you like to be contacted page")
      VapingDutyPage.goToUrl(VapingDutyPage.howDoYouWantToBeContactedUrl)

      Then("I should be on the vaping duty tell us how we should contact you page")
      assert(
        VapingDutyPage.urlConfirmation(VapingDutyPage.howDoYouWantToBeContactedUrl),
        "Expected to be on the tell us how we should contact you page"
      )

      When("I click on the email radio button")
      VapingDutyPage.selectContactPreference("Email")

      Then("I should be on the what email address should we use to contact you page")
      assert(
        VapingDutyPage.urlConfirmation(VapingDutyPage.enterEmailAddressUrl),
        "Expected to be on the what email address should we use to contact you page"
      )

      When("I enter a valid email address and click continue")
      VapingDutyPage.submitEmailAddress(VapingDutyPage.emailAddressToVerify)

      Then("I should be on the you have asked us to use the email address page")
      assert(
        VapingDutyPage.urlConfirmation(VapingDutyPage.submitEmailPreviouslyVerifiedUrl),
        "Expected to be on the you have asked us to use the email address page"
      )

      When("I click submit on your confirmation code has been received and approved")
      VapingDutyPage.confirmCodeHasBeenReceivedAndApproved()

//      Then("I should be on the your contact preference has been updated page")
//      assert(
//        VapingDutyPage.urlConfirmation(VapingDutyPage.emailContactPreferenceConfirmationUrl),
//        "Expected to be on the your contact preference has been updated page"
//      )
    }

    Scenario(
      "Vaping Duty Journey User attempts confirmation code 5 times",
      VapingDutyTaggedTest,
      ZapAccessibility
    ) {
      Given("I authenticate using Government Gateway and redirect to tell us how we should contact you page ")
      VapingDutyPage.signIntoAuth(
        AuthUser.organisation(Some(Enrolment.contactPreferenceEmail)),
        VapingDutyPage.howDoYouWantToBeContactedUrl
      )

      Then("I should be on the vaping duty tell us how we should contact you page")
      assert(
        VapingDutyPage.urlConfirmation(VapingDutyPage.howDoYouWantToBeContactedUrl),
        "Expected to be on the tell us how we should contact you page"
      )

      When("I click on the email radio button")
      VapingDutyPage.selectContactPreference("Email")

      Then("I should be on the what email address should we use to contact you page")
      assert(
        VapingDutyPage.urlConfirmation(VapingDutyPage.enterEmailAddressUrl),
        "Expected to be on the what email address should we use to contact you page"
      )

      When("I enter a valid email address and click continue")
      VapingDutyPage.submitEmailAddress(VapingDutyPage.emailAddressForWrongCode)

      Then("I should be on the enter code to confirm your email address page")
      assert(
        VapingDutyPage.urlConfirmation(VapingDutyPage.enterToConfirmCodeUrl),
        "Expected to be on the enter code to confirm your email address"
      )

      When("I get and submit the wrong confirmation code 6 times")
      VapingDutyPage.submitIncorrectConfirmationCodeSixTimes(VapingDutyPage.wrongConfirmationCode)

      Then("I should be on the you have reached the maximum number of attempts to enter a confirmation code")
      assert(
        VapingDutyPage.urlConfirmation(VapingDutyPage.accountLockOutUrl),
        "Expected to be on the you have reached the maximum number of attempts to enter a confirmation code"
      )

    }

  }
}
