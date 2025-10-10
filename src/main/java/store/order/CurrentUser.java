package store.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class CurrentUser {
  private static final String HEADER = "X-User-Id";
  @Autowired private HttpServletRequest request;

  public String id() {
    String userId = request.getHeader(HEADER);
    if (userId == null || userId.isBlank()) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Missing authenticated user");
    }
    return userId;
  }
}
