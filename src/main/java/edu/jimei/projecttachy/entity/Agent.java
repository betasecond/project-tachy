package edu.jimei.projecttachy.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "agents")
public class Agent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(name = "avatar_url")
    private String avatarUrl;

    @Column(name = "compatible_sources", columnDefinition = "text[]")
    private List<String> compatibleSources;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public List<String> getCompatibleSources() {
        return compatibleSources;
    }

    public void setCompatibleSources(List<String> compatibleSources) {
        this.compatibleSources = compatibleSources;
    }
} 