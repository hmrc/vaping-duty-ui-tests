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
import uk.gov.hmrc.ui.specs.tags.{CompleteReturn, VapingDutyTest, ZapAccessibility}

class CompleteReturnSpecs extends BaseSpec {

  Feature("Vaping Duty Complete Return Tests") {
    Scenario(
      "Vaping Duty Journey user with no duty to declare",
      CompleteReturn,
      VapingDutyTest,
      ZapAccessibility
    ) {
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

    Scenario("Vaping Duty Journey submit return journey", VapingDutyTest, CompleteReturn, ZapAccessibility) {
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
