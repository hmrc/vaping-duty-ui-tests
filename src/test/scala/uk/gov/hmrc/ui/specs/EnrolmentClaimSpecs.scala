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
import uk.gov.hmrc.ui.specs.tags.{EnrolmentClaim, VapingDutyTest, ZapAccessibility}

class EnrolmentClaimSpecs extends BaseSpec {

  Feature("Vaping Duty Enrolment Claim Tests") {

    Scenario("Vaping Duty Journey User Without Enrolment To Claim", EnrolmentClaim, VapingDutyTest, ZapAccessibility) {
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

    Scenario("Vaping Duty Journey User With Enrolment To Claim", EnrolmentClaim, VapingDutyTest, ZapAccessibility) {
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

    Scenario(
      "Vaping Duty Journey User With Enrolment Already Claimed",
      EnrolmentClaim,
      VapingDutyTest,
      ZapAccessibility
    ) {
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

    Scenario("Vaping Duty Journey User With Agent account", EnrolmentClaim, VapingDutyTest, ZapAccessibility) {
      Given("User authenticates using Government Gateway")
      VapingDutyPage.signIntoAuth(AuthUser.agent())

      Then("User should be on the organisation sign in page")
      assert(
        VapingDutyPage.urlConfirmation(VapingDutyPage.administratorRequiredUrl),
        "Expected to be on the organisation sign in page"
      )
    }

    Scenario("Vaping Duty Journey User With Individual account", EnrolmentClaim, VapingDutyTest, ZapAccessibility) {
      Given("User authenticates using Government Gateway")
      VapingDutyPage.signIntoAuth(AuthUser.individual())

      Then("User should be on the organisation sign in page")
      assert(
        VapingDutyPage.urlConfirmation(VapingDutyPage.administratorRequiredUrl),
        "Expected to be on the organisation sign in page"
      )
    }

  }
}
