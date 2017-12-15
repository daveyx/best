package com.example.jobs;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DemoScheduler {
	private static final Logger log = LoggerFactory.getLogger(DemoScheduler.class);
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
   
//    @Autowired
//    private UserAccountRepository customerRepository;

    @Scheduled(cron = "0 0/1 * * * *")
    public void reportCurrentTime() {
        log.info("The time is now {}", dateFormat.format(new Date()));
//		System.out.println("Customers found with findAll():");
//		System.out.println("-------------------------------");
//		for (UserAccount customer : customerRepository.findAll()) {
//			System.out.println(customer);
//		}
    }
}
