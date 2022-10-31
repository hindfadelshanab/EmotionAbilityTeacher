package com.nuwa.robot.r2022.emotionalability.listener;

import com.nuwa.robot.r2022.emotionalability.model.Level;
import com.nuwa.robot.r2022.emotionalability.model.Phase;
import com.nuwa.robot.r2022.emotionalability.model.QuestionContent;

public interface OnQuestionContentListener {
    public  void  OnQuestionContent(int position , Level level);
}
