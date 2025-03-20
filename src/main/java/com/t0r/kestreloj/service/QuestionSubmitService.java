package com.t0r.kestreloj.service;

import com.t0r.kestreloj.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.t0r.kestreloj.model.entity.QuestionSubmit;
import com.baomidou.mybatisplus.extension.service.IService;
import com.t0r.kestreloj.model.entity.User;

/**
* @author Towerrrr
* @description 针对表【question_submit(题目提交)】的数据库操作Service
* @createDate 2025-03-18 19:54:13
*/
public interface QuestionSubmitService extends IService<QuestionSubmit> {
    /**
     * 题目提交
     *
     * @param questionSubmitAddRequest 题目提交信息
     * @param loginUser
     * @return
     */
    long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser);

}
