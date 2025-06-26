Project Tachy - READMEEnglish | 简体中文English1. Project BackgroundIn the rapidly evolving field of artificial intelligence, there is a growing demand for customizable and specialized conversational agents. Generic, one-size-fits-all chatbots often fail to meet the specific needs of different users or domains. Project Tachy was created to address this gap. It provides a flexible and scalable backend platform that allows developers and users to create, manage, and interact with multiple distinct AI agents, each with its own unique personality, instructions, and conversational history.2. Project PurposeProject Tachy is a Spring Boot-based application that serves as the core backend for a multi-agent chat system. Its primary purpose is to provide a complete set of RESTful APIs for managing users, AI agents, and conversations.Key Features:User Authentication: Secure user registration and login system using JWT (JSON Web Tokens) for stateless authentication.Agent Management: Allows users to create, update, retrieve, and delete customizable AI agents.Agent Specialization: Each agent can be defined with a unique name and a "system prompt," which instructs the AI on its role, personality, and the context it should operate within.Conversational Management: Manages conversations between users and agents, ensuring that each conversation's history is stored and isolated.Persistent Storage: Uses a PostgreSQL database with Flyway for version-controlled schema management to persistently store all user, agent, and message data.Scalable Architecture: Built with a standard layered architecture (Controller, Service, Repository), making the project easy to maintain and extend.3. Technology StackBackend Framework: Spring Boot 3Programming Language: Java 17Authentication/Security: Spring Security, JSON Web Tokens (jjwt)Database: PostgreSQLDatabase Migration: FlywayData Persistence: Spring Data JPA (Hibernate)Build Tool: Gradle4. How to UsePrerequisitesJDK 17 or laterGradle 7.0 or laterPostgreSQL Server1. Database SetupMake sure your PostgreSQL server is running.Create a new database for the project. The default expected name is project_tachy.CREATE DATABASE project_tachy;
2. Application ConfigurationOpen the src/main/resources/application.properties file.Update the database connection settings to match your local environment:# Adjust host, port, and database name if you used a different one
spring.datasource.url=jdbc:postgresql://localhost:5432/project_tachy
# Enter your PostgreSQL username and password
spring.datasource.username=your_username
spring.datasource.password=your_password
Set a secure secret key for JWT generation. This is a critical security step.# Replace "your-secret-key" with a long, random, and secure string
jwt.secret=your-secret-key
3. Running the ApplicationYou can run the application in one of the following ways:Using Gradle Wrapper:# On macOS/Linux
./gradlew bootRun

