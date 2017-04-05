package com.tsse.controller

import com.tsse.ResponseBody
import com.tsse.domain.Schedule
import com.tsse.domain.invalidFormException
import com.tsse.service.ScheduleService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.Errors
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

/**
 * Controller used for REST operations related to schedules.
 *
 * @author Fabian de Almeida Ramos
 * @version 1.0.0
 */
@RestController
@RequestMapping("/api/schedule")
class ScheduleController(private val service: ScheduleService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createSchedule(@Valid @RequestBody schedule: Schedule, errors: Errors): Schedule {
        validateRequest(errors)

        return service.saveSchedule(schedule)
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun getSchedule(@PathVariable id: Long): Schedule {
        return service.getSchedule(id)
    }

    @GetMapping
    fun getSchedules(): ResponseEntity<ResponseBody<List<Schedule>>> {

        val result: ResponseBody<List<Schedule>> = ResponseBody()

        result.msg = "Schedule list found!"
        result.response = service.getAllSchedules()

        return ResponseEntity.ok(result)
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    fun updateSchedule(@Valid @RequestBody schedule: Schedule, errors: Errors): Schedule {

        validateRequest(errors)

        return service.updateSchedule(schedule)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun deleteSchedule(@PathVariable id: Long) = service.deleteSchedule(id)

    private fun validateRequest(errors: Errors) {
        if (errors.hasErrors()) {

            val errorMessages = errors.allErrors.map { it.defaultMessage }.toString()
            throw invalidFormException(errorMessages)

        }
    }


}
