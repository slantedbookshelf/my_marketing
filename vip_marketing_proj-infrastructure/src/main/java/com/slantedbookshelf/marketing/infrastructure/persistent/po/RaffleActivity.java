package com.slantedbookshelf.marketing.infrastructure.persistent.po;

import com.slantedbookshelf.marketing.domain.strategy.model.valobj.ActivityStateVO;
import lombok.Data;

import java.util.Date;

@Data
public class RaffleActivity {
    private Long activityId;
    private String activityName;
    private String activityDesc;
    private Date beginDateTime;
    private Date endDateTime;
    private Long activityCountId;
    private Long strategyId;
    private String state;
}
