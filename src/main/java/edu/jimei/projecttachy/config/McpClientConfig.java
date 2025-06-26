package edu.jimei.projecttachy.config;

import org.springframework.ai.mcp.client.McpClient;
import org.springframework.ai.mcp.client.McpSyncClientCustomizer;
import org.springframework.ai.mcp.schema.McpSchema;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;

/**
 * MCP (Model Context Protocol) Client 配置类
 * 提供自定义的 MCP 客户端配置，包括事件处理和日志记录
 */
@Configuration
public class McpClientConfig {

    private static final Logger logger = LoggerFactory.getLogger(McpClientConfig.class);

    /**
     * 自定义 MCP 同步客户端配置
     * 
     * @return McpSyncClientCustomizer 实例
     */
    @Bean
    public McpSyncClientCustomizer mcpSyncClientCustomizer() {
        return new McpSyncClientCustomizer() {
            @Override
            public void customize(String serverConfigurationName, McpClient.SyncSpec spec) {
                logger.info("正在为服务器配置 '{}' 自定义 MCP 客户端", serverConfigurationName);
                
                // 设置请求超时
                spec.requestTimeout(Duration.ofSeconds(30));
                
                // 监听工具变化通知
                spec.toolsChangeConsumer((List<McpSchema.Tool> tools) -> {
                    logger.info("服务器 '{}' 的工具列表已更新，当前工具数量: {}", 
                            serverConfigurationName, tools.size());
                    tools.forEach(tool -> 
                        logger.debug("工具: {} - {}", tool.name(), tool.description())
                    );
                });
                
                // 监听资源变化通知
                spec.resourcesChangeConsumer((List<McpSchema.Resource> resources) -> {
                    logger.info("服务器 '{}' 的资源列表已更新，当前资源数量: {}", 
                            serverConfigurationName, resources.size());
                    resources.forEach(resource -> 
                        logger.debug("资源: {} - {}", resource.name(), resource.description())
                    );
                });
                
                // 监听提示词变化通知
                spec.promptsChangeConsumer((List<McpSchema.Prompt> prompts) -> {
                    logger.info("服务器 '{}' 的提示词列表已更新，当前提示词数量: {}", 
                            serverConfigurationName, prompts.size());
                    prompts.forEach(prompt -> 
                        logger.debug("提示词: {} - {}", prompt.name(), prompt.description())
                    );
                });
                
                // 监听日志消息
                spec.loggingConsumer((McpSchema.LoggingMessageNotification log) -> {
                    String level = log.level().toString();
                    String data = log.data().toString();
                    logger.info("MCP 服务器 '{}' 日志 [{}]: {}", 
                            serverConfigurationName, level, data);
                });
            }
        };
    }
} 