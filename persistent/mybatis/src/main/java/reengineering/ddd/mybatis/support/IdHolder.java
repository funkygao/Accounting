package reengineering.ddd.mybatis.support;

/**
 * 获取MySQL auto_increment id的返回值
 */
public class IdHolder {
    private Long id;

    public String id() {
        return id.toString();
    }
}
