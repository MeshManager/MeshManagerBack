package org.agent.service.common.filter;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class ErrorMetricsFilter extends OncePerRequestFilter {

    private final MeterRegistry meterRegistry;

    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain
    ) throws ServletException, IOException {
        filterChain.doFilter(request, response);

        // 5xx 응답일 경우 에러 메트릭 증가
        if (response.getStatus() >= 500) {
            String requestURI = request.getRequestURI();

            // 숫자 ID를 {id}로, UUID를 {uuid}로 바꾸기
            String normalizedURI = requestURI
                .replaceAll("/\\d+", "/{id}")
                .replaceAll("/[a-f0-9\\-]{36}", "/{uuid}");

            Counter.builder("api_errors_total")
                .description("API 5xx Error Counter")
                .tag("application", "agent-service")
                .tag("uri", normalizedURI)
                .register(meterRegistry)
                .increment();
        }
    }
}
