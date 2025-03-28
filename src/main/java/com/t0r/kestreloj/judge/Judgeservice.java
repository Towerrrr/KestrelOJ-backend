package com.t0r.kestreloj.judge;

import com.t0r.kestreloj.model.entity.QuestionSubmit;

/**
 * 判题服务
 */
public interface Judgeservice {

    /**
     * 判题
     * @param questionId
     * @return
     */
    QuestionSubmit doJudge(long questionId);
}
