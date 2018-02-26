package www.mjxy.rq.manager.configure.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * Created by wwhai on 2017/11/22.
 */
public class UserAuthenticationProvider implements AuthenticationProvider {
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    public  UserAuthenticationProvider(){
        super();
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return false;
    }
}