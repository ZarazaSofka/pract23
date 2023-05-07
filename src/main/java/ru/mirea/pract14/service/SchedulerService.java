package ru.mirea.pract14.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.mirea.pract14.entity.Student;
import ru.mirea.pract14.entity.University;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class SchedulerService implements SchedulerServiceMBean {
    private final StudentService studentService;
    private final UniversityService universityService;

    @Scheduled(fixedRate = 30 * 60000) // 30 минут
    @Override
    public void restart() {
        File dir = new File("./data");
        log.info("Starting backup in {}...", dir.getAbsolutePath());
        if (!dir.mkdirs())
            try {
                FileUtils.cleanDirectory(dir);
            }
            catch (IOException e) {
                System.err.println(e.getMessage());
            }
        File student_data = new File(dir.getPath() + "/student_data");
        File university_data = new File(dir.getPath() + "/university_data");

        List<Student> students = studentService.readAll();
        List<University> universities = universityService.readAll();
        try {
            PrintWriter printWriter = new PrintWriter(student_data);
            for (Student student : students) {
                printWriter.println(student.toString() + "\n");
            }
            printWriter.close();
            printWriter = new PrintWriter(university_data);
            for (University university : universities) {
                printWriter.println(university.toString() + "\n");
            }
            printWriter.close();
        }
        catch (IOException e) {
            System.err.println(e.getMessage());
        }
        Arrays.stream(Objects.requireNonNull(dir.listFiles())).forEach((file) -> log.info("Created file {}", file.getPath()));
    }
}
