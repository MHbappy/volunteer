package com.app.volunteer.service;

import com.app.volunteer.model.Users;
import com.app.volunteer.model.Volunteer;
import com.app.volunteer.repository.VolunteerRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class VolunteerService {

    private final Logger log = LoggerFactory.getLogger(VolunteerService.class);

    private final VolunteerRepository volunteerRepository;

    private final UserService userService;

//    public VolunteerService(VolunteerRepository volunteerRepository) {
//        this.volunteerRepository = volunteerRepository;
//    }

    
    public Volunteer save(Volunteer volunteer) {
        log.debug("Request to save Volunteer : {}", volunteer);
        return volunteerRepository.save(volunteer);
    }

    
    public Volunteer update(Volunteer volunteer) {
        log.debug("Request to update Volunteer : {}", volunteer);
        return volunteerRepository.save(volunteer);
    }

    
    public Optional<Volunteer> partialUpdate(Volunteer volunteer) {
        log.debug("Request to partially update Volunteer : {}", volunteer);

        return volunteerRepository
            .findById(volunteer.getId())
            .map(existingVolunteer -> {
                if (volunteer.getName() != null) {
                    existingVolunteer.setName(volunteer.getName());
                }
                if (volunteer.getFathersName() != null) {
                    existingVolunteer.setFathersName(volunteer.getFathersName());
                }
                if (volunteer.getMothersName() != null) {
                    existingVolunteer.setMothersName(volunteer.getMothersName());
                }
                if (volunteer.getAddress() != null) {
                    existingVolunteer.setAddress(volunteer.getAddress());
                }
                if (volunteer.getDescription() != null) {
                    existingVolunteer.setDescription(volunteer.getDescription());
                }
                if (volunteer.getIsActive() != null) {
                    existingVolunteer.setIsActive(volunteer.getIsActive());
                }

                return existingVolunteer;
            })
            .map(volunteerRepository::save);
    }

    
    @Transactional(readOnly = true)
    public List<Volunteer> findAll() {
        log.debug("Request to get all Volunteers");
        return volunteerRepository.findAll();
    }


    @Transactional(readOnly = true)
    public Volunteer findByUserId(HttpServletRequest httpServletRequest) {
        log.debug("Request to get all Volunteers");

        Users user = userService.whoami(httpServletRequest);
        Volunteer volunteer = volunteerRepository.findByUsers_Id(user.getId());
        if (volunteer == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid id");
        }
        return volunteerRepository.findByUsers_Id(user.getId());
    }

    
    @Transactional(readOnly = true)
    public Optional<Volunteer> findOne(Long id) {
        log.debug("Request to get Volunteer : {}", id);
        return volunteerRepository.findById(id);
    }

    
    public void delete(Long id) {
        log.debug("Request to delete Volunteer : {}", id);
        volunteerRepository.deleteById(id);
    }
}
