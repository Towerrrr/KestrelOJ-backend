package com.t0r.kestreloj.judge;

import cn.hutool.json.JSONUtil;
import com.t0r.kestreloj.common.ErrorCode;
import com.t0r.kestreloj.exception.BusinessException;
import com.t0r.kestreloj.judge.codesandbox.CodeSandBoxFactory;
import com.t0r.kestreloj.judge.codesandbox.CodeSandBoxProxy;
import com.t0r.kestreloj.judge.codesandbox.CodeSandbox;
import com.t0r.kestreloj.judge.codesandbox.model.ExecuteCodeRequest;
import com.t0r.kestreloj.judge.codesandbox.model.ExecuteCodeResponse;
import com.t0r.kestreloj.model.dto.question.JudgeCase;
import com.t0r.kestreloj.model.dto.question.JudgeConfig;
import com.t0r.kestreloj.model.dto.questionsubmit.JudgeInfo;
import com.t0r.kestreloj.model.entity.Question;
import com.t0r.kestreloj.model.entity.QuestionSubmit;
import com.t0r.kestreloj.model.enums.JudgeInfoMessageEnum;
import com.t0r.kestreloj.model.enums.QuestionSubmitStatusEnum;
import com.t0r.kestreloj.model.vo.QuestionSubmitVO;
import com.t0r.kestreloj.service.QuestionService;
import com.t0r.kestreloj.service.QuestionSubmitService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JudgeserviceImpl implements Judgeservice {

    @Resource
    private QuestionService questionService;

    @Resource
    private QuestionSubmitService questionSubmitService;

    @Value("${codesandbox.type:example}")
    private String type;

    @Override
    public QuestionSubmitVO doJudge(long questionSubmitId) {
        // 传入题目提交id，获取对应题目、提交信息
        QuestionSubmit questionSubmit = questionSubmitService.getById(questionSubmitId);
        if (questionSubmit == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "题目提交信息不存在");
        }
        Long questionId = questionSubmit.getQuestionId();
        Question question = questionService.getById(questionId);
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "题目不存在");
        }
        // 如果题目提交状态不为等待中，不用重复执行
        if (!questionSubmit.getStatus().equals(QuestionSubmitStatusEnum.WAITING.getValue())) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "题目正在判题中");
        }
        // 更改题目状态为判题中，防止重复执行
        QuestionSubmit questionSubmitUpdate = new QuestionSubmit();
        questionSubmitUpdate.setId(questionSubmitId);
        questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.RUNNING.getValue());
        boolean update = questionSubmitService.updateById(questionSubmitUpdate);
        if (!update) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "题目状态更新错误");
        }
        // 调用沙箱，获取执行结果
        CodeSandbox codeSandbox = CodeSandBoxFactory.newInstance(type);
        codeSandbox = new CodeSandBoxProxy(codeSandbox);
        String language = questionSubmit.getLanguage();
        String code = questionSubmit.getCode();
        // 获取输入用例
        String judgeCaseStr = question.getJudgeCase();
        List<JudgeCase> judgeCaseList = JSONUtil.toList(judgeCaseStr, JudgeCase.class);
        List<String> inputList = judgeCaseList.stream().map(JudgeCase::getInput).collect(Collectors.toList());
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .language(language)
                .inputList(inputList)
                .build();
        ExecuteCodeResponse executeCodeResponse = codeSandbox.executeCode(executeCodeRequest);
        // 根据沙箱的执行结果，设置题目的判题状态和信息
        List<String> outputList = executeCodeResponse.getOutputList();
        JudgeInfoMessageEnum judgeInfoMessageEnum = JudgeInfoMessageEnum.WAITING;
        // 判断沙箱执行结果数量与预期输出数量是否一致
        if (outputList.size() != inputList.size()) {
            judgeInfoMessageEnum = JudgeInfoMessageEnum.WRONG_ANSWER;
            return null;
        }
        // 判断每一项输出是否与预期输出一致
        for (int i = 0; i < judgeCaseList.size(); i++) {
            JudgeCase judgeCase = judgeCaseList.get(i);
            if (!judgeCase.getOutput().equals(outputList.get(i))) {
                judgeInfoMessageEnum = JudgeInfoMessageEnum.WRONG_ANSWER;
                return null;
            }
        }
        // 判断题目的限制是否符合要求
        JudgeInfo judgeInfo = executeCodeResponse.getJudgeInfo();
        Long memory = judgeInfo.getMemory();
        Long time = judgeInfo.getTime();
        String judgeConfigStr = question.getJudgeConfig();
        JudgeConfig judgeConfig = JSONUtil.toBean(judgeConfigStr, JudgeConfig.class);
        Long needMemoryLimit = judgeConfig.getMemoryLimit();
        Long needTimeLimit = judgeConfig.getTimeLimit();
        if (memory > needMemoryLimit) {
            judgeInfoMessageEnum = JudgeInfoMessageEnum.MEMORY_LIMIT_EXCEEDED;
            return null;
        }
        if (time > needTimeLimit) {
            judgeInfoMessageEnum = JudgeInfoMessageEnum.TIME_EXCEEDED;
            return null;
        }
        return null;
    }
}
