package cn.iocoder.yudao.module.vhr.service.dept;

import org.springframework.stereotype.Service;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import cn.iocoder.yudao.module.vhr.controller.admin.dept.vo.*;
import cn.iocoder.yudao.module.vhr.dal.dataobject.dept.DeptDO;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.PageParam;
import cn.iocoder.yudao.framework.common.util.object.BeanUtils;

import cn.iocoder.yudao.module.vhr.dal.mysql.dept.DeptMapper;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.vhr.enums.ErrorCodeConstants.*;

/**
 * 部门 Service 实现类
 *
 * @author 苏左拉
 */
@Service
@Validated
public class DeptServiceImpl implements DeptService {

    @Resource
    private DeptMapper deptMapper;

    @Override
    public Long createDept(DeptSaveReqVO createReqVO) {
        // 校验父部门id的有效性
        validateParentDept(null, createReqVO.getParentId());
        // 校验部门id的唯一性
        validateDeptIdUnique(null, createReqVO.getParentId(), createReqVO.getId());

        // 插入
        DeptDO dept = BeanUtils.toBean(createReqVO, DeptDO.class);
        deptMapper.insert(dept);
        // 返回
        return dept.getId();
    }

    @Override
    public void updateDept(DeptSaveReqVO updateReqVO) {
        // 校验存在
        validateDeptExists(updateReqVO.getId());
        // 校验父部门id的有效性
        validateParentDept(updateReqVO.getId(), updateReqVO.getParentId());
        // 校验部门id的唯一性
        validateDeptIdUnique(updateReqVO.getId(), updateReqVO.getParentId(), updateReqVO.getId());

        // 更新
        DeptDO updateObj = BeanUtils.toBean(updateReqVO, DeptDO.class);
        deptMapper.updateById(updateObj);
    }

    @Override
    public void deleteDept(Long id) {
        // 校验存在
        validateDeptExists(id);
        // 校验是否有子部门
        if (deptMapper.selectCountByParentId(id) > 0) {
            throw exception(DEPT_EXITS_CHILDREN);
        }
        // 删除
        deptMapper.deleteById(id);
    }

    private void validateDeptExists(Long id) {
        if (deptMapper.selectById(id) == null) {
            throw exception(DEPT_NOT_EXISTS);
        }
    }

    private void validateParentDept(Long id, Long parentId) {
        if (parentId == null || DeptDO.PARENT_ID_ROOT.equals(parentId)) {
            return;
        }
        // 1. 不能设置自己为父部门
        if (Objects.equals(id, parentId)) {
            throw exception(DEPT_PARENT_ERROR);
        }
        // 2. 父部门不存在
        DeptDO parentDept = deptMapper.selectById(parentId);
        if (parentDept == null) {
            throw exception(DEPT_PARENT_NOT_EXITS);
        }
        // 3. 递归校验父部门，如果父部门是自己的子部门，则报错，避免形成环路
        if (id == null) { // id 为空，说明新增，不需要考虑环路
            return;
        }
        for (int i = 0; i < Short.MAX_VALUE; i++) {
            // 3.1 校验环路
            parentId = parentDept.getParentId();
            if (Objects.equals(id, parentId)) {
                throw exception(DEPT_PARENT_IS_CHILD);
            }
            // 3.2 继续递归下一级父部门
            if (parentId == null || DeptDO.PARENT_ID_ROOT.equals(parentId)) {
                break;
            }
            parentDept = deptMapper.selectById(parentId);
            if (parentDept == null) {
                break;
            }
        }
    }

    private void validateDeptIdUnique(Long id, Long parentId, String id) {
        DeptDO dept = deptMapper.selectByParentIdAndId(parentId, id);
        if (dept == null) {
            return;
        }
        // 如果 id 为空，说明不用比较是否为相同 id 的部门
        if (id == null) {
            throw exception(DEPT_ID_DUPLICATE);
        }
        if (!Objects.equals(dept.getId(), id)) {
            throw exception(DEPT_ID_DUPLICATE);
        }
    }

    @Override
    public DeptDO getDept(Long id) {
        return deptMapper.selectById(id);
    }

    @Override
    public List<DeptDO> getDeptList(DeptListReqVO listReqVO) {
        return deptMapper.selectList(listReqVO);
    }

}