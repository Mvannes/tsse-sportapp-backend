package com.tsse.domain

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

/**
 * Exception classes.
 *
 * @author Boyd Hogerheijde
 * @version 1.0.0
 */
@ResponseStatus(HttpStatus.CONFLICT, reason = "Resource already exists.")
abstract class ResourceAlreadyExistsException(message: String) : RuntimeException(message)

@ResponseStatus(HttpStatus.NOT_FOUND, reason = "Resource not found.")
abstract class ResourceNotFoundException(message: String) : RuntimeException(message)

class ExerciseAlreadyExistsException(exercise: Exercise) : ResourceAlreadyExistsException("$exercise already exists.")

class ExerciseNotFoundException : ResourceNotFoundException {

    constructor(id: Long) : super("Exercise with id \'$id\' not found.")

    constructor(name: String) : super("Exercise with name \'$name\' not found.")
}