package www.mjxy.rq.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import www.mjxy.rq.manager.model.AppUser;
import www.mjxy.rq.manager.service.AppUserService;
import www.mjxy.rq.manager.utils.MD5Generator;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@SpringBootApplication
public class RQManagerApplication implements CommandLineRunner {
    @Autowired
    AppUserService appUserService;

    public static void main(String[] args) {
        SpringApplication.run(RQManagerApplication.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
//        AppUser admin = new AppUser();
//        admin.setDepartment("团委处");
//        admin.setTrueName("管理员");
//        admin.setSchoolCode("10000001");
//        admin.setEmail("123456@qq.com");
//        admin.setUsername("admin");
//        admin.setPassword(MD5Generator.EncodingMD5("password"));
//        admin.setPhone("111101010101");
//
//        AppUser appUser = new AppUser();
//        appUser.setDepartment("计算机系");
//        appUser.setTrueName("张老师");
//        appUser.setSchoolCode("200000001");
//        appUser.setEmail("1111111@qq.com");
//        appUser.setUsername("user");
//        appUser.setPassword(MD5Generator.EncodingMD5("password"));
//        appUser.setPhone("13123423432");
//        appUserService.createUser(appUser);
//        appUserService.createUser(admin);
    }
}
