package com.moveo.epicure.aspect;

import com.moveo.epicure.annotation.PermissionNeeded;
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
//    @Around("@annotation(PermissionNeeded)")
//    public ResponseEntity checkPermit(ProceedingJoinPoint joinPoint) {
//        final HttpServletRequest httpRequest =
//                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
//        String token = httpRequest.getHeader("Authorization");
//        token = token.substring(7);

    // joinPoint.getSignature().getName()
//    }
}
