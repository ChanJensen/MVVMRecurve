/*
 * Copyright 2018, The TangPJ
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.tangpj.recurve.apollo

import com.apollographql.apollo.api.Response
import com.tangpj.recurve.resource.*


fun <T> create(response: Response<T>, nextPageStrategy: NextPageStrategy? = null): ApiResponse<T> {
    return if (!response.hasErrors()) {
        val body = response.data()
        if (body == null ) {
            ApiEmptyResponse()
        } else {
            ApiSuccessResponse(
                    body = body,
                    linkHeader = null,
                    nextPageStrategy = nextPageStrategy
            )
        }
    } else {
        val msg = response.errors().joinToString { error -> error.message() ?: "unknown error" }
        ApiErrorResponse(msg)
    }
}