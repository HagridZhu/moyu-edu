package com.moyulab.cn.exam.service.impl;

import com.moyulab.cn.exam.dto.ClassDto;
import com.moyulab.cn.exam.enums.AnswerStatusEnum;
import com.moyulab.cn.exam.common.MoyuLabException;
import com.moyulab.cn.exam.dto.PaperAnswerDto;
import com.moyulab.cn.exam.entity.*;
import com.moyulab.cn.exam.enums.PaperStatusEnum;
import com.moyulab.cn.exam.mapper.*;
import com.moyulab.cn.exam.service.PaperAnswerService;
import com.moyulab.cn.exam.service.PaperService;
import com.moyulab.cn.exam.service.PaperUserService;
import com.moyulab.cn.exam.util.BeanUtil;
import com.moyulab.cn.exam.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import com.moyulab.cn.exam.util.RequestUtil;

@Service
public class PaperServiceImpl implements PaperService {

    @Autowired
    private ExamQuestionMapper examQuestionMapper;
    @Autowired
    private ExamPaperQuestionMapper examPaperQuestionMapper;
    @Autowired
    private ExamPaperMapper examPaperMapper;
    @Autowired
    private PaperAnswerService paperAnswerService;
    @Autowired
    private ExamPaperUserMapper examPaperUserMapper;
    @Autowired
    private PaperUserService paperUserService;
    @Autowired
    private ExamClassMapper examClassMapper;

    @Override
    public int createPaperQuestion(Long paperId, Integer questionNum, Integer questionScore, ExamQuestion examQuestion) {
        checkPaperId(paperId);
        Integer defaultScore = examQuestion.getDefaultScore();
        Long questionId = examQuestion.getQuestionId();
        // 1、获取试题信息，试题id为空则创建，不为空则查询数据库
        if (questionId == null) {
            if (defaultScore == null) {
                throw new MoyuLabException("分数不能为空");
            }
            if (examQuestion.getType() == null) {
                throw new MoyuLabException("type不能为空");
            }
            examQuestionMapper.insert(examQuestion);
            questionId = examQuestion.getQuestionId();
        }else{
            examQuestion = examQuestionMapper.selectById(questionId);
            if (examQuestion == null) {
                throw new MoyuLabException("通过试题id[" + questionId + "]找不到试题");
            }
        }

        Integer type = examQuestion.getType();
        // 2、题号为空，查找同种类型试题对应的题号
        if (questionNum == null) {
            questionNum = examPaperQuestionMapper.getNum(paperId, type);
        }
        // 没有同类型的试题，插入到该类型试题的第一题
        if (questionNum == null || questionNum == 0) {
            questionNum = examPaperQuestionMapper.getFristNum(paperId, type);
        }
        // 没有试题，默认第一题
        if (questionNum == null) {
            questionNum = 1;
        }

        // 3、没有设置分数，取试题的默认分数
        if (questionScore == null) {
            questionScore = examQuestion.getDefaultScore();
        }

        // 4、新增试题到试卷
        ExamPaperQuestion examPaperQuestion = new ExamPaperQuestion();
        examPaperQuestion.setQuestionScore(questionScore);
        examPaperQuestion.setQuestionNum(questionNum);
        examPaperQuestion.setPaperId(paperId);
        examPaperQuestion.setQuestionId(questionId);
        int insert = examPaperQuestionMapper.insert(examPaperQuestion);

        // 5、更新试题后面的题号 +1
        examPaperQuestionMapper.incrNum(paperId, questionNum, 1, examPaperQuestion.getPaperQuestionId());
        return insert;
    }

    @Override
    public int deletePaperQuestion(Long paperQuestionId) {
        ExamPaperQuestion examPaperQuestion = examPaperQuestionMapper.selectById(paperQuestionId);
        if (examPaperQuestion == null) {
            throw new MoyuLabException("找不到试题,paperQuestionId=" + paperQuestionId);
        }
        // 5、更新试题后面的题号 -1
        examPaperQuestionMapper.incrNum(examPaperQuestion.getPaperId(), examPaperQuestion.getQuestionNum(), -1, paperQuestionId);
        return examPaperQuestionMapper.deleteById(paperQuestionId);
    }

