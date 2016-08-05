package app.base;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.support.RequestContext;

public class Message {

	public static RequestContext context;

	public static String getMessage(HttpServletRequest request,
			String message_key) {
		RequestContext requestContext = getContext(request);
		return requestContext.getMessage(message_key);
	}

	public static RequestContext getContext(HttpServletRequest request) {
		if (context == null) {
			context = new RequestContext(request);
		}
		return context;
	}
}
