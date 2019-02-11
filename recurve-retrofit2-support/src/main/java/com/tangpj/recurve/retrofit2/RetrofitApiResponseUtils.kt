/*
 * Copyright (C) 2018 Tang
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

package com.tangpj.recurve.retrofit2

import com.tangpj.recurve.resource.*
import retrofit2.Response

fun <T> create(response: Response<T>, nextPageStrategy: NextPageStrategy? = null): ApiResponse<T> {
    return if (response.isSuccessful) {
        val body = response.body()
        if (body == null || response.code() == 204) {
            ApiEmptyResponse()
        } else {
            ApiSuccessResponse(
                    body = body,
                    nextPageStrategy = nextPageStrategy
            )
        }
    } else {
        val msg = response.errorBody()?.string()
        val errorMsg = if (msg.isNullOrEmpty()) {
            response.message()
        } else {
            msg
        }
        ApiErrorResponse(errorMsg ?: "unknown error")
    }
}