    @Override
    public PaperDetailVo getPaperDetailVo(Long paperId) {
        // 1、查询试卷信息
        ExamPaper examPaper = getExamPaper(paperId);
        PaperDetailVo vo = BeanUtil.copy(examPaper, new PaperDetailVo());
        // 2、查询试题列表
        List<QuestionVo> questionVoList = listQuestionVo(paperId);
        vo.setQuestionVoList(questionVoList);
        return vo;
    }

    @Override
    public PaperAnswerVo createPaperAnswer(PaperAnswerDto paperAnswerDto) {
        Long paperUserId = paperAnswerDto.getPaperUserId();
        Long paperId = paperAnswerDto.getPaperId();
        ExamPaperUser examPaperUser = null;
        if (paperUserId != null) {
            examPaperUser = this.examPaperUserMapper.selectById(paperUserId);
            if (examPaperUser == null) {
                throw new MoyuLabException("找不到该答卷,paperUserId=" + paperUserId);
            }
            if (Objects.equals(examPaperUser.getPaperStatus(), PaperStatusEnum.已考.getValue())) {
                throw new MoyuLabException("该答卷已考,paperUserId=" + paperUserId);
            }
            if (!Objects.equals(examPaperUser.getPaperId(), paperId)) {
                throw new MoyuLabException("答卷与试卷不匹配!paperUserId=" + paperUserId + ",paperId=" + paperId);
            }
        }

        List<ExamPaperAnswer> examPaperAnswerList = paperAnswerDto.getExamPaperAnswerList();
        if (CollectionUtils.isEmpty(examPaperAnswerList)) {
            throw new MoyuLabException("答案不能为空");
        }
        // 1、TODO 校验是否超时，超时之后无法提交
        ExamPaper examPaper = getExamPaper(paperId);
        // 2、查询试卷答案
        List<QuestionVo> questionVoList = listQuestionVo(paperId);
        PaperAnswerVo vo = new PaperAnswerVo();
        if (CollectionUtils.isEmpty(questionVoList)) {
            vo.setUserScore(0);
            vo.setAnswerVoList(Collections.emptyList());
            return vo;
        }
        // 3、创建答题记录
        List<ExamPaperAnswer> answerList = new ArrayList<>(questionVoList.size());
        List<AnswerVo> voList = new ArrayList<>(questionVoList.size()); //返回的vo
        List<String> correctIdList = new ArrayList<>(); // 正确题目的id
        List<String> wrongIdList = new ArrayList<>(); // 错误的题目
        Integer totalScore = 0;
        for (QuestionVo question : questionVoList) {
            Long paperQuestionId = question.getPaperQuestionId();
            ExamPaperAnswer answer = getExamPaperAnswer(examPaperAnswerList, paperQuestionId);
            if (answer == null) {
                // 找不到答题者的答案，当作空白答案
                answer = new ExamPaperAnswer();
                answer.setPaperQuestionId(paperQuestionId);
            }
            answer.setAnswerNum(question.getQuestionNum());
            // 3.1 设置相关参数，1）答题情况；2）分数
            answer.setPaperAnswerId(null);
            // 3.2 比对答案，计算得分
            if (Objects.equals(answer.getAnswer(), question.getQuestionAnswer())) {
                answer.setAnswerStatus(AnswerStatusEnum.正确.getValue());
                answer.setAnswerScore(question.getQuestionScore());
                totalScore += question.getQuestionScore(); //计算总分
                correctIdList.add(question.getQuestionId().toString());
            }else{
                answer.setAnswerStatus(AnswerStatusEnum.错误.getValue());
                wrongIdList.add(question.getQuestionId().toString());
            }
            // 3.3 存起来，再批量存到数据库
            answerList.add(answer);
        }
        // 3.4 创建/更新 答题结果，总分
        if (examPaperUser == null) {
            examPaperUser = new ExamPaperUser();
        }
        examPaperUser.setPaperId(paperId);
        examPaperUser.setPaperStatus(PaperStatusEnum.已考.getValue());
        examPaperUser.setUserScore(totalScore);
        createOrUpdate(examPaperUser);
        Long finalPaperUserId = examPaperUser.getPaperUserId();
        examPaperUser.getPaperUserId();
        // 3.5 批量保存每题的答题记录
        answerList.stream().forEach(e -> e.setPaperUserId(finalPaperUserId));
        paperAnswerService.saveBatch(answerList);
        // 3.6 批量更新题目的对错数量
        incrCorrectNum(correctIdList);
        incrWrongNum(wrongIdList);
        answerList.stream().forEach(e -> voList.add(new AnswerVo()));
        BeanUtil.copyList(answerList, voList);
        vo.setAnswerVoList(voList);
        vo.setUserScore(totalScore);
        vo.setPaperUserId(finalPaperUserId);
        vo.setPaperId(paperId);
        return vo;
    }

