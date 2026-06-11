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
      VapingDutyPage.signIntoAuth(
        AuthUser.organisation(Some(Enrolment.RandomVpdId)),
        VapingDutyPage.viewYourReturnsUrl
      )

      Then("User should be on the view your returns page")
      assert(
        VapingDutyPage.urlConfirmation(VapingDutyPage.viewYourReturnsUrl),
        "Expected to be on the view your returns page"
      )

      When("User Clicks on the submit return link")
      VapingDutyPage.clickSubmitReturnLink()

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

      When("User clicks on moved or received vpd duty suspense link")
      VapingDutyPage.clickLinkFromTaskList("dutySuspended")

      Then("User should be on the Have you received or moved any finished vaping products in duty suspense page")
      assert(
        VapingDutyPage.urlConfirmation(VapingDutyPage.dutySuspendedUrl),
        "Expected to be on the Have you received or moved any finished vaping products in duty suspense page"
      )

      When("User selects no on Have you received or moved any finished vaping products in duty suspense page")
      VapingDutyPage.selectHaveYouReceivedDutySuspenseRadio(false)

      Then("User should be on task list page")
      assert(
        VapingDutyPage.urlConfirmation(VapingDutyPage.taskListUrl),
        "Expected to be on the task list page"
      )

      When("User clicks on Tell us if you have any spoilt adjustments link")
      VapingDutyPage.clickLinkFromTaskList("spoiltAdjustments")

      Then("User should be on the declare spoilt products page")
      assert(
        VapingDutyPage.urlConfirmation(VapingDutyPage.declareSpoiltProductsUrl),
        "Expected to be on the declare spoilt products page"
      )

      When("User selects no on do you have any spoilt vaping products to declare page")
      VapingDutyPage.selectHasSpoiltProductsRadio(false)

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

      Then("User should be on the declaration page")
      assert(
        VapingDutyPage.urlConfirmation(VapingDutyPage.returnDeclarationUrl),
        "Expected to be on the declaration page"
      )

      When("User completes the declaration and submits the return")
      VapingDutyPage.submitReturnDeclaration(
        fullName = "Auto Test",
        capacity = "Director",
        email = "autotest@example.com"
      )

      Then("User should be on the return submitted confirmation page")
      assert(
        VapingDutyPage.urlConfirmation(VapingDutyPage.returnSubmittedUrl),
        "Expected to be on the return submitted confirmation page"
      )

    }

    Scenario("Vaping Duty Journey submit return journey", VapingDutyTest, CompleteReturn, ZapAccessibility) {
      Given("User authenticates using Government Gateway and user redirects to before you start page")
      VapingDutyPage.signIntoAuth(
        AuthUser.organisation(Some(Enrolment.RandomVpdId)),
        VapingDutyPage.viewYourReturnsUrl
      )

      Then("User should be on view your returns page")
      assert(
        VapingDutyPage.urlConfirmation(VapingDutyPage.viewYourReturnsUrl),
        "Expected to be on the view your returns page"
      )

      When("User Clicks on the submit return link")
      VapingDutyPage.clickSubmitReturnLink()

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

      When("User clicks on moved or received vpd duty suspense link")
      VapingDutyPage.clickLinkFromTaskList("dutySuspended")

      Then("User should be on the Have you received or moved any finished vaping products in duty suspense page")
      assert(
        VapingDutyPage.urlConfirmation(VapingDutyPage.dutySuspendedUrl),
        "Expected to be on the Have you received or moved any finished vaping products in duty suspense page"
      )

      When("User selects yes on Have you received or moved any finished vaping products in duty suspense page")
      VapingDutyPage.selectHaveYouReceivedDutySuspenseRadio(true)

      Then("User should be on the how much duty suspension did you receive or move")
      assert(
        VapingDutyPage.urlConfirmation(VapingDutyPage.receivedOrMovedAmountUrl),
        "Expected to be on the how much duty suspension did you receive or move"
      )

      When("User enters an amount received and moved and click continue")
      VapingDutyPage.submitDutySuspenseMovedOrReceived("1000")

      Then("User should be on task list page")
      assert(
        VapingDutyPage.urlConfirmation(VapingDutyPage.taskListUrl),
        "Expected to be on the task list page"
      )

      When("User clicks on Tell us if you have any spoilt adjustments link")
      VapingDutyPage.clickLinkFromTaskList("spoiltAdjustments")

      Then("User should be on the declare spoilt products page")
      assert(
        VapingDutyPage.urlConfirmation(VapingDutyPage.declareSpoiltProductsUrl),
        "Expected to be on the do you have any spoilt vaping products to declare page"
      )

      When("User selects yes on do you have any spoilt vaping products to declare page")
      VapingDutyPage.selectHasSpoiltProductsRadio(true)

      Then("User should be on the select spoilt period page")
      assert(
        VapingDutyPage.urlConfirmation(VapingDutyPage.selectSpoiltPeriodUrl),
        "Expected to be on the select spoilt period page"
      )

      When("User selects a period to adjust")
      VapingDutyPage.clickSelectSpoiltPeriod()

      Then("User should be on the enter spoilt amount page")
      assert(
        VapingDutyPage.urlConfirmation(VapingDutyPage.enterSpoiltAmountUrl),
        "Expected to be on the enter spoilt amount page"
      )

      When("User enters a spoilt amount and clicks save and continue")
      VapingDutyPage.submitSpoiltAmount("5")

      Then("User should be on the add another spoilt adjustment page")
      assert(
        VapingDutyPage.urlConfirmation(VapingDutyPage.addAnotherSpoiltAdjustmentUrl),
        "Expected to be on the add another spoilt adjustment page"
      )

      When("User selects no to adding another spoilt adjustment")
      VapingDutyPage.selectAddAnotherSpoiltAdjustment(false)

      Then("User should be on the task list page")
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

      Then("User should be on the declaration page")
      assert(
        VapingDutyPage.urlConfirmation(VapingDutyPage.returnDeclarationUrl),
        "Expected to be on the declaration page"
      )

      When("User completes the declaration and submits the return")
      VapingDutyPage.submitReturnDeclaration(
        fullName = "Auto Test",
        capacity = "Director",
        email = "autotest@example.com"
      )

      Then("User should be on the return submitted confirmation page")
      assert(
        VapingDutyPage.urlConfirmation(VapingDutyPage.returnSubmittedUrl),
        "Expected to be on the return submitted confirmation page"
      )
    }

    Scenario("Vaping Duty Journey view individual return journey", VapingDutyTest, CompleteReturn, ZapAccessibility) {
      Given("User authenticates using Government Gateway and user redirects to before you start page")
      VapingDutyPage.signIntoAuth(
        AuthUser.organisation(Some(Enrolment.contactPreferenceEmailToPost)),
        VapingDutyPage.viewYourReturnsUrl
      )

      Then("User should be on view your returns page")
      assert(
        VapingDutyPage.urlConfirmation(VapingDutyPage.viewYourReturnsUrl),
        "Expected to be on the view your returns page"
      )

      When("User Clicks on the view return link")
      VapingDutyPage.clickViewReturnLink()

      Then("User should be on the view your return page")
      assert(
        VapingDutyPage.urlConfirmation(VapingDutyPage.viewYourReturnsUrl),
        "Expected to be on the view your return page"
      )
    }
  }
}
