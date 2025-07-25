
package com.isa.brtaeasyfill.security;
import com.isa.brtaeasyfill.model.Institute;
import com.isa.brtaeasyfill.repository.InstituteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Component
public class IpRestrictionFilter implements Filter {

    private final InstituteRepository instituteRepository;

    @Autowired
    public IpRestrictionFilter(InstituteRepository instituteRepository) {
        this.instituteRepository = instituteRepository;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        String instituteIdStr = req.getHeader("Institute-Id");
        String clientIp = req.getRemoteAddr();

        if (instituteIdStr != null) {
            try {
                Long instituteId = Long.parseLong(instituteIdStr);
                Optional<Institute> instituteOpt = instituteRepository.findById(instituteId);

                if (instituteOpt.isPresent()) {
                    Institute institute = instituteOpt.get();
                    String allowedIp = institute.getAllowedIp();
                    if (allowedIp != null && !allowedIp.equals(clientIp)) {
                        HttpServletResponse httpResp = (HttpServletResponse) response;
                        httpResp.setContentType("application/json");
                        httpResp.setStatus(HttpServletResponse.SC_FORBIDDEN);
                        httpResp.getWriter().write("{\"error\":\"Unauthorized IP address\"}");
                        return;
                    }
                }
            } catch (NumberFormatException e) {
                // Invalid institute id header, ignore or log error
            }
        }
        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // No initialization needed
    }

    @Override
    public void destroy() {
        // No cleanup needed
    }
}