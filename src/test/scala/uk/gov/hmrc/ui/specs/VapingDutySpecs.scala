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
        VapingDutyPage.urlConfirmation(VapingDutyPage.noApprovalIdUrl),
        "Expected to be on the apply for approval page"
      )
    }

    Scenario("Vaping Duty Journey User With Enrolment To Claim", VapingDutyTaggedTest, ZapAccessibility) {
      Given("I authenticate using Government Gateway")
      VapingDutyPage.signIntoAuth(AuthUser.organisation())

      When("User selects yes on VPMA page")
      VapingDutyPage.selectVapingDutyProductsIdRadio(true)

//      Then("I should be on enrolment request access page")
//      assert(
//        VapingDutyPage.urlConfirmation(enrolmentAccessUrl),
//        "Expected to be on the request enrolment access page"
//      )
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
        VapingDutyPage.urlConfirmation(VapingDutyPage.businessAccountUrl),
        "Expected to be on the BTA page"
      )
    }

    Scenario("Vaping Duty Journey User With Agent account", VapingDutyTaggedTest, ZapAccessibility) {
      Given("I authenticate using Government Gateway")
      VapingDutyPage.signIntoAuth(AuthUser.agent(Some(Enrolment.Vpd)))

      Then("I should be on the organisation sign in page")
      assert(
        VapingDutyPage.urlConfirmation(VapingDutyPage.orgSignInUrl),
        "Expected to be on the organisation sign in page"
      )
    }

    Scenario("Vaping Duty Journey User With Individual account", VapingDutyTaggedTest, ZapAccessibility) {
      Given("I authenticate using Government Gateway")
      VapingDutyPage.signIntoAuth(AuthUser.individual(Some(Enrolment.Vpd)))

      Then("I should be on the organisation sign in page")
      assert(
        VapingDutyPage.urlConfirmation(VapingDutyPage.orgSignInUrl),
        "Expected to be on the organisation sign in page"
      )
    }
  }
}
