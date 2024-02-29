package cafe.lunarconcerto;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.BasicSessionCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Date;

/**
 * @author LunarConcerto
 * @time 2023/12/28
 */
@SpringBootTest
public class CosTest {

    @Test
    void testCos() throws FileNotFoundException {
// 1 初始化用户身份信息（secretId, secretKey）。
// SECRETID 和 SECRETKEY 请登录访问管理控制台 https://console.cloud.tencent.com/cam/capi 进行查看和管理
        String secretId = "AKIDqhYHvjV6iIlEYmlPvHcvEPWRBi3DByb5";
        String secretKey = "Mk0Nf2KTr8Obq9UhW9jI6zumeL4hJnCW";
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
// 2 设置 bucket 的地域, COS 地域的简称请参见 https://cloud.tencent.com/document/product/436/6224
// clientConfig 中包含了设置 region, https(默认 http), 超时, 代理等 set 方法, 使用可参见源码或者常见问题 Java SDK 部分。
        Region region = new Region("ap-nanjing");
        ClientConfig clientConfig = new ClientConfig(region);
// 这里建议设置使用 https 协议
// 从 5.6.54 版本开始，默认使用了 https
        clientConfig.setHttpProtocol(HttpProtocol.https);
// 3 生成 cos 客户端。
        COSClient cosClient = new COSClient(cred, clientConfig);

        // 指定要上传的文件
        File localFile = new File("C:\\Users\\13992\\Pictures\\Camera Roll\\Cafe.png");
// 指定文件将要存放的存储桶
        String bucketName = "blog-1307417584";

        FileInputStream fileInputStream = new FileInputStream("C:\\Users\\13992\\Pictures\\Camera Roll\\Cafe.png");

        String key = "img/"+ (fileInputStream.hashCode())+ "A" + System.currentTimeMillis() +".jpg";
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key,
                fileInputStream, new ObjectMetadata());
        PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);

    }

}
