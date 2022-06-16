package com.moveo.epicure.aspect;

import com.moveo.epicure.entity.Permit;
import com.moveo.epicure.exception.NoPermitException;
import com.moveo.epicure.repo.PermitRepo;
import com.moveo.epicure.util.TokenUtil;
import io.jsonwebtoken.Claims;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class PermissionAspect {

    private PermitRepo repo;

    public PermissionAspect(PermitRepo repo) {
        this.repo = repo;
    }

    /**
     * Gets user type from token, checks if the method is in the type's permission list.
     * If an exception is thrown in the process of getting the token, getting the type's permission list and looking for the permit in the list
     * or if the permit is not found: throws NoPermitException
     * Otherwise, run the method and returns the ResponseEntity it generated/ throws the exception it threw.
     * @param joinPoint
     * @return the response entity from the requested method
     * @throws Throwable - if the requested method threw an exception while running
     */
    @Around("@annotation(com.moveo.epicure.annotation.PermissionNeeded)")
    public ResponseEntity checkPermit(ProceedingJoinPoint joinPoint) throws Throwable {
        final HttpServletRequest httpRequest =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        System.out.println("request found");
        boolean permitted = false;
        try {
            Claims claims = TokenUtil.validateAndGetClaims(httpRequest);
            System.out.println("claims: "+claims);
            String userType = claims.get("userType").toString();
            System.out.println("user type: "+userType);
            String methodName = joinPoint.getSignature().getName();
            System.out.println("method name: "+methodName);
            List<String> permissionList = repo.findById(userType).get().getPermissionList();
            System.out.println("permission list: ");
            System.out.println(permissionList);
            permitted = permissionList.contains(methodName);
            System.out.println("permitted= "+permitted);
        } catch (Exception e) {
            System.out.println("throwing exception from catch");
            System.out.println(e);
            throw new NoPermitException();
        }
        if (!permitted) {
            System.out.println("throwing exception from lack of permission");
            throw new NoPermitException();
        }
        try{
            Object proceed = joinPoint.proceed();
            System.out.println("result: "+proceed);
            boolean instance = proceed instanceof ResponseEntity;
            System.out.println("is instance of response entity: "+instance);
            return (ResponseEntity) proceed;
        } catch (Exception e) {
            System.out.println("exception thrown while proceed");
        }
        return null;
    }
}
