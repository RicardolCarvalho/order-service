package store.order;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

class OrderRequest {
  static String idAccount() {
    var attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    if (attrs == null) return null;
    return attrs.getRequest().getHeader("id-account");
  }
}
