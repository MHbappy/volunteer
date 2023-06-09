package com.app.volunteer.service;

import com.app.volunteer.config.Constraints;
import com.app.volunteer.dto.account.Invoice;
import com.app.volunteer.model.Volunteer;
import com.app.volunteer.security.JwtTokenProvider;
import lombok.AllArgsConstructor;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This class is responsible for connect with account server
 */
@Service
@AllArgsConstructor
public class AccountRestService {
    private final RestTemplate restTemplate;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    /**
     * save invoice with acoount applicaiton
     * @param invoice
     * @return  a invoice
     */
    public Invoice saveInvoice(Invoice invoice){
        try {
            Invoice invoice1 = restTemplate.postForObject(Constraints.ACCOUNT_URL + "/api/invoices", invoice, Invoice.class);
            return invoice1;
        }catch (HttpStatusCodeException ex){
            JsonParser springParser = JsonParserFactory.getJsonParser();
            Map< String, Object > map = springParser.parseMap(ex.getResponseBodyAsString());
            String message = (String) map.get("message");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, message);
        }
    }


    /**
     * list of invoice by volunteer
     * @return list of invoice
     */
    public List<Invoice> getInvoiceListByVolunteerId(){
        try {
            Boolean isRoleAccount = jwtTokenProvider.isRoleExist("ROLE_ACCOUNT");
            List<Invoice> invoice1 = new ArrayList<>();
            if (isRoleAccount){
                invoice1 = restTemplate.getForObject(Constraints.ACCOUNT_URL + "/api/invoices", List.class);
            }else {
                Volunteer volunteer = userService.getVolunteerByCurrentUser();
                invoice1 = restTemplate.getForObject(Constraints.ACCOUNT_URL + "/api/volunteer-invoices?volunteerId="+volunteer.getId(), List.class);
            }
            return invoice1;
        }catch (HttpStatusCodeException ex){
            ex.printStackTrace();
            JsonParser springParser = JsonParserFactory.getJsonParser();
            Map< String, Object > map = springParser.parseMap(ex.getResponseBodyAsString());
            String message = (String) map.get("message");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, message);
        }
    }

    /**
     * get invoice by volunteerId and invoice No
     * @param volunteerId
     * @param invoiceNo
     * @return
     */
    public Invoice getInvoiceByVolunteerIdAndId(Long volunteerId, String invoiceNo){
        try {
            String url = Constraints.ACCOUNT_URL + "/api/volunteer-invoices-by-invoice-id-and-volunteer-id?volunteerId=" + volunteerId + "&invoiceNo=" + invoiceNo;
            Invoice invoice = restTemplate.getForObject(url, Invoice.class);
            return invoice;
        }catch (HttpStatusCodeException ex){
            ex.printStackTrace();
            JsonParser springParser = JsonParserFactory.getJsonParser();
            Map< String, Object > map = springParser.parseMap(ex.getResponseBodyAsString());
            String message = (String) map.get("message");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, message);
        }
    }


    /**
     * payment by invoice no
     * @param amount
     * @param invoiceNo
     * @return a boolean
     */
    public boolean getPayment(Double amount, String invoiceNo){
        try {
            String url = Constraints.ACCOUNT_URL + "/api/payment?amount=" + amount + "&invoiceNo=" + invoiceNo;
            Boolean isPayment = restTemplate.postForObject(url, null, Boolean.class);
            return isPayment;
        }catch (HttpStatusCodeException ex){
            ex.printStackTrace();
            JsonParser springParser = JsonParserFactory.getJsonParser();
            Map< String, Object > map = springParser.parseMap(ex.getResponseBodyAsString());
            String message = (String) map.get("message");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, message);
        }
    }

    /**
     * invoice eligibility
     * @param volunteerId
     * @return a boolean object
     */
    public boolean isPermitForGraduate(Long volunteerId){
        try {
            String url = Constraints.ACCOUNT_URL + "/api/graduate-eligibility?volunteerId=" + volunteerId;
            Boolean isPayment = restTemplate.getForObject(url, Boolean.class);
            return isPayment;
        }catch (HttpStatusCodeException ex){
            ex.printStackTrace();
            JsonParser springParser = JsonParserFactory.getJsonParser();
            Map< String, Object > map = springParser.parseMap(ex.getResponseBodyAsString());
            String message = (String) map.get("message");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, message);
        }
    }




}
