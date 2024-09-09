package cn.iocoder.yudao.module.vhr.enums;

import cn.iocoder.yudao.framework.common.exception.ErrorCode;

/**
 * Vhr 错误码枚举类
 *
 * Vhr 系统，使用 2_002_004_000 段
 */
public interface ErrorCodeConstants {

    ErrorCode DEPT_NOT_EXISTS = new ErrorCode(2_002_004_000, "部门不存在");
    ErrorCode DEPT_EXITS_CHILDREN = new ErrorCode(2_002_004_001, "存在存在子部门，无法删除");
    ErrorCode DEPT_PARENT_NOT_EXITS = new ErrorCode(2_002_004_001,"父级部门不存在");
    ErrorCode DEPT_PARENT_ERROR = new ErrorCode(2_002_004_001, "不能设置自己为父部门");
    ErrorCode DEPT_ID_DUPLICATE = new ErrorCode(2_002_004_001, "已经存在该部门id的部门");
    ErrorCode DEPT_PARENT_IS_CHILD = new ErrorCode(2_002_004_001, "不能设置自己的子Dept为父Dept");

}
