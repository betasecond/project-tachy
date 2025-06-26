package edu.jimei.projecttachy.service;

import org.springframework.ai.mcp.client.McpSyncClient;
import org.springframework.ai.mcp.client.SyncMcpToolCallbackProvider;
import org.springframework.ai.mcp.schema.McpSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

/**
 * MCP (Model Context Protocol) 服务类
 * 提供与 MCP 服务器交互的业务逻辑
 */
@Service
public class McpService {

    private static final Logger logger = LoggerFactory.getLogger(McpService.class);

    private final List<McpSyncClient> mcpClients;
    private final Optional<SyncMcpToolCallbackProvider> toolCallbackProvider;

    @Autowired
    public McpService(List<McpSyncClient> mcpClients, 
                     Optional<SyncMcpToolCallbackProvider> toolCallbackProvider) {
        this.mcpClients = mcpClients;
        this.toolCallbackProvider = toolCallbackProvider;
        logger.info("MCP Service 初始化完成，已注册 {} 个 MCP 客户端", mcpClients.size());
    }

    /**
     * 获取所有 MCP 客户端的信息
     * 
     * @return 客户端信息列表
     */
    public List<String> getClientInfo() {
        return mcpClients.stream()
                .map(client -> {
                    try {
                        var serverInfo = client.getServerInfo();
                        return String.format("客户端: %s (版本: %s)", 
                                serverInfo.name(), 
                                serverInfo.version());
                    } catch (Exception e) {
                        logger.warn("获取客户端信息失败", e);
                        return "客户端信息不可用";
                    }
                })
                .toList();
    }

    /**
     * 获取所有可用的工具
     * 
     * @return 工具列表
     */
    public List<McpSchema.Tool> getAvailableTools() {
        return mcpClients.stream()
                .flatMap(client -> {
                    try {
                        var result = client.listTools();
                        logger.debug("从客户端获取到 {} 个工具", result.tools().size());
                        return result.tools().stream();
                    } catch (Exception e) {
                        logger.warn("获取工具列表失败", e);
                        return List.<McpSchema.Tool>of().stream();
                    }
                })
                .toList();
    }

    /**
     * 获取所有可用的资源
     * 
     * @return 资源列表
     */
    public List<McpSchema.Resource> getAvailableResources() {
        return mcpClients.stream()
                .flatMap(client -> {
                    try {
                        var result = client.listResources();
                        logger.debug("从客户端获取到 {} 个资源", result.resources().size());
                        return result.resources().stream();
                    } catch (Exception e) {
                        logger.warn("获取资源列表失败", e);
                        return List.<McpSchema.Resource>of().stream();
                    }
                })
                .toList();
    }

    /**
     * 获取所有可用的提示词
     * 
     * @return 提示词列表
     */
    public List<McpSchema.Prompt> getAvailablePrompts() {
        return mcpClients.stream()
                .flatMap(client -> {
                    try {
                        var result = client.listPrompts();
                        logger.debug("从客户端获取到 {} 个提示词", result.prompts().size());
                        return result.prompts().stream();
                    } catch (Exception e) {
                        logger.warn("获取提示词列表失败", e);
                        return List.<McpSchema.Prompt>of().stream();
                    }
                })
                .toList();
    }

    /**
     * 执行指定的工具
     * 
     * @param toolName 工具名称
     * @param arguments 工具参数
     * @return 执行结果
     */
    public String executeTool(String toolName, Object arguments) {
        for (McpSyncClient client : mcpClients) {
            try {
                var toolsResult = client.listTools();
                var tool = toolsResult.tools().stream()
                        .filter(t -> t.name().equals(toolName))
                        .findFirst();
                
                if (tool.isPresent()) {
                    var callResult = client.callTool(toolName, arguments);
                    logger.info("工具 '{}' 执行成功", toolName);
                    return callResult.toString();
                }
            } catch (Exception e) {
                logger.warn("执行工具 '{}' 失败: {}", toolName, e.getMessage());
            }
        }
        
        return "未找到工具: " + toolName;
    }

    /**
     * 获取工具回调提供者信息
     * 
     * @return 工具回调信息
     */
    public String getToolCallbackInfo() {
        if (toolCallbackProvider.isPresent()) {
            var provider = toolCallbackProvider.get();
            var callbacks = provider.getToolCallbacks();
            return String.format("工具回调提供者已启用，共有 %d 个回调", callbacks.length);
        } else {
            return "工具回调提供者未启用";
        }
    }

    /**
     * 检查 MCP 客户端的健康状态
     * 
     * @return 健康状态报告
     */
    public String getHealthStatus() {
        if (mcpClients.isEmpty()) {
            return "没有可用的 MCP 客户端";
        }

        int totalClients = mcpClients.size();
        int healthyClients = 0;

        for (McpSyncClient client : mcpClients) {
            try {
                client.getServerInfo();
                healthyClients++;
            } catch (Exception e) {
                logger.warn("客户端健康检查失败", e);
            }
        }

        return String.format("MCP 客户端状态: %d/%d 健康", healthyClients, totalClients);
    }
} 