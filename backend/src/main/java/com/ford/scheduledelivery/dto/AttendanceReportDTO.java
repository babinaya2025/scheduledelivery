package com.ford.scheduledelivery.dto;

import java.util.List;

public class AttendanceReportDTO {
  private List<AttendanceRecordDTO> records;
  private List<AttendanceSummaryDTO> summary;
  private String eventName;

  // Constructors
  public AttendanceReportDTO() {}

  public AttendanceReportDTO(List<AttendanceRecordDTO> records,
                             List<AttendanceSummaryDTO> summary,
                             String eventName) {
    this.records = records;
    this.summary = summary;
    this.eventName = eventName;
  }

  // Getters and Setters
  public List<AttendanceRecordDTO> getRecords() { return records; }
  public void setRecords(List<AttendanceRecordDTO> records) { this.records = records; }

  public List<AttendanceSummaryDTO> getSummary() { return summary; }
  public void setSummary(List<AttendanceSummaryDTO> summary) { this.summary = summary; }

  public String getEventName() { return eventName; }
  public void setEventName(String eventName) { this.eventName = eventName; }
}
