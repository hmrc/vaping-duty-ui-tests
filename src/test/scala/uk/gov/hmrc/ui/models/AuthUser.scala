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

final case class AuthUser(
  affinityGroup: String,
  enrolment: Option[Enrolment] = None
)

object AuthUser {

  def user(
    affinityGroup: String,
    enrolment: Option[Enrolment] = None
  ): AuthUser =
    AuthUser(
      affinityGroup = affinityGroup,
      enrolment = enrolment
    )

  def organisation(enrolment: Option[Enrolment] = None): AuthUser =
    user("Organisation", enrolment)

  def agent(enrolment: Option[Enrolment] = None): AuthUser =
    user("Agent", enrolment)

  def individual(enrolment: Option[Enrolment] = None): AuthUser =
    user("Individual", enrolment)
}
