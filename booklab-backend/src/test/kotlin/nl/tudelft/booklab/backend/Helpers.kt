/*
 * Copyright 2018 The BookLab Authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package nl.tudelft.booklab.backend

import com.typesafe.config.ConfigFactory
import io.ktor.config.HoconApplicationConfig
import io.ktor.http.HttpHeaders
import io.ktor.server.testing.TestApplicationEngine
import io.ktor.server.testing.TestApplicationRequest
import io.ktor.server.testing.createTestEnvironment
import io.ktor.server.testing.withApplication
import nl.tudelft.booklab.backend.auth.JwtConfiguration
import java.time.Instant
import java.util.Date

/**
 * Construct a [TestApplicationEngine] from a configuration file and run the given block in its scope.
 *
 * @param test The block to run in its scope.
 */
fun <R> withTestEngine(test: TestApplicationEngine.() -> R) =
    withApplication(createTestEnvironment {
        config = HoconApplicationConfig(ConfigFactory.load("application-test.conf"))
        module { booklab() }
    }, test = test)

/**
 * Configure the authorization header for calls that require an access token.
 */
fun TestApplicationRequest.configureAuthorization() {
    val token = call.application.attributes[JwtConfiguration.KEY].run {
        val now = Instant.now()

        creator
            .withSubject("access-token")
            .withIssuedAt(Date.from(now))
            .withExpiresAt(Date.from(Instant.now().plus(validity)))
            .withClaim("user", "test@example.com")
            .withClaim("client", "test")
            .sign(algorithm)
    }

    addHeader(HttpHeaders.Authorization, "Bearer $token")
}
