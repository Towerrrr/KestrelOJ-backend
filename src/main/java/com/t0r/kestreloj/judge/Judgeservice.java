package com.t0r.kestreloj.judge;

import com.t0r.kestreloj.model.vo.QuestionSubmitVO;

/**
 * 判题服务
 */
public interface Judgeservice {

    /**
     * 判题
     * @param questionId
     * @return
     */
    QuestionSubmitVO doJudge(long questionId);
}
