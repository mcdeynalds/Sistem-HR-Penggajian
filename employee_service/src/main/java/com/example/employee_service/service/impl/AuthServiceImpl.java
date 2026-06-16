package com.example.employee_service.service.impl;

import com.example.employee_service.entity.RolesEntity;
import com.example.employee_service.entity.UsersEntity;
import com.example.employee_service.payload.req.LoginReq;
import com.example.employee_service.payload.req.RegisterReq;
import com.example.employee_service.payload.res.GlobalRes;
import com.example.employee_service.payload.res.LoginRes;
import com.example.employee_service.repository.RolesRepository;
import com.example.employee_service.repository.UsersRepository;
import com.example.employee_service.service.AuthService;
import com.example.employee_service.utility.JwtUtil;
import com.example.employee_service.utility.Message;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final UsersRepository usersRepository;

    private final RolesRepository rolesRepository;

    private final AuthenticationManager authenticationManager;

    private final PasswordEncoder passwordEncoder;

    private final JwtUtil jwtUtil;

    public AuthServiceImpl(UsersRepository usersRepository,
            RolesRepository rolesRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder,
            JwtUtil jwtUtil) {
        this.usersRepository = usersRepository;
        this.rolesRepository = rolesRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    // Login stateless - JWT
    @Override
    public GlobalRes<LoginRes> loginJwt(LoginReq req) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            req.getUsername(), req.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            UsersEntity user = usersRepository.findByUsername(req.getUsername())
                    .orElseThrow(() -> new RuntimeException(Message.LOGIN_FAILED));

            String token = jwtUtil.generateToken(user.getUsername());

            LoginRes res = new LoginRes(
                    user.getUsername(),
                    user.getEmail(),
                    user.getRole().getRoleName(),
                    token);

            return new GlobalRes<>(true, Message.LOGIN_SUCCESS, res);

        } catch (Exception e) {
            return new GlobalRes<>(false, Message.LOGIN_FAILED, null);
        }
    }

    // Login stateful - Session
    @Override
    public GlobalRes<LoginRes> loginSession(LoginReq req, HttpServletRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            req.getUsername(), req.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Simpan authentication ke session
            HttpSession session = request.getSession(true);
            session.setAttribute("SPRING_SECURITY_CONTEXT",
                    SecurityContextHolder.getContext());

            UsersEntity user = usersRepository.findByUsername(req.getUsername())
                    .orElseThrow(() -> new RuntimeException(Message.LOGIN_FAILED));

            LoginRes res = new LoginRes(
                    user.getUsername(),
                    user.getEmail(),
                    user.getRole().getRoleName(),
                    null // session tidak pakai token
            );

            return new GlobalRes<>(true, Message.LOGIN_SUCCESS, res);

        } catch (Exception e) {
            return new GlobalRes<>(false, Message.LOGIN_FAILED, null);
        }
    }

    // Logout
    @Override
    public GlobalRes<String> logout(HttpServletRequest request,
            HttpServletResponse response) {
        try {
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.invalidate();
            }
            SecurityContextHolder.clearContext();
            return new GlobalRes<>(true, Message.LOGOUT_SUCCESS, null);
        } catch (Exception e) {
            return new GlobalRes<>(false, e.getMessage(), null);
        }
    }

    // Register user baru
    @Override
    public GlobalRes<String> register(RegisterReq req) {
        try {
            // Cek username sudah ada atau belum
            if (usersRepository.existsByUsername(req.getUsername())) {
                return new GlobalRes<>(false, "Username sudah terdaftar", null);
            }

            // Cek email sudah ada atau belum
            if (usersRepository.existsByEmail(req.getEmail())) {
                return new GlobalRes<>(false, "Email sudah terdaftar", null);
            }

            // Cari role
            RolesEntity role = rolesRepository.findByRoleName(req.getRoleName())
                    .orElseThrow(() -> new RuntimeException("Role tidak ditemukan"));

            // Buat user baru
            UsersEntity user = new UsersEntity();
            user.setUsername(req.getUsername());
            user.setPassword(passwordEncoder.encode(req.getPassword()));
            user.setEmail(req.getEmail());
            user.setRole(role);

            usersRepository.save(user);

            return new GlobalRes<>(true, Message.REGISTER_SUCCESS, null);

        } catch (Exception e) {
            return new GlobalRes<>(false, e.getMessage(), null);
        }
    }
}