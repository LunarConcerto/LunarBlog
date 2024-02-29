package cafe.lunarconcerto.controller;

import cafe.lunarconcerto.domain.entity.ResponseResult;
import cafe.lunarconcerto.service.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author LunarConcerto
 * @time 2023/12/28
 */
@RestController
public class UploadController {

    @Autowired
    private UploadService uploadService;

    @PostMapping("/upload")
    public ResponseResult<?> uploadPOST(MultipartFile img){
        return uploadService.upload(img);
    }

    @PutMapping("/upload")
    public ResponseResult<?> uploadPUT(MultipartFile img){
        return uploadService.upload(img);
    }



}
