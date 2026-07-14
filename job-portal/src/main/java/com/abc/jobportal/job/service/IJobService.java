package com.abc.jobportal.job.service;

import com.abc.jobportal.dto.JobApplicationDto;
import com.abc.jobportal.dto.JobDto;
import com.abc.jobportal.dto.UpdateJobApplicationDto;

import java.util.List;

public interface IJobService {
    List<JobDto> getEmployerJobs(String employerEmail);

    JobDto updateJobStatus(Long jobId, String status, String employerEmail);

    JobDto createJob(JobDto jobDto, String employerEmail);

    List<JobApplicationDto> getApplicationsByJobForEmployer(Long jobId);

    boolean updateJobApplication(UpdateJobApplicationDto updateJobApplicationDto);
}
