package com.t0r.kestreloj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.t0r.kestreloj.model.entity.Question;
import com.t0r.kestreloj.service.QuestionService;
import com.t0r.kestreloj.mapper.QuestionMapper;
import org.springframework.stereotype.Service;

/**
* @author Towerrrr
* @description 针对表【question(题目)】的数据库操作Service实现
* @createDate 2025-03-18 19:51:38
*/
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question>
    implements QuestionService{

}




