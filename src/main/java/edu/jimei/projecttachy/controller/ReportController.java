package edu.jimei.projecttachy.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//-TODO: Replace with actual DTOs
record ReportTask(long id, String name, String dataSource, String created, String duration, String status) {}
record ReportTaskDetail(long id, String name, String dataSource, String created, String duration, String status, String content) {}

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    @GetMapping
    public List<ReportTask> getReportTasks() {
        // TODO: Implement logic to fetch report tasks
        return List.of(
                new ReportTask(1, "Monthly Sales Report", "Sales Data", "2024-04-01", "00:15:00", "Completed")
        );
    }

    @GetMapping("/{id}")
    public ReportTaskDetail getReportTask(@PathVariable Long id) {
        // TODO: Implement logic to fetch a single report task
        return new ReportTaskDetail(1, "Monthly Sales Report", "Sales Data", "2024-04-01", "00:15:00", "Completed", "### Monthly Sales Report\n\n| Product | Units Sold | Revenue |\n|---|---|---|\n| Widget A | 500 | $10,000 |\n| Widget B | 300 | $15,000 |");
    }

    @PostMapping("/{id}/refresh")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void refreshReportTask(@PathVariable Long id) {
        // TODO: Implement logic to queue a refresh for the report task
        System.out.println("Refreshing report task: " + id);
    }
} 