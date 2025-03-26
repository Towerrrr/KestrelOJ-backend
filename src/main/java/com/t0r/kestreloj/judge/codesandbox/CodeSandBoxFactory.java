package com.t0r.kestreloj.judge.codesandbox;

import com.t0r.kestreloj.judge.codesandbox.impl.ExampleCodeSandbox;
import com.t0r.kestreloj.judge.codesandbox.impl.RemoteCodeSandbox;
import com.t0r.kestreloj.judge.codesandbox.impl.ThirdPartyCodeSandbox;

/**
 * 代码沙箱工厂（根据字符串参数创建代码沙箱实例）
 */
public class CodeSandBoxFactory {

    /**
     * 创建代码沙箱实例
     *
     * @param type 沙箱类型
     * @return
     */
    public static CodeSandbox newInstance(String type) {
        switch (type) {
            case "example":
                return new ExampleCodeSandbox();
            case "remote":
                return new RemoteCodeSandbox();
            case "thirdParty":
                return new ThirdPartyCodeSandbox();
            default:
                return new ExampleCodeSandbox();
        }
    }
}
