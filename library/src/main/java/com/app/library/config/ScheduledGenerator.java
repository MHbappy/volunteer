package com.app.library.config;

import com.app.library.dto.account.Invoice;
import com.app.library.dto.account.VolunteerInfo;
import com.app.library.enumuration.BookStatus;
import com.app.library.enumuration.InvoiceFor;
import com.app.library.enumuration.InvoiceType;
import com.app.library.model.Book;
import com.app.library.repository.BookRepository;
import com.app.library.service.AccountRestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;



@EnableAsync
@Component
public class ScheduledGenerator {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AccountRestService accountRestService;

    /**
     * Every 30 second, check is there any late book. If there have any late book then create a invoice for payment.
     */
    @Async
    @Scheduled(fixedRate = 30000)
    public void scheduleFixedRateTaskAsync() throws InterruptedException {
        System.out.println("Fixed rate task async - " + System.currentTimeMillis() / 1000);
        List<Book> bookList = bookRepository.getAllLateBook();

        for (Book book : bookList){
            book.setBookStatus(BookStatus.LATE);
            Book book1 = bookRepository.save(book);
            //Generate invoice
            Invoice invoice = new Invoice();
            final String uuid = UUID.randomUUID().toString().replace("-", "");
            invoice.setInvoiceNo(uuid);
            invoice.setName(book1.getName());
            invoice.setInvoiceFor(InvoiceFor.BOOK);
            invoice.setAmount(2d);
            invoice.setBookCourseId(book.getId());
            invoice.setInvoiceType(InvoiceType.PENDING);
            invoice.setVolunteerInfo(new VolunteerInfo(0l, book.getVolunteerInfo().getVolunteerId()));
            accountRestService.saveInvoice(invoice);
        }
    }

}
