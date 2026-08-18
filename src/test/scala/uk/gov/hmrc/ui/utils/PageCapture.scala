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

package uk.gov.hmrc.ui.utils

import org.openqa.selenium.chrome.ChromeDriver
import uk.gov.hmrc.selenium.webdriver.Driver

import java.net.URI
import java.nio.file.{Files, Path, Paths}
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Base64
import scala.jdk.CollectionConverters._

object PageCapture {

  private val baseDir: Path        = Paths.get("target", "page-captures")
  private val enabled: Boolean     = sys.props.get("page.capture").contains("true")
  private var currentTestDir: Path = baseDir
  private var counter: Int         = 0

  def startTest(testName: String): Unit =
    if (enabled) {
      val timestamp = LocalDateTime.now.format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmm-ss"))
      currentTestDir = baseDir.resolve(s"${timestamp}_${sanitise(testName)}")
      counter = 0
    }

  def fromCurrentUrl(): Unit =
    if (enabled) {
      val path    = new URI(Driver.instance.getCurrentUrl).getPath
      val trimmed = Option(path)
        .map(_.replaceFirst("^/vaping-duty", ""))
        .filter(_.nonEmpty)
        .getOrElse("page")
      apply(trimmed)
    }

  def apply(pageName: String): Unit =
    if (enabled) {
      Files.createDirectories(currentTestDir)
      counter += 1

      val result = Driver.instance
        .asInstanceOf[ChromeDriver]
        .executeCdpCommand(
          "Page.captureScreenshot",
          Map[String, AnyRef](
            "captureBeyondViewport" -> java.lang.Boolean.TRUE,
            "fromSurface"           -> java.lang.Boolean.TRUE
          ).asJava
        )

      val fileName = f"$counter%03d-${sanitise(pageName)}.png"
      Files.write(currentTestDir.resolve(fileName), Base64.getDecoder.decode(result.get("data").toString))
    }

  private def sanitise(name: String): String =
    name.toLowerCase.replaceAll("[^a-z0-9-]+", "-").stripPrefix("-").stripSuffix("-").take(80)
}
