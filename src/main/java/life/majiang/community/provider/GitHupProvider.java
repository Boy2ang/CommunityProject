package life.majiang.community.provider;

import com.alibaba.fastjson.JSON;
import life.majiang.community.dto.AccessTokenDTO;
import life.majiang.community.dto.GitHupUser;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GitHupProvider {
    /**
     * @param accessTokenDTO 需要的参数
     * @return 返回 AccessToken
     */
    public String getAccessToken(AccessTokenDTO accessTokenDTO) {
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenDTO));
        Request request = new Request.Builder().url("https://github.com/login/oauth/access_token").post(body).build();
        try {
            Response response = client.newCall(request).execute();
            //ctrl + alt + n 减少代码声明
            return response.body().string().split("&")[0].split("=")[1];
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @param accessToken
     * @return 拿到授权登录着的用户信息
     */
    public GitHupUser getUser(String accessToken) {
        System.out.println("accessToken-->" + accessToken);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("https://api.github.com/user?access_token=" + accessToken).build();
        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            System.out.println(string);
            GitHupUser gitHupUser = JSON.parseObject(string, GitHupUser.class);
            return gitHupUser;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
