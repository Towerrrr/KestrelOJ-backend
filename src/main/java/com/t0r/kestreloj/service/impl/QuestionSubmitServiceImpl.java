package com.t0r.kestreloj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.t0r.kestreloj.common.ErrorCode;
import com.t0r.kestreloj.exception.BusinessException;
import com.t0r.kestreloj.model.dto.questionsubmit.QuestionSubmitAddRequest;
import com.t0r.kestreloj.model.entity.Question;
import com.t0r.kestreloj.model.entity.QuestionSubmit;
import com.t0r.kestreloj.model.entity.User;
import com.t0r.kestreloj.model.enums.QuestionSubmitLanguageEnum;
import com.t0r.kestreloj.model.enums.QuestionSubmitStatusEnum;
import com.t0r.kestreloj.service.QuestionService;
import com.t0r.kestreloj.service.QuestionSubmitService;
import com.t0r.kestreloj.mapper.QuestionSubmitMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author Towerrrr
* @description 针对表【question_submit(题目提交)】的数据库操作Service实现
* @createDate 2025-03-18 19:54:13
*/
@Service
public class QuestionSubmitServiceImpl extends ServiceImpl<QuestionSubmitMapper, QuestionSubmit>
    implements QuestionSubmitService{

    @Resource
    private QuestionService questionService;

    /**
     * 提交题目
     *
     * @param questionSubmitAddRequest
     * @param loginUser
     * @return
     */
    @Override
    public long doQuestionSubmit(QuestionSubmitAddRequest questionSubmitAddRequest, User loginUser) {
        // 编程语言是否合法
        String language = questionSubmitAddRequest.getLanguage();
        QuestionSubmitLanguageEnum languageEnum = QuestionSubmitLanguageEnum.getEnumByValue(language);
        if (languageEnum == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "语言不合法");
        }

        long questionId = questionSubmitAddRequest.getQuestionId();
        // 判断实体是否存在，根据类别获取实体
        Question question = questionService.getById(questionId);
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR);
        }
        // 是否已提交题目
        long userId = loginUser.getId();
        // 每个用户串行提交题目
        QuestionSubmit questionSubmit = new QuestionSubmit();
        questionSubmit.setQuestionId(questionId);
        questionSubmit.setUserId(userId);
        questionSubmit.setCode(questionSubmitAddRequest.getCode());
        questionSubmit.setLanguage(language);

        // 设置初始状态
        questionSubmit.setStatus(QuestionSubmitStatusEnum.WAITING.getValue());
        questionSubmit.setJudgeInfo("{}");

        boolean save = this.save(questionSubmit);
        if (!save) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "数据插入失败");
        }
        return questionSubmit.getId();
    }

}




