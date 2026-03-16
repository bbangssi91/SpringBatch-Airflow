package com.moon.springbatchairflow.api;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.JobExecution;
import org.springframework.batch.core.job.parameters.JobParameters;
import org.springframework.batch.core.job.parameters.JobParametersBuilder;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequiredArgsConstructor
@RestController
public class TestApi {

    private final JobOperator jobOperator;
    private final JobRepository jobRepository;
    
    @Qualifier("sampleJob")
    private final Job sampleJob;
    
    @Qualifier("sampleJob2")
    private final Job sampleJob2;

    @Qualifier("sampleJob3")
    private final Job sampleJob3;

    @PostMapping("/batch/jobs/sample")
    public ResponseEntity<Long> runSample() throws Exception {
        JobParameters params = new JobParametersBuilder()
                .addLong("timestamp", System.currentTimeMillis())
                .toJobParameters();
        long executionId = jobOperator.start(sampleJob, params).getId();

        return ResponseEntity.accepted().body(executionId);
    }

    @PostMapping("/batch/jobs/sample2")
    public ResponseEntity<Long> runSample2() throws Exception {
        JobParameters params = new JobParametersBuilder()
                .addLong("timestamp", System.currentTimeMillis())
                .toJobParameters();
        long executionId = jobOperator.start(sampleJob2, params).getId();

        return ResponseEntity.accepted().body(executionId);
    }

    @PostMapping("/batch/jobs/sample3")
    public ResponseEntity<Long> runSample3() throws Exception {
        JobParameters params = new JobParametersBuilder()
                .addLong("timestamp", System.currentTimeMillis())
                .toJobParameters();
        long executionId = jobOperator.start(sampleJob3, params).getId();

        return ResponseEntity.accepted().body(executionId);
    }

    @GetMapping("/batch/jobs/{executionId}/status")
    public ResponseEntity<Map<String, String>> getStatus(@PathVariable Long executionId) {
        JobExecution jobExecution = jobRepository.getJobExecution(executionId);

        if (jobExecution == null) {
            return ResponseEntity.notFound().build();
        }

        String status = jobExecution.getStatus().name();
        return ResponseEntity.ok(Map.of("status", status));
    }
}
