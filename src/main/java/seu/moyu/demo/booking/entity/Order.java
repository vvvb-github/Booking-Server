package seu.moyu.demo.booking.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
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
@TableName("t_Order")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "uuid", type = IdType.AUTO)
    private Integer uuid;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private Double price;

    private Integer state;

    @TableField("hotelId")
    private Integer hotelId;

    private Integer star;

    @TableField("roomId")
    private Integer roomId;

    @TableField("userId")
    private Integer userId;


}
