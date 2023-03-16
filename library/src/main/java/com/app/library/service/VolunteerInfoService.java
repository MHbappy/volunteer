package com.app.library.service;

import com.app.library.model.VolunteerInfo;
import com.app.library.repository.VolunteerInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class VolunteerInfoService {
    @Autowired
    VolunteerInfoRepository volunteerInfoRepository;

    public Boolean registerVolunteer(Long volunteerId){
        if (volunteerId == null || volunteerId ==0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Volunteer Id not valid");
        }
        VolunteerInfo volunteerInfo = new VolunteerInfo();
        volunteerInfo.setVolunteerId(volunteerId);
        volunteerInfoRepository.save(volunteerInfo);
        return true;
    }

}
