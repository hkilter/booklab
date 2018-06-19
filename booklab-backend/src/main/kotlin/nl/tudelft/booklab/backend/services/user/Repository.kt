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

package nl.tudelft.booklab.backend.services.user

import org.springframework.data.repository.CrudRepository

/**
 * A repository for accessing users from a database.
 */
interface UserRepository : CrudRepository<User, Int> {
    /**
     * Find a user by its email address.
     *
     * @return The user that has been found or `null`.
     */
    fun findByEmail(email: String): User?

    /**
     * Determine whether the given user exists by email.
     */
    fun existsByEmail(email: String): Boolean
}