# On Windows
gradlew.bat bootRun
From your IDE:Locate the ProjectTachyApplication.java file and run it as a Java application.The application will start, and Flyway will automatically run the database migrations located in src/main/resources/db/migration to set up the required tables.4. API EndpointsOnce the application is running, you can interact with it via its REST API (default port 8080).AuthenticationRegister a new user:POST /api/auth/register{
    "username": "testuser",
    "password": "password123"
}
Login to get a JWT token:POST /api/auth/login{
    "username": "testuser",
    "password": "password123"
}
The response will contain the JWT token. You must include this token in the Authorization header for all subsequent protected requests.Header: Authorization: Bearer <your_jwt_token>Agent ManagementCreate a new Agent: POST /api/agentsGet all Agents: GET /api/agentsUpdate an Agent: PUT /api/agents/{agentId}ChattingStart a new conversation with an agent:POST /api/conversations{
    "agentId": 1,
    "title": "My first chat"
}
Send a message in a conversation:POST /api/conversations/{conversationId}/messages{
    "content": "Hello, who are you?"
}
The system will save your message and return the agent's response. (Note: The current ChatService implementation provides a simple "Echo" response. This would be the integration point for a real Large Language Model like GPT or Gemini.)Get all messages in a conversation:GET /api/conversations/{conversationId}/messages简体中文1. 项目背景在人工智能领域飞速发展的今天，市场对可定制化、专业化的对话代理（AI Agent）的需求日益增长。通用型、一刀切的聊天机器人往往无法满足不同用户或特定领域的具体需求。Project Tachy 正是为了填补这一空白而创建的。它提供了一个灵活且可扩展的后端平台，允许开发者和用户创建、管理多个独立的AI代理，并与之互动。每个AI代理都可以拥有自己独特的个性、指令和对话历史。2. 项目作用Project Tachy 是一个基于 Spring Boot 的应用程序，作为多代理聊天系统的核心后端。其主要目的是提供一整套完整的 RESTful API，用于管理用户、AI代理和对话。核心功能:用户认证: 提供基于 JWT (JSON Web Tokens) 的安全用户注册和登录系统，实现无状态认证。代理管理: 允许用户创建、更新、查询和删除可定制的AI代理。代理专业化: 每个代理都可以通过定义唯一的名称和“系统提示（System Prompt）”来进行定制。系统提示可以指导AI扮演特定角色、拥有某种性格，并规定其行为的上下文。对话管理: 管理用户与代理之间的对话，确保每次对话的历史记录都被独立存储和隔离。持久化存储: 使用 PostgreSQL 数据库和 Flyway（用于版本控制的数据库迁移）来持久化存储所有用户、代理和消息数据。可扩展架构: 采用标准的分层架构（Controller、Service、Repository），使项目易于维护和扩展。3. 技术栈后端框架: Spring Boot 3编程语言: Java 17认证/安全: Spring Security, JSON Web Tokens (jjwt)数据库: PostgreSQL数据库迁移: Flyway数据持久化: Spring Data JPA (Hibernate)构建工具: Gradle4. 如何使用前置条件JDK 17 或更高版本Gradle 7.0 或更高版本PostgreSQL 服务器1. 数据库设置确保您的 PostgreSQL 服务器正在运行。为项目创建一个新的数据库。默认期望的数据库名称是 project_tachy。CREATE DATABASE project_tachy;
2. 应用配置打开 src/main/resources/application.properties 文件。更新数据库连接设置，以匹配您的本地环境：# 如果您使用了不同的主机、端口或数据库名，请在此处调整
spring.datasource.url=jdbc:postgresql://localhost:5432/project_tachy
# 输入您的 PostgreSQL 用户名和密码
spring.datasource.username=your_username
spring.datasource.password=your_password
为JWT生成设置一个安全的密钥。这是一个关键的安全步骤。# 将 "your-secret-key" 替换为一个长的、随机的、安全的字符串
jwt.secret=your-secret-key
3. 运行应用您可以通过以下任一方式运行应用程序：使用 Gradle 包装器:# 在 macOS/Linux 上
./gradlew bootRun

# 在 Windows 上
gradlew.bat bootRun
从您的 IDE 运行:找到 ProjectTachyApplication.java 文件，并将其作为Java应用程序运行。应用程序启动后，Flyway 将自动运行位于 src/main/resources/db/migration 目录下的数据库迁移脚本，以创建所需的表结构。4. API 端点应用运行后，您可以通过其 REST API (默认端口 8080) 与之交互。认证注册新用户:POST /api/auth/register{
    "username": "testuser",
    "password": "password123"
}
登录以获取 JWT 令牌:POST /api/auth/login{
    "username": "testuser",
    "password": "password123"
}
响应中将包含 JWT 令牌。对于所有后续需要保护的请求，您必须在 Authorization 请求头中包含此令牌。请求头格式: Authorization: Bearer <your_jwt_token>代理管理创建新代理: POST /api/agents获取所有代理: GET /api/agents更新代理: PUT /api/agents/{agentId}聊天与一个代理开始新的对话:POST /api/conversations{
    "agentId": 1,
    "title": "我的第一次聊天"
}
在对话中发送消息:POST /api/conversations/{conversationId}/messages{
    "content": "你好，你是谁？"
}
系统将保存您的消息并返回代理的回复。（注意：当前 ChatService 的实现仅提供一个简单的“回声”式回复。这里是未来集成真正的大语言模型，如GPT或Gemini的地方。）获取对话中的所有消息:GET /api/conversations/{conversationId}/messages