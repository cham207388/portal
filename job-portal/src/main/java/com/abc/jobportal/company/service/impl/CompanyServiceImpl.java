package com.abc.jobportal.company.service.impl;

import com.abc.jobportal.company.service.ICompanyService;
import com.abc.jobportal.constants.ApplicationConstants;
import com.abc.jobportal.dto.CompanyDto;
import com.abc.jobportal.dto.JobDto;
import com.abc.jobportal.entity.Company;
import com.abc.jobportal.entity.Job;
import com.abc.jobportal.repository.CompanyRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CompanyServiceImpl implements ICompanyService {

    private final CompanyRepository companyRepository;

    @Override
    public List<CompanyDto> getAllCompanies() {
        List<Company> companyList =companyRepository.fetchCompaniesWithJobsByStatus(ApplicationConstants.ACTIVE_STATUS);
        return companyList.stream().map(this::transformCompanyToDto).collect(Collectors.toList());
    }

    @Override
    public List<CompanyDto> getAllCompaniesForAdmin() {
        // Use JPQL constructor-expression repository method to load only required columns into CompanyDto
        return companyRepository.findAllAsDto();
    }

    @Transactional
    @Override
    public void deleteCompanyById(Long id) {
        companyRepository.deleteById(id);
    }

    @Transactional
    @Override
    public boolean updateCompanyDetails(Long id, CompanyDto companyDto) {
        int updatedRecords = companyRepository.updateCompanyDetails(
                id,companyDto.name(),companyDto.logo(),
                companyDto.industry(),companyDto.size(),companyDto.rating(),
                companyDto.locations(),companyDto.founded(),companyDto.description(),
                companyDto.employees(),companyDto.website()
        );
        return updatedRecords > 0;
    }

    @Transactional
    @Override
    public boolean createCompany(CompanyDto companyDto) {
        Company company = transformCompanyDtoToEntity(companyDto);
        Company savedCompany = companyRepository.save(company);
        return savedCompany.getId() != null && savedCompany.getId() > 0;
    }

    private CompanyDto transformCompanyToDto(Company company) {
        List<JobDto> jobDtos = company.getJobs().stream()
                .map(this::transformJobToDto)
                .collect(Collectors.toList());
        return new CompanyDto(company.getId(), company.getName(), company.getLogo(),
                company.getIndustry(), company.getSize(), company.getRating(),
                company.getLocations(), company.getFounded(), company.getDescription(),
                company.getEmployees(), company.getWebsite(), company.getCreatedAt(),jobDtos);
    }

    private JobDto transformJobToDto(Job job) {
        return new JobDto(
                job.getId(),
                job.getTitle(),
                job.getCompany().getId(),
                job.getCompany().getName(),
                job.getCompany().getLogo(),
                job.getLocation(),
                job.getWorkType(),
                job.getJobType(),
                job.getCategory(),
                job.getExperienceLevel(),
                job.getSalaryMin(),
                job.getSalaryMax(),
                job.getSalaryCurrency(),
                job.getSalaryPeriod(),
                job.getDescription(),
                job.getRequirements(),
                job.getBenefits(),
                job.getPostedDate(),
                job.getApplicationDeadline(),
                job.getApplicationsCount(),
                job.getFeatured(),
                job.getUrgent(),
                job.getRemote(),
                job.getStatus()
        );
    }

    private Company transformCompanyDtoToEntity(CompanyDto companyDto) {
        Company company = new Company();
        BeanUtils.copyProperties(companyDto, company);
        return company;
    }

    private CompanyDto transformCompanyToDtoForAdmin(Company company) {
        return new CompanyDto(company.getId(), company.getName(), company.getLogo(),
                company.getIndustry(), company.getSize(), company.getRating(),
                company.getLocations(), company.getFounded(), company.getDescription(),
                company.getEmployees(), company.getWebsite(), company.getCreatedAt(),null);
    }

}