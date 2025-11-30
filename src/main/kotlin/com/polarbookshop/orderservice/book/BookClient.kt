package com.polarbookshop.orderservice.book

import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.WebClientResponseException
import org.springframework.web.reactive.function.client.awaitBody

private const val BOOKS_ROOT_API = "/books/"

@Component
class BookClient(val webClient: WebClient) {
    suspend fun getBookByIsbn(isbn: String): Book? {
        return try {
            webClient
                .get()
                .uri(BOOKS_ROOT_API + isbn)
                .retrieve()
                .awaitBody<Book>()
        } catch (e: WebClientResponseException) {
            when (e.statusCode.value()) {
                404 -> null
                else -> throw e
            }
        }
    }
}
