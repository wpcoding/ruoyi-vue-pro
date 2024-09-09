package cn.iocoder.yudao.module.vhr.controller.admin.dept.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 部门 Response VO")
@Data
@ExcelIgnoreUnannotated
public class DeptRespVO {

    @Schema(description = "部门id", requiredMode = Schema.RequiredMode.REQUIRED, example = "22370")
    @ExcelProperty("部门id")
    private Long id;

    @Schema(description = "部门名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "芋艿")
    @ExcelProperty("部门名称")
    private String name;

    @Schema(description = "父部门id", requiredMode = Schema.RequiredMode.REQUIRED, example = "28588")
    @ExcelProperty("父部门id")
    private Long parentId;

    @Schema(description = "显示顺序", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("显示顺序")
    private Integer sort;

    @Schema(description = "负责人", example = "8670")
    @ExcelProperty("负责人")
    private Long leaderUserId;

    @Schema(description = "联系电话")
    @ExcelProperty("联系电话")
    private String phone;

    @Schema(description = "邮箱")
    @ExcelProperty("邮箱")
    private String email;

    @Schema(description = "部门状态（0正常 1停用）", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty("部门状态（0正常 1停用）")
    private Integer status;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}