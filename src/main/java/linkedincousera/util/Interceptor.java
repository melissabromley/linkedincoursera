package linkedincoursera.util;

import linkedincoursera.services.LinkedinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.linkedin.api.LinkedInProfile;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by harsh on 5/10/15.
 */
@Component
public class Interceptor implements HandlerInterceptor {
    @Autowired
    LinkedinService linkedinService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        LinkedInProfile basicProf = linkedinService.getLinkedInProfile();
        System.out.println("***************************8");
        modelAndView.addObject("userName",basicProf.getFirstName()+" "+basicProf.getLastName());
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
