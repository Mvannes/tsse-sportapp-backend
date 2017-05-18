package com.tsse.controller

import com.tsse.domain.model.Workout
import com.tsse.service.WorkoutService
import org.springframework.http.HttpStatus
import org.springframework.validation.Errors
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

/**
 * Controller used for REST operations related to Workouts.
 *
 * @author Floris van Lent
 * @version 1.0.1
 */
@RestController
@CrossOrigin("*")
@RequestMapping("/api/workouts")
class WorkoutController(private val service: WorkoutService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createWorkout(@Valid @RequestBody workout: Workout, errors: Errors) = service.saveWorkout(workout)

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun getWorkout(@PathVariable id: Long): Workout = service.getWorkout(id)

    @GetMapping
    fun getWorkouts(): List<Workout> = service.getAllWorkouts()

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    fun updateWorkout(@Valid @RequestBody workout: Workout, errors: Errors) = service.updateWorkout(workout)

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteWorkout(@PathVariable id: Long) = service.deleteWorkout(id)
}