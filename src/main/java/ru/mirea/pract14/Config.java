package ru.mirea.pract14;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;
import ru.mirea.pract14.service.SchedulerService;

import javax.management.*;
import java.lang.management.ManagementFactory;


@Configuration
@EnableJpaRepositories
@EnableAspectJAutoProxy
@EnableScheduling
@RequiredArgsConstructor
@EnableJdbcHttpSession
public class Config {
    private final ApplicationContext applicationContext;

    @PostConstruct
    public void initJMC() throws NotCompliantMBeanException, InstanceAlreadyExistsException,
            MBeanRegistrationException, MalformedObjectNameException {
        SchedulerService sc = applicationContext.getBean(SchedulerService.class);
        System.out.println(sc.getClass().getSimpleName());
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = new ObjectName("ru.mirea.pract14.service:type=SchedulerService");
        mbs.registerMBean(sc, name);
    }
}