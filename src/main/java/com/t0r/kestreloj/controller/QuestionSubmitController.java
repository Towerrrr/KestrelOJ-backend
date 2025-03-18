package com.t0r.kestreloj.controller;

import com.t0r.kestreloj.common.BaseResponse;
import com.t0r.kestreloj.common.ErrorCode;
import com.t0r.kestreloj.common.ResultUtils;
import com.t0r.kestreloj.exception.BusinessException;
import com.t0r.kestreloj.model.dto.postthumb.QuestionSubmitAddRequest;
import com.t0r.kestreloj.model.entity.User;
import com.t0r.kestreloj.service.QuestionSubmitService;
import com.t0r.kestreloj.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 题目提交接口
 */
@RestController
@RequestMapping("/question_submit")
@Slf4j
public class QuestionSubmitController {

    @Resource
    private QuestionSubmitService questionSubmitService;

    @Resource
    private UserService userService;

    /**
     * 点赞 / 取消点赞
     *
     * @param questionSubmitAddRequest
     * @param request
     * @return resultNum 本次点赞变化数
     */
    @PostMapping("/")
    public BaseResponse<Integer> doThumb(@RequestBody QuestionSubmitAddRequest questionSubmitAddRequest,
            HttpServletRequest request) {
        if (questionSubmitAddRequest == null || questionSubmitAddRequest.getPostId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 登录才能点赞
        final User loginUser = userService.getLoginUser(request);
        long postId = questionSubmitAddRequest.getPostId();
        int result = questionSubmitService.doQuestionSubmit(postId, loginUser);
        return ResultUtils.success(result);
    }

}
