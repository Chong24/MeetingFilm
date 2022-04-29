package com.mooc.meetingfilm.properties;

/**
 * JWT的配置文件：可以配置JWT的加密方式、secret键、和生成的token过期时间
 * @author wang
 * @create 2022-04-19
 */
public class JwtProperties {

    private static JwtProperties jwtProperties = new JwtProperties();

    private JwtProperties(){

    }

    public static JwtProperties getJwtProperties(){
        return jwtProperties;
    }

    //JWT的前缀
    public static final String JWT_PREFIX = "jwt";

    //JWT的header信息，一般是在请求头里加入Authorization
    private String header = "Authorization";

    //secret是保存在服务器端的，jwt的签发生成也是在服务器端的，
    // secret就是用来进行jwt的签发和jwt的验证，所以，它就是你服务端的私钥
    private String secret = "defaultSecret";

    //Token过期时间
    private Long expiration = 604800L; // 默认是七天

    //验证路径
    private String authPath = "login";

    //加密方式
    private String md5Key = "randomKey";

    public static String getJwtPrefix() {
        return JWT_PREFIX;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public Long getExpiration() {
        return expiration;
    }

    public void setExpiration(Long expiration) {
        this.expiration = expiration;
    }

    public String getAuthPath() {
        return authPath;
    }

    public void setAuthPath(String authPath) {
        this.authPath = authPath;
    }

    public String getMd5Key() {
        return md5Key;
    }

    public void setMd5Key(String md5Key) {
        this.md5Key = md5Key;
    }

}
