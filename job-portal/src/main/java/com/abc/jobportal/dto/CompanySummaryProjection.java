package com.abc.jobportal.dto;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * Projection interface for selecting only the Company summary fields needed by admin views.
 * Spring Data will map query results into this interface to avoid loading full entity state.
 */
public interface CompanySummaryProjection {
    Long getId();

    String getName();

    String getLogo();

    String getIndustry();

    String getSize();

    BigDecimal getRating();

    String getLocations();

    Integer getFounded();

    String getDescription();

    Integer getEmployees();

    String getWebsite();

    Instant getCreatedAt();
}

