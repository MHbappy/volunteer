package com.app.library.service;


import com.app.library.config.Constraints;
import com.app.library.dto.account.Invoice;
import lombok.AllArgsConstructor;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import java.util.Map;

@Service
@AllArgsConstructor
public class AccountRestService {
    private final RestTemplate restTemplate;

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
}
