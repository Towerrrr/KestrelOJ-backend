package com.t0r.kestreloj.model.vo;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.annotation.TableName;
import com.t0r.kestreloj.judge.codesandbox.model.JudgeInfo;
import com.t0r.kestreloj.model.entity.QuestionSubmit;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import java.io.Serializable;
import java.util.Date;

/**
 * 题目提交封装类
 * @TableName question
 */
@TableName(value ="question")
@Data
public class QuestionSubmitVO implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 编程语言
     */
    private String language;

    /**
     * 代码
     */
    private String code;

    /**
     * 判题信息
     */
    private JudgeInfo judgeInfo;

    /**
     * 状态：0-等待判题，1-正在判题，2-判题完成，3-判题失败
     */
    private Integer status;

    /**
     * 题目 id
     */
    private Long questionId;

    /**
     * 创建用户 id
     */
    private Long userId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 提交用户信息
     */
    private UserVO userVO;

    /**
     * 题目信息
     */
    private QuestionVO questionVO;

    /**
     * 包装类转对象
     *
     * @param questionSubmitVO
     * @return
     */
    public static QuestionSubmit voToObj(QuestionSubmitVO questionSubmitVO) {
        if (questionSubmitVO == null) {
            return null;
        }
        QuestionSubmit questionSubmit = new QuestionSubmit();
        BeanUtils.copyProperties(questionSubmitVO, questionSubmit);
        JudgeInfo judgeInfoObj = questionSubmitVO.getJudgeInfo();
        if (judgeInfoObj!= null) {
            questionSubmit.setJudgeInfo(JSONUtil.toJsonStr(judgeInfoObj));
        }
        return questionSubmit;
    }

    /**
     * 对象转包装类
     *
     * @param questionSubmit
     * @return
     */
    public static QuestionSubmitVO objToVo(QuestionSubmit questionSubmit) {
        if (questionSubmit == null) {
            return null;
        }
        QuestionSubmitVO questionSubmitVO = new QuestionSubmitVO();
        BeanUtils.copyProperties(questionSubmit, questionSubmitVO);

        String judgeInfoStr = questionSubmit.getJudgeInfo();
        questionSubmitVO.setJudgeInfo(JSONUtil.toBean(judgeInfoStr, JudgeInfo.class));

        return questionSubmitVO;
    }

    private static final long serialVersionUID = 1L;
}