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

import org.openqa.selenium.WebDriver
import org.openqa.selenium.remote.http.{ClientConfig, HttpClient, HttpMethod, HttpRequest}
import uk.gov.hmrc.configuration.TestEnvironment

import java.net.URI
import java.nio.charset.StandardCharsets
import scala.jdk.CollectionConverters.*

final case class AuthStubSession(sessionId: String, bearerToken: String)

class AuthLoginStubSessionClient {

  private val AuthLoginStubSessionPath = "/session"

  def getSession(
    driver: WebDriver,
    authLoginStubBaseUrl: String = TestEnvironment.url("auth-login-stub")
  ): AuthStubSession = {

    val currentUrl = driver.getCurrentUrl

    driver.navigate().to(s"${authLoginStubBaseUrl.stripSuffix("/")}$AuthLoginStubSessionPath")

    val cookieHeader = driver
      .manage()
      .getCookies
      .asScala
      .map(c => s"${c.getName}=${c.getValue}")
      .mkString("; ")

    driver.navigate().to(currentUrl)

    val client =
      HttpClient.Factory
        .createDefault()
        .createClient(
          ClientConfig
            .defaultConfig()
            .baseUrl(URI.create(authLoginStubBaseUrl).toURL)
        )

    try {
      val request = new HttpRequest(HttpMethod.GET, AuthLoginStubSessionPath)
      request.addHeader("accept", "text/html")
      if (cookieHeader.nonEmpty) request.addHeader("cookie", cookieHeader)

      val response = client.execute(request)
      val body     = new String(response.getContent.get().readAllBytes(), StandardCharsets.UTF_8)

      if (response.getStatus != 200) {
        throw new RuntimeException(
          s"auth-login-stub $AuthLoginStubSessionPath returned ${response.getStatus}. Body:\n$body"
        )
      }

      val sessionId =
        "(?is)sessionId[\\s\\S]{0,800}?(session-[a-z0-9\\-]+)".r
          .findFirstMatchIn(body)
          .map(_.group(1))
          .getOrElse(throw new RuntimeException(s"Could not extract sessionId. First 500 chars:\n${body.take(500)}"))

      val bearerToken =
        "(?is)(Bearer\\s+[A-Za-z0-9._\\-+/=]+)".r
          .findFirstMatchIn(body)
          .map(_.group(1).trim)
          .getOrElse(throw new RuntimeException(s"Could not extract Bearer token. First 500 chars:\n${body.take(500)}"))

      AuthStubSession(sessionId, bearerToken)
    } finally client.close()
  }
}
