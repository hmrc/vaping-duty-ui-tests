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
import uk.gov.hmrc.ui.specs.tags.{VapingDutyTest, ViewPayments, ZapAccessibility}

class ViewPaymentsSpecs extends BaseSpec {

  Feature("Vaping Duty View Payments Tests") {

    Scenario(
      "Vaping Duty Journey User views their VPD payments",
      ViewPayments,
      VapingDutyTest,
      ZapAccessibility
    ) {
      Given("An approved vaping product manufacturer has submitted payments that have been processed")
      VapingDutyPage.signIntoAuth(
        AuthUser.organisation(Some(Enrolment.RandomVpdId)),
        VapingDutyPage.viewPaymentsUrl
      )

      Then("User should be on the vaping products duty payments page")
      assert(
        VapingDutyPage.urlConfirmation(VapingDutyPage.viewPaymentsUrl),
        "Expected to be on the vaping products duty payments page"
      )
    }
  }
}
