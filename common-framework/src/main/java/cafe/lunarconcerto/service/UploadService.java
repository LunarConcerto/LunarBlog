package cafe.lunarconcerto.service;

import cafe.lunarconcerto.domain.entity.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author LunarConcerto
 * @time 2023/12/28
 */
public interface UploadService {

    ResponseResult<?> upload(MultipartFile img);

}
