package com.app.volunteer.service;

import com.app.volunteer.dto.VolunteerInfo;
import com.app.volunteer.dto.account.Invoice;
import com.app.volunteer.enumuration.InvoiceFor;
import com.app.volunteer.enumuration.InvoiceType;
import com.app.volunteer.model.Course;
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
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
public class VolunteerService {

    private final Logger log = LoggerFactory.getLogger(VolunteerService.class);
    private final VolunteerRepository volunteerRepository;
    private final UserService userService;
    private final AccountRestService accountRestService;

    /**
     * update own profile
     * @param volunteer
     * @return
     */
    @Transactional
    public Volunteer update(Volunteer volunteer) {
        log.debug("Request to update Volunteer : {}", volunteer);
        Volunteer volunteer1 = volunteerRepository.save(volunteer);

        if (volunteer.getCourses() != null && !volunteer.getCourses().isEmpty()){
            for (Course course : volunteer1.getCourses()){
                Invoice invoice = new Invoice();
                final String uuid = UUID.randomUUID().toString().replace("-", "");
                invoice.setInvoiceNo(uuid);
                invoice.setName("course: " + course.getName());
                invoice.setInvoiceFor(InvoiceFor.COURSE);
                invoice.setAmount(course.getCoursePrice());
                invoice.setBookCourseId(course.getId());
                invoice.setInvoiceType(InvoiceType.PENDING);
                invoice.setVolunteerInfo(new VolunteerInfo(0l, volunteer1.getId()));
                accountRestService.saveInvoice(invoice);
            }
        }
        return volunteer1;
    }

    /**
     * Get volunteer by userId
     * @param httpServletRequest
     * @return
     */
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

    /**
     * get volunteer by id
     * @param id
     * @return
     */
    @Transactional(readOnly = true)
    public Optional<Volunteer> findOne(Long id) {
        log.debug("Request to get Volunteer : {}", id);
        return volunteerRepository.findById(id);
    }

}
