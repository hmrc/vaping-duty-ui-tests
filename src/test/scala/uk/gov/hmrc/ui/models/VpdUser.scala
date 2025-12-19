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

package uk.gov.hmrc.ui.models

final case class VpdUser(
  affinityGroup: String,
  enrolmentKey: Option[String] = None,
  identifierName: Option[String] = None,
  identifierValue: Option[String] = None
)

object VpdUsers {

  val VPDEnrolledOrganisation: VpdUser =
    VpdUser("Organisation", Some("HMRC-VPD-ORG"), Some("VPPAID"), Some("X"))

  val VPDNonEnrolledOrganisation: VpdUser =
    VpdUser("Organisation")

  val AgentUser: VpdUser =
    VpdUser("Agent", Some("HMRC-VPD-ORG"), Some("VPPAID"), Some("X"))

  val IndividualUser: VpdUser =
    VpdUser("Individual", Some("HMRC-VPD-ORG"), Some("VPPAID"), Some("X"))
}
