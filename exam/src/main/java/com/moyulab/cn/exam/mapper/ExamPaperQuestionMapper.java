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
    @Select("select t.num + 1 from exam_paper_question t JOIN  exam_question q USING(question_id) WHERE t.paper_id = #{paperId} and q.type = #{type} ORDER BY t.num DESC limit 1")
    Integer getNum(Long paperId, Integer type);

    /**
     * 更新指定题号后的试题的题号+1
     * @param paperId 试卷id
     * @param num 插入的题号位置
     * @param id 插入的试题id
     */
    @Update("UPDATE exam_paper_question SET num = num + 1 WHERE paper_id = #{paperId} AND num >= #{num} AND id <> #{id}")
    int incrNum(Long paperId, Integer num, Long id);

    @Select("select pq.id AS paperQuestionId, pq.num,pq.score,q.* from exam_paper_question pq JOIN exam_question q USING(question_id) WHERE pq.paper_id = #{paperId} ORDER BY pq.num")
    List<QuestionVo> listQuestionVo(Long paperId);




}
