package com.ace.controller.auth;

import com.ace.common.base.ApiBaseResponse;
import com.ace.entity.user.SysUser;
import com.ace.repository.AuthService;
import com.ace.repository.SysUserRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@Api(value = "用户controller", description = "用户操作")
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private AuthService authService;
    @Autowired
    private SysUserRepository userRepository;

    @ApiOperation("登录")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody ApiAuthReqParam apiAuthReqParam
    ) throws AuthenticationException {
        SysUser user=apiAuthReqParam.getSysUser();
        SysUser sysUser = authService.login(user.getUsername(), user.getPassword());
        return ResponseEntity.ok(ApiBaseResponse.fromHttpStatus(HttpStatus.OK, sysUser));

    }

    /*@RequestMapping(value = "/login", method = RequestMethod.GET)
    public ResponseEntity<?> refreshAndGetAuthenticationToken(
        HttpServletRequest request) throws AuthenticationException {
        String token = request.getHeader(tokenHeader);
        String refreshedToken = authService.refresh(token);
        if (refreshedToken == null) {
            return ResponseEntity.badRequest().body(null);
        } else {
            return ResponseEntity.ok(new JwtAuthenticationResponse(refreshedToken));
        }
    }*/
    @ApiOperation("注册")
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public SysUser register(@RequestBody SysUser addedUser) throws AuthenticationException {
        return authService.register(addedUser);
    }

}
