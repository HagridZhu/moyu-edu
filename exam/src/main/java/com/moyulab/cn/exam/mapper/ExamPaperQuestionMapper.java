package com.moyulab.cn.exam.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.moyulab.cn.exam.entity.ExamPaperQuestion;
import com.moyulab.cn.exam.vo.QuestionVo;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface ExamPaperQuestionMapper extends BaseMapper<ExamPaperQuestion> {

    /**
     * 查询试卷的某种类型试题的结尾题号+1
     * @param paperId 试卷id
     * @param type 试题类型
     */
    @Select("select t.question_num + 1 from exam_paper_question t JOIN  exam_question q USING(question_id) WHERE t.paper_id = #{paperId} and q.type = #{type} ORDER BY t.question_num DESC limit 1")
    Integer getNum(Long paperId, Integer type);

    /**
     * 获取试卷的某种类型试题的第一个题号
     * @param paperId 试卷id
     * @param type 试题类型
     */
    @Select("select ifNull(t.question_num, 0) + 1 from exam_paper_question t JOIN  exam_question q USING(question_id) WHERE t.paper_id = #{paperId} and q.type < #{type} ORDER BY t.question_num DESC limit 1")
    Integer getFristNum(Long paperId, Integer type);



    /**
     * 更新指定题号后的试题的题号+1
     * @param paperId 试卷id
     * @param questionNum 插入的题号位置
     * @param paperQuestionId 插入的试题id
     */
    @Update("UPDATE exam_paper_question SET question_num = question_num + #{value} WHERE paper_id = #{paperId} AND question_num >= #{questionNum} AND paper_question_id <> #{paperQuestionId}")
    int incrNum(Long paperId, Integer questionNum, Integer value, Long paperQuestionId);

    @Select("select pq.paper_question_id, pq.question_num,pq.question_score,q.* from exam_paper_question pq JOIN exam_question q USING(question_id) WHERE pq.paper_id = #{paperId} ORDER BY pq.question_num")
    List<QuestionVo> listQuestionVo(Long paperId);

    @Select("select pq.id AS paperQuestionId, pq.question_num,pq.question_score,q.* from exam_paper_question pq JOIN exam_question q USING(question_id) WHERE pq.paper_id = #{paperId} ORDER BY pq.question_num")
    List<QuestionVo> listQuestionVoByPaperUserId(Long paperUserId);



}
