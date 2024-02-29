package cafe.lunarconcerto.controller;

import cafe.lunarconcerto.domain.entity.ResponseResult;
import cafe.lunarconcerto.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/link")
public class LinkController {

    @Autowired
    private LinkService linkService;

    @GetMapping("/getAllLink")
    public ResponseResult<?> getAllLink(){
        return linkService.getAllLink();
    }

}
