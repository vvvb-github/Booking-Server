package seu.moyu.demo.booking.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import seu.moyu.demo.booking.entity.User;
import seu.moyu.demo.booking.mapper.UserMapper;
import seu.moyu.demo.booking.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.sql.Wrapper;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.logging.Logger;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author moyu_group
 * @since 2021-01-01
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    /**
     * Spring Boot 提供了一个发送邮件的简单抽象，使用的是下面这个接口，这里直接注入即可使用
     */
    @Autowired
    private JavaMailSender mailSender;

    /**
     * 配置文件中我的qq邮箱
     */
    private String from = "vvvb100@163.com";

    //过期时间
    private static final long EXPIRE_TIME = 60 * 60 * 1000;
    //私钥
    private static final String TOKEN_SECRET = "privateKey";

    @Override
    public void sendSimpleMail(String to, String subject, String content) {
        //创建SimpleMailMessage对象
        SimpleMailMessage message = new SimpleMailMessage();
        //邮件发送人
        message.setFrom(from);
        //邮件接收人
        message.setTo(to);
        //邮件主题
        message.setSubject(subject);
        //邮件内容
        message.setText(content);
        //发送邮件
        mailSender.send(message);
    }

    @Override
    public void SetIcon(Integer uuid, String path) {
        UpdateWrapper wrapper = new UpdateWrapper();
        wrapper.eq("uuid", uuid);
        User user = getById(uuid);
        user.setIconUrl(path);
        update(user, wrapper);
    }

    @Override
    public User CheckUser(String email, String password) {
        QueryWrapper wrapper = new QueryWrapper<User>();
        wrapper.eq("email", email);
        wrapper.eq("password", password);
        List<User> users = list(wrapper);
        return users.get(0);
    }

    @Override
    public String GenerateToken(Integer userid) {
        // 设置过期时间
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        // 私钥和加密算法
        Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
        // 设置头部信息
        Map<String, Object> header = new HashMap<>(2);
        header.put("Type", "Jwt");
        header.put("alg", "HS256");
        // 返回token字符串
        return JWT.create()
                .withHeader(header)
                .withClaim("userId", userid)
                .withExpiresAt(date)
                .sign(algorithm);
    }

    @Override
    public Integer AnalyzeToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT jwt = verifier.verify(token);
        Integer userId = jwt.getClaim("userId").asInt();
        return userId;
    }

    @Override
    public Integer FindUser(String email){
        QueryWrapper wrapper = new QueryWrapper<User>();
        wrapper.eq("email", email);
        List<User> p = list(wrapper);
        if(p.isEmpty()) return 0;
        return 1;
    }

    @Override
    public Integer AddUser(String email, String userName, String password) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setNickName(userName);
        user.setIconUrl("https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png");
        user.setIdCardNumber("未知");
        user.setRealName("未知");
        user.setPhoneNumber("未知");
        save(user);
        return 1;
    }

    @Override
    public Integer ResetPassword(String email,String password){
        User user = new User();
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("email",email);
        user.setPassword(password);
        update(user,wrapper);
        return 1;
    }

    @Override
    public void ChangeInformation(String token, String nickName, String phoneNumber, String realName, String idCardNumber) {
        User user = new User();
        Integer uuid = AnalyzeToken(token);
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("uuid",uuid);
        user.setNickName(nickName);
        user.setIdCardNumber(idCardNumber);
        user.setRealName(realName);
        user.setPhoneNumber(phoneNumber);
        update(user,wrapper);
    }
}
