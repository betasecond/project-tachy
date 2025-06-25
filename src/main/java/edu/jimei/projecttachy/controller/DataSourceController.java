package edu.jimei.projecttachy.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//-TODO: Replace with actual DTOs
record DataSourceOption(String value, String label) {}
record DashboardDataSource(long id, String name, String type, String status, String created) {}
record NewDataSourceRequest(String name, String type) {}


@RestController
@RequestMapping("/api")
public class DataSourceController {

    @GetMapping("/datasources")
    public List<DataSourceOption> getDataSourceOptions() {
        // TODO: Implement logic to fetch data source options
        return List.of(
                new DataSourceOption("", "Select Data Source (e.g., Sales Q4 2023)"),
                new DataSourceOption("sales_q4_2023", "Sales Q4 2023"),
                new DataSourceOption("customer_feedback_jan", "Customer Feedback Jan"),
                new DataSourceOption("market_trends_report", "Market Trends Report")
        );
    }

    @GetMapping("/dashboard/datasources")
    public List<DashboardDataSource> getDashboardDataSources() {
        // TODO: Implement logic to fetch data sources for the dashboard
        return List.of(
                new DashboardDataSource(1, "Sales Data", "CSV", "Active", "2024-01-15")
        );
    }

    @PostMapping("/dashboard/datasources")
    @ResponseStatus(HttpStatus.CREATED)
    public DashboardDataSource addDataSource(@RequestBody NewDataSourceRequest request) {
        // TODO: Implement logic to create a new data source
        System.out.println("Creating new data source: " + request.name());
        // In a real app, this would be saved to a DB and the new object returned
        return new DashboardDataSource(4, request.name(), request.type(), "Inactive", "2024-07-25");
    }
} 