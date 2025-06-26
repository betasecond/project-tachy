package edu.jimei.projecttachy.controller;

import edu.jimei.projecttachy.service.McpService;
import org.springframework.ai.mcp.schema.McpSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * MCP (Model Context Protocol) REST 控制器
 * 提供 MCP 客户端功能的 HTTP API 端点
 */
@RestController
@RequestMapping("/api/mcp")
@PreAuthorize("hasRole('USER')")
public class McpController {

    private static final Logger logger = LoggerFactory.getLogger(McpController.class);

    private final McpService mcpService;

    @Autowired
    public McpController(McpService mcpService) {
        this.mcpService = mcpService;
    }

    /**
     * 获取 MCP 客户端状态信息
     * 
     * @return 客户端状态响应
     */
    @GetMapping("/status")
    public ResponseEntity<Map<String, Object>> getStatus() {
        try {
            var clientInfo = mcpService.getClientInfo();
            var healthStatus = mcpService.getHealthStatus();
            var toolCallbackInfo = mcpService.getToolCallbackInfo();

            var response = Map.of(
                "clientInfo", clientInfo,
                "healthStatus", healthStatus,
                "toolCallbackInfo", toolCallbackInfo,
                "timestamp", System.currentTimeMillis()
            );

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("获取 MCP 状态失败", e);
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "获取状态失败: " + e.getMessage()));
        }
    }

    /**
     * 获取所有可用的工具
     * 
     * @return 工具列表
     */
    @GetMapping("/tools")
    public ResponseEntity<List<McpSchema.Tool>> getTools() {
        try {
            var tools = mcpService.getAvailableTools();
            logger.info("返回 {} 个可用工具", tools.size());
            return ResponseEntity.ok(tools);
        } catch (Exception e) {
            logger.error("获取工具列表失败", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * 获取所有可用的资源
     * 
     * @return 资源列表
     */
    @GetMapping("/resources")
    public ResponseEntity<List<McpSchema.Resource>> getResources() {
        try {
            var resources = mcpService.getAvailableResources();
            logger.info("返回 {} 个可用资源", resources.size());
            return ResponseEntity.ok(resources);
        } catch (Exception e) {
            logger.error("获取资源列表失败", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * 获取所有可用的提示词
     * 
     * @return 提示词列表
     */
    @GetMapping("/prompts")
    public ResponseEntity<List<McpSchema.Prompt>> getPrompts() {
        try {
            var prompts = mcpService.getAvailablePrompts();
            logger.info("返回 {} 个可用提示词", prompts.size());
            return ResponseEntity.ok(prompts);
        } catch (Exception e) {
            logger.error("获取提示词列表失败", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    /**
     * 执行指定的工具
     * 
     * @param toolName 工具名称
     * @param request 工具执行请求
     * @return 执行结果
     */
    @PostMapping("/tools/{toolName}/execute")
    public ResponseEntity<Map<String, Object>> executeTool(
            @PathVariable String toolName,
            @RequestBody(required = false) Map<String, Object> request) {
        
        try {
            logger.info("执行工具: {}", toolName);
            
            Object arguments = request != null ? request.get("arguments") : null;
            String result = mcpService.executeTool(toolName, arguments);
            
            var response = Map.of(
                "toolName", toolName,
                "result", result,
                "timestamp", System.currentTimeMillis(),
                "success", true
            );
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("执行工具 '{}' 失败", toolName, e);
            
            var errorResponse = Map.of(
                "toolName", toolName,
                "error", e.getMessage(),
                "timestamp", System.currentTimeMillis(),
                "success", false
            );
            
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }

    /**
     * 获取 MCP 系统的健康检查
     * 
     * @return 健康检查结果
     */
    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> healthCheck() {
        try {
            var healthStatus = mcpService.getHealthStatus();
            var isHealthy = !healthStatus.contains("0/");
            
            var response = Map.of(
                "status", healthStatus,
                "healthy", isHealthy,
                "timestamp", System.currentTimeMillis()
            );
            
            return isHealthy ? 
                ResponseEntity.ok(response) : 
                ResponseEntity.status(503).body(response);
                
        } catch (Exception e) {
            logger.error("健康检查失败", e);
            
            var errorResponse = Map.of(
                "status", "健康检查失败",
                "healthy", false,
                "error", e.getMessage(),
                "timestamp", System.currentTimeMillis()
            );
            
            return ResponseEntity.status(503).body(errorResponse);
        }
    }

    /**
     * 获取 MCP 功能概览
     * 
     * @return 功能概览
     */
    @GetMapping("/overview")
    public ResponseEntity<Map<String, Object>> getOverview() {
        try {
            var tools = mcpService.getAvailableTools();
            var resources = mcpService.getAvailableResources();
            var prompts = mcpService.getAvailablePrompts();
            var clientInfo = mcpService.getClientInfo();
            
            var overview = Map.of(
                "toolsCount", tools.size(),
                "resourcesCount", resources.size(),
                "promptsCount", prompts.size(),
                "clientsCount", clientInfo.size(),
                "clients", clientInfo,
                "capabilities", Map.of(
                    "tools", !tools.isEmpty(),
                    "resources", !resources.isEmpty(),
                    "prompts", !prompts.isEmpty()
                ),
                "timestamp", System.currentTimeMillis()
            );
            
            return ResponseEntity.ok(overview);
        } catch (Exception e) {
            logger.error("获取 MCP 概览失败", e);
            return ResponseEntity.internalServerError()
                    .body(Map.of("error", "获取概览失败: " + e.getMessage()));
        }
    }
} 