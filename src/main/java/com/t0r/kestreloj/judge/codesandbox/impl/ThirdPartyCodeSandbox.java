package com.t0r.kestreloj.judge.codesandbox.impl;

import com.t0r.kestreloj.judge.codesandbox.CodeSandbox;
import com.t0r.kestreloj.judge.codesandbox.model.ExecuteCodeRequest;
import com.t0r.kestreloj.judge.codesandbox.model.ExecuteCodeResponse;

/**
 * 第三方代码沙箱
 */
public class ThirdPartyCodeSandbox implements CodeSandbox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("执行第三方代码沙箱");
        return null;
    }
}
