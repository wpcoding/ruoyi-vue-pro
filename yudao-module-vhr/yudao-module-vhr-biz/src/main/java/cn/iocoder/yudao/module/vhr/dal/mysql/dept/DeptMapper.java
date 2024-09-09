package cn.iocoder.yudao.module.vhr.dal.mysql.dept;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.mybatis.core.query.LambdaQueryWrapperX;
import cn.iocoder.yudao.framework.mybatis.core.mapper.BaseMapperX;
import cn.iocoder.yudao.module.vhr.dal.dataobject.dept.DeptDO;
import org.apache.ibatis.annotations.Mapper;
import cn.iocoder.yudao.module.vhr.controller.admin.dept.vo.*;

/**
 * 部门 Mapper
 *
 * @author 苏左拉
 */
@Mapper
public interface DeptMapper extends BaseMapperX<DeptDO> {

    default List<DeptDO> selectList(DeptListReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<DeptDO>()
                .likeIfPresent(DeptDO::getName, reqVO.getName())
                .eqIfPresent(DeptDO::getParentId, reqVO.getParentId())
                .eqIfPresent(DeptDO::getSort, reqVO.getSort())
                .eqIfPresent(DeptDO::getLeaderUserId, reqVO.getLeaderUserId())
                .eqIfPresent(DeptDO::getPhone, reqVO.getPhone())
                .eqIfPresent(DeptDO::getEmail, reqVO.getEmail())
                .eqIfPresent(DeptDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(DeptDO::getCreateTime, reqVO.getCreateTime())
                .orderByDesc(DeptDO::getId));
    }

	default DeptDO selectByParentIdAndId(Long parentId, String id) {
	    return selectOne(DeptDO::getParentId, parentId, DeptDO::getId, id);
	}

    default Long selectCountByParentId(Long parentId) {
        return selectCount(DeptDO::getParentId, parentId);
    }

}