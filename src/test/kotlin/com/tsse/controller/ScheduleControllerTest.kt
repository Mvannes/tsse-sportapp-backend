package com.tsse.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.tsse.domain.Schedule
import com.tsse.domain.ScheduleNotFoundException
import com.tsse.service.ScheduleService
import org.hamcrest.Matchers.equalTo
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.verify
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.MockitoAnnotations
import org.springframework.http.MediaType
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import java.util.*

/**
 * Tests related to the ScheduleController.
 *
 * @author Fabian de Almeida Ramos
 * @version 1.0.0
 */

@RunWith(SpringRunner::class)
class ScheduleControllerTest {

    @Mock lateinit var service: ScheduleService

    @InjectMocks lateinit var scheduleController: ScheduleController

    lateinit var mockMvc: MockMvc

    val uri = "/api/schedule/"

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this::class)
        mockMvc = MockMvcBuilders
                .standaloneSetup(scheduleController)
                .build()
    }

    @Test
    fun testSaveSchedule_returnsHttpStatusIsCreated() {

        val schedule = Schedule("Name", "Description", ArrayList(), 1)

        given(service.saveSchedule(schedule)).willReturn(schedule)
        mockMvc.perform(
                MockMvcRequestBuilders.post(uri)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(asJsonString(schedule)))
                .andExpect(status().isCreated)
                .andExpect { content().equals(schedule) }
    }

    @Test
    fun testSaveScheduleEmptyName_returnsHttpStatusIsBadRequest() {

        val schedule = Schedule("", "Description", ArrayList(), 1)

        mockMvc.perform(
                MockMvcRequestBuilders.post(uri)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(asJsonString(schedule)))
                .andExpect(status().isBadRequest)
                .andExpect { jsonPath("message", equalTo("Object sent is not valid: [Schedule name cannot be empty!]")) }
    }

    @Test
    fun testSaveScheduleEmptyDescription_returnsHttpStatusIsCreated() {

        val schedule = Schedule("Name", "", ArrayList(), 1)

        given(service.saveSchedule(schedule)).willReturn(schedule)
        mockMvc.perform(
                MockMvcRequestBuilders.post(uri)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(asJsonString(schedule)))
                .andExpect(status().isCreated)
                .andExpect { content().equals(schedule) }
    }

    @Test
    fun testSaveScheduleEmptyAmountOfTrainings_returnsHttpStatusIsBadRequest() {

        val schedule = Schedule("Name", "Description", ArrayList(), 0)

        given(service.saveSchedule(schedule)).willReturn(schedule)
        mockMvc.perform(
                MockMvcRequestBuilders.post(uri)
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(asJsonString(schedule)))
                .andExpect(status().isBadRequest)
                .andExpect { jsonPath("message", equalTo("Object sent is not valid: [Schedule needs at least one training per week!]")) }
    }

    @Test
    fun testGetScheduleById_returnsHttpStatusOk() {

        val schedule = Schedule("Name", "Description", ArrayList(), 0)
        val id = 1L
        given(service.getSchedule(id)).willReturn(schedule)

        mockMvc.perform(
                get(uri + "{id}", id))
                .andExpect(status().isOk)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect { content().equals(schedule) }


    }

    @Test
    fun testGetScheduleById_returnsHttpStatusIsNotFound() {

        val id = 1L
        given(service.getSchedule(id)).willThrow(ScheduleNotFoundException::class.java)

        mockMvc.perform(
                get(uri + "{id}", id))
                .andExpect(status().isNotFound)
    }

    @Test
    fun testGetAllSchedules_returnsHttpStatusOk() {

        val results: List<Schedule> = arrayListOf(Schedule("Name", "Description", ArrayList(), 0),
                Schedule("Name2", "Description2", ArrayList(), 0))


        given(service.getAllSchedules()).willReturn(results)

        mockMvc.perform(
                get(uri))
                .andExpect(status().isOk)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect { content().equals(results) }

    }

    


    private fun asJsonString(obj: Any): String = ObjectMapper().writeValueAsString(obj)


}