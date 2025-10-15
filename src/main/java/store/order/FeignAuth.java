package store.order;

import feign.RequestInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class FeignAuth {

  @Bean
  public RequestInterceptor forwardAuthHeader() {
    return template -> {
      var attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
      if (attrs == null) return;
      HttpServletRequest req = attrs.getRequest();
      String auth = req.getHeader("Authorization");
      if (auth != null && !auth.isBlank()) {
        template.header("Authorization", auth);
      }
    };
  }
}
