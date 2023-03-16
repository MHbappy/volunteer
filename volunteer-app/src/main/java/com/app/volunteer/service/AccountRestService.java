package com.app.volunteer.service;

import com.app.volunteer.config.Constraints;
import com.app.volunteer.dto.account.Invoice;
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

@Service
@AllArgsConstructor
public class AccountRestService {
    private final RestTemplate restTemplate;
    private final JwtTokenProvider jwtTokenProvider;

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


    public List<Invoice> getInvoiceListByVolunteerId(Long volunteerId){
        try {
            Boolean isRoleAdmin = jwtTokenProvider.isRoleExist("ROLE_ADMIN");
            List<Invoice> invoice1 = new ArrayList<>();
            if (isRoleAdmin){
                invoice1 = restTemplate.getForObject(Constraints.ACCOUNT_URL + "/api/invoices", List.class);
            }else {
                invoice1 = restTemplate.getForObject(Constraints.ACCOUNT_URL + "/api/volunteer-invoices?volunteerId="+volunteerId, List.class);
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


    public Invoice getInvoiceByVolunteerIdAndId(Long volunteerId, String invoiceNo){
        try {
            String url = Constraints.ACCOUNT_URL + "/api/volunteer-invoices-by-invoice-id-and-volunteer-id?volunteerId=" + volunteerId + "&invoiceNo=" + invoiceNo;

            System.out.println(url);
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
