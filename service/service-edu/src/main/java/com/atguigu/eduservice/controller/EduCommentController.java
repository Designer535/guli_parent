package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.JwtUtils;
import com.atguigu.commonutils.R;
import com.atguigu.commonutils.UcenterMemberOrder;
import com.atguigu.eduservice.client.UcenterClient;
import com.atguigu.eduservice.entity.EduComment;
import com.atguigu.eduservice.service.EduCommentService;
import com.atguigu.servicebase.exceptionHandler.GuLiException;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.FreeMarkerConfigurerBeanDefinitionParser;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 * 评论 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-04-04
 */
@RestController
@CrossOrigin
@RequestMapping("/eduservice/edu-comment")
public class EduCommentController {

    @Autowired
    private EduCommentService eduCommentService;

    @Autowired
    private UcenterClient ucenterClient;

    @GetMapping("/getCommentPage/{page}/{limit}/{courseId}")
    public R getCommentPage(@PathVariable("page") long page, @PathVariable("limit") long limit, @PathVariable("courseId") String courseId) {
        Page<EduComment> pageParam = new Page<>(page, limit);
        Map<String, Object> map = eduCommentService.getCommentPage(pageParam, courseId);
        return R.ok().data("map", map);
    }

    @PostMapping("addComment")
    public R addComment(@RequestBody EduComment eduComment, HttpServletRequest request) {
        String memberIdByJwtToken = JwtUtils.getMemberIdByJwtToken(request);
        if (StringUtils.isEmpty(memberIdByJwtToken)) {
            return R.fail().code(28004).message("请先登录");
        }
        eduComment.setMemberId(memberIdByJwtToken);
        UcenterMemberOrder memberOrder = ucenterClient.getMemberOrder(memberIdByJwtToken);
        eduComment.setAvatar(memberOrder.getAvatar());
        eduComment.setNickname(memberOrder.getNickname());
        boolean save = eduCommentService.save(eduComment);
        if (save) {
            return R.ok();
        } else {
            return R.fail();
        }
    }
}

