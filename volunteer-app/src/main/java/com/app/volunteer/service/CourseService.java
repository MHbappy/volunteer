package com.app.volunteer.service;

import com.app.volunteer.model.Course;
import com.app.volunteer.repository.CourseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class CourseService {
    private final Logger log = LoggerFactory.getLogger(CourseService.class);

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public Course save(Course course) {
        log.debug("Request to save Course : {}", course);
        return courseRepository.save(course);
    }

    public Course update(Course course) {
        log.debug("Request to update Course : {}", course);
        return courseRepository.save(course);
    }

    @Transactional(readOnly = true)
    public List<Course> findAll() {
        log.debug("Request to get all Courses");
        return courseRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Course> findOne(Long id) {
        log.debug("Request to get Course : {}", id);
        return courseRepository.findById(id);
    }

    public void delete(Long id) {
        log.debug("Request to delete Course : {}", id);
        courseRepository.deleteById(id);
    }
}
