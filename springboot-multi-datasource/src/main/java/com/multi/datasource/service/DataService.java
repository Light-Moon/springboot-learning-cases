package com.multi.datasource.service;

import com.multi.datasource.bean.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description: 数据源处理服务类
 * @author: QL Zhang
 * @time: 2020/08/10 19:29
 **/

@Service
public class DataService {

    /**
     * 用@Qualifier注解指定要使用的数据源配置类
     *
     * @author: QL Zhang
     * @time: 2020/8/10 19:47
     */
    @Autowired
    @Qualifier("ctdfsJdbcTemplate")
    private JdbcTemplate ctdfsJdbcTemplate;

    /**
     * 无参数形式的查询
     *
     * @author: QL Zhang
     * @time: 2020/8/10 19:37
     */
    public List<UserEntity> select(){
        //下述sql拼接语句仅示例用
        StringBuilder sql = new StringBuilder()
                .append("SELECT t.prod_inst_id, t.tenant_id, ")
                .append("a.host_ip, a.property_key, a.property_value, ")
                .append("b.comptype, b.entity_level1, b.entity_level2, b.entity_level3, ")
                .append("c.prov_id ")
                .append("FROM t_b_paas_product t ")
                .append("JOIN t_sys_ctdfs_conf a ON t.prod_inst_id = a.prod_inst_id ")
                .append("JOIN t_sys_ctdfs_conf_north b ON a.property_key = b.property_key ")
                .append("LEFT JOIN t_sys_ctdfs_north_piid_provid c ON a.prod_inst_id = c.prod_inst_id ")
                .append("WHERE t.prod_running_state = 2 ")
                .append("ORDER BY prod_inst_id, host_ip, comptype ");
        // 通过BeanPropertyRowMapper类可以将数据库中查询出来的数据自动映射成类数据
        return ctdfsJdbcTemplate.query(sql.toString(),  new BeanPropertyRowMapper<>(UserEntity.class));
    }

    /**
     * 更新数据表
     *
     * @author: QL Zhang
     * @time: 2020/8/10 19:37
     */
    public void update(int id, String timeStr) {
        String sql = "update t_sys_ctdfs_logs set push_time = ? WHERE id = ?";

        ctdfsJdbcTemplate.update(sql, timeStr, id);
    }

    /**
     * 带参数的查询
     *
     * @author: QL Zhang
     * @time: 2020/8/10 19:41
     */
    public List<UserEntity> selectWithParam(UserEntity user){
        StringBuilder sql = new StringBuilder().append("SELECT * FROM tb_user AS u WHERE u.name =  ? and u.age = ?");
        return ctdfsJdbcTemplate.query(sql.toString(), new Object[] { user.getName(), user.getAge() }, new BeanPropertyRowMapper<>(UserEntity.class));
    }

}
