import org.it611.das.util.RedisUtil;
import org.junit.Test;
import redis.clients.jedis.Jedis;

public class RedisTest {

    @Test
    public void Test1() {
        Jedis client = RedisUtil.getJedis();
        String result = client.set("abc","123");
        System.out.println(result);
        System.out.println(client.get("abc"));
        client.close();
    }
}
