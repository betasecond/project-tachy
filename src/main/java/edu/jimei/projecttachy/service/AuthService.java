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
} 