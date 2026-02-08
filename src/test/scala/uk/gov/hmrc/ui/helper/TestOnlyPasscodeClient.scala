/*
 * Copyright 2026 HM Revenue & Customs
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

package uk.gov.hmrc.ui.helper

import org.openqa.selenium.json.Json
import org.openqa.selenium.remote.http.{ClientConfig, HttpClient, HttpMethod, HttpRequest}

import java.net.URI
import java.nio.charset.StandardCharsets
import scala.jdk.CollectionConverters.*

class TestOnlyPasscodeClient {

  private val json = new Json()

  def getLatestPasscode(
    baseUrl: String,
    email: String,
    authorization: String,
    sessionId: String
  ): String = {

    val client =
      HttpClient.Factory
        .createDefault()
        .createClient(
          ClientConfig.defaultConfig().baseUrl(URI.create(baseUrl).toURL)
        )

    try {
      val request = new HttpRequest(HttpMethod.GET, "/passcodes")
      request.addHeader("accept", "application/json")
      request.addHeader("authorization", authorization)
      request.addHeader("x-session-id", sessionId)

      val response = client.execute(request)
      val body     = new String(response.getContent.get().readAllBytes(), StandardCharsets.UTF_8)

      if (response.getStatus != 200) {
        throw new RuntimeException(
          s"Failed to fetch passcodes. Status=${response.getStatus}, Body=$body"
        )
      }

      val root =
        json.toType(body, Json.MAP_TYPE).asInstanceOf[java.util.Map[String, Any]]

      val passcodesAny =
        Option(root.get("passcodes"))
          .getOrElse(throw new RuntimeException(s"Response missing 'passcodes'. Body=$body"))

      val passcodes = passcodesAny match {
        case list: java.util.List[_] => list
        case other                   =>
          throw new RuntimeException(s"'passcodes' is not a list. Type=${other.getClass}. Body=$body")
      }

      val matching = passcodes.asScala.toList
        .collect { case m: java.util.Map[_, _] =>
          val mm = m.asInstanceOf[java.util.Map[String, Any]]
          val e  = Option(mm.get("email")).map(_.toString).getOrElse("")
          val pc = Option(mm.get("passcode")).map(_.toString).getOrElse("")
          (e, pc)
        }
        .collect {
          case (e, pc) if e.equalsIgnoreCase(email) && pc.nonEmpty => pc
        }

      matching.lastOption.getOrElse {
        throw new RuntimeException(
          s"No passcode found for email [$email]. Body=$body"
        )
      }

    } finally client.close()
  }
}
