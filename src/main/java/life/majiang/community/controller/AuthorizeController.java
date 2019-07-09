package life.majiang.community.controller;

import life.majiang.community.dto.AccessTokenDTO;
import life.majiang.community.dto.GitHupUser;
import life.majiang.community.mapper.UserMapper;
import life.majiang.community.model.User;
import life.majiang.community.provider.GitHupProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {

    @Autowired
    private GitHupProvider gitHupProvider;

    @Autowired
    private UserMapper userMapper;

    //spring 初始化时会根据注解里面的键去application.properties把值注入进来
    @Value("${githup.client.id}")
    private String clientId;
    @Value("${githup.client.secret}")
    private String clientSecret;
    @Value("${githup.redirect.uri}")
    private String redirectUri;
    private String token;

    /**
     * 回调方法 对githup登录发起请求时会返回对应的参数并回到指定的页面 http://localhost:8080/callback
     * @param code
     * @param state
     * @return
     */
    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code")String code, @RequestParam(name = "state")String state, HttpServletResponse response){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        String accessToken = gitHupProvider.getAccessToken(accessTokenDTO);
        GitHupUser githupUser = gitHupProvider.getUser(accessToken);
        if (githupUser != null) {
            User user = new User();
            token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(githupUser.getName());
            user.setAccountId(String.valueOf(githupUser.getId()));
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            user.setBio(githupUser.getBio());
            userMapper.insert(user);
            //如果登录成功了,将user用户的token存到cookie中
            response.addCookie(new Cookie("token",token));
            return "redirect:/";
        }else {
            //登录失败
            return "redirect:/";

        }
    }
}
