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

import uk.gov.hmrc.ui.pages.VapingDutyPage
import uk.gov.hmrc.ui.specs.tags.{VapingDutyTaggedTest, ZapAccessibility}

class VapingDutySpecs extends BaseSpec {

  Feature("VapingDuty Tests") {

    Scenario("Vaping Duty Journey User Without Enrolment To Claim", VapingDutyTaggedTest, ZapAccessibility) {
      Given("I authenticate using Government Gateway")
      VapingDutyPage.signIntoAuth("VPPAIX", "Organisation", "http://localhost:8140/vaping-duty/enrolment/approval-id")

      When("User selects no on VPMA page")
      VapingDutyPage.SelectVapingDutyProductsIdRadio(false)

      Then("I should be on apply for approval page")
      assert(
        VapingDutyPage.confirmation(
          "http://localhost:8140/vaping-duty/enrolment/no-approval-id"
        ),
        "Expected to be on the apply for approval page"
      )
    }

    Scenario("Vaping Duty Journey User With Enrolment To Claim", VapingDutyTaggedTest, ZapAccessibility) {
      Given("I authenticate using Government Gateway")
      VapingDutyPage.signIntoAuth("VPPAIX", "Organisation", "http://localhost:8140/vaping-duty/enrolment/approval-id")

      When("User selects yes on VPMA page")
      VapingDutyPage.SelectVapingDutyProductsIdRadio(true)

//      Then("I should be on enrolment request access page")
//      assert(
//        VapingDutyPage.confirmation(
//          "http://localhost:8140/vaping-duty/enrolment/enrolment-access"
//        ),
//        "Expected to be on the request enrolment access page"
//      )
    }

    Scenario("Vaping Duty Journey User With Enrolment Already Claimed", VapingDutyTaggedTest, ZapAccessibility) {
      Given("I authenticate using Government Gateway")
      VapingDutyPage.signIntoAuth("VPPAID", "Organisation", "http://localhost:8140/vaping-duty/enrolment/approval-id")

      // This page will be implemented in the next sprint (url below will be changed)
      Then("I should be on interrupt page")
      assert(
        VapingDutyPage.confirmation(
          "http://localhost:8140/vaping-duty/there-is-a-problem"
        ),
        "Expected to be on the interrupt page"
      )
    }

    Scenario("Vaping Duty Journey User With Agent account", VapingDutyTaggedTest, ZapAccessibility) {
      Given("I authenticate using Government Gateway")
      VapingDutyPage.signIntoAuth("VPPAID", "Agent", "http://localhost:8140/vaping-duty/enrolment/approval-id")

      Then("I should be on the organisation sign in page")
      assert(
        VapingDutyPage.confirmation(
          "http://localhost:8140/vaping-duty/enrolment/organisation-sign-in"
        ),
        "Expected to be on the organisation sign in page"
      )
      // Implement when user clicks on Sign in as organisation
    }

    Scenario("Vaping Duty Journey User With Individual account", VapingDutyTaggedTest, ZapAccessibility) {
      Given("I authenticate using Government Gateway")
      VapingDutyPage.signIntoAuth("VPPAID", "Individual", "http://localhost:8140/vaping-duty/enrolment/approval-id")

      Then("I should be on the organisation sign in page")
      assert(
        VapingDutyPage.confirmation(
          "http://localhost:8140/vaping-duty/enrolment/organisation-sign-in"
        ),
        "Expected to be on the organisation sign in page"
      )

      // Implement when user clicks on Sign in as organisation
    }
  }
}
