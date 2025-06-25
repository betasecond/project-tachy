package edu.jimei.projecttachy.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//-TODO: Replace with actual DTO
record Stat(String label, String value) {}

@RestController
@RequestMapping("/api/stats")
public class DashboardController {

    @GetMapping
    public List<Stat> getStats() {
        // TODO: Implement logic to calculate and fetch dashboard stats
        return List.of(
                new Stat("Tasks in Progress", "5"),
                new Stat("Reports Completed This Month", "23"),
                new Stat("Total Data Sources Connected", "8")
        );
    }
} 