package com.t0r.kestreloj.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.t0r.kestreloj.model.dto.question.QuestionQueryRequest;
import com.t0r.kestreloj.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.t0r.kestreloj.model.dto.questionsubmit.QuestionSubmitQueryRequest;
import com.t0r.kestreloj.model.entity.Question;
import com.t0r.kestreloj.model.entity.QuestionSubmit;
import com.baomidou.mybatisplus.extension.service.IService;
import com.t0r.kestreloj.model.entity.User;
import com.t0r.kestreloj.model.vo.QuestionSubmitVO;
import com.t0r.kestreloj.model.vo.QuestionVO;

import javax.servlet.http.HttpServletRequest;

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

    /**
     * 获取查询条件
     *
     * @param questionSubmitQueryRequest
     * @return
     */
    QueryWrapper<QuestionSubmit> getQueryWrapper(QuestionSubmitQueryRequest questionSubmitQueryRequest);

    /**
     * 获取题目封装
     *
     * @param questionSubmit
     * @param loginUser
     * @return
     */
    QuestionSubmitVO getQuestionSubmitVO(QuestionSubmit questionSubmit, User loginUser);

    /**
     * 分页获取题目封装
     *
     * @param questionSubmitPage
     * @param loginUser
     * @return
     */
    Page<QuestionSubmitVO> getQuestionSubmitVOPage(Page<QuestionSubmit> questionSubmitPage, User loginUser);
}
