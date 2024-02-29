package cafe.lunarconcerto.service.impl;

import cafe.lunarconcerto.domain.entity.ResponseResult;
import cafe.lunarconcerto.enums.AppHttpCodeEnum;
import cafe.lunarconcerto.exception.SystemException;
import cafe.lunarconcerto.service.UploadService;
import cafe.lunarconcerto.utils.PathUtils;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

/**
 * @author LunarConcerto
 * @time 2023/12/28
 */
@Service
@Data
@ConfigurationProperties(prefix = "cos")
public class TencentCosUploadService implements UploadService {

    private String secretId;

    private String secretKey;

    private String bucketName;

    private String hostname;

    private String regionName;

    @Override
    public ResponseResult<?> upload(MultipartFile img) {
        // 判断文件类型
        // 获取原始文件名
        String originalFilename = img.getOriginalFilename();

        if (originalFilename == null){
            throw new SystemException(AppHttpCodeEnum.FILE_TYPE_ERROR);
        }

        // 对原始文件名进行判断
        if (!originalFilename.endsWith(".png") && !originalFilename.endsWith(".jpg") && !originalFilename.endsWith(".gif")){
            throw new SystemException(AppHttpCodeEnum.FILE_TYPE_ERROR);
        }

        // 判断通过则上传文件到COS
        try {
            String url = uploadCos(img);
            return ResponseResult.okResult(url);
        } catch (IOException e) {
            throw new SystemException(AppHttpCodeEnum.SYSTEM_ERROR);
        }
    }


    private String uploadCos(MultipartFile img) throws IOException {
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        Region region = new Region(regionName);
        ClientConfig clientConfig = new ClientConfig(region);
        clientConfig.setHttpProtocol(HttpProtocol.https);
        COSClient cosClient = new COSClient(cred, clientConfig);
        String key = PathUtils.generateFilePath(Objects.requireNonNull(img.getOriginalFilename()));
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key,
                img.getInputStream(), new ObjectMetadata());
        PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);

        return hostname + "/" + key;
    }

}
