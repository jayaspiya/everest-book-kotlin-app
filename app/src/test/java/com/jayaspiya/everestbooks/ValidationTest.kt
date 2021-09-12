package com.jayaspiya.everestbooks

import com.jayaspiya.everestbooks.api.ServiceBuilder
import com.jayaspiya.everestbooks.model.User
import com.jayaspiya.everestbooks.repository.BookRepository
import com.jayaspiya.everestbooks.repository.UserRepository
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class ValidationTest {
    private lateinit var userRepository: UserRepository
    @Test
    fun loginUser() = runBlocking {
        userRepository = UserRepository()
        val user = User(email="user@everest.com", password = "123456")
        val response = userRepository.loginUser(user)
        val expectedResult = true
        val actualResult = response.success
        assertEquals(expectedResult, actualResult)
    }
    @Test
    fun registerUser() = runBlocking {
        val user = User(
            email = "user21@everest.com",
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

//    TODO: ERROR ERROR
//    @Test
//    fun addToCart() = runBlocking {
//        userRepository = UserRepository()
//        val accessToken = "Bearer " + userRepository.loginUser(User(email="user@everest.com", password = "123456")).accessToken
//        ServiceBuilder.token=accessToken
//        val response = userRepository.addToCart("612d0ad8dc66860f808be2a7")
//        val expectedResult = true
//        val actualResult = response.success
//        assertEquals(expectedResult, actualResult)
//    }
}