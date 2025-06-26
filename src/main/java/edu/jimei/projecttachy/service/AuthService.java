package edu.jimei.projecttachy.service;

import edu.jimei.projecttachy.entity.User;
import edu.jimei.projecttachy.repository.UserRepository;
import edu.jimei.projecttachy.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public String authenticate(String email, String password) {
        Optional<User> userOpt = userRepository.findByEmail(email);
        
        if (userOpt.isEmpty()) {
            throw new RuntimeException("用户不存在");
        }

        User user = userOpt.get();
        
        // 检查密码
        if (!passwordEncoder.matches(password, user.getPasswordHash())) {
            throw new RuntimeException("密码错误");
        }

        // 生成JWT token
        return jwtUtil.generateToken(email);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("用户不存在"));
    }

    public User register(String name, String email, String password) {
        // 检查邮箱是否已存在
        if (userRepository.findByEmail(email).isPresent()) {
            throw new RuntimeException("邮箱已被注册");
        }

        // 验证输入
        if (name == null || name.trim().isEmpty()) {
            throw new RuntimeException("姓名不能为空");
        }
        
        if (email == null || email.trim().isEmpty()) {
            throw new RuntimeException("邮箱不能为空");
        }
        
        if (password == null || password.length() < 6) {
            throw new RuntimeException("密码长度至少6位");
        }

        // 创建新用户
        User user = new User();
        user.setName(name.trim());
        user.setEmail(email.trim().toLowerCase());
        user.setPasswordHash(passwordEncoder.encode(password));
        
        return userRepository.save(user);
    }
} 