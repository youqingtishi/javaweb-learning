package top.soft.classoa.service;


import top.soft.classoa.entity.Node;
import top.soft.classoa.mapper.NodeMapper;
import top.soft.classoa.utils.MyBatisUtils;

import java.util.List;

/**
 * @author Administrator
 * @description: TODO
 * @date 2024/12/7 15:01
 */
public class NodeService {
    public List<Node> selectNodeByUserId(Long userId){
        return(List<Node>) MyBatisUtils.executeQuery(sqlSession -> {
            NodeMapper mapper = sqlSession.getMapper(NodeMapper.class);
            return mapper.selectNodeByUserId(userId);
        });
    }
}
