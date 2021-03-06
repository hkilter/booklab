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

package nl.tudelft.booklab.backend.api.v1

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.ktor.application.Application
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.routing.Routing
import io.ktor.routing.route
import io.ktor.server.testing.contentType
import io.ktor.server.testing.handleRequest
import nl.tudelft.booklab.backend.booklab
import nl.tudelft.booklab.backend.createTestContext
import nl.tudelft.booklab.backend.ktor.Routes
import nl.tudelft.booklab.backend.spring.bootstrap
import nl.tudelft.booklab.backend.withTestEngine
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.context.support.beans

/**
 * Unit test suite for the meta endpoint of the BookLab REST api.
 *
 * @author Fabian Mastenbroek (f.s.mastenbroek@student.tudelft.nl)
 */
internal class MetaTest {
    /**
     * The Jackson mapper class that maps JSON to objects.
     */
    lateinit var mapper: ObjectMapper

    @BeforeEach
    fun setUp() {
        mapper = jacksonObjectMapper()
    }

    @Test
    fun `health check should return true`() = withTestEngine({ module() }) {
        with(handleRequest(HttpMethod.Get, "/api/health")) {
            assertEquals(HttpStatusCode.OK, response.status())
            assertTrue(response.contentType().match(ContentType.Application.Json))

            val response: ApiResponse.Success<HealthCheck>? = response.content?.let { mapper.readValue(it) }
            assertEquals(HealthCheck(true), response?.data)
        }
    }

    @Test
    fun `health check requires GET`() = withTestEngine({ module() }) {
        with(handleRequest(HttpMethod.Post, "/api/health")) {
            assertEquals(HttpStatusCode.MethodNotAllowed, response.status())
            assertTrue(response.contentType().match(ContentType.Application.Json))

            val response: ApiResponse.Failure? = response.content?.let { mapper.readValue(it) }
            assertEquals("method_not_allowed", response?.error?.code)
        }
    }

    private fun Application.module() {
        val context = createTestContext {
            beans {
                // Application routes
                bean("routes") { Routes.from { routes() } }
            }.initialize(this)
        }
        context.bootstrap(this) { booklab() }
    }

    /**
     * The routes of the application.
     */
    private fun Routing.routes() {
        route("/api") { meta() }
    }
}
