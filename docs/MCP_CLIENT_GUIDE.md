# MCP Client 使用指南

## 概述

本项目已集成 Spring AI 的 Model Context Protocol (MCP) Client，允许与各种 MCP 服务器进行交互，扩展 AI 应用的功能。

## 功能特性

- ✅ **多客户端支持** - 同时连接多个 MCP 服务器
- ✅ **双传输协议** - 支持 STDIO 和 SSE 传输
- ✅ **工具集成** - 自动集成 MCP 工具到 Spring AI 框架
- ✅ **事件监听** - 实时监听服务器事件和状态变化
- ✅ **REST API** - 提供完整的 HTTP API 接口
- ✅ **健康检查** - 内置客户端状态监控

## 配置方式

### 1. 基本配置 (application.properties)

```properties
# 启用 MCP Client
spring.ai.mcp.client.enabled=true
spring.ai.mcp.client.name=tachy-mcp-client
spring.ai.mcp.client.type=SYNC
spring.ai.mcp.client.request-timeout=30s
spring.ai.mcp.client.toolcallback.enabled=true
```

### 2. STDIO 传输配置

```properties
# 文件系统服务器
spring.ai.mcp.client.stdio.connections.filesystem.command=npx
spring.ai.mcp.client.stdio.connections.filesystem.args=-y,@modelcontextprotocol/server-filesystem,/tmp

# 搜索服务器
spring.ai.mcp.client.stdio.connections.search.command=npx
spring.ai.mcp.client.stdio.connections.search.args=-y,@modelcontextprotocol/server-brave-search
spring.ai.mcp.client.stdio.connections.search.env.BRAVE_API_KEY=your-api-key
```

### 3. SSE 传输配置

```properties
# 远程 MCP 服务器
spring.ai.mcp.client.sse.connections.remote.url=http://localhost:8080
spring.ai.mcp.client.sse.connections.remote.sse-endpoint=/sse
```

### 4. JSON 配置文件

可以使用 Claude Desktop 格式的 JSON 配置文件：

```properties
spring.ai.mcp.client.stdio.servers-configuration=classpath:mcp-servers.json
```

## API 端点

### 状态查询

```http
GET /api/mcp/status
```

获取所有 MCP 客户端的状态信息。

### 功能概览

```http
GET /api/mcp/overview
```

获取 MCP 系统的完整功能概览。

### 工具管理

```http
# 获取所有可用工具
GET /api/mcp/tools

# 执行指定工具
POST /api/mcp/tools/{toolName}/execute
Content-Type: application/json

{
  "arguments": {
    "param1": "value1",
    "param2": "value2"
  }
}
```

### 资源和提示词

```http
# 获取所有资源
GET /api/mcp/resources

# 获取所有提示词
GET /api/mcp/prompts
```

### 健康检查

```http
GET /api/mcp/health
```

## 常用 MCP 服务器

### 1. 文件系统服务器

```bash
# 安装
npm install -g @modelcontextprotocol/server-filesystem

# 配置
spring.ai.mcp.client.stdio.connections.filesystem.command=npx
spring.ai.mcp.client.stdio.connections.filesystem.args=-y,@modelcontextprotocol/server-filesystem,/path/to/directory
```

### 2. Brave 搜索服务器

```bash
# 安装
npm install -g @modelcontextprotocol/server-brave-search

# 配置
spring.ai.mcp.client.stdio.connections.search.command=npx
spring.ai.mcp.client.stdio.connections.search.args=-y,@modelcontextprotocol/server-brave-search
spring.ai.mcp.client.stdio.connections.search.env.BRAVE_API_KEY=your-brave-api-key
```

### 3. GitHub 服务器

```bash
# 安装
npm install -g @modelcontextprotocol/server-github

# 配置
spring.ai.mcp.client.stdio.connections.github.command=npx
spring.ai.mcp.client.stdio.connections.github.args=-y,@modelcontextprotocol/server-github
spring.ai.mcp.client.stdio.connections.github.env.GITHUB_PERSONAL_ACCESS_TOKEN=your-token
```

## 代码示例

### 服务层使用

```java
@Autowired
private McpService mcpService;

// 获取所有工具
List<McpSchema.Tool> tools = mcpService.getAvailableTools();

// 执行工具
String result = mcpService.executeTool("file_read", Map.of("path", "/tmp/test.txt"));

// 检查健康状态
String status = mcpService.getHealthStatus();
```

### 控制器使用

```java
@RestController
public class MyController {
    
    @Autowired
    private McpService mcpService;
    
    @GetMapping("/my-tools")
    public List<McpSchema.Tool> getMyTools() {
        return mcpService.getAvailableTools();
    }
}
```

## 故障排除

### 常见问题

1. **客户端连接失败**
   - 检查 MCP 服务器是否正确安装
   - 验证命令路径和参数
   - 查看应用日志获取详细错误信息

2. **工具执行失败**
   - 确认工具名称正确
   - 检查参数格式和类型
   - 验证权限和环境变量

3. **性能问题**
   - 调整请求超时设置
   - 考虑使用异步客户端 (`spring.ai.mcp.client.type=ASYNC`)
   - 监控服务器资源使用

### 日志配置

```properties
# 启用 MCP 相关日志
logging.level.edu.jimei.projecttachy.config.McpClientConfig=DEBUG
logging.level.edu.jimei.projecttachy.service.McpService=DEBUG
logging.level.org.springframework.ai.mcp=DEBUG
```

## 安全注意事项

- MCP 工具具有系统访问权限，确保只连接可信的服务器
- 在生产环境中谨慎配置文件系统访问路径
- 定期轮换 API 密钥和访问令牌
- 监控工具执行日志以发现异常活动

## 更多资源

- [Spring AI MCP Client 文档](https://docs.spring.io/spring-ai/reference/api/mcp/mcp-client-boot-starter-docs.html)
- [Model Context Protocol 规范](https://modelcontextprotocol.io/)
- [MCP 服务器列表](https://github.com/modelcontextprotocol/servers) 