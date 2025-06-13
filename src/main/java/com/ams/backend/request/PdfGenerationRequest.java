package com.ams.backend.request;

import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
public class PdfGenerationRequest {
  private PageData pageData;

  @Data
  public static class PageData {
    private int totalAudits;
    private int completedAudits;
    private int pendingAudits;
    private List<RecentAudit> recentAudits;
    private List<ChartData> chartData;
    private List<PieData> pieData;
    private DateRange dateRange;
  }

  @Data
  public static class RecentAudit {
    private Long id;
    private String auditDate;
    // Add other audit fields as needed
  }

  @Data
  public static class ChartData {
    private String name;
    private int completed;
    private int pending;
  }

  @Data
  public static class PieData {
    private String name;
    private int value;
  }

  @Data
  public static class DateRange {
    private Date start;
    private Date end;
  }
}