package fa.training.config;

import fa.training.interceptor.AdminAuthenticationInterceptor;

import fa.training.interceptor.SiteAuthenticationInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AuthenticationInterceptorConfig implements WebMvcConfigurer {
    private final AdminAuthenticationInterceptor adminAuthenticationInterceptor;
    private final SiteAuthenticationInterceptor siteAuthenticationInterceptor;
    public AuthenticationInterceptorConfig(AdminAuthenticationInterceptor adminAuthenticationInterceptor, SiteAuthenticationInterceptor siteAuthenticationInterceptor) {
        this.adminAuthenticationInterceptor = adminAuthenticationInterceptor;
        this.siteAuthenticationInterceptor = siteAuthenticationInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(adminAuthenticationInterceptor)
                .addPathPatterns("/admin/**");

        registry.addInterceptor(siteAuthenticationInterceptor)
                .addPathPatterns("/site/**");
    }
}
