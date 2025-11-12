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

  val url: String             = TestEnvironment.url("auth-login-stub")
  val redirectUrl: String     = TestEnvironment.url("vaping-duty-frontend")
  val continueButton: By      = By.className("govuk-button")
  val GgSignInButton: By      = By.id("vpd-gg-logon-1")
  val redirectionUrlField: By = By.id("redirectionUrl")
  val affinityGroupSelect: By = By.id("affinityGroupSelect")
  val enrolmentKey: By        = By.id("enrolment[0].name")
  val identifierName: By      = By.id("input-0-0-name")
  val SubmitButton: By        = By.cssSelector("#inputForm > input:nth-child(37)")
}