    @Override
    public PaperAnswerVo getPaperAnswerDetail(Long paperUserId){
        if (paperUserId == null) {
            throw new MoyuLabException("paperUserId不能为空");
        }
        // 查询答卷信息
        ExamPaperUser examPaperUser = this.examPaperUserMapper.selectById(paperUserId);
        if (examPaperUser == null) {
            throw new MoyuLabException("找不到该答卷,paperUserId=" + paperUserId);
        }
        // 查询试卷信息
        ExamPaper examPaper = this.examPaperMapper.selectById(examPaperUser.getPaperId());
        if (examPaper == null) {
            throw new MoyuLabException("找不到该试卷,paperId=" + examPaperUser.getPaperId());
        }
        // 查询答题列表
        List<AnswerVo> answerVoList = paperAnswerService.listAnswerVo(paperUserId);
        PaperAnswerVo paperAnswerVo = new PaperAnswerVo();
        BeanUtil.copy(examPaperUser, paperAnswerVo);
        BeanUtil.copy(examPaper, paperAnswerVo);
        paperAnswerVo.setAnswerVoList(answerVoList);
        return paperAnswerVo;
    }

    @Override
    public int createPaperUserByClassId(Long paperId, Long classId) {
        if (paperId == null) {
            throw new MoyuLabException("paperId不能为空");
        }
        if (classId == null) {
            throw new MoyuLabException("classId不能为空");
        }
        ExamPaper examPaper = this.examPaperMapper.selectById(paperId);
        if (examPaper == null) {
            throw new MoyuLabException("找不到该试卷,paperId=" + paperId);
        }

        ClassDto classDto = new ClassDto();
        classDto.setClassId(classId);
        classDto.setCreateBy(RequestUtil.getUserId()); //查自己的班级
        List<ClassUserVo> list = examClassMapper.listClassUserVo(classDto);
        if (CollectionUtils.isEmpty(list)) {
            throw new MoyuLabException("该班级下没有学生,classId=" + classId);
        }
        List<ExamPaperUser> paperUserList = new ArrayList<>(list.size());
        list.stream().forEach(e -> {
            ExamPaperUser examPaperUser = new ExamPaperUser();
            examPaperUser.setPaperStatus(PaperStatusEnum.待考.getValue());
            examPaperUser.setPaperId(paperId);
            examPaperUser.setUserId(e.getUserId());
            paperUserList.add(examPaperUser);
        });
        paperUserService.saveBatch(paperUserList);
        return paperUserList.size();
    }

    private List<QuestionVo> listQuestionVo(Long paperId){
        return examPaperQuestionMapper.listQuestionVo(paperId);
    }{}

    private void checkPaperId(Long paperId){
        if (paperId == null) {
            throw new MoyuLabException("paperId不能为空");
        }
    }

    private ExamPaper getExamPaper(Long paperId){
        checkPaperId(paperId);
        ExamPaper examPaper = examPaperMapper.selectById(paperId);
        if (examPaper == null) {
            throw new MoyuLabException("通过paperId[" + paperId + "]找不到试卷");
        }
        return examPaper;
    }

    private ExamPaperAnswer getExamPaperAnswer(List<ExamPaperAnswer> list, Long paperQuestionId){
        for (ExamPaperAnswer answer : list) {
            if (Objects.equals(answer.getPaperQuestionId(), paperQuestionId)) {
                return answer;
            }
        }
        return null;
    }

    private void incrCorrectNum(List<String> idList){
        if (CollectionUtils.isEmpty(idList)) {
            return ;
        }
        examQuestionMapper.incrCorrectNum(String.join(",", idList));
    }

    private void incrWrongNum(List<String> idList){
        if (CollectionUtils.isEmpty(idList)) {
            return ;
        }
        examQuestionMapper.incrWrongNum(String.join(",", idList));
    }

    private int createOrUpdate(ExamPaperUser examPaperUser){
        if (examPaperUser.getPaperUserId() == null) {
            return examPaperUserMapper.insert(examPaperUser);
        }
        return examPaperUserMapper.updateById(examPaperUser);
    }

}
