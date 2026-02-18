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

import java.security.SecureRandom

final case class Enrolment(
  enrolmentKey: String,
  identifierName: String,
  identifierValue: String,
  credId: Option[String] = None
)

object Enrolment {

  private val secureRandom = new SecureRandom()

  private def randomNumericId(length: Int = 16): String = {
    val firstDigit = secureRandom.nextInt(9) + 1
    val remaining  = (1 until length).map(_ => secureRandom.nextInt(10)).mkString
    firstDigit.toString + remaining
  }

  private lazy val emailCredId: String = randomNumericId(16)

  val Vpd: Enrolment =
    Enrolment(
      enrolmentKey = "HMRC-VPD-ORG",
      identifierName = "ZVPD",
      identifierValue = "X"
    )

  val contactPreferenceEmailToPost: Enrolment =
    Enrolment(
      enrolmentKey = "HMRC-VPD-ORG",
      identifierName = "ZVPD",
      identifierValue = "XMADP0000100211"
    )

  val contactPreferencePostToPost: Enrolment =
    Enrolment(
      enrolmentKey = "HMRC-VPD-ORG",
      identifierName = "ZVPD",
      identifierValue = "XMADP1000100211"
    )

  val contactPreferenceEmailAlreadyVerified: Enrolment =
    Enrolment(
      enrolmentKey = "HMRC-VPD-ORG",
      identifierName = "ZVPD",
      identifierValue = "XMADP4000100211",
      credId = Some(emailCredId)
    )

  val contactPreferenceEmail: Enrolment =
    Enrolment(
      enrolmentKey = "HMRC-VPD-ORG",
      identifierName = "ZVPD",
      identifierValue = "XMADP4000100211"
    )
}
