package edu.jimei.projecttachy.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

//-TODO: Replace with actual DTO
record Agent(long id, String name, String description, String compatible, String avatar) {}

@RestController
@RequestMapping("/api/agents")
public class AgentController {

    @GetMapping
    public List<Agent> getAgents() {
        // TODO: Implement logic to fetch all agents
        return List.of(
                new Agent(1, "Sales Analyst", "Analyzes sales data and provides insights.", "Sales Data, CRM Exports", "https://lh3.googleusercontent.com/aida-public/AB6AXuB0tsX4smz7Riiv0KuBo0i6NXSRKYEywD9FQHdO1zM2UpZSHAXlJB5OEvqhte8rnoNh7HKM7ZVZKoGVmNyhArUDVvsZvBedEggO2GPWa71t0b9Wd1GUJbXWBAKHp8j1qr6FCPNJQoPegqOiHeazan90M_EE6CBzLh5Js8j3_zX-hdXPrPvB_kOOuhL7GxBz4NjSA0GwbDc4XoKzK-dRgJH7p_XOsOOcpXWCQzvoWM-vZzt3uqkBx6Ufb8mHnaug5RBfyCi94qMb-R52"),
                new Agent(2, "Customer Feedback Analyzer", "Analyzes customer feedback and identifies trends.", "Survey Results, Support Tickets", "https://lh3.googleusercontent.com/aida-public/AB6AXuCD2phqUzWJO9i-WQsmmB5cqNo55OXOa1jpVvpiTX-ebZlmIAb9dzL8rXZcVAPwZfIWFroKSVfzaEeIExsoz4IrYn7cb0hzTTJFClWAviNQDYQVFKwFRz8puqioHyEou4cm7DiA1NGm2lZ7sqXbykyWeV2RAe3V-wjNcUn6L0-dBCSIHrO2Rf8BnM2kZAjKysgGi9Ij8cyTqRB_XRuuEf6xo4B6BENJbPWXAz0VYvFvlKPKdGCDGZNCFEIAqUlTx4M5NqtzZ3tWXuhP")
        );
    }
} 