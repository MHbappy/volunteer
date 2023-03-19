package com.app.volunteer.controller;

import com.app.volunteer.model.Course;
import com.app.volunteer.repository.CourseRepository;
import com.app.volunteer.service.CourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class CourseResource {

    private final Logger log = LoggerFactory.getLogger(CourseResource.class);

    private final CourseService courseService;

    private final CourseRepository courseRepository;

    public CourseResource(CourseService courseService, CourseRepository courseRepository) {
        this.courseService = courseService;
        this.courseRepository = courseRepository;
    }

    /**
     * save a course
     * @param course
     * @return single course
     * @throws URISyntaxException
     */
    @PostMapping("/courses")
    public ResponseEntity<Course> createCourse(@RequestBody Course course) throws URISyntaxException {
        log.debug("REST request to save Course : {}", course);
        if (course.getId() != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A new course cannot already have an ID");
        }
        Course result = courseService.save(course);
        return ResponseEntity
            .created(new URI("/api/courses/" + result.getId()))
            .body(result);
    }


    /**
     * update a course
     * @param id
     * @param course
     * @return
     * @throws URISyntaxException
     */
    @PutMapping("/courses/{id}")
    public ResponseEntity<Course> updateCourse(@PathVariable(value = "id", required = false) final Long id, @RequestBody Course course)
        throws URISyntaxException {
        log.debug("REST request to update Course : {}, {}", id, course);
        if (course.getId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid id");
        }
        if (!Objects.equals(id, course.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid id");
        }

        if (!courseRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A new course cannot already have an ID");
        }

        Course result = courseService.update(course);
        return ResponseEntity
            .ok()
            .body(result);
    }


    /**
     * get all course
     * @return
     */
    @GetMapping("/courses")
    public List<Course> getAllCourses() {
        log.debug("REST request to get all Courses");
        return courseService.findAll();
    }


    /**
     * get course by id
     * @param id
     * @return
     */
    @GetMapping("/courses/{id}")
    public ResponseEntity<Course> getCourse(@PathVariable Long id) {
        log.debug("REST request to get Course : {}", id);
        Optional<Course> course = courseService.findOne(id);
        return ResponseEntity.ok(course.get());
    }


    /**
     * delete a course
     * @param id
     * @return boif
     */
    @DeleteMapping("/courses/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        log.debug("REST request to delete Course : {}", id);
        courseService.delete(id);
        return ResponseEntity
            .noContent()
            .build();
    }
}
