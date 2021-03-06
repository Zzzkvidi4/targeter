package com.targeter.server.entity;

import com.vladmihalcea.hibernate.type.array.ListArrayType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "schedule")
@TypeDef(
        name = "list-array",
        typeClass = ListArrayType.class
)
public class Schedule {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "begin_time", columnDefinition = "interval")
    private @NotNull Timestamp beginTime;

    /*@Column(name = "interval")
    private @Nullable PGInterval interval;*/

    @Type(
            type = "com.vladmihalcea.hibernate.type.array.ListArrayType",
            parameters = {
                    @Parameter(
                            name = ListArrayType.SQL_ARRAY_TYPE,
                            value = "week_day"
                    )
            }
    )
    @Column(name = "week_days", columnDefinition = "week_day[]")
    private @Nullable List<WeekDay> weekDays;

    @Type(type = "list-array")
    @Column(
            name = "week_days_time",
            columnDefinition = "time[]"
    )
    private List<Date> weekDaysTime;

    @Column(name = "cron")
    private @Nullable String cron;

    /*@OneToOne(mappedBy = "schedule")
    private @NotNull Target target;*/
}
