package seu.moyu.demo.booking.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author moyu_group
 * @since 2021-01-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_User")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "uuid", type = IdType.AUTO)
    private Integer uuid;

    @TableField("nickName")
    private String nickName;

    private String password;

    private String email;

    @TableField("phoneNumber")
    private String phoneNumber;

    @TableField("iconUrl")
    private String iconUrl;

    @TableField("realName")
    private String realName;

    @TableField("idCardNumber")
    private String idCardNumber;


}
