package cafe.lunarconcerto.controller;

import cafe.lunarconcerto.domain.entity.ResponseResult;
import cafe.lunarconcerto.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author LunarConcerto
 * @time 2023/12/30
 */
@RestController
public class UploadController {

    @Autowired
    private UploadService uploadService;

    @PostMapping("/upload")
    public ResponseResult<?> uploadImg(@RequestParam("img") MultipartFile multipartFile) {
        return uploadService.upload(multipartFile);
    }

}
