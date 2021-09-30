package com.jayaspiya.everestbooks

import com.jayaspiya.everestbooks.api.ServiceBuilder
import com.jayaspiya.everestbooks.model.User
import com.jayaspiya.everestbooks.repository.BookRepository
import com.jayaspiya.everestbooks.repository.OrderRepository
import com.jayaspiya.everestbooks.repository.UserRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class ValidationTest {
    private lateinit var userRepository: UserRepository
    private lateinit var bookRepository: BookRepository
    private lateinit var orderRepository: OrderRepository
    @Test
    fun loginUser() = runBlocking {
        userRepository = UserRepository()
        val user = User(email="a@a.com", password = "a123")
        val response = userRepository.loginUser(user)
        val expectedResult = true
        val actualResult = response.success
        assertEquals(expectedResult, actualResult)
    }
    @Test
    fun registerUser() = runBlocking {
        val user = User(
            email = "tghrtyue@everest.com",
            firstname = "Point",
            lastname = "Pin",
            address = "Somewhere",
            phone = "9898989898",
            password = "123456"
        )
        userRepository = UserRepository()
        val response = userRepository.registerUser(user)
        val expectedResult = true
        val actualResult = response.success
        assertEquals(expectedResult, actualResult)
    }

    @Test
    fun getUserCart() = runBlocking {
        userRepository = UserRepository()
        val accessToken = "Bearer " + userRepository.loginUser(User(email="a@a.com", password = "a123")).accessToken
        ServiceBuilder.token=accessToken
        val response = userRepository.getCart()
        val expectedResult = true
        val actualResult = response.success
        assertEquals(expectedResult, actualResult)
    }


    @Test
    fun getUserProfile() = runBlocking {
        userRepository = UserRepository()
        val accessToken = "Bearer " + userRepository.loginUser(User(email="a@a.com", password = "a123")).accessToken
        ServiceBuilder.token=accessToken
        val response = userRepository.getProfile()
        val expectedResult = true
        val actualResult = response.success
        assertEquals(expectedResult, actualResult)
    }
    @Test
    fun getAllOrder() = runBlocking {
        orderRepository = OrderRepository()
        userRepository = UserRepository()
        val accessToken = "Bearer " + userRepository.loginUser(User(email="a@a.com", password = "a123")).accessToken
        ServiceBuilder.token=accessToken
        val response = orderRepository.getOrder()
        val expectedResult = true
        val actualResult = response.success
        assertEquals(expectedResult, actualResult)
    }
    
}