package com.accenture.java.msopentdmaven.service.scheduler;

import com.accenture.java.msopentdmaven.repository.database.CreateTdRequestsRepository;
import com.accenture.java.msopentdmaven.repository.database.entity.CreateTdRequest;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;


@Component
@ConditionalOnProperty(value = {"application.createTdAccountScheduler.enabled"})
public class CreateTdAccountScheduler {

    private Logger log = LoggerFactory.getLogger(CreateTdAccountScheduler.class);

    @Autowired
    private CreateTdRequestsRepository createTdRequestsRepository;
    
    @PostConstruct
    public void announce() {
        log.info("message='CreateTdAccountScheduler is created!'");
    }

    @Scheduled(cron = "${application.createTdAccountScheduler.cron}")
    @SchedulerLock(name = "CreateTdAccountScheduler_start")
    public void start() {
        log.info("message='CreateTdAccountScheduler triggered!'");

        List<CreateTdRequest> list = this.createTdRequestsRepository
                .findFailedRequests();

        for (CreateTdRequest createTdRequest : list) {
            createTdRequest.increaseAttemptCount();

            switch (createTdRequest.getStatus()) {
                case "GET_PRODUCT":

                    break;
                default:
                    break;
            }
        }

        if (!list.isEmpty()) {
            this.createTdRequestsRepository.saveAll(list);
        }
    }


}
