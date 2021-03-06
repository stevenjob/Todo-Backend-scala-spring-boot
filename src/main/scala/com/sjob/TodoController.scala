package com.sjob

import org.springframework.web.bind.annotation._
import org.springframework.validation.BindingResult
import org.springframework.beans.factory.annotation.Autowired
import java.lang.Long
import java.net.URI
import javax.servlet.http.HttpServletRequest
import javax.validation.Valid
import scala.collection.JavaConversions._

@RestController
@CrossOrigin
@RequestMapping(Array("/todos"))
class TodoController @Autowired()(private val todoService: TodoService) {

  @RequestMapping(method = Array(RequestMethod.GET))
  def getAll(): java.util.List[Todo] = {
    //todoService get all returns a seq witch is implicitly casted to a java list so jackson can jsonify it
    todoService getAll
  }

  @RequestMapping(value = Array("/{id}"), method = Array(RequestMethod.GET))
  def getByID(@PathVariable("id") id: Long): Todo = {
    //todo service get by id returns an option object so we need to unpack it
    todoService.get(id).getOrElse(null)
  }

  @RequestMapping(method = Array(RequestMethod.POST))
  def create(@RequestBody todo: Todo, request: HttpServletRequest): Todo = {
    todo.requestURI = request.getRequestURL().toString
    todoService.create(todo)
  }

  @RequestMapping(value = Array("/{id}"), method = Array(RequestMethod.PATCH))
  def edit(@PathVariable("id") id: String, @RequestBody todoUpdates: Todo, request: HttpServletRequest): Todo = {
    todoUpdates.id = id.toLong
    todoService.edit(todoUpdates)
  }

  @RequestMapping(value = Array("/{id}"), method = Array(RequestMethod.DELETE))
  def deleteByID(@PathVariable("id") id: Long) = {
    todoService.delete(id)
  }

  @RequestMapping(method = Array(RequestMethod.DELETE))
  def deleteAll() {
    todoService deleteAll
  }

}
