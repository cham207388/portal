package com.abc.jobportal.repository;

import com.abc.jobportal.entity.Company;
import com.abc.jobportal.dto.CompanyDto;
import com.abc.jobportal.dto.CompanySummaryProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    @Query("SELECT DISTINCT c FROM Company c JOIN FETCH c.jobs j WHERE j.status = :status")
    List<Company> findAllWithJobsByStatus(@Param("status") String status);

    List<Company> fetchCompaniesWithJobsByStatus(@Param("status") String status);
    
    // Projection-based method to return only summary fields for companies (avoids loading full entity)
    List<CompanySummaryProjection> findAllProjectedBy();

    // JPQL constructor expression to construct CompanyDto directly from selected columns.
    @Query("SELECT new com.abc.jobportal.dto.CompanyDto(c.id, c.name, c.logo, c.industry, c.size, c.rating, c.locations, c.founded, c.description, c.employees, c.website, c.createdAt, null) FROM Company c")
    List<CompanyDto> findAllAsDto();

    @Query(value = "SELECT DISTINCT c.* FROM companies c JOIN jobs j ON c.id = j.company_id WHERE j.status = ?",
            nativeQuery = true)
    List<Company> findAllWithJobsByStatusNative(String status);

    List<Company> fetchCompaniesWithJobsByStatusNative(String status);
    @Modifying(clearAutomatically = true, flushAutomatically = true)
    int updateCompanyDetails(
            @Param("id") Long id,
            @Param("name") String name,
            @Param("logo") String logo,
            @Param("industry") String industry,
            @Param("size") String size,
            @Param("rating") BigDecimal rating,
            @Param("locations") String locations,
            @Param("founded") Integer founded,
            @Param("description") String description,
            @Param("employees") Integer employees,
            @Param("website") String website
    );
}
