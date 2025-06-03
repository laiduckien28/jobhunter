package vn.devops.jobhunter.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import vn.devops.jobhunter.domain.Permission;
import vn.devops.jobhunter.domain.Role;
import vn.devops.jobhunter.domain.User;
import vn.devops.jobhunter.service.UserService;
import vn.devops.jobhunter.util.SecurityUtil;
@Transactional
public class PermissionInterceptor implements HandlerInterceptor {
    @Autowired
    private UserService userService;

    // public PermissionInterceptor(UserService userService) {
    //     this.userService = userService;
    // }

    @Override


    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response, Object handler)
            throws Exception {

      String path = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        String requestURI = request.getRequestURI();
        String httpMethod = request.getMethod();
        System.out.println(">>> RUN preHandle");
        System.out.println(">>> path= " + path);
        System.out.println(">>> httpMethod= " + httpMethod);
        System.out.println(">>> requestURI= " + requestURI);


        // check permission 

        String email =  SecurityUtil.getCurrentUserLogin().isPresent() ? SecurityUtil.getCurrentUserLogin().get() : "";
        

        if(  email != null && !email.isEmpty()  )
        { 
            User user = this.userService.handleGetUserByUsername(email);
            if( user !=  null)  {
                Role  role = user.getRole();
                if( role != null)  {
                    List<Permission> permissions =  role.getPermissions();
                    boolean isAllow  =  permissions.stream().anyMatch(item  ->  
                    item.getApiPath().equals(path) &&
                    item.getMethod().equals(httpMethod)

                );
                if( isAllow == false) { 
                    return  false;
                }
                }
            }
        }

        return true;
    }
}